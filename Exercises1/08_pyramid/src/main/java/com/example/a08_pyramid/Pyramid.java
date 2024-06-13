package com.example.a08_pyramid;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Pyramid {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter number of rows: ");
        int rows = scanner.nextInt();

        for (int i = 1; i <= rows; i++) {
            String number = String.valueOf(i);
            List<String> listOfNumbers = new ArrayList<>();
            for (int j = 0; j < i; j++) {
                listOfNumbers.add(number);
            }
            String formattedNumbers = String.join(" ", listOfNumbers);

            String line = " ".repeat(rows - i) + formattedNumbers;
            System.out.println(line);
        }
    }
}