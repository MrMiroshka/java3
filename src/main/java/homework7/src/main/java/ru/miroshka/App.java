package ru.miroshka;

import ru.miroshka.Tests.StartTests;
import ru.miroshka.Tests.TestMultiplicationInteger;
import ru.miroshka.Tests.TestSummInteger;

public class App {
    public static void main(String[] args) {
        System.out.println("�������� ���� ����� ����� - " + TestSummInteger.class.getName());
        StartTests.start(TestSummInteger.class);
        System.out.println("�������� ���� ����� ����� - " + TestMultiplicationInteger.class.getName());
        StartTests.start(TestMultiplicationInteger.class);
    }
}
