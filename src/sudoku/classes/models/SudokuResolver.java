package sudoku.classes.models;

/**
 * The `SudokuResolver` class represents a Sudoku resolver.
 * It contains the backtracking, reduction, and mixed resolvers.
 *
 * @author Idrissa and Marouane
 */
public class SudokuResolver {

    //<editor-fold defaultstate="collapsed" desc="ATTRIBUTES">
    private final SudokuResolverBT resolverBT;
    private final SudokuResolverReduction resolverReduction;
    private final SudokuResolverMixed resolverMixed;
    //</editor-fold>

    // <editor-fold defaultstate="collapsed" desc="CONSTRUCTORS">
    /**
     * Constructor
     * @param sudoku The Sudoku to resolve
     */
    public SudokuResolver(Sudoku sudoku) {
        this.resolverBT = new SudokuResolverBT(sudoku.getGrid());
        this.resolverReduction = new SudokuResolverReduction(sudoku.getGrid());
        this.resolverMixed = new SudokuResolverMixed(sudoku.getGrid());
    }
    // </editor-fold>

    //<editor-fold defaultstate="collapsed" desc="METHODS">
    /**
     * Solve the Sudoku
     * @param option The option to choose the resolver
     * @return True if the Sudoku is solved, false otherwise
     */
    public boolean solve(int option) {
        System.out.println("Résolution du Sudoku en cours...");
        return switch (option) {
            case 1 -> resolverBT.resolveSudoku();
            case 2 -> resolverReduction.resolveSudoku();
            case 3 -> resolverMixed.resolveSudoku();
            default -> {
                System.out.println("Option invalide.");
                yield false;
            }
        };
    }

    public void logOperation(String message) {
        System.out.println("Log: " + message);
    }
    //</editor-fold>
}

