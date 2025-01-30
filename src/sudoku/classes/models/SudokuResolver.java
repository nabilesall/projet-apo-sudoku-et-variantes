package sudoku.classes.models;

public class SudokuResolver {
    private final Sudoku sudoku;
    private final SudokuResolverBT resolverBT;
    private final SudokuResolverReduction resolverReduction;
    private final SudokuResolverMixed resolverMixed;

    public SudokuResolver(Sudoku sudoku) {
        this.sudoku = sudoku;
        this.resolverBT = new SudokuResolverBT(sudoku.getGrid(), "sudoku_log.txt");
        this.resolverReduction = new SudokuResolverReduction(sudoku.getGrid(), "sudoku_log.txt");
        this.resolverMixed = new SudokuResolverMixed(sudoku.getGrid());
    }

    public boolean solve(int option) {
        return switch (option) {
            case 1 -> resolverBT.resolveSudoku(sudoku.getGrid());
            case 2 -> resolverReduction.resolveSudoku(sudoku.getGrid());
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
}

