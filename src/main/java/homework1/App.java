package homework1;

import java.util.ArrayList;
import java.util.Collections;

/**
 * �������� ������� �1 ���� Java 3
 *
 * @author Miroshnichenko Igor
 * @version 3.1
 * created on 2022-05-10
 */
public class App {
    public static void main(String[] args) {

        System.out.println("������� 1:");
        System.out.println("������ � ����������� ��������� �����");
        Element[] mas = WorkingWithArrays.generateMassive(5);
        WorkingWithArrays.printMas(mas);
        if (WorkingWithArrays.move(mas, 2, 3) == null) {
            System.out.println("�������� ��� �������� ������� ������� �� �������, ���������� ������� ����� �� ������ �������");
        }
        ;
        WorkingWithArrays.printMas(mas);
        System.out.println();

        System.out.println("������ String");
        String[] masString = new String[]{"1", "2", "3", "4", "5", "6"};
        WorkingWithArrays.printMas(masString);
        if (WorkingWithArrays.move(masString, 0, 4) == null) {
            System.out.println("�������� ��� �������� ������� ������� �� �������, ���������� ������� ����� �� ������ �������");
        }
        ;
        WorkingWithArrays.printMas(masString);
        System.out.println();

        System.out.println("������ String - � �������. P.S.: ��������.");
        String[] masStringError = new String[]{"1", "2", "3", "4", "5", "6"};
        WorkingWithArrays.printMas(masStringError);
        if (WorkingWithArrays.move(masStringError, 0, 10) == null) {
            System.out.println("�������� ��� �������� ������� ������� �� �������, ���������� ������� ����� �� ������ �������");
        }
        ;
        WorkingWithArrays.printMas(masStringError);
        System.out.println();

        System.out.println("������� 2:");
        System.out.println("�������������� ������� � ����������� ��������� �����");
        System.out.println("�������� ������:");
        WorkingWithArrays.printMas(mas);
        ArrayList<Element> masArrayList = WorkingWithArrays.toArrayList(mas);
        System.out.println("ArrayList:");
        if (masArrayList == null) {
            System.out.println("�������������� � ArrayList ������ ��������!");
        } else {
            WorkingWithArrays.printArrayList(masArrayList);
        }
        System.out.println();

        System.out.println("�������������� ������� String");
        System.out.println("�������� ������:");
        WorkingWithArrays.printMas(masString);
        ArrayList<String> masStringArrayList = WorkingWithArrays.toArrayList(masString);
        System.out.println("ArrayList:");
        if (masStringArrayList == null) {
            System.out.println("�������������� � ArrayList ������ ��������!");
        } else {
            WorkingWithArrays.printArrayList(masStringArrayList);
        }
        System.out.println();

        System.out.println("�������������� ������� String - � �������. P.S.: ��������.");
        System.out.println("��������� �� �������������� � ArrayList NULL");
        ArrayList<String> masStringArrayListError = WorkingWithArrays.toArrayList(null);
        System.out.println("ArrayList:");
        if (masStringArrayListError == null) {
            System.out.println("�������������� � ArrayList ������ ��������!");
        } else {
            WorkingWithArrays.printArrayList(masStringArrayListError);
        }
        System.out.println();


        //������� 3
        Box<Apple> boxApple = Box.create();
        boxApple.put(new Apple("������ � ����", 1f));
        boxApple.put(new Apple("������ � ������", 2f));
        double weightBoxApple = boxApple.getWeight();
        System.out.println("��� ������� � �������� - " + weightBoxApple);

        Box<Orange> boxOrange = Box.create();
        boxOrange.put(new Orange("�������� � ����", 1f));
        boxOrange.put(new Orange("�������� � ������", 1f));
        double weightBoxOrange = boxOrange.getWeight();
        System.out.println("��� ������� � ����������� - " + weightBoxOrange);

        System.out.println("����� �� ������� � �������� � �������� � ����������� - "+boxApple.compareTo(boxOrange));


        Box<Orange> boxOrange2 = Box.create();
        boxOrange2.put(new Orange("�������� � ����", 0.5f));
        boxOrange2.put(new Orange("�������� � ������", 0.5f));
        takeAllFruits(boxOrange.getFruits(),boxOrange2.getFruits());
        System.out.println("������� � ��������");
        boxApple.printElements();
        System.out.println("������� ������ � �����������");
        boxOrange.printElements();
        System.out.println("������� ������ � �����������");
        boxOrange2.printElements();
    }


    private static <G> void takeAllFruits(ArrayList<? super G> dst, ArrayList<? extends G> src) {
        for (int i = 0; src.size()!=0;) {
            dst.add(src.get(i));
            src.remove(i);
        }
    }


}
