package io.github.dreamlike;


public interface LeydenCase extends Runnable {
    default void timerRun() {
        long start = System.currentTimeMillis();
        run();
        long end = System.currentTimeMillis();
        System.out.println(this.getClass() + " Time: " + (end - start) + "ms");
    }
}
