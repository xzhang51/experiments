package com.solutions.java8;

import junit.framework.TestCase;

import java.io.File;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Java8Test extends TestCase {

    public void test1() {
        List<String> array = Arrays.asList( "a", "f", "d" );
        array.forEach(s->System.out.println(s));
        array.sort( (e1, e2 ) -> e1.compareTo( e2 ));
        array.forEach(s->System.out.println(s));
    }

    public void test2() {
        MyFunctionalInterface testObj = MyFunctionalInterface.create(MyFunctionalInterfaceImpl::new);
        System.out.println(testObj.defaultMethod());
        assertEquals("default interface", testObj.defaultMethod());

        testObj = new AnotherImpl();
        System.out.println(testObj.defaultMethod());
        assertEquals("override default", testObj.defaultMethod());
    }

    public void testStream() {
        List<String> list = Arrays.asList("1", "2", "3", "30");

        list.forEach(s->System.out.println(s));
        int sum1 = list.stream()
                .mapToInt(s -> Integer.parseInt(s))
                .filter(s -> s>1).sum();
        assertEquals(35, sum1);

        assertTrue(36==list.stream().parallel().map(s -> Integer.parseInt(s)).reduce(0, Integer::sum));
    }

    public void testFileStream() {
        final Path filePath = new File("/Users/xifengzhang/IdeaProjects/webappexample/src/test/java/com/solutions/java8/AnotherImpl.java").toPath();
        try {
            Stream<String> lines = Files.lines(filePath, StandardCharsets.UTF_8);
            List<String> list = lines.collect(Collectors.toList());
            System.out.println(list.size());
            assertEquals(12, list.size());
        } catch (Exception e) {;}
    }
}
