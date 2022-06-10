package ru.miroshka.Tests;

import ru.miroshka.Annotations.AfterSuite;
import ru.miroshka.Annotations.BeforeSuite;
import ru.miroshka.Annotations.Test;
import ru.miroshka.CalcMultiplicationInteger;


public class TestMultiplicationInteger {

    @Test(priority = 3)
    public boolean testSummPositive() {
        return (new CalcMultiplicationInteger()).multiplication(1, 2) == 2 ? true : false;
    }

    @Test(priority = 9)
    public boolean testSummNegative() {
        return (new CalcMultiplicationInteger()).multiplication(-1, -2) == 2 ? true : false;
    }


    @Test(priority = 8)
    public boolean testSummMixed() {
        return (new CalcMultiplicationInteger()).multiplication(-1, 1) == -1 ? true : false;
    }

    @AfterSuite
    private void printTerminalStart(){
        System.out.println("Тест начался!");
    }

    @AfterSuite
    private void printTerminalFinish(){
        System.out.println("Тест закончился!");
    }
}
