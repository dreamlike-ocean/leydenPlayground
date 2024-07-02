package io.github.dreamlike.beans

import io.github.dreamlike.WebVerticle
import io.github.dreamlike.beans.generated.Service1
import io.github.dreamlike.beans.generated.Service2

import io.quarkus.runtime.StartupEvent
import io.vertx.core.Vertx
import jakarta.enterprise.event.Observes
import jakarta.enterprise.inject.Instance
import jakarta.inject.Singleton

@Singleton
class BeanStartup {

    fun init(@Observes e: StartupEvent, service1: Service1, service2: Instance<Service2>) {
        service1.run()
        service2.get().run()
    }

}