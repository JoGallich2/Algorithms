import java.io.File;
import java.io.FileNotFoundException;

public class Main {
    public static void main(String[] args) {
        System.out.println("Solving Sudoku");
        Sudoku sudoku = new Sudoku();
        try {
            sudoku.loadBoard("/Users/grabnerv/Developer/CS4050/group6_cs4050_assignment5/src/sudoku.txt");
            sudoku.solve();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}