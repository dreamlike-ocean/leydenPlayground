package io.github.dreamlike;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;

public class JdkProxyLeydenCasae implements LeydenCase {
    @Override
    public void run() {
        InvocationHandler proxyHandle = (Object proxy, Method method, Object[] args) -> {
            Class<?> returnType = method.getReturnType();
            if (returnType == int.class) {
                return 1;
            }

            if (returnType == boolean.class) {
                return true;
            }
            return null;
        };
        Object o = Proxy.newProxyInstance(
                Thread.currentThread().getContextClassLoader(),
                new Class[]{Function.class, Predicate.class, Consumer.class},
                proxyHandle);

        ((Function<String, String>) o).apply("!23");

        ((Predicate<String>) o).test("!@3");


        ((Consumer<String>) o).accept("");
    }
}
