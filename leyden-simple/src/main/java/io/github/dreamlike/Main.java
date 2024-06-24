package io.github.dreamlike;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.StringJoiner;

public class Main {
    public static void main(String[] args) throws IOException {
        new HelloWorldCase().timerRun();
       new ClassforNameCase().timerRun();
       new LeibnizCase().timerRun();
       new HeavyClassInitCase();
    }
}
