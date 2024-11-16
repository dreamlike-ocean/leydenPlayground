package io.github.dreamlike;

import java.util.List;
import java.util.stream.Collectors;

public class HelloWorldCase implements LeydenCase{
    @Override
    public void run() {
        var words = List.of("hello", "fuzzy", "world");
        var greeting = words.stream()
                .filter(w -> !w.contains("z"))
                .collect(Collectors.joining(", "));
        System.out.println(greeting);  // hello, world
    }
}
