package io.github.dreamlike;

public interface BaseService {

    default void run() {
        new Exception().printStackTrace();
        System.out.println("Hello world!");
    }
}
