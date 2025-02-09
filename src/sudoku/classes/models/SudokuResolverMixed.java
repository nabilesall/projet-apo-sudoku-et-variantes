package sudoku.classes.models;

/**
 * The `SudokuResolverMixed` class represents a Sudoku resolver using a mixed method.
 * It contains the mixed algorithm to resolve the Sudoku.
 * It combines the deduction and backtracking methods.
 *
 * @author Idrissa and Marouane
 */
public class SudokuResolverMixed {

    //<editor-fold defaultstate="collapsed" desc="ATTRIBUTES">
    private final int[][] sudoku;
    //</editor-fold>

    // <editor-fold defaultstate="collapsed" desc="CONSTRUCTOR">
    /**
     * Constructor
     * @param sudoku The Sudoku to resolve
     */
    public SudokuResolverMixed(int[][] sudoku) {
        this.sudoku = sudoku;
    }

    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="METHODS">
    /**
     * Resolve the Sudoku using a mixed method (deduction and backtracking).
     * @return true if the Sudoku is solved, false otherwise
     */
    public boolean resolveSudoku() {
        // Step 1: Deduction
        SudokuResolverReduction reduction = new SudokuResolverReduction(sudoku);
        boolean solvedWithReduction = reduction.resolveSudoku();

        if (solvedWithReduction) {
            return true;
        }

        // Step 2: Backtracking
        SudokuResolverBT backtracking = new SudokuResolverBT(sudoku);
        return backtracking.resolveSudoku();
    }
    // </editor-fold>
}
