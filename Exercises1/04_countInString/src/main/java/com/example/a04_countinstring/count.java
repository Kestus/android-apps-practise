package com.example.a04_countinstring;

import java.util.Scanner;

public class count {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter a string: ");
        String input = scanner.nextLine();
        int letters = 0;
        int spaces = 0;
        int numbers = 0;
        int other = 0;
        for (int i = 0; i < input.length(); i++) {
//            char c = input.charAt(i);
//            if (Character.isLetter(c)) {
//                letters++;
//            } else if (Character.isWhitespace(c)) {
//                spaces++;
//            } else if (Character.isDigit(c)) {
//                numbers++;
//            } else {
//                other++;
//            }
            switch (Character.getType(input.charAt(i))) {
                case Character.LOWERCASE_LETTER, Character.UPPERCASE_LETTER -> letters++;
                case Character.DIRECTIONALITY_WHITESPACE -> spaces++;
                case Character.DECIMAL_DIGIT_NUMBER -> numbers++;
                default -> other++;
            }
        }
        System.out.println("String contains of;");
        System.out.println("Letters: " + letters);
        System.out.println("Numbers: " + numbers);
        System.out.println("Spaces:  " + spaces);
        System.out.println("Other:   " + other);
    }
}