package com.company;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        double[][] matrix = new double[0][];
        String fileName = null;
        System.out.println("Вы хотите загрузить(1)/написать(2)/для выхода(exit) ?");
        Scanner scanner = new Scanner(System.in);
        boolean exit = false;
        while (!exit) {
            String answer = scanner.next().toLowerCase();
            if (answer.equals("1")){
                //получение матрицы из файла
                System.out.println("Введите путь до файла");
                fileName = scanner.next();
                //верификация на целостность данных
                if (verifyMatrixFile(fileName)) {
                    System.out.println("Матрица была успешно проверена на целостность");
                }
                matrix = loadMatrix(fileName);
                exit = true;
            } else if (answer.equals("2")){
                // Получение матрицы с клавиатуры
                matrix = Utils.createMatrixFromKeyBoard();
                exit = true;
            }else if (answer.equals("exit")) {
                exit = true;
            }
            else
                System.out.println("Вы ввели неверный ответ, попробуйте еще раз:");
        }

        System.out.println();
        printMatrix(matrix, "Введенная матрица: ");
        double[][] saveMatrix = cloneMatrix(matrix);
//        printMatrix(saveMatrix, "SAVE MATRIX");
        double[][] determinantMatrix = squareMatrix(matrix);
        System.out.println();
        printMatrix(determinantMatrix,"Квадратная матрица: ");
        calcRectangleMatrix(matrix);
        System.out.println();
        printMatrix(matrix, "Треугольная матрица: ");
        rectangleMatrixDeterminate(matrix);
        ArrayList<Double> saveAnswers = calcGays(matrix);
        printAnswerMatrix(saveAnswers);
        divergence(saveMatrix, saveAnswers);
    }

    public static double[][] cloneMatrix(double[][] oldMatrix){
        double[][] newMatrix = new double[oldMatrix.length][oldMatrix[0].length];
        for (int i = 0; i < newMatrix.length; i++) {
            for (int j = 0; j < newMatrix[0].length; j++) {
                newMatrix[i][j] = oldMatrix[i][j];
            }
        }
        return newMatrix;
    }

    private static void divergence(double[][] saveMatrix, ArrayList<Double> arrayList) {
        double ans = 0;
        System.out.println("---------------------------------------------------------");
        System.out.println("Вектор невязки: ");
        System.out.println("---------------------------------------------------------");
        for (int i = 0; i < saveMatrix.length; i++) {
            for (int j = 0; j < saveMatrix[0].length - 1; j++) {
                ans += saveMatrix[i][j] * arrayList.get(j);
            }
            System.out.println("["+i+"]= " + (ans - saveMatrix[i][saveMatrix[0].length - 1]));
            ans = 0;
        }
    }

    private static void rectangleMatrixDeterminate(double[][] matrix) {
        int det = 1;
        for (int i = 0; i < matrix.length; i++) {
            det *= matrix[i][i];
        }
        System.out.println("---------------------------------------------------------");
        System.out.println("Детерминант: " + det);
    }

    public static void printAnswerMatrix(ArrayList<Double> list){
        System.out.println("---------------------------------------------------------");
        System.out.println("Cтолбец ответов :");
        System.out.println("---------------------------------------------------------");
        var ref = new Object() {
            int i = 0;
        };
        list.forEach(x -> {
            System.out.println("["+ ref.i +"][0] = " + x);
            ref.i++;
        });
    }

    public static double[][] squareMatrix(double[][] matrix){
        double[][] newMatrix = new double[matrix.length][matrix[0].length - 1];
        for (int i = 0; i < matrix[0].length - 1; i++) {
            for (int j = 0; j < matrix.length; j++) {
                newMatrix[i][j] = matrix[i][j];
            }
        }
        return newMatrix;
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
                System.out.println("Вводим матрицу с консоли");
                // Получение матрицы с клавиатуры
                double[][] matrix = Utils.createMatrixFromKeyBoard();
                // Нахождение решения

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
            list.add(0, ans);
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

    public static void printMatrix(double[][] matrix, String name) {
        System.out.println("---------------------------------------------------------");
        System.out.println(name);
        System.out.println("---------------------------------------------------------");
        for (int i = 0; i < matrix.length; i++) {
            System.out.println();
            for (int j = 0; j < matrix[i].length; j++) {
                String result = String.format("%.2f", matrix[i][j]);
                System.out.print("[" + i + "][" + j +"]= " + result + "\t\t");
            }
        }
        System.out.println();
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
