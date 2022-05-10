package homework1;

import java.util.ArrayList;

/**
 * Домашнее задание №1 курс Java 3
 *
 * @author Miroshnichenko Igor
 * @version 3.1
 * created on 2022-05-10
 */
public class App {
    public static void main(String[] args) {

        System.out.println("Задание 1:");
        System.out.println("Массив с собственным ссылочным типом");
        Element[] mas = WorkingWithArrays.generateMassive(5);
        WorkingWithArrays.printMas(mas);
        if (WorkingWithArrays.move(mas, 2, 3) == null) {
            System.out.println("Поменять два элемента массива местами не удалось, задаваемые индексы вышли за размер массива");
        }
        ;
        WorkingWithArrays.printMas(mas);
        System.out.println();

        System.out.println("Массив String");
        String[] masString = new String[]{"1", "2", "3", "4", "5", "6"};
        WorkingWithArrays.printMas(masString);
        if (WorkingWithArrays.move(masString, 0, 4) == null) {
            System.out.println("Поменять два элемента массива местами не удалось, задаваемые индексы вышли за размер массива");
        }
        ;
        WorkingWithArrays.printMas(masString);
        System.out.println();

        System.out.println("Массив String - с ОШИБКОЙ. P.S.: проверка.");
        String[] masStringError = new String[]{"1", "2", "3", "4", "5", "6"};
        WorkingWithArrays.printMas(masStringError);
        if (WorkingWithArrays.move(masStringError, 0, 10) == null) {
            System.out.println("Поменять два элемента массива местами не удалось, задаваемые индексы вышли за размер массива");
        }
        ;
        WorkingWithArrays.printMas(masStringError);
        System.out.println();

        System.out.println("Задание 2:");
        System.out.println("Преобразование массива с собственным ссылочным типом");
        System.out.println("Исходный массив:");
        WorkingWithArrays.printMas(mas);
        ArrayList<Element> masArrayList = WorkingWithArrays.toArrayList(mas);
        System.out.println("ArrayList:");
        if (masArrayList == null) {
            System.out.println("Преобразование в ArrayList прошло неудачно!");
        } else {
            WorkingWithArrays.printArrayList(masArrayList);
        }
        System.out.println();

        System.out.println("Преобразование массива String");
        System.out.println("Исходный массив:");
        WorkingWithArrays.printMas(masString);
        ArrayList<String> masStringArrayList = WorkingWithArrays.toArrayList(masString);
        System.out.println("ArrayList:");
        if (masStringArrayList == null) {
            System.out.println("Преобразование в ArrayList прошло неудачно!");
        } else {
            WorkingWithArrays.printArrayList(masStringArrayList);
        }
        System.out.println();

        System.out.println("Преобразование массива String - с ОШИБКОЙ. P.S.: проверка.");
        System.out.println("Передадим на преобразование в ArrayList NULL");
        ArrayList<String> masStringArrayListError = WorkingWithArrays.toArrayList(null);
        System.out.println("ArrayList:");
        if (masStringArrayListError == null) {
            System.out.println("Преобразование в ArrayList прошло неудачно!");
        } else {
            WorkingWithArrays.printArrayList(masStringArrayListError);
        }
        System.out.println();

    }


}
