package com.company;

import javax.sound.midi.Patch;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {





//        System.out.println("Введите путь до файла");
//        Scanner scanner = new Scanner(System.in);
//        String fileName = scanner.next();
        String fileName = "C:\\Users\\sss\\IdeaProjects\\Computational_Math_Lab_1\\src\\com\\company\\4x5.txt";
        if (verifyMatrixFile(fileName)) {
            System.out.println("Матрица была успешно проверена на целостность");
        }
        double[][] matrix = loadMatrix(fileName);
        System.out.println("Матрица была загружена");
        printMatrix(matrix);
        calcRectangleMatrix(matrix);
        printMatrix(matrix);
        calcGays(matrix).forEach(System.out::println);
    }

    public static void howTo(){
        System.out.println("Вы хотите загрузить(1)/написать(2)/для выхода(exit) ?");
        Scanner scanner = new Scanner(System.in);
        boolean exit = false;
        while (!exit) {
            String answer = scanner.next().toLowerCase();
            if (answer.equals("1")){
                System.out.println("Введите путь до файла");
                String fileName = scanner.next();
            } else if (answer.equals("2")){

            }else if (answer.equals("exit")) {
                exit = true;
            }
            else
                System.out.println("Вы ввели неверный ответ, попробуйте еще раз:");
        }
    }


    public static ArrayList<Double> calcGays(double[][] matrix){
        int iter = 1;
        double ans;
        ArrayList<Double> list = new ArrayList<>(matrix.length);
        for (int l = matrix.length - 1; l >= 0; l--) {
            iter++;
            ans = calcLine(matrix[l], iter );
            list.add(ans);
            for (int c = matrix.length - iter; c >= 0; c--) {
                matrix[c][l] *= ans;
            }
        }
        return list;
    }

    public static double calcLine(double[] line, int countNums) {
        //функция вычисляет значение X для треугольной матрицы, если это крайний элемент строки != 0 то делит ответ на него
        //Иначе вычитает из ответа
        double ans = line[line.length - 1];
        for (int i = line.length - 2; i >= line.length - countNums ; i--) {
            if (i - (line.length - countNums) == 0) {
                ans /= line[i];
            } else {
                ans -= line[i];
            }
        }
        return ans;
    }

    public static void calcRectangleMatrix(double[][] matrix) {
        double divider;
        int line;

        for (int column = 0; column < matrix.length; column++) {
            //column - указывает на элемент с помощью которого вычисляется делидель (0,0 ; 1,1; 2,2;)
            line = column + 1;
            for (; line < matrix.length; line++) {
                //line - считает строку по которой мы проходимся
                //divider получаем путем деления line-column-элемента на column-column
                divider = matrix[line][column] / matrix[column][column];
                for (int j = column; j < matrix[column].length; j++) {
                    //получаем нужное значение элемента путем вычитания из значения элемента произведения элементов на главной строке умноженной на делитель
                    matrix[line][j] = matrix[line][j] - ((matrix[column][j]) * divider);
                }
            }
        }
    }

    public static void printMatrix(double[][] matrix) {
        System.out.println("---------------------------------------------------------");
        for (int i = 0; i < matrix.length; i++) {
            System.out.println();
            for (int j = 0; j < matrix[i].length; j++) {
                String result = String.format("%.2f", matrix[i][j]);
                System.out.print("[" + i + "][" + j +"]= " + result + "\t\t");
            }
        }
        System.out.println();
    }


    public static boolean verifyMatrixFile(String fileName) {
        Path path = Paths.get(fileName);
        BufferedReader reader;
        try {
            reader = Files.newBufferedReader(path);
        } catch (IOException e) {
            System.out.println("Не получается прочитать файл, возможно нет прав или нет файла.");
            return false;
        }
        try {
            int numbersInLine = -1;
            int numbersInCurrentLine = 0;
            while (reader.ready()) {
                String line = reader.readLine().trim();
                char[] byteBuff = line.toCharArray();
                for (char c : byteBuff) {
                    if (c == ' ') {
                        numbersInCurrentLine++;
                    }
                }
                if (numbersInLine == -1) {
                    numbersInLine = numbersInCurrentLine;
                    numbersInCurrentLine = 0;
                } else if (numbersInCurrentLine == numbersInLine) {
                    numbersInCurrentLine = 0;
                } else {
                    System.out.println("Файл содержит разное количество элементов в строках матрицы");
                    return false;
                }
            }
            reader.close();
            return true;
        } catch (IOException e) {
            System.out.println("Проблемы с чтение из файла");
            return false;
        }
    }

    public static double[][] loadMatrix(String fileName) {
        Path path = Paths.get(fileName);
        BufferedReader reader = null;
        try {
            reader = Files.newBufferedReader(path);
        } catch (IOException e) {
            System.out.println("Не получается прочитать файл, возможно нет прав или нет файла.");
        }
        int columnCount = 1;
        int lineCount = 0;
        try {
            while (reader.ready()) {
                String line = reader.readLine().trim();
                lineCount++;
                if (columnCount == 1) {
                    char[] byteBuff = line.toCharArray();
                    for (char c : byteBuff) {
                        if (c == ' ') {
                            columnCount++;
                        }
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        double[][] matrix = new double[lineCount][columnCount];
        try {
            reader = Files.newBufferedReader(path);
            for (int i = 0; i < lineCount; i++) {
                String line = reader.readLine().trim();
                String[] strings = line.split(" ");
                for (int j = 0; j < columnCount; j++) {
                    matrix[i][j] = Double.parseDouble(strings[j].trim());
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return matrix;
    }
}
