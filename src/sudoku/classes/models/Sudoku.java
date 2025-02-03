package sudoku.classes.models;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Sudoku {
    private int size;
    private int[][] grid;
    private final String symbols;

    public Sudoku(int size, String symbols) {
        this.size = size;
        this.grid = new int[size][size];
        this.symbols = symbols;
    }

    public void printGrid() {
        for (int row = 0; row < size; row++) {
            if (row % 3 == 0 && row != 0)
                System.out.println("-----------");
            for (int col = 0; col < size; col++) {
                if (col % 3 == 0 && col != 0)
                    System.out.print("|");
                System.out.print(grid[row][col] == 0 ? " " : grid[row][col]);
            }
            System.out.println();
        }
    }

    public void importGridFromFile(String filePath) throws FileNotFoundException {
        Scanner fileScanner = new Scanner(new File(filePath));
        int count = 0;
        // compter le nombre de lignes
        while (fileScanner.hasNextLine()) {
            fileScanner.nextLine();
            count++;
        }
        fileScanner.close();

        if (count != size || grid[0].length != size) {
            System.out.println("Erreur : la grille doit être de taille " + size + "x" + size);
            grid = new int[size][size];
            throw new IllegalArgumentException("Erreur : la grille doit être de taille " + size + "x" + size);
        }

        fileScanner = new Scanner(new File(filePath));
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (fileScanner.hasNextInt()) {
                    grid[i][j] = fileScanner.nextInt();
                }
            }
        }
        fileScanner.close();
    }

    public boolean enterGridManually(String input) {
        if (input.length() != size * size) {
            return false;
        }
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                grid[i][j] = input.charAt(i * size + j) - '0';
            }
        }
        return true;
    }

    public String validateGrid() {
        for (int row = 0; row < size; row++) {
            for (int col = 0; col < size; col++) {
                int num = grid[row][col];
                if (num != 0) {
                    grid[row][col] = 0; // Temporarily clear the cell
                    if (!isValidPlacement(row, col, num)) {
                        grid[row][col] = num; // Restore the cell
                        return "Erreur : doublon trouvé à la ligne " + (row + 1) + ", colonne " + (col + 1);
                    }
                    grid[row][col] = num; // Restore the cell
                }
            }
        }
        return "OK";
    }

    private boolean isValidPlacement(int row, int col, int num) {
        // Check row
        for (int c = 0; c < size; c++) {
            if (grid[row][c] == num) {
                return false;
            }
        }
        // Check column
        for (int r = 0; r < size; r++) {
            if (grid[r][col] == num) {
                return false;
            }
        }
        // Check box
        int boxSize = (int) Math.sqrt(size);
        int boxRowStart = (row / boxSize) * boxSize;
        int boxColStart = (col / boxSize) * boxSize;
        for (int r = boxRowStart; r < boxRowStart + boxSize; r++) {
            for (int c = boxColStart; c < boxColStart + boxSize; c++) {
                if (grid[r][c] == num) {
                    return false;
                }
            }
        }
        return true;
    }

    public int[][] getGrid() {
        return grid;
    }

    public int getSize() {
        return size;
    }

    public void setGrid() {
        this.grid = new int[size][size];
    }
    public void setSize(int size) {
        this.size = size;
    }
}
