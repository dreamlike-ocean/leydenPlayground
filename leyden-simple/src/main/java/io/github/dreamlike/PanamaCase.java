package io.github.dreamlike;

import java.lang.foreign.FunctionDescriptor;
import java.lang.foreign.Linker;
import java.lang.foreign.MemoryLayout;
import java.lang.foreign.ValueLayout;
import java.lang.invoke.MethodHandle;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class PanamaCase implements LeydenCase {

    private static final List<FunctionDescriptor> functionDescriptors = allType();

    @Override
    public void run() {
        for (FunctionDescriptor descriptor : functionDescriptors) {
            Linker.nativeLinker()
                    .downcallHandle(
                            descriptor,
                            Linker.Option.critical(true)
                    );
            Linker.nativeLinker()
                    .downcallHandle(
                            descriptor,
                            Linker.Option.critical(false)
                    );
            Linker.nativeLinker()
                    .downcallHandle(
                            descriptor
                    );
        }

    }

    private static List<FunctionDescriptor> allType(){
        List<ValueLayout> allType = List.of(ValueLayout.JAVA_INT, ValueLayout.JAVA_BOOLEAN, ValueLayout.JAVA_BYTE, ValueLayout.JAVA_SHORT, ValueLayout.JAVA_CHAR, ValueLayout.JAVA_FLOAT, ValueLayout.JAVA_DOUBLE, ValueLayout.ADDRESS);

        ArrayList<FunctionDescriptor> res = new ArrayList<>();

        for (ValueLayout valueLayout : allType) {
            for (int i = 0; i < 10; i++) {
                MemoryLayout[] memoryLayouts = new MemoryLayout[i + 1];
                Arrays.fill(memoryLayouts, valueLayout);
                res.add(
                        FunctionDescriptor.ofVoid(memoryLayouts)
                );
            }

        }

        return res;
    }


}
