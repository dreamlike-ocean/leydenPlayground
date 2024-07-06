package io.github.dreamlike;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.concurrent.Callable;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

public class JdkCaptureProxyLeydenCase implements LeydenCase {
    @Override
    public void run() {
        Owner owner = new Owner();
        InvocationHandler proxyHandle = (Object proxy, Method method, Object[] args) -> {
            Class<?> returnType = method.getReturnType();
            if (returnType == int.class) {
                return 1;
            }

            if (returnType == boolean.class) {
                return true;
            }
            return method.invoke(owner, args);
        };
        Object o = Proxy.newProxyInstance(
                Thread.currentThread().getContextClassLoader(),
                new Class[]{Runnable.class, Callable.class, Supplier.class},
                proxyHandle
        );
        ((Runnable) o).run();

        try {
            ((Callable<String>) o).call();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        ((Supplier<String>) o).get();



    }


    static class Owner implements
            Runnable, Callable<String>, Supplier<String>,
            Consumer<String>, Function<String, String>, Predicate<String> {

        @Override
        public void run() {

        }

        @Override
        public String call() throws Exception {
            return "";
        }

        @Override
        public void accept(String s) {

        }

        @Override
        public String apply(String s) {
            return "";
        }

        @Override
        public boolean test(String s) {
            return false;
        }

        @Override
        public String get() {
            return "";
        }
    }
}
