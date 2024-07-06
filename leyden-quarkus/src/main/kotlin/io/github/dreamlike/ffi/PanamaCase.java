package io.github.dreamlike.ffi;

import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.ApplicationScoped;

import java.lang.foreign.FunctionDescriptor;
import java.lang.foreign.Linker;
import java.lang.foreign.MemorySegment;
import java.lang.foreign.ValueLayout;
import java.lang.invoke.MethodHandle;

@ApplicationScoped
public class PanamaCase {

    private static final MethodHandle pageSizeMH;

    static {
        MemorySegment fp = Linker.nativeLinker()
                .defaultLookup().find("getpagesize").get();
        pageSizeMH = Linker.nativeLinker()
                .downcallHandle(
                        fp, FunctionDescriptor.of(ValueLayout.JAVA_INT),
                        Linker.Option.critical(false)
                );
    }


    public int getPageSize() {
        try {
            return (int) pageSizeMH.invokeExact();
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }
}
