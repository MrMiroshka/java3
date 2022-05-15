package homework1;

import java.util.ArrayList;
import java.util.Collections;

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


        //Задание 3
        Box<Apple> boxApple = Box.create();
        boxApple.put(new Apple("Яблоко с дачи", 1f));
        boxApple.put(new Apple("Яблоко с Турции", 2f));
        double weightBoxApple = boxApple.getWeight();
        System.out.println("Вес коробки с яблоками - " + weightBoxApple);

        Box<Orange> boxOrange = Box.create();
        boxOrange.put(new Orange("Апельсин с дачи", 1f));
        boxOrange.put(new Orange("Апельсин с Турции", 1f));
        double weightBoxOrange = boxOrange.getWeight();
        System.out.println("Вес коробки с апельсинами - " + weightBoxOrange);

        System.out.println("Равна ли коробка с яблоками с коробкой с апельсинами - "+boxApple.compareTo(boxOrange));


        Box<Orange> boxOrange2 = Box.create();
        boxOrange2.put(new Orange("Апельсин с дачи", 0.5f));
        boxOrange2.put(new Orange("Апельсин с Турции", 0.5f));
        takeAllFruits(boxOrange.getFruits(),boxOrange2.getFruits());
        System.out.println("Коробка с яблоками");
        boxApple.printElements();
        System.out.println("Коробка первая с апельсинами");
        boxOrange.printElements();
        System.out.println("Коробка вторая с апельсинами");
        boxOrange2.printElements();
    }


    private static <G> void takeAllFruits(ArrayList<? super G> dst, ArrayList<? extends G> src) {
        for (int i = 0; src.size()!=0;) {
            dst.add(src.get(i));
            src.remove(i);
        }
    }


}
