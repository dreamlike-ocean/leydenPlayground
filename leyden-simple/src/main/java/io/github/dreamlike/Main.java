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

//var rootPath = Path.of("/home/dreamlike/jdks/jdk/src/java.base/share/classes");
//        ArrayList<String> classNames = new ArrayList<>();
//        ArrayDeque<Path> paths = new ArrayDeque<>();
//        paths.add(rootPath);
//        while (!paths.isEmpty()) {
//            var path = paths.pop();
//            var file = path.toFile();
//            if (file.isDirectory()) {
//                var files = file.listFiles();
//                for (var f : files) {
//                    paths.add(f.toPath());
//                }
//            } else {
//                var name = file.getName();
//                if (name.endsWith(".java") && !name.contains("module-info") && !name.contains("package-info")) {
//                    var className = path.toString().substring(rootPath.toString().length() + 1);
//                    className = className.substring(0, className.length() - 5).replace('/', '.');
//                    classNames.add(className);
//                }
//            }
//        }
//        String all = String.join("\n", classNames);
//        Files.writeString(Path.of("all.txt"), all);