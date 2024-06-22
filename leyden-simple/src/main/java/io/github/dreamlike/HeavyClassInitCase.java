package io.github.dreamlike;

public class HeavyClassInitCase implements LeydenCase{
    public static final double pi;
    @Override
    public void run() {

    }

    static {
        long start = System.currentTimeMillis();
        LeibnizCase leibnizCase = new LeibnizCase();
        for (int i = 0; i < 100_000; i++) {
            leibnizCase.singeCal();
        }
        pi = 0.0;
        long end = System.currentTimeMillis();
        System.out.println("HeavyClassInitCase init Time: " + (end - start) + "ms");
    }
}
