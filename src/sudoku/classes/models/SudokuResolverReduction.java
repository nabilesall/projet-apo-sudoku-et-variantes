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

    public boolean resolveSudoku() {
        boolean progressMade;
        int size = this.sudoku.length;

        do {
            progressMade = false;

            for (int row = 0; row < size; row++) {
                for (int col = 0; col < size; col++) {
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
        int size = sudoku.length;

        for (int i = 1; i <= size; i++) {
            possibilities.add(i);
        }

        // Eliminer les chiffres de la ligne
        for (int c = 0; c < size; c++) {
            possibilities.remove(sudoku[row][c]);
        }

        // Eliminer les chiffres de la colonne
        for (int r = 0; r < size; r++) {
            possibilities.remove(sudoku[r][col]);
        }

        int boxSize = (int) Math.sqrt(sudoku.length);
        // Eliminer les chiffres de la boîte size x size
        int boxRowStart = (row / boxSize) * boxSize;
        int boxColStart = (col / boxSize) * boxSize;
        for (int r = boxRowStart; r < boxRowStart + boxSize; r++) {
            for (int c = boxColStart; c < boxColStart + boxSize; c++) {
                possibilities.remove(sudoku[r][c]);
            }
        }

        return possibilities;
    }

    private static boolean isSudokuSolved(int[][] sudoku) {
        int size = sudoku.length;
        for (int row = 0; row < size; row++) {
            for (int col = 0; col < size; col++) {
                if (sudoku[row][col] == 0) {
                    return false;
                }
            }
        }
        return true;
    }
}
