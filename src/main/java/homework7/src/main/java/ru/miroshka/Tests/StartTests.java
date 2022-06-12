package ru.miroshka.Tests;

import ru.miroshka.Annotations.AfterSuite;
import ru.miroshka.Annotations.BeforeSuite;
import ru.miroshka.Annotations.Test;
import ru.miroshka.CalcMethodsInteger;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.LinkedList;

public class StartTests {
    public static void start(Class testClass) {
        System.out.println("Сначало выведем в консоль в каком порядке встретились нам методы (имя и приоритет):");
        Method[] methods = testClass.getDeclaredMethods();
        LinkedList<Method> array = new LinkedList<>();
        Method tailMethod = null;
        Method headMethod = null;
        for (Method o : methods) {
            if (o.getAnnotation(Test.class) != null) {
                System.out.println("Имя метода - " + o.getName());
                System.out.println("Приоритет - " + o.getAnnotation(Test.class).priority());
                System.out.println();
                if (array.size() == 0) {
                    array.add(o);
                    continue;
                }
                int n = -1;
                for (int i = 0; i < array.size(); i++) {
                    if (o.getAnnotation(Test.class).priority() >= array.get(i).getAnnotation(Test.class).priority()) {
                        n = i;
                        break;
                    }
                }
                if (n != -1) {
                    array.add(n, o);
                } else {
                    array.add(o);
                }
            }
            if (o.getAnnotation(BeforeSuite.class) != null) {
                System.out.println("Имя метода - " + o.getName());
                System.out.println();
                if (headMethod != null) {
                    throw new RuntimeException("В тестовом классе более одного метода с аннотацией- @BeforeSuite");
                }
                headMethod = o;
            }
            if (o.getAnnotation(AfterSuite.class) != null) {
                System.out.println("Имя метода - " + o.getName());
                System.out.println();
                if (tailMethod != null) {
                    throw new RuntimeException("В тестовом классе более одного метода с аннотацией- @AfterSuite");
                }
                tailMethod = o;
            }
        }

        if (headMethod != null) {
            headMethod.setAccessible(true);
            array.add(0, headMethod);
        }
        if (tailMethod != null) {
            tailMethod.setAccessible(true);
            array.add(tailMethod);
        }

        System.out.println();
        System.out.println("Выполним методы согласно приоритету:");

        for (Method method : array) {
            try {
                System.out.println("Имя метода - " + method.getName());
                if (method.getAnnotation(Test.class) != null) {
                    System.out.println("Приоритет - " + method.getAnnotation(Test.class).priority());
                    System.out.println("Успешно ли прошел тест?");
                    System.out.println("Ответ: " + method.invoke((testClass.getConstructor()).newInstance()));
                }else{
                    method.invoke((testClass.getConstructor()).newInstance());
                }

                System.out.println();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            }
        }


    }
}
