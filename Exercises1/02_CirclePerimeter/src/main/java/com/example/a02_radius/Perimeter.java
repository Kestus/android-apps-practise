package com.example.a02_radius;

import java.util.Scanner;

public class Perimeter {
    public static void main(String[] args) {
        System.out.println("Enter radius of the circle: ");
        Scanner scanner = new Scanner(System.in);
        int radius = scanner.nextInt();
        double perimeter = 2 * Math.PI * radius;
        double area = Math.PI * radius * radius;
        System.out.println("Perimeter = " + perimeter);
        System.out.println("Area = " + area);
    }
}