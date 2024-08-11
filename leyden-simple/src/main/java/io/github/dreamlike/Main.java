package io.github.dreamlike;

import java.io.IOException;
import java.lang.foreign.FunctionDescriptor;
import java.lang.foreign.Linker;
import java.lang.foreign.MemoryLayout;
import java.lang.foreign.ValueLayout;
import java.lang.invoke.MethodHandle;
import java.util.*;

@EnableGenerate
public class Main {
    public static void main(String[] args) throws IOException {
        new EnvCase().timerRun();
        new PanamaCase().timerRun();
        new DynamicEntryLeydenCase().timerRun();
        new HelloWorldCase().timerRun();
        new ClassforNameCase().timerRun();
        new LeibnizCase().timerRun();
        new HeavyClassInitCase().timerRun();
        new JdkProxyLeydenCasae().timerRun();
        new JdkCaptureProxyLeydenCase().timerRun();
        new ALotLambdaLeydenCase().timerRun();
        new GroovyLeydenCase().timerRun();
    }

}
