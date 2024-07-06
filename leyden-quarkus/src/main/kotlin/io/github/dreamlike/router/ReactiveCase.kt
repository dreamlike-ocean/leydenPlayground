package io.github.dreamlike.router

import io.github.dreamlike.dispatcher
import io.quarkus.vertx.web.Route
import io.quarkus.vertx.web.RouteBase
import io.smallrye.common.annotation.RunOnVirtualThread
import io.smallrye.mutiny.Uni
import io.smallrye.mutiny.coroutines.uni
import io.smallrye.mutiny.uni
import io.vertx.ext.web.RoutingContext
import jakarta.enterprise.context.ApplicationScoped
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import java.util.UUID

@ApplicationScoped
@RouteBase(path = "/router")
class ReactiveCase {

    @Route(methods = [Route.HttpMethod.GET], path = "/hello")
    fun hello(rc: RoutingContext): Uni<String> {
        return uni(CoroutineScope(rc.vertx().dispatcher())) {
            println("${Thread.currentThread()} hello")
            delay(100L)
            UUID.randomUUID().toString() + Thread.currentThread()
        }
    }

    @Route(methods = [Route.HttpMethod.GET], path = "/block", type = Route.HandlerType.BLOCKING)
    fun hello2(rc: RoutingContext): String {
        println("${Thread.currentThread()} hello2")
        return UUID.randomUUID().toString() + Thread.currentThread()
    }

    @Route(methods = [Route.HttpMethod.GET], path = "/vt")
    @RunOnVirtualThread
    fun hello3(rc: RoutingContext): String {
        println("${Thread.currentThread()} vt")
        Thread.sleep(1000)
        return UUID.randomUUID().toString() + Thread.currentThread()
    }




}