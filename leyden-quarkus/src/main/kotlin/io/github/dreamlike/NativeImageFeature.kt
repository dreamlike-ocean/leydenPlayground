package io.github.dreamlike

import io.github.dreamlike.entity.Person
import io.netty.channel.socket.nio.NioDatagramChannel
import org.graalvm.nativeimage.hosted.Feature
import org.graalvm.nativeimage.hosted.Feature.BeforeAnalysisAccess
import org.graalvm.nativeimage.hosted.Feature.DuringSetupAccess
import org.graalvm.nativeimage.hosted.RuntimeClassInitialization
import org.graalvm.nativeimage.hosted.RuntimeForeignAccess
import org.graalvm.nativeimage.hosted.RuntimeReflection
import java.lang.foreign.FunctionDescriptor
import java.lang.foreign.Linker
import java.lang.foreign.ValueLayout
import java.net.Inet6Address
import kotlin.reflect.jvm.internal.ReflectionFactoryImpl

class NativeImageFeature :Feature {
    override fun duringSetup(access: DuringSetupAccess?) {
        RuntimeForeignAccess
            .registerForDowncall(
                FunctionDescriptor.of(ValueLayout.JAVA_INT),
                Linker.Option.critical(false)
            )
    }

    override fun beforeAnalysis(access: BeforeAnalysisAccess?) {
    }

    private fun registerSingleClass(c: Class<*>) {
        RuntimeReflection.register(c)
        RuntimeReflection.registerAllDeclaredConstructors(c)
        for (constructor in c.declaredConstructors) {
            RuntimeReflection.register(constructor)
        }
        for (field in c.declaredFields) {
            RuntimeReflection.register(field)
            RuntimeReflection.registerFieldLookup(c, field.name)
        }
        for (method in c.methods) {
            RuntimeReflection.registerMethodLookup(c, method.name, *method.parameterTypes)
            RuntimeReflection.register(method)
        }
    }
}