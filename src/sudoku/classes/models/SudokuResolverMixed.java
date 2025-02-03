package sudoku.classes.models;

public class SudokuResolverMixed {

    private final int[][] sudoku;

    public SudokuResolverMixed(int[][] sudoku) {
        this.sudoku = sudoku;
    }

    /**
     * Résout le Sudoku en combinant les méthodes de déduction et de retour sur trace.
     * @return true si le Sudoku est résolu, sinon false.
     */
    public boolean resolveSudoku() {
        // Étape 1 : Réduction (placement unique)
        SudokuResolverReduction reduction = new SudokuResolverReduction(sudoku);
        boolean solvedWithReduction = reduction.resolveSudoku();

        if (solvedWithReduction) {
            // Si la grille est résolue uniquement avec la réduction, on termine ici
            return true;
        }

        // Étape 2 : Retour sur trace pour les cellules restantes
        SudokuResolverBT backtracking = new SudokuResolverBT(sudoku);
        return backtracking.resolveSudoku();
    }
}
