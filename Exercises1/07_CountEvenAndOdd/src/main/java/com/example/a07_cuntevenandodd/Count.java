package com.example.a07_cuntevenandodd;

public class Count {
    public static void main(String[] args) {
        int[] numbers = {5, 7, 2, 4, 9};
        int even = 0;
        int odd = 0;
        for (int number : numbers) {
            if (number % 2 == 0) {
                even++;
            } else {
                odd++;
            }
        }
        System.out.println("Number of evens: " + even);
        System.out.println("Number of  odds: " + odd);
    }
}