package io.github.dreamlike

import io.github.dreamlike.ffi.PanamaCase
import io.smallrye.common.annotation.Blocking
import io.smallrye.common.annotation.NonBlocking
import io.smallrye.common.annotation.RunOnVirtualThread
import jakarta.ws.rs.GET
import jakarta.ws.rs.Path
import jakarta.ws.rs.Produces
import jakarta.ws.rs.core.MediaType
import org.jboss.logmanager.Logger
import java.util.logging.Level

@Path("/controller")
class ExampleResource(val ffi: PanamaCase) {
    companion object {
        val logger = Logger.getLogger(ExampleResource::class.java.toString())
    }

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    fun hello() = "Hello from Quarkus REST ${Thread.currentThread()}"

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    @Path("/kt")
    suspend fun hell1o() = "Hello from Quarkus Suspend REST ${Thread.currentThread()}"

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    @Path("/vt")
    @RunOnVirtualThread
    fun hell2o() = "Hello from Quarkus VT REST ${Thread.currentThread()}"

    @GET
    @Path("/pageSize")
    suspend fun getPageSize() = "${Thread.currentThread()} + ${ffi.pageSize}"

    @GET
    @Path("/error")
    @NonBlocking
    fun error(): String {
        logger.log(Level.INFO,"error!")
        RuntimeException().printStackTrace()
        return "error ${Thread.currentThread()}"
    }
}