package io.github.dreamlike

import io.github.dreamlike.entity.Person
import io.quarkus.redis.datasource.ReactiveRedisDataSource
import io.quarkus.redis.datasource.value.ReactiveValueCommands
import io.quarkus.vertx.core.runtime.context.VertxContextSafetyToggle
import io.smallrye.common.vertx.ContextLocals
import io.smallrye.mutiny.coroutines.awaitSuspending
import io.vertx.core.AbstractVerticle
import io.vertx.core.Future
import io.vertx.core.Vertx
import io.vertx.core.spi.context.storage.ContextLocal
import io.vertx.ext.web.Route
import io.vertx.ext.web.Router
import io.vertx.ext.web.RoutingContext
import io.vertx.ext.web.handler.BodyHandler
import jakarta.enterprise.context.Dependent
import jakarta.enterprise.inject.spi.CDI
import kotlinx.coroutines.*
import java.util.concurrent.Executor
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException

@Dependent
class WebVerticle(
    private val ds: ReactiveRedisDataSource
) : AbstractVerticle() {

    private val valueCommands: ReactiveValueCommands<String, String> = ds.value(String::class.java, String::class.java)


    override fun start() {
        val router = Router.router(vertx)
        val dnsClient = vertx.createDnsClient()
        CoroutineScope(vertx.dispatcher()).launch {

            router.get("/delay")
                .coroutineHandler {
                    delay(1000)
                    it.end("Hello World! ${Thread.currentThread()}")
                }


            router.get("/param")
                .coroutineHandler {
                    val name = it.queryParam("name")?.firstOrNull() ?: "io/github/dreamlike"
                    it.json(
                        Person(name, 123)
                    )
                }
            router.get("/dns")
                .coroutineHandler {
                    val result = dnsClient.resolveA("www.baidu.com").coAwait()
                    it.end("DNS: $result")
                }
            router.post("/post")
                .handler(BodyHandler.create())
                .coroutineHandler {
                    val (name, age) = it.body().asPojo(Person::class.java)
                    it.json(
                        mapOf(
                            "message" to "Hello $name, you are $age years old.",
                            "person_info" to mapOf(
                                "name" to name,
                                "age" to age
                            )
                        )
                    )
                }

            router.get("/redis")
                .coroutineHandler {
                    valueCommands.set("123", "io/github/dreamlike").awaitSuspending()
                    val value = valueCommands.get("123").awaitSuspending()
                    it.end("redis: $value")
                }

            router.get("/service")
                .coroutineHandler {
                    val s = it.queryParam("id").firstOrNull()
                    if (s == null) {
                        it.end("null")
                        return@coroutineHandler
                    }

                    var clazz = Class.forName("io.github.dreamlike.beans.generated.Service$s")
                    var any = CDI.current().select(clazz).get()

                    val method = clazz.getMethod("run")
                    method.invoke(any)
                    it.end("success")
                }


            router.errorHandler(500) {
                it.failure().printStackTrace()
                it.end("Internal Server Error")
            }
            try {
                val httpServer = vertx.createHttpServer()
                    .requestHandler(router)
                    .listen(4399).coAwait()
                println("Server started at http://localhost:4399 $httpServer")
            } catch (t: Throwable) {
                t.printStackTrace()
            }
        }
    }

    fun Route.coroutineHandler(handle: suspend (RoutingContext) -> Unit) {

        handler { ctx ->
            CoroutineScope(ctx.vertx().dispatcher()).launch {
                handle(ctx)
            }
        }
    }


    suspend fun <T> Future<T>.coAwait() = suspendCancellableCoroutine<T> { c ->
        this.onSuccess { c.resume(it) }
        this.onFailure { c.resumeWithException(it) }
    }
}

fun Vertx.dispatcher() = Executor { r -> this.orCreateContext.runOnContext { r.run() } }.asCoroutineDispatcher()

