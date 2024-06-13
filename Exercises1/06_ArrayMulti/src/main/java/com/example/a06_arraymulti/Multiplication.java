package com.example.a06_arraymulti;

import java.util.Arrays;

public class Multiplication {
    public static void main(String[] args) {
        int[] array1 = {1, -2, 3, -4};
        int[] array2 = {3, -2, 1,  5};
        int[] result = new int[array1.length];
        for (int i = 0; i < array1.length; i++) {
            result[i] = array1[i] * array2[i];
        }
        System.out.println("Multiplication result: " + Arrays.toString(result));
    }
}