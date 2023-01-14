package com.interviews;

import org.junit.Test;
import org.reflections.Reflections;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class ReflectionTest {

    /**
     * https://stackoverflow.com/questions/205573/at-runtime-find-all-classes-in-a-java-application-that-extend-a-base-class
     */
    @Test
    public void findAllClassesExtendClassTest() {
//        String s = Paths.get(".").toAbsolutePath().toString();
        Reflections reflections = new Reflections("com.interviews");
        Set<Class<? extends MyInterface>> classes = reflections.getSubTypesOf(MyInterface.class);
    }

    @Test
    public void findAllClassesExtendClassTest2() throws Exception {
        Reflections reflections = new Reflections("java.util");
        Set<Class<? extends List>> classes = reflections.getSubTypesOf(java.util.List.class);
        System.out.println(classes);
        for (Class<? extends List> aClass : classes) {
            System.out.println(aClass.getName());
            if (aClass == ArrayList.class) {
                List list = aClass.getDeclaredConstructor().newInstance();
                list.add("test");
                System.out.println(list.getClass().getName() + ": " + list.size());
            }
        }
    }


    private interface MyInterface {
        void foo();
    }

    private class MyclassA implements MyInterface {
        @Override
        public void foo() {
            System.out.println(this.getClass());
        }
    }

    private class MyclassC extends MyclassA {
        @Override
        public void foo() {
            System.out.println(this.getClass());
        }
    }


    private class MyclassB implements MyInterface {
        @Override
        public void foo() {
            System.out.println(this.getClass());
        }
    }
}
