package homework1;

import java.util.ArrayList;

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

    }


}
