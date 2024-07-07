package io.github.dreamlike;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.StringJoiner;
import java.util.function.IntFunction;
import java.util.function.Supplier;

@EnableGenerate
public class Main {
    public static void main(String[] args) throws IOException {
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
