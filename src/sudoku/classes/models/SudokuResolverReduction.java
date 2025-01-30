package sudoku.classes.models;

import java.util.HashSet;

public class SudokuResolverReduction {

    private int[][] sudoku;

    // <editor-fold defaultstate="collapsed" desc="CONSTRUCTOR">
    /**
     * Démarre le programme
     * @param sudoku Correspond au sudoku à résoudre
     */
    public SudokuResolverReduction(int[][] sudoku) {
        this.sudoku = sudoku;
    }

    public boolean resolveSudoku(int[][] sudoku) {
        boolean progressMade;

        do {
            progressMade = false;

            for (int row = 0; row < 9; row++) {
                for (int col = 0; col < 9; col++) {
                    if (sudoku[row][col] == 0) {
                        HashSet<Integer> possibilities = getPossibilities(sudoku, row, col);

                        if (possibilities.size() == 1) {
                            // Placement unique
                            sudoku[row][col] = possibilities.iterator().next();
                            progressMade = true;
                        }
                    }
                }
            }
        } while (progressMade);

        // Vérification si le Sudoku est résolu
        return isSudokuSolved(sudoku);
    }

    private static HashSet<Integer> getPossibilities(int[][] sudoku, int row, int col) {
        HashSet<Integer> possibilities = new HashSet<>();
        for (int i = 1; i <= 9; i++) {
            possibilities.add(i);
        }

        // Eliminer les chiffres de la ligne
        for (int c = 0; c < 9; c++) {
            possibilities.remove(sudoku[row][c]);
        }

        // Eliminer les chiffres de la colonne
        for (int r = 0; r < 9; r++) {
            possibilities.remove(sudoku[r][col]);
        }

        // Eliminer les chiffres de la boîte 3x3
        int boxRowStart = (row / 3) * 3;
        int boxColStart = (col / 3) * 3;
        for (int r = boxRowStart; r < boxRowStart + 3; r++) {
            for (int c = boxColStart; c < boxColStart + 3; c++) {
                possibilities.remove(sudoku[r][c]);
            }
        }

        return possibilities;
    }

    private static boolean isSudokuSolved(int[][] sudoku) {
        for (int row = 0; row < 9; row++) {
            for (int col = 0; col < 9; col++) {
                if (sudoku[row][col] == 0) {
                    return false;
                }
            }
        }
        return true;
    }
}
