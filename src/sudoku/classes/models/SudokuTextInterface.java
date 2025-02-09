package sudoku.classes.models;

import sudoku.enums.SudokuSize;
import sudoku.enums.SudokuSymbols;

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
        SudokuSize selectedSize = chooseGridSize();
        if (selectedSize == null) {
            System.out.println("Choix invalide. Veuillez redémarrer le programme.");
            return;
        }
        int size = selectedSize.getSize(); // Récupère la taille en int

        // demander les symobols
        String symbols = (size == 16) ? "123456789ABCDEFG" : chooseSymbols(size);

        System.out.println("symbols : " + symbols);

        sudoku = new Sudoku(size, symbols); // Initialisation de la grille avec la taille choisie

        System.out.println("Comment voulez-vous entrer la grille à résoudre ?");
        System.out.println("1. Upload d'un fichier");
        System.out.println("2. Saisie en ligne de commande");

        int choiceGrille = scanner.nextInt();
        scanner.nextLine(); // Consomme la ligne restante

        switch (choiceGrille) {
            case 1 -> loadGridFromFile();
            case 2 -> enterGridManually(size);
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
            throw new IllegalArgumentException("Erreur : fichier introuvable.");
        }
    }

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

    private SudokuSize chooseGridSize() {
        System.out.println("Veuillez choisir la taille du Sudoku :");
        for (SudokuSize size : SudokuSize.values()) {
            System.out.println(size.ordinal() + 1 + ". " + size.getLabel());
        }

        int choiceSize = scanner.nextInt();
        scanner.nextLine();

        return SudokuSize.fromChoice(choiceSize);
    }

    private String chooseSymbols(int size) {
        System.out.println("Veuillez choisir les symboles du Sudoku :");
        System.out.println("1. Chiffres");
        System.out.println("2. Lettres");
//        System.out.println("3. Emojis");

        int choiceSymbols = scanner.nextInt();
        scanner.nextLine();

        return switch (choiceSymbols) {
            case 1 -> "123456789".substring(0, size);
            case 2 -> "ABCDEFGHIJKL".substring(0, size);
//            case 3 -> "🐶🐹🐰🦊🐻🐼🐨🐯🦁🐮🐷🐽🐸🐵🐶".substring(0, size);
            default -> "123456789".substring(0, size);
        };


    }
}
