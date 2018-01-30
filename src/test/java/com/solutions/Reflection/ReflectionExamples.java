package com.solutions.Reflection;

import junit.framework.TestCase;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Arrays;

public class ReflectionExamples extends TestCase {

    public void testReflectionMethods() {
        final Method[] methods = String.class.getMethods();
        System.out.println("Methods:");
        System.out.println("--------------");
        Arrays.stream(methods).forEach(m -> System.out.println(m.getName()));
    }

    public void testReflectionFields() {
        final Field[] fields = String.class.getFields();
        System.out.println("\nFields:");
        System.out.println("--------------");
        Arrays.stream(fields).forEach(f -> System.out.println(f.getName()));
    }

    public void testMethodInvoke() throws Exception {
        final Method method = String.class.getMethod("length");
        String str = "1234567890";
        System.out.println("1234567890 has length of " + (int) method.invoke(str));

    }
}
