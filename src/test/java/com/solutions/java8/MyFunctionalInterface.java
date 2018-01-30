package com.solutions.java8;

import java.util.function.Supplier;

@FunctionalInterface
public interface MyFunctionalInterface {
    public void someMethod();

    default String defaultMethod() {
        return "default interface";
    }

    public static MyFunctionalInterface create( Supplier<MyFunctionalInterface> supplier ) {
        return supplier.get();
    }
}
