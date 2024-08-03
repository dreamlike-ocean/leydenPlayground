package io.github.dreamlike.beans;

import io.github.dreamlike.EnableGenerate;
import jakarta.interceptor.AroundInvoke;
import jakarta.interceptor.Interceptor;
import jakarta.interceptor.InvocationContext;

@Enhance
@Interceptor
@EnableGenerate
public class EnhanceInterceptor {
    @AroundInvoke
    Object logInvocation(InvocationContext context) throws Exception {
        System.out.println("before");
        Object ret = context.proceed();
        System.out.println("after");
        // ...log after
        return ret;
    }
}
