package sudoku.enums;

/**
 * The `SudokuSymbols` enum represents the symbols of the Sudoku.
 * It contains the different symbols of the Sudoku.
 * It allows to choose the symbols of the Sudoku.
 */
public enum SudokuSymbols {

    // <editor-fold defaultstate="collapsed" desc="ATTRIBUTES">
    DIGITS("Chiffres", "123456789"),
    LETTERS("Lettres", "ABCDEFGHIJKL"),
    HEXADECIMAL("Hexadécimal", "123456789ABCDEFG");

    private final String label;
    private final String symbols;
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="CONSTRUCTORS">
    SudokuSymbols(String label, String symbols) {
        this.label = label;
        this.symbols = symbols;
    }
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="GETTERS">
    public String getLabel() {
        return label;
    }

    public String getSymbols(int size) {
        return symbols.substring(0, size);
    }
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="METHODS">
    public static SudokuSymbols fromLabel(String label) {
        for (SudokuSymbols symbolSet : values()) {
            System.out.println(symbolSet.getLabel());
            if (symbolSet.getLabel().equals(label)) {
                return symbolSet;
            }
        }
        return DIGITS;
    }
    // </editor-fold>
}
