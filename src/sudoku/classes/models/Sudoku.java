package sudoku.classes.models;

//<editor-fold defaultstate="collapsed" desc=" IMPORTS">
import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
//</editor-fold>

/**
 * The `Sudoku` class represents a Sudoku puzzle.
 * It contains the grid, the size of the grid, the symbols used in the grid,
 * and methods to import the grid from a file,
 * enter the grid manually, print the grid, validate the grid,
 * and check if the grid is empty.
 *
 * @author Idrissa and Marouane
 */
public class Sudoku {

    // <editor-fold defaultstate="collapsed" desc="ATTRIBUTES">
    private int size;
    private int[][] grid;
    private String symbols;
    private final char[] symbolsArray;
    private final Map<Character, Integer> symbolToInt;
    private final Map<Integer, Character> intToSymbol;
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="CONSTRUCTORS">
    /**
     * Constructor
     * @param size The size of the grid
     * @param symbols The symbols used in the grid
     */
    public Sudoku(int size, String symbols) {
        this.size = size;
        this.symbols = symbols;
        this.symbolsArray = symbols.toCharArray();
        this.grid = new int[size][size];

        // Création des mappings entre symboles et entiers
        symbolToInt = new HashMap<>();
        intToSymbol = new HashMap<>();
        for (int i = 0; i < symbolsArray.length; i++) {
            symbolToInt.put(symbolsArray[i], i + 1);
            intToSymbol.put(i + 1, symbolsArray[i]);
        }
    }
    // </editor-fold>

    //<editor-fold defaultstate="collapsed" desc="METHODS">
    /**
     * Print the grid
     */
    public void printGrid() {
        int boxSize = (int) Math.sqrt(size);
        for (int row = 0; row < size; row++) {
            if (row % boxSize == 0 && row != 0) {
                System.out.println("-".repeat(size * 2 + boxSize - 1));
            }
            for (int col = 0; col < size; col++) {
                if (col % boxSize == 0 && col != 0) {
                    System.out.print("| ");
                }
                System.out.print(grid[row][col] == 0 ? "  " : intToSymbol.get(grid[row][col])+ " ");
            }
            System.out.println();
        }
    }

    /**
     * Import the grid from a file
     * @param filePath The path of the file
     * @throws FileNotFoundException
     */
    public void importGridFromFile(String filePath) throws FileNotFoundException {
        Scanner fileScanner = new Scanner(new File(filePath));
        int count = 0;
        while (fileScanner.hasNextLine()) {
            fileScanner.nextLine();
            count++;
        }
        fileScanner.close();

        if (count != size || grid[0].length != size) {
            grid = new int[size][size];
            throw new IllegalArgumentException("Erreur : la grille doit être de taille " + size + "x" + size);
        }

        fileScanner = new Scanner(new File(filePath));
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (fileScanner.hasNextLine()) {
                    String value = fileScanner.next();
                    if (!isValidSymbol(value) && !value.equals("0")) {
                        throw new IllegalArgumentException("Erreur : Symbole invalide '" + value + "' détecté.");
                    }
                    if(!value.equals("0")) {
                        grid[i][j] = symbolToInt.get(value.charAt(0));
                    } else {
                        grid[i][j] = 0;
                    }
                }
            }
        }
        fileScanner.close();
    }

    /**
     * Enter the grid manually
     * @param input The input string
     * @throws IllegalArgumentException
     */
    public void enterGridManually(String input) throws IllegalArgumentException {
        if (input.length() != size * size) {
            throw new IllegalArgumentException("Erreur : la chaîne doit contenir exactement " + size * size + " chiffres.");
        }
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (!isValidSymbol(String.valueOf(input.charAt(i * size + j))) && input.charAt(i * size + j) != '0') {
                    throw new IllegalArgumentException("Erreur : Symbole invalide '" + input.charAt(i * size + j) + "' détecté.");
                }
                if(input.charAt(i * size + j) != '0') {
                    grid[i][j] = symbolToInt.get(input.charAt(i * size + j));
                } else {
                    grid[i][j] = 0;
                }
                String value  = String.valueOf(input.charAt(i * size + j));
                if(!value.equals("0")) {
                    grid[i][j] = symbolToInt.get(value.charAt(0));
                } else {
                    grid[i][j] = 0;
                }
            }
        }
    }

    /**
     * Validate the grid
     * @return The validation message
     */
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

    /**
     * Check if the placement of a number is valid
     * @return True if the placement is valid, false otherwise
     */
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

    /**
     * Check if the symbol is valid
     * @return True if the symbols are valid, false otherwise
     */
    private boolean isValidSymbol(String value) {
        for (char symbol : symbolsArray) {
            if (String.valueOf(symbol).equals(value)) {
                return true;
            }
        }
        return false;
    }

    // Getters and Setters
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

    /**
     * Check if the grid is empty
     * @return True if the grid is empty, false otherwise
     */
    public boolean isEmpty() {
        for (int[] row : grid) {
            for (int cell : row) {
                if (cell != 0) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * Get the mapping between symbols and integers
     * @return The symbols
     */
    public Map<Integer, Character> getIntToSymbol() {
        return this.intToSymbol;
    }
    //</editor-fold>
}
