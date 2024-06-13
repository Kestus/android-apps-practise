package com.example.a09_matrixaddition;

import java.util.Arrays;

public class Addition {
    public static void main(String[] args) {
        int[][] matrix1 = {
                {1, 2, 1},
                {2, 1, 3},
                {3, 2, 1},
        };

        int[][] matrix2 = {
                {2, 1, 2},
                {3, 2, 1},
                {1, 3, 2},
        };

        int[][] result = new int[matrix1.length][matrix1[0].length];

        for (int i = 0; i < matrix1.length; i++) {
            for (int j = 0; j < matrix1[i].length; j++) {
                result[i][j] = matrix1[i][j] + matrix2[i][j];
            }
        }

        for (int[] row : result) {
            System.out.println(Arrays.toString(row));
        }

    }
}