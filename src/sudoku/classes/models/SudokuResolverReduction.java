package sudoku.classes.models;

//<editor-fold defaultstate="collapsed" desc=" IMPORTS">
import java.util.HashSet;
//</editor-fold>

/**
 * The `SudokuResolverReduction` class represents a Sudoku resolver using reduction.
 * It contains the reduction algorithm to resolve the Sudoku.
 *
 * @author Idrissa and Marouane
 */
public class SudokuResolverReduction {

    // <editor-fold defaultstate="collapsed" desc="ATTRIBUTES">
    private final int[][] sudoku;
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="CONSTRUCTOR">
    /**
     * Constructor
     * @param sudoku The Sudoku to resolve
     */
    public SudokuResolverReduction(int[][] sudoku) {
        this.sudoku = sudoku;
    }

    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="METHODES">

    /**
     * Resolve the Sudoku
     * @return True if the Sudoku is solved, false otherwise
     */
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

    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="METHODES STATICS">

    /**
     * Returns the possibilities for the cell [row][col] of the "sudoku"
     * @param sudoku Corresponds to the sudoku on which the cell [row][col] will be analyzed
     * @param row Corresponds to the row number of the "sudoku" to be analyzed
     * @param col Corresponds to the column number of the "sudoku" to be analyzed
     * @return Returns the possibilities for the cell [row][col] of the "sudoku"
     */
    private static HashSet<Integer> getPossibilities(int[][] sudoku, int row, int col) {
        HashSet<Integer> possibilities = new HashSet<>();
        int size = sudoku.length;

        for (int i = 1; i <= size; i++) {
            possibilities.add(i);
        }

        // Eliminate numbers from the row
        for (int c = 0; c < size; c++) {
            possibilities.remove(sudoku[row][c]);
        }

        // Eliminate numbers from the column
        for (int r = 0; r < size; r++) {
            possibilities.remove(sudoku[r][col]);
        }

        int boxSize = (int) Math.sqrt(sudoku.length);
        // Eliminate numbers from the box
        int boxRowStart = (row / boxSize) * boxSize;
        int boxColStart = (col / boxSize) * boxSize;
        for (int r = boxRowStart; r < boxRowStart + boxSize; r++) {
            for (int c = boxColStart; c < boxColStart + boxSize; c++) {
                possibilities.remove(sudoku[r][c]);
            }
        }

        return possibilities;
    }

    /**
     * Returns whether or not the Sudoku is solved
     * @param sudoku Corresponds to the sudoku to be analyzed
     * @return Returns true if the Sudoku is solved, false otherwise
     */
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

    // </editor-fold>
}
