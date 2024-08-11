package io.github.dreamlike;

public interface BaseService {

    default void run() {
        System.out.println("Hello world!");
    }
}
