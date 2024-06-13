package com.example.a10_calcaverage;

import java.util.Arrays;

public class Calculate {
    public static void main(String[] args) {
        int[] arr = new int[] {1, 2, 3, 4, 5, 6, 7, 8, 9};
        int sum = 0;
        for (int num : arr) {
            sum += num;
        }
        float average = (float) sum / arr.length;
        System.out.println("With array: " + Arrays.toString(arr));
        System.out.println("Average is: " + average);
    }
}