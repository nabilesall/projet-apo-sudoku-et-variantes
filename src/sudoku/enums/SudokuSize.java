package sudoku.enums;

/**
 * The `SudokuSize` enum represents the size of the Sudoku.
 * It contains the different sizes of the Sudoku.
 * It allows to choose the size of the Sudoku.
 */
public enum SudokuSize {

    // <editor-fold defaultstate="collapsed" desc="ATTRIBUTES">
    SMALL(4, "4x4"),
    MEDIUM(9, "9x9"),
    LARGE(16, "16x16");

    private final int size;
    private final String label;
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="CONSTRUCTOR">
    SudokuSize(int size, String label) {
        this.size = size;
        this.label = label;
    }
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="GETTERS">
    public int getSize() {
        return size;
    }

    public String getLabel() {
        return label;
    }
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="METHODS">
    /**
     * Returns the size of the Sudoku according to the choice
     * @param choice Corresponds to the choice of the size of the Sudoku
     * @return The size of the Sudoku according to the choice
     */
    public static SudokuSize fromChoice(int choice) {
        return switch (choice) {
            case 1 -> SMALL;
            case 2 -> MEDIUM;
            case 3 -> LARGE;
            default -> null;
        };
    }

    /**
     * Returns the size of the Sudoku according to the label
     * @param label Corresponds to the label of the size of the Sudoku
     * @return The size of the Sudoku according to the label
     */
    public static SudokuSize fromLabel(String label) {
        for (SudokuSize size : values()) {
            if (size.getLabel().equals(label)) {
                return size;
            }
        }
        return MEDIUM;
    }

    // </editor-fold>
}
