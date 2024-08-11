package io.github.dreamlike;

public class EnvCase implements LeydenCase{

    private static final long time = System.currentTimeMillis();

    private static final String env = System.getenv("leydenTest");


    @Override
    public void run() {
        System.out.println(String.format("env: %s, time: %d", env, time));
    }
}
