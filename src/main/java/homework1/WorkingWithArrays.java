package homework1;

import java.util.ArrayList;
import java.util.Arrays;

public class WorkingWithArrays {
    /**
     * √енерируем массив дл€ задани€ заданной длинны
     *
     * @param count
     * @return
     */
    public static Element[] generateMassive(int count) {
        Element[] masElements = new Element[count];
        for (int i = 0; i < count; i++) {
            masElements[i] = new Element.Builder().name("Ёлемент - " + i).build();
        }
        return masElements;
    }

    /**
     * ћен€ет дваелемента местами.
     *
     * @param mas массив заданного типа, в котором мен€ютс€ дваэлемента местами
     * @param src индекс первого елемента массива
     * @param dst индекс второго элемента массива
     * @param <T> «адаетс€ тим массива
     * @return ¬озвращает измененый массив в случае успешного изменени€, иначе null.
     */
    public static <T> T[] move(T[] mas, int src, int dst) {
        if (mas!=null && (src >= 0 && src < mas.length) && (dst >= 0 && dst < mas.length)) {
            T elementMasTemp = mas[dst];
            mas[dst] = mas[src];
            mas[src] = elementMasTemp;
            return mas;
        } else {
            return null;
        }
    }

    /**
     * ѕреобразует массив в ArrayList
     *
     * @param mas исходный массив
     * @param <T> тип массива
     * @return возвращает ArraList если преобразование прошло удачно, иначе null
     */
    public static <T> ArrayList<T> toArrayList(T[] mas) {
        if (mas != null) {
            ArrayList<T> arrayList = new ArrayList<T>(mas.length);
            for (T element : mas) {
                arrayList.add(element);
            }
            return arrayList;
        } else {
            return null;
        }
    }

    /**
     * ¬ыводит вконсоль елементы массива
     * @param mas массив
     * @param <T> тип элементов массива
     */
    public static <T> void printMas(T[] mas) {
        if (mas==null){
            System.out.println("ћассив = NULL");
            return;
        }
        System.out.print("{ ");
        for (int i = 0; i < mas.length; i++) {
            System.out.print(mas[i]);
            if (i != mas.length - 1) {
                System.out.print(" , ");
            }
        }
        System.out.print(" }");
        System.out.println();
    }

    /**
     * ¬ыводит вконсоль елементы ArrayList
     * @param arrayList массив данных ввиде ArrayList
     * @param <T> тип элементов ArrayList
     */
    public static <T> void printArrayList(ArrayList<T> arrayList) {
        if (arrayList==null){
            System.out.println("ArrayList = NULL");
            return;
        }
        System.out.print("{ ");
        for (int i = 0; i < arrayList.size(); i++) {
            System.out.print(arrayList.get(i));
            if (i != arrayList.size() - 1) {
                System.out.print(" , ");
            }
        }
        System.out.print(" }");
        System.out.println();
    }
}
