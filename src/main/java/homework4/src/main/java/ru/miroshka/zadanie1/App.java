package ru.miroshka.zadanie1;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

//Создать три потока, каждый из которых выводит определенную букву
// (A, B и C) 5 раз (порядок – ABСABСABС). Используйте wait/notify/notifyAll.
public class App {

    private static final Object lock1 = new Object();
    private static final Object lock2 = new Object();
    private volatile char currentLetter = 'A';

    public static void main(String[] args) {
        ExecutorService service = Executors.newFixedThreadPool(3);
        App zadanie1 = new App();
        int count = 5;

        service.execute(() -> {
            zadanie1.printA(count);
        });


        service.execute(() -> {
            zadanie1.printB(count);
        });

        service.execute(() -> {
            zadanie1.printC(count);
        });


        service.shutdown();

        //решение задачи через список задач

        //остановим основной поток
        synchronized (lock2) {
            try {
                lock2.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println();
        System.out.println("Решение задачи через список задач");
        ExecutorService service2 = Executors.newFixedThreadPool(3);
        List<Callable<Boolean>> tasks = new ArrayList<>();
        tasks.add(() -> {
            zadanie1.printA(count);
            return true;
        });

        tasks.add(() -> {
            zadanie1.printB(count);
            return true;
        });

        tasks.add(() -> {
            zadanie1.printC(count);
            return true;
        });

        try {
             service2.invokeAll(tasks);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        //т.к. метод invokeAll блокирующий - значит нет опасения, что наши потоки еще выполняются
        service2.shutdown();
    }


    public void printA(Integer count) {
        synchronized (lock1) {
            try {
                for (int i = 0; i < count; i++) {
                    while (currentLetter != 'A') {
                        lock1.wait();
                    }
                    System.out.print("A");
                    currentLetter = 'B';
                    lock1.notifyAll();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }


    public void printB(Integer count) {
        synchronized (lock1) {
            try {
                for (int i = 0; i < count; i++) {
                    while (currentLetter != 'B') {
                        lock1.wait();
                    }
                    System.out.print("B");
                    currentLetter = 'C';
                    lock1.notifyAll();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }


    public void printC(Integer count) {
        synchronized (lock1) {
            try {
                for (int i = 0; i < count; i++) {
                    while (currentLetter != 'C') {
                        lock1.wait();
                    }
                    System.out.print("C");
                    currentLetter = 'A';
                    lock1.notifyAll();
                }
                synchronized (lock2) {
                    lock2.notify();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
