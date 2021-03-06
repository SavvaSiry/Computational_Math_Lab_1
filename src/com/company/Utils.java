package com.company;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;


/*
Тут собраны методы для получения матриц. С консоли, из файла, генерация.
 */
public class Utils {
    private static Scanner scanner = new Scanner(System.in);
    // Метод для получения матрицы с клавиатуры
    public static double[][] createMatrixFromKeyBoard(){
        try {
            System.out.println("Введите размерность матрицы");
            String buffer = scanner.nextLine();
            buffer = buffer.trim();
            int size = Integer.parseInt(buffer);
            if (size > 20 || size <= 0) {
                throw new Exception();
            }
            System.out.println("Введите строки матрицы");
            double [][] matrix = new double[size][size+1];
            String [][] arr = new String[size][size+1];
            for (int i = 0; i < size;i++) {
                buffer = scanner.nextLine();
                arr[i] = buffer.trim().split(" ");
            }
            for (int i = 0; i < size;i++){
                for (int j = 0; j < size+1;j++) {
                    matrix[i][j] = Double.parseDouble(arr[i][j].trim());
                }
            }
            return matrix;
        } catch (Exception e) {
            System.out.println("Введена неверная размерность");
        }
        return null;
    }

    public static double[][] createRandomMatrix(int size) {
        try{
            if (size > 20 || size <= 0) {
                throw new Exception();
            }
            double[][] matrix = new double[size][size + 1];
            for (int i = 0; i < matrix.length; i++) {
                for (int j = 0; j < matrix.length + 1; j++) {
                    matrix[i][j] = Math.random() * 50 - 25;
                }
            }
            return matrix;
        } catch (Exception e) {
            System.out.println("Введена неверная размерность");
        }
        return null;
    }
}