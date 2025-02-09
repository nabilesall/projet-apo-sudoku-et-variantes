package sudoku.enums;

public enum SudokuSymbols {
    DIGITS("Chiffres", "123456789"),
    LETTERS("Lettres", "ABCDEFGHIJKL"),
    HEXADECIMAL("Hexadécimal", "123456789ABCDEFG");

    private final String label;
    private final String symbols;

    SudokuSymbols(String label, String symbols) {
        this.label = label;
        this.symbols = symbols;
    }

    public String getLabel() {
        return label;
    }

    public String getSymbols(int size) {
        return symbols.substring(0, size);
    }

    public static SudokuSymbols fromChoice(int choice) {
        System.out.println("choice symbols: " + choice);
        return switch (choice) {
            case 1 -> DIGITS;
            case 2 -> LETTERS;
            default -> null;
        };
    }

    public static SudokuSymbols fromLabel(String label) {
        // ne pas afficher Hexadécimal
        for (SudokuSymbols symbolSet : values()) {
            System.out.println(symbolSet.getLabel());
            if (symbolSet.getLabel().equals(label)) {
                return symbolSet;
            }
        }
        return DIGITS; // Valeur par défaut
    }
}
