package io.github.dreamlike

import io.quarkus.runtime.StartupEvent
import io.vertx.core.Vertx
import jakarta.enterprise.context.ApplicationScoped
import jakarta.enterprise.event.Observes
import jakarta.enterprise.inject.Instance


@ApplicationScoped
class VertxStartup {


    fun init(@Observes e: StartupEvent, vertx: Vertx, verticleGetter: Instance<WebVerticle>) {
       for (i in 1..5) {
           val webVerticle = verticleGetter.get()
           vertx.deployVerticle(webVerticle).onFailure { it.printStackTrace() }
       }
    }
}



