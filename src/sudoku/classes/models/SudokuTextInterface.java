package sudoku.classes.models;

import java.io.FileNotFoundException;
import java.util.Scanner;

public class SudokuTextInterface {

    private final Scanner scanner;
    private Sudoku sudoku;

    public SudokuTextInterface(Scanner scanner) {
        this.scanner = scanner;
    }

    public void start() {
        System.out.println("Vous avez choisi l'interface textuelle.");

        // Demander la taille du Sudoku
        int size = chooseGridSize();
        if (size == -1) {
            System.out.println("Choix invalide. Veuillez redémarrer le programme.");
            return;
        }
        sudoku = new Sudoku(size, "0-9"); // Initialisation de la grille avec la taille choisie

        System.out.println("Comment voulez-vous entrer la grille à résoudre ?");
        System.out.println("1. Upload d'un fichier");
        System.out.println("2. Saisie en ligne de commande");

        int choiceGrille = scanner.nextInt();
        scanner.nextLine(); // Consomme la ligne restante

        switch (choiceGrille) {
            case 1 -> loadGridFromFile();
            case 2 -> enterGridManually();
            default -> {
                System.out.println("Choix invalide. Veuillez redémarrer le programme.");
                return;
            }
        }

        // Validation de la grille
        String validationMessage = sudoku.validateGrid();
        if (!validationMessage.equals("OK")) {
            System.out.println("Erreur : la grille n'est pas valide ou n'est pas résolvable.");
            System.out.println(validationMessage);
            return;
        }

        solveSudoku();
    }

    private void loadGridFromFile() {
        System.out.println("Veuillez entrer le chemin du fichier :");
        String cheminFichier = scanner.nextLine();
        try {
            sudoku.importGridFromFile(cheminFichier);
            System.out.println("Grille chargée avec succès !");
            sudoku.printGrid();
        } catch (FileNotFoundException e) {
            System.out.println("Erreur : fichier introuvable.");
        }
    }

    private void enterGridManually() {
        System.out.println("Veuillez saisir la grille sous forme d'une seule ligne (81 chiffres, 0 pour les cases vides) :");
        String sudokuInline = scanner.nextLine();
        try {
            sudoku.enterGridManually(sudokuInline);
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
            return;
        }
        System.out.println("Grille saisie avec succès !");
        sudoku.printGrid();
    }

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

    private int chooseGridSize() {
        System.out.println("Veuillez choisir la taille du Sudoku :");
        System.out.println("1. 4x4");
        System.out.println("2. 9x9");
        System.out.println("3. 16x16");

        int choiceSize = scanner.nextInt();
        scanner.nextLine(); // Consomme la ligne restante

        return switch (choiceSize) {
            case 1 -> 4;
            case 2 -> 9;
            case 3 -> 16;
            default -> -1;
        };
    }
}
