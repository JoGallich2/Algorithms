import java.io.File;
import java.io.FileNotFoundException;

public class Main {
    public static void main(String[] args) {
        System.out.println("Solving Sudoku");
        Sudoku sudoku = new Sudoku();
        try {
            //Make sure to change this path to the path on your machine to the sudoku.txt file.
            sudoku.loadBoard("/Users/Windol/Desktop/College Classes/CS_4050/Analysis-of-Algorithms---Group6/group6_cs4050_assignment5/src/sudoku.txt");
            sudoku.solve();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}