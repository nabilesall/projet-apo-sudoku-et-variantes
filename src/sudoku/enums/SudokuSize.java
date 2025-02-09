package sudoku.enums;

public enum SudokuSize {
    SMALL(4, "4x4"),
    MEDIUM(9, "9x9"),
    LARGE(16, "16x16");

    private final int size;
    private final String label;

    SudokuSize(int size, String label) {
        this.size = size;
        this.label = label;
    }

    public int getSize() {
        return size;
    }

    public String getLabel() {
        return label;
    }

    public static SudokuSize fromChoice(int choice) {
        return switch (choice) {
            case 1 -> SMALL;
            case 2 -> MEDIUM;
            case 3 -> LARGE;
            default -> null;
        };
    }

    public static SudokuSize fromLabel(String label) {
        for (SudokuSize size : values()) {
            if (size.getLabel().equals(label)) {
                return size;
            }
        }
        return MEDIUM; // Valeur par défaut (9x9)
    }

}


