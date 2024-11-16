package io.github.dreamlike;

import java.io.IOException;

@EnableGenerate
public class Main {
    public static void main(String[] args) throws IOException {

        String caseName = args[0];

        switch (caseName) {
            case "env":
                new EnvCase().timerRun();
                break;
            case "panama":
                new PanamaCase().timerRun();
                break;
            case "dynamic":
                new DynamicEntryLeydenCase().timerRun();
                break;
            case "hello":
                new HelloWorldCase().timerRun();
                break;
            case "reflection":
                new ClassforNameCase().timerRun();
                break;
            case "leibniz":
                new LeibnizCase().timerRun();
                break;
            case "javaProxy":
                new JdkProxyLeydenCasae().timerRun();
                break;
            case "jcapture":
                new JdkCaptureProxyLeydenCase().timerRun();
                break;
            case "lambda":
                new ALotLambdaLeydenCase().timerRun();
                break;
            case "heavy":
                new HeavyClassInitCase().timerRun();
                break;
        }
    }

}
