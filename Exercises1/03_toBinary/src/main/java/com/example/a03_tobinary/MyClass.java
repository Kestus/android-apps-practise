package com.example.a03_tobinary;

import java.util.ArrayList;
import java.util.Scanner;

public class MyClass {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter decimal number: ");
        int quotient = scanner.nextInt();
        ArrayList<String> binStrings = new ArrayList<>();

        while (quotient != 0) {
            int remainder = quotient % 2;
            binStrings.add(String.valueOf(remainder));
            quotient = quotient / 2;
        }

        String binary = String.join("", binStrings);
        System.out.println("Binary: " + binary);
    }
}