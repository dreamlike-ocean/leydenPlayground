package io.github.dreamlike;


import java.lang.invoke.ConstantCallSite;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.reflect.Constructor;


public class DynamicEntryLeydenCase implements LeydenCase {

    private static final String Dynamic_Test_Case_Class_name = "DynamicEntryLeydenCaseTestCase";

    private static final String Condy_Method_Name = "condyInvoke";

    private static final String INDY_MTD = "indyInvoke";


    public static ConstantCallSite indyFactory(MethodHandles.Lookup lookup, String name, MethodType type) throws NoSuchFieldException, IllegalAccessException {
        var leibnizCase = new LeibnizCase();
        for (int i = 0; i < 100_000; i++) {
            leibnizCase.singeCal();
        }
        return new ConstantCallSite(MethodHandles.constant(Object.class, "12123"));
    }

    public static Object condyFactory(MethodHandles.Lookup lookup, String name, Class type) throws NoSuchFieldException, IllegalAccessException {
        var leibnizCase = new LeibnizCase();
        for (int i = 0; i < 100_000; i++) {
            leibnizCase.singeCal();
        }
        return "12123";
    }

    @Override
    public void run() {
        try {
            Class<?> clazz = Class.forName(Dynamic_Test_Case_Class_name);
            Constructor<?> constructor = clazz.getConstructor();
            Object o = constructor.newInstance();
            System.out.println(clazz.getMethod(Condy_Method_Name).invoke(o));
            System.out.println(clazz.getMethod(INDY_MTD).invoke(o));
        } catch (Throwable throwable) {
            throwable.printStackTrace();
            throw new RuntimeException(throwable);
        }
    }


}
