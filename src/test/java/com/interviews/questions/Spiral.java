package com.interviews.questions;

import org.junit.Test;

public class Spiral {


    @Test
    public void printSpiral() {
        int[][] matrix = new int[][]{
                {1, 2, 3, 4, 5, 6, 7, 8, 9},
                {10, 11, 12, 13, 14, 15, 16, 17, 18},
                {19, 20, 21, 22, 23, 24, 25, 26, 27},
                {28, 29, 30, 31, 32, 33, 34, 35, 36},
                {37, 38, 39, 40, 41, 42, 43, 44, 45},
                {46, 47, 48, 49, 50, 51, 52, 53, 54},
                {55, 56, 57, 58, 58, 60, 61, 62, 63},

//                {1, 2, 3, 4, 5},
//                {10, 11, 12, 13, 14},
//                {19, 20, 21, 22, 23},
//                {28, 29, 30, 31, 32},
//                {37, 38, 39, 40, 41},
        };

        int row = matrix.length;
        int col = matrix[0].length;
        printMatrixAsSpiralRecursive(matrix, row, col, 0);
    }

    private void printMatrixAsSpiralRecursive(int[][] matrix, int rowNum, int colNum, int startIndex) {
        printLeftToRight(matrix[startIndex], colNum, startIndex);
        System.out.println();

        printUpToDown(matrix, rowNum, colNum, startIndex);
        System.out.println();

        printRightToLeft(matrix[rowNum - 1], colNum, startIndex);
        System.out.println();

        for (int i = 1; i < rowNum - 1 - startIndex; i++)
            System.out.print(matrix[rowNum - 1 - i][startIndex]+" ");
        System.out.println();

        startIndex += 1;
        if (startIndex < rowNum)
            printMatrixAsSpiralRecursive(matrix, rowNum - 1, colNum - 1, startIndex);
    }

    private void printRightToLeft(int[] matrix, int col, int startIndex) {
        for (int i = col - 1; i > startIndex; i--)
            System.out.print(matrix[i - 1]+" ");
    }

    private void printUpToDown(int[][] matrix, int row, int col, int startIndex) {
        for (int i = startIndex + 1; i < row; i++)
            System.out.print(matrix[i][col - 1]+" ");
    }

    private void printLeftToRight(int[] matrix, int col, int startIndex) {
        for (int i = startIndex; i < col; i++)
            System.out.print(matrix[i]+" ");
    }

}
