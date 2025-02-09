package sudoku.classes.models;

/**
 * The `SudokuResolverBT` class represents a Sudoku resolver using backtracking.
 * It contains the backtracking algorithm to resolve the Sudoku.
 *
 * @author Idrissa and Marouane
 */
public class SudokuResolverBT {

    // <editor-fold defaultstate="collapsed" desc="ATTRIBUTES">
    private final int[][] sudoku;
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="CONSTRUCTOR">
    /**
     * Constructor
     * @param sudoku The Sudoku to resolve
     */
    public SudokuResolverBT(int[][] sudoku) {
        this.sudoku = sudoku;
    }

    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="METHODES STATICS">
    /**
     * Returns whether or not the number "number" is already included in the row "row" of the "sudoku"
     * @param sudoku Corresponds to the sudoku on which the row "row" will be analyzed
     * @param number Corresponds to the number to be tested for existence on the row "row" of the "sudoku"
     * @param row Corresponds to the row number of the "sudoku" to be analyzed
     * @return Returns true if the number "number" is already present on row "row", otherwise false
     */

    private static boolean hasNumberInRow(int[][] sudoku, int number, int row){

        // Parcourt chaque colonne. "column" contient successivement le numéro de chaque colonne
        for(int column = 0; column < sudoku.length; column++){

            // Si le sudoku contient "number" dans la cellule [row][column], alors renvoie true
            if(sudoku[row][column] == number)
                return true;

        }

        //Sinon retourne false
        return false;
    }

    /**
     * Returns whether or not the number "number" is already included in the column "column" of the "sudoku"
     * @param sudoku Corresponds to the sudoku on which the column "column" will be analyzed
     * @param number Corresponds to the number to be tested for existence on the column "column" of the "sudoku"
     * @param column Corresponds to the column number of the "sudoku" to be analyzed* @return Returns true if the number "number" is already present on column "column", otherwise false
     */
    private static boolean hasNumberInColumn(int[][] sudoku, int number, int column){

        for(int row = 0; row < sudoku.length; row++){
            if(sudoku[row][column] == number)
                return true;
        }
        return false;
    }

    /**
     * Returns whether or not the number "number" is already included in the box containing the coordinate [row][column] of the "sudoku"
     * @param sudoku Corresponds to the sudoku on which a box will be analyzed
     * @param number Corresponds to the number to be tested for existence in the "sudoku" box
     * @param row Corresponds to a row number of the "sudoku" that jointly points with "column" to a box to be analyzed
     * @param column Corresponds to a column number of the "sudoku" that jointly points with "row" to a box to be analyzed
     * @return Returns true if the number "number" is already present in the pointed box, otherwise false
     */
    private static boolean hasNumberInBox(int[][] sudoku, int number, int row, int column){
        int boxSize = (int) Math.sqrt(sudoku.length);
        // Represents the number of the row pointing to the upper left corner of a box
        int topLeftCornerBoxRow    = row    - row    % boxSize;

        // Represents the number of the column pointing to the upper left corner of a box
        int topLeftCornerBoxColumn = column - column % boxSize;

        for(int i = topLeftCornerBoxRow; i < topLeftCornerBoxRow + boxSize; i++){
            for(int j = topLeftCornerBoxColumn; j < topLeftCornerBoxColumn + boxSize; j++){
                if(sudoku[i][j] == number)
                    return true;
            }
        }

        return false;
    }

    /**
     * Returns whether or not a number is not present on a row "row", nor on a column "column", nor in a box containing the coordinate [row][column] of the "sudoku"
     * @param sudoku Corresponds to the sudoku on which a row, a column and a box will be analyzed
     * @param number Corresponds to the number to be tested for existence on a row "row" and on a column "column" and in the box containing the coordinate [row][column] of the "sudoku"
     * @param row Corresponds to the row number of the "sudoku" to be analyzed
     * @param column Corresponds to the column number of the "sudoku" to be analyzed
     * @return Returns true if the number "number" is not present on row "row", nor on column "column", nor in the box containing the coordinate [row][column] of the "sudoku"
     */
    private static boolean isValidPlacement(int[][] sudoku, int number, int row, int column){

        return !hasNumberInRow(sudoku, number, row) &&
               !hasNumberInColumn(sudoku, number, column) &&
               !hasNumberInBox(sudoku, number, row, column);

    }

    /**
     * Résout un sudoku
     * @return Retourne true si le sudoku est résolu sinon false. Ou pour chaque boucle récursive, true si le sudoku est en théorie résolvable, sinon false
     */
    public boolean resolveSudoku(){
        int size = this.sudoku.length;
        for(int row = 0; row < size ; row++){
            for(int column = 0; column < size ; column++){
                if(sudoku[row][column] == 0){
                    for(int numberToTry = 1; numberToTry <= size; numberToTry++){
                        if(isValidPlacement(sudoku, numberToTry, row, column)){
                            sudoku[row][column] = numberToTry;
                            if(resolveSudoku())
                                return true;
                            else
                                sudoku[row][column] = 0;
                        }
                    }
                    return false;
                }
            }
        }

        return true;
    }
    // </editor-fold>

}