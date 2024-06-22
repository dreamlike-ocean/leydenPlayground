package io.github.dreamlike;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class ClassforNameCase implements LeydenCase {
    @Override
    public void run() {
        try {
            int success = 0;
            int fail = 0;
            List<String> strings = Files.readAllLines(Path.of("all.txt"));
            for (String string : strings) {
                try {
                    Class.forName(string);
                    success++;
                } catch (Throwable e) {
                    fail++;
                    continue;
                }
            }
            System.out.println("load success "+ success);
            System.out.println("load fail "+ fail);
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }
}
