package ru.miroshka.metods;

public class WorkWithArray {

    public static int[] takeArrayAfter4(int[] array) {

        int index = -1;

        for (int i = 0; i < array.length; i++) {
            if (array[i] == 4) {
                index = i;
            }
        }

        if (index == -1) {
            throw new RuntimeException();
        } else if (index == array.length - 1) {
            return new int[0];
        }

        int start = index + 1;
        int[] tempArray = new int[array.length - start];
        System.arraycopy(array, start, tempArray, 0, array.length - start);
        return tempArray;
    }

    public static Boolean arrayContain(int[] array) {
        boolean flag = false;
        for (int i = 0; i < array.length; i++) {
            if (array[i] == 4 || array[i] == 1) {
                flag = true;
                break;
            }
        }
        return flag;
    }
}
