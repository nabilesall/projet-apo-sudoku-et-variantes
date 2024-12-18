/*
 * Copyright (C) 2022 IUT
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */


/**
 * Cette classe a pour but de résoudre un sudoku
 * @author Joseph BRIGUET
 * @version 1.0
 */
public class SudokuResolver {
    
    // <editor-fold defaultstate="collapsed" desc="MAIN">
    /**
     * Démarre le programme
     * @param args Correspond aux éventuels arguments. "Il n'y en a pas ici"
     */
    public static void main(String[] args) {
        
        // Correspond au sudoku à résoudre
        int[][] sudoku = {{5, 3, 0, 0, 7, 0, 0, 0, 0},
                          {6, 0, 0, 1, 9, 5, 0, 0, 0},
                          {0, 9, 8, 0, 0, 0, 0, 6, 0},
                          {8, 0, 0, 0, 6, 0, 0, 0, 3},
                          {4, 0, 0, 8, 0, 3, 0, 0, 1},
                          {7, 0, 0, 0, 2, 0, 0, 0, 6},
                          {0, 6, 0, 0, 0, 0, 2, 8, 0},
                          {0, 0, 0, 4, 1, 9, 0, 0, 5},
                          {0, 0, 0, 0, 8, 0, 0, 7, 9}};
        
        // Affiche le sudoku à résoudre
        printSudoku(sudoku);
        
        // Résout le sudoku et renvoie s'il s'agit d'une réussite ou pas
        boolean success = resolveSudoku(sudoku);
        
        // Affiche un message approprié en fonction du résultat
        System.out.println(success ? "Solved successfully" : "Unsolvable sudoku");
        
        // Affiche le sudoku rempli
        printSudoku(sudoku);
        
    }
    // </editor-fold>
    
    

    // <editor-fold defaultstate="collapsed" desc="METHODES STATICS">
    /**
     * Affiche un sudoku dans la console
     * @param sudoku Correspond au sudoku à afficher
     */
    private static void printSudoku(int[][] sudoku) {
        
        // Parcourt chaque ligne. "row" contient successivement le numéro de chaque ligne
        for(int row = 0; row < sudoku.length; row++){
            
            // A la quatrième ligne du sudoku, insère une ligne horizontale complète
            if(row % 3 == 0 && row != 0)
                System.out.println("-----------");
            
            // Parcourt chaque colonne. "column" contient successivement le numéro de chaque colonne
            for(int column = 0; column < sudoku.length; column++){
                
                // A la quatrième colonne du sudoku, insère une ligne verticale
                if(column % 3 == 0 && column != 0)
                    System.out.print("|");
                
                // Si la cellule du sudoku, ne contient aucune valeur, alors affiche un espace vide
                if(sudoku[row][column] == 0)
                    System.out.print(" ");
                
                // Sinon affiche la valeur de la cellule
                else
                    System.out.print(sudoku[row][column]);
            }
            
            // Va à la ligne suivante
            System.out.println();
        }
        
    }
    
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
        
        // Correspond au numéro de la ligne qui pointe le coin inférieur gauche d'une boîte
        int topLeftCornerBoxRow    = row    - row    % 3;
        
        // Correspond au numréo de la colonne qui pointe le coin inférieur gauche d'une boîte
        int topLeftCornerBoxColumn = column - column % 3;
        
        // Parcourt chaque ligne d'une boîte. "i" contient successivement le numéro de chaque ligne
        for(int i = topLeftCornerBoxRow; i < topLeftCornerBoxRow + 3; i++){
            
            // Parcourt chaque colonne d'une boîte. "j" contient successivement le numéro de chaque colonne
            for(int j = topLeftCornerBoxColumn; j < topLeftCornerBoxColumn + 3; j++){
                
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
     * @param sudoku Correspond au sudoku à résoudre
     * @return Retourne true si le sudoku est résolu sinon false. Ou pour chaque boucle récursive, true si le sudoku est en théorie résolvable, sinon false
     */
    private static boolean resolveSudoku(int[][] sudoku){
        
        // Parcourt chaque ligne. "row" contient successivement le numéro de chaque ligne
        for(int row = 0; row < sudoku.length ; row++){
            
            // Parcourt chaque colonne. "column" contient successivement le numéro de chaque colonne
            for(int column = 0; column < sudoku.length ; column++){
                
                // Si la cellule pointée par [row][column] = 0, c'est que la cellule n'a pas de valeur, donc l'algorithme peut tenter de remplir cette cellule par une valeur
                if(sudoku[row][column] == 0){
                    
                    // Parcourt chaque numéro que peut prendre la cellule à remplir
                    for(int numberToTry = 1; numberToTry <= sudoku.length; numberToTry++){
                        
                        // Si le numéro est valide par rapport à sa ligne, sa colonne et sa boîte, alors...
                        if(isValidPlacement(sudoku, numberToTry, row, column)){
                            
                            // La cellule prend la valeur essayée
                            sudoku[row][column] = numberToTry;
                            
                            // A partir de ce point là, on considère que la cellule rempli contient la bonne valeur.
                            // Cela aurait pû être un autre numéro, mais tant pis, on considère que ce premier numéro est correct
                            
                            // Puisque ce numéro est considéré comme correct on appelle de nouveau la résolution du sudoku dans l'état où il se trouve. Si celui-ci réussi alors, la méthode renvoie true
                            if(resolveSudoku(sudoku))
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