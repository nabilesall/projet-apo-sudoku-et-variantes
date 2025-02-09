package sudoku.classes.models;

//<editor-fold defaultstate="collapsed" desc=" IMPORTS">
import sudoku.enums.SudokuSize;

import java.io.FileNotFoundException;
import java.util.Scanner;
//</editor-fold>

/**
 * The `SudokuTextInterface` class represents the text interface of the Sudoku.
 * It contains the methods to interact with the user in the console.
 * It allows the user to choose the size of the Sudoku, the symbols, the grid to resolve and the resolution method.
 * It also allows the user to enter the grid manually or to load it from a file.
 */
public class SudokuTextInterface {

    // <editor-fold defaultstate="collapsed" desc="ATTRIBUTES">
    private final Scanner scanner;
    private Sudoku sudoku;
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="CONSTRUCTORS">
    public SudokuTextInterface(Scanner scanner) {
        this.scanner = scanner;
    }
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="METHODS">
    /**
     * Start the text interface of the Sudoku.
     */
    public void start() {
        System.out.println("Vous avez choisi l'interface textuelle.");

        // Ask the user to choose the size of the Sudoku
        SudokuSize selectedSize = chooseGridSize();
        if (selectedSize == null) {
            System.out.println("Choix invalide. Veuillez redémarrer le programme.");
            return;
        }
        int size = selectedSize.getSize();

        // Ask the user to choose the symbols of the Sudoku
        String symbols = (size == 16) ? "123456789ABCDEFG" : chooseSymbols(size);

        sudoku = new Sudoku(size, symbols);

        System.out.println("Comment voulez-vous entrer la grille à résoudre ?");
        System.out.println("1. Upload d'un fichier");
        System.out.println("2. Saisie en ligne de commande");

        int choiceGrille = scanner.nextInt();
        scanner.nextLine();

        switch (choiceGrille) {
            case 1:
                loadGridFromFile();
                break;
            case 2:
                enterGridManually(size);
                break;
            default :
                System.out.println("Choix invalide. Veuillez redémarrer le programme.");
                break;
        }

        // Validate the grid
        String validationMessage = sudoku.validateGrid();
        if (!validationMessage.equals("OK")) {
            System.out.println("Erreur : la grille n'est pas valide ou n'est pas résolvable.");
            System.out.println(validationMessage);
            return;
        }

        solveSudoku();
    }

    /**
     * Load the grid from a file.
     */
    private void loadGridFromFile() {
        System.out.println("Veuillez entrer le chemin du fichier : src/assets/");
        String cheminFichier = scanner.nextLine();
        try {
            sudoku.importGridFromFile("src/assets/"+cheminFichier);
            System.out.println("Grille chargée avec succès !");
            sudoku.printGrid();
        } catch (FileNotFoundException e) {
            System.out.println("Erreur : fichier introuvable.");
            throw new IllegalArgumentException("Erreur : fichier introuvable.");
        }
    }

    /**
     * Enter the grid manually.
     * @param size The size of the grid
     */
    private void enterGridManually(int size) {
        System.out.println("Veuillez saisir la grille sous forme d'une seule ligne (" + size + "x" + size + " caractères). 0 pour les cases vides :");
        String sudokuInline = scanner.nextLine();
        try {
            sudoku.enterGridManually(sudokuInline);
        } catch (IllegalArgumentException e) {
            System.out.println("Erreur au moment d'entrer la grille manuellement : " +e.getMessage());
            throw new IllegalArgumentException("Erreur au moment d'entrer la grille manuellement : " +e.getMessage());
        }
        System.out.println("Grille saisie avec succès !");
        sudoku.printGrid();
    }

    /**
     * Solve the Sudoku.
     */
    private void solveSudoku() {
        SudokuResolver resolver = new SudokuResolver(sudoku);

        System.out.println("Veuillez choisir une méthode de résolution :");
        System.out.println("1. Retour sur trace");
        System.out.println("2. Règle de déduction");
        System.out.println("3. Mixte");

        int choiceMethode = scanner.nextInt();

        if (resolver.solve(choiceMethode)) {
            System.out.println("Résolu avec succès 🎉");
        } else {
            System.out.println("Impossible à résoudre ❌");
        }
        sudoku.printGrid();
    }

    /**
     * Choose the size of the Sudoku.
     * @return The size of the Sudoku
     */
    private SudokuSize chooseGridSize() {
        System.out.println("Veuillez choisir la taille du Sudoku :");
        for (SudokuSize size : SudokuSize.values()) {
            System.out.println(size.ordinal() + 1 + ". " + size.getLabel());
        }

        int choiceSize = scanner.nextInt();
        scanner.nextLine();

        return SudokuSize.fromChoice(choiceSize);
    }

    /**
     * Choose the symbols of the Sudoku.
     * @param size The size of the Sudoku
     * @return The symbols of the Sudoku
     */
    private String chooseSymbols(int size) {
        System.out.println("Veuillez choisir les symboles du Sudoku :");
        System.out.println("1. Chiffres");
        System.out.println("2. Lettres");
//        System.out.println("3. Emojis");

        int choiceSymbols = scanner.nextInt();
        scanner.nextLine();


        switch (choiceSymbols) {
            case 1:
                return "123456789".substring(0, size);
            case 2:
                return "ABCDEFGHIJKL".substring(0, size);
            default:
                return "123456789".substring(0, size);
        }

    }
    // </editor-fold>
}
