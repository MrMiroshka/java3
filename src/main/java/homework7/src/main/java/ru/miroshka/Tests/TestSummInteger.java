package ru.miroshka.Tests;

import ru.miroshka.Annotations.AfterSuite;
import ru.miroshka.Annotations.BeforeSuite;
import ru.miroshka.Annotations.Test;
import ru.miroshka.CalcMethodsInteger;
import ru.miroshka.Calculator;

//public class TestSummInteger implements TestSumm<Integer> {
public class TestSummInteger {


    @Test(priority = 3)
    public boolean testSummPositive() {
        return (new CalcMethodsInteger()).summ(1, 2) == 3 ? true : false;
    }

    @Test(priority = 9)
    public boolean testSummNegative() {
        return (new CalcMethodsInteger()).summ(-1, -2) == -3 ? true : false;
    }


    @BeforeSuite
    private void printTerminalStart(){
        System.out.println("Тест начался!");
    }

    @AfterSuite
    private void printTerminalFinish(){
        System.out.println("Тест закончился!");
    }

    @Test(priority = 8)
    public boolean testSummMixed() {
        return (new CalcMethodsInteger()).summ(-1, 1) == 0 ? true : false;
    }
}
