package sudoku.classes.models;

public class SudokuResolverBT {

    private int[][] sudoku;

    // <editor-fold defaultstate="collapsed" desc="CONSTRUCTOR">
    /**
     * Démarre le programme
     * @param sudoku Correspond au sudoku à résoudre
     */
    public SudokuResolverBT(int[][] sudoku) {
        this.sudoku = sudoku;
    }

    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="METHODES STATICS">

    /**
     * Renvoie si oui ou non le numéro "number" est déjà compris sur la ligne "row" du "sudoku"
     * @param sudoku Correspond au sudoku sur lequel la ligne "row" va être analysée
     * @param number Correspond au numéro dont on cherche à tester l'existance sur la ligne "row" du "sudoku"
     * @param row Correspond au numéro de la ligne du "sudoku" à analyser
     * @return Retourne true si le numéro "number" est déjà présent sur la ligne "row", sinon false
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
     * Renvoie si oui ou non le numéro "number" est déjà compris sur la colonne "column" du "sudoku"
     * @param sudoku Correspond au sudoku sur lequel la colonne "column" va être analysée
     * @param number Correspond au numéro dont on cherche à tester l'existance sur la colonne "column" du "sudoku"
     * @param column Correspond au numéro de la colonne du "sudoku" à analyser
     * @return Retourne true si le numéro "number" est déjà présent sur la colonne "column", sinon false
     */
    private static boolean hasNumberInColumn(int[][] sudoku, int number, int column){

        // Parcourt chaque ligne. "row" contient successivement le numéro de chaque ligne
        for(int row = 0; row < sudoku.length; row++){

            // Si le sudoku contient "number" dans la cellule [row][column], alors renvoie true
            if(sudoku[row][column] == number)
                return true;

        }

        //Sinon retourne false
        return false;
    }

    /**
     * Renvoie si oui ou non le numéro "number" est déjà compris dans la boîte contenant la coordonnée [row][column] du "sudoku"
     * @param sudoku Correspond au sudoku sur lequel une boîte va être analysée
     * @param number Correspond au numéro dont on cherche à tester l'existance dans la boîte du "sudoku"
     * @param row Correspond à un numéro de ligne du "sudoku" qui pointe conjointement avec "column" une boîte à analyser
     * @param column Correspond à un numéro de colonne du "sudoku" qui pointe conjointement avec "row" une boîte à analyser
     * @return Retourne true si le numéro "number" est déjà présent dans la boîte pointée, sinon false
     */
    private static boolean hasNumberInBox(int[][] sudoku, int number, int row, int column){
        int boxSize = (int) Math.sqrt(sudoku.length);
        // Correspond au numéro de la ligne qui pointe le coin inférieur gauche d'une boîte
        int topLeftCornerBoxRow    = row    - row    % boxSize;

        // Correspond au numréo de la colonne qui pointe le coin inférieur gauche d'une boîte
        int topLeftCornerBoxColumn = column - column % boxSize;

        // Parcourt chaque ligne d'une boîte. "i" contient successivement le numéro de chaque ligne
        for(int i = topLeftCornerBoxRow; i < topLeftCornerBoxRow + boxSize; i++){

            // Parcourt chaque colonne d'une boîte. "j" contient successivement le numéro de chaque colonne
            for(int j = topLeftCornerBoxColumn; j < topLeftCornerBoxColumn + boxSize; j++){

                // Si la cellule [i][j] contient le nombre "number", alors c'est que la boîte contient le nombre "number". Alors la méthode retourne true
                if(sudoku[i][j] == number)
                    return true;

            }
        }

        // Aucun nombre "number" n'a été trouvé dans la boîte, donc la méthode retourne false
        return false;
    }

    /**
     * Renvoie si oui ou non un nombre n'est pas présent ni sur une ligne "row", ni sur une colonne "column", ni dans une boîte contenant la coordonnée [row][column] du "sudoku"
     * @param sudoku Correspond au sudoku sur lequel une ligne, une colonne et une boîte vont être analysées
     * @param number Correspond au numéro dont on cherche à tester l'existance sur une ligne "row" et sur une colonne "column" et dans la boîte contenant la coordonnée [row][column] du "sudoku"
     * @param row Correspond au numéro de la ligne du "sudoku" à analyser
     * @param column Correspond au numéro de la colonne du "sudoku" à analyser
     * @return Retourne true le nombre "number" n'est pas présent ni sur la ligne "row", ni sur la colonne "column", ni dans la boîte contenant la coordonnée [row][column] du "sudoku"
     */
    private static boolean isValidPlacement(int[][] sudoku, int number, int row, int column){

        // Renvoie true si "number" n'est présent ni sur la ligne "row", ni sur la colonne "column" et ni dans la boîte contenant la coordonnée [row][column] du "sudoku", sinon false
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
        // Parcourt chaque ligne. "row" contient successivement le numéro de chaque ligne
        for(int row = 0; row < size ; row++){
            // Parcourt chaque colonne. "column" contient successivement le numéro de chaque colonne
            for(int column = 0; column < size ; column++){

                // Si la cellule pointée par [row][column] = 0, c'est que la cellule n'a pas de valeur, donc l'algorithme peut tenter de remplir cette cellule par une valeur
                if(sudoku[row][column] == 0){

                    // Parcourt chaque numéro que peut prendre la cellule à remplir
                    for(int numberToTry = 1; numberToTry <= size; numberToTry++){

                        // Si le numéro est valide par rapport à sa ligne, sa colonne et sa boîte, alors...
                        if(isValidPlacement(sudoku, numberToTry, row, column)){

                            // La cellule prend la valeur essayée
                            sudoku[row][column] = numberToTry;

                            // A partir de ce point là, on considère que la cellule rempli contient la bonne valeur.
                            // Cela aurait pû être un autre numéro, mais tant pis, on considère que ce premier numéro est correct

                            // Puisque ce numéro est considéré comme correct on appelle de nouveau la résolution du sudoku dans l'état où il se trouve. Si celui-ci réussi alors, la méthode renvoie true
                            if(resolveSudoku())
                                return true;

                            // Sinon c'est que la valeur essayée précédemment (quoi qu'elle soit correcte au niveau de la ligne, colonne et boîte) n'est pas correcte.
                            // Cette valeur essayée empêche la résolution du sudoku. Il convient donc d'effacer la cellule et d'essayer un nouveau nombre.
                            // Si tous les nombres ont été testés, alors ce n'est pas seulement ce nombre qui n'est pas correct, mais aussi le précédent, voir celui d'avant également...
                            else
                                sudoku[row][column] = 0;
                        }
                    }

                    // Tous les nombres ont été testés, mais cependant la cellule précédemment visée ne peut être rempli.
                    // Donc il y a une erreur dans la cellule précédemment rempli, donc la méthode retourne false pour vider la cellule précédante et tester un nouveau nombre
                    return false;
                }
            }
        }

        // Pour arriver à ce renvoie, il faut que le sudoku est entièrement été rempli. Et si c'est le cas, cela veut dire qu'il est juste, alors la méthode renvoie true
        return true;
    }
    // </editor-fold>

}