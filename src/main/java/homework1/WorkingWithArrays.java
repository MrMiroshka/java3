package homework1;

import java.util.ArrayList;
import java.util.Arrays;

public class WorkingWithArrays {
    /**
     * ���������� ������ ��� ������� �������� ������
     *
     * @param count
     * @return
     */
    public static Element[] generateMassive(int count) {
        Element[] masElements = new Element[count];
        for (int i = 0; i < count; i++) {
            masElements[i] = new Element.Builder().name("������� - " + i).build();
        }
        return masElements;
    }

    /**
     * ������ ����������� �������.
     *
     * @param mas ������ ��������� ����, � ������� �������� ����������� �������
     * @param src ������ ������� �������� �������
     * @param dst ������ ������� �������� �������
     * @param <T> �������� ��� �������
     * @return ���������� ��������� ������ � ������ ��������� ���������, ����� null.
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
     * ����������� ������ � ArrayList
     *
     * @param mas �������� ������
     * @param <T> ��� �������
     * @return ���������� ArraList ���� �������������� ������ ������, ����� null
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
     * ������� �������� �������� �������
     * @param mas ������
     * @param <T> ��� ��������� �������
     */
    public static <T> void printMas(T[] mas) {
        if (mas==null){
            System.out.println("������ = NULL");
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
     * ������� �������� �������� ArrayList
     * @param arrayList ������ ������ ����� ArrayList
     * @param <T> ��� ��������� ArrayList
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
