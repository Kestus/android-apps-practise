package com.example.a2nums;

import java.util.Scanner;

public class exercise {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter first number:");
        int num1 = scanner.nextInt();
        System.out.println("Enter second number:");
        int num2 = scanner.nextInt();
        float div = (float) num1 / num2;
        int rem = num1 % num2;
        System.out.println("Division = " + div);
        System.out.println("Remainder = " + rem);
    }
}