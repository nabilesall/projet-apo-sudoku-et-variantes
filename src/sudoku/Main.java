package sudoku;

import sudoku.classes.models.Sudoku;
import sudoku.classes.models.SudokuResolver;

import java.io.FileNotFoundException;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        // Message de bienvenue
        System.out.println("Hello, World!");

        // Création d'un scanner pour lire l'entrée utilisateur
        Scanner scanner = new Scanner(System.in);

        // Choisir l'interface
        System.out.println("Veuillez choisir une interface :");
        System.out.println("1. Interface textuelle");
        System.out.println("2. Interface graphique");

        int choiceInterface = scanner.nextInt();

        switch (choiceInterface) {
            case 1:
                System.out.println("Vous avez choisi l'interface textuelle.");
                System.out.println("Comment voulez-vous entrer la grille à résoudre ?");
                System.out.println("1. Upload d'un fichier");
                System.out.println("2. Saisie en ligne de commande");

                int choiceGrille = scanner.nextInt();
                scanner.nextLine(); // Consomme la ligne restante

                Sudoku sudoku = new Sudoku(9, "0-9"); // Grille classique 9x9 avec chiffres

                switch (choiceGrille) {
                    case 1:
                        System.out.println("Veuillez entrer le chemin du fichier :");
                        String cheminFichier = scanner.nextLine();
                        try {
                            sudoku.importGridFromFile(cheminFichier);
                            System.out.println("Grille chargée avec succès !");
                            sudoku.printGrid();
                        } catch (FileNotFoundException e) {
                            System.out.println("Erreur : fichier introuvable.");
                            return;
                        }
                        break;

                    case 2:
                        System.out.println("Veuillez saisir la grille sous forme d'une seule ligne (81 chiffres, 0 pour les cases vides) :");
                        String sudokuInline = scanner.nextLine();
                        if (!sudoku.enterGridManually(sudokuInline)) {
                            System.out.println("Erreur : la chaîne doit contenir exactement 81 chiffres.");
                            return;
                        }
                        System.out.println("Grille saisie avec succès !");
                        sudoku.printGrid();
                        break;

                    default:
                        System.out.println("Choix invalide. Veuillez redémarrer le programme.");
                        return;
                }

                // Validation de la grille
                if (!sudoku.validateGrid()) {
                    System.out.println("Erreur : la grille n'est pas valide ou n'est pas résolvable.");
                    return;
                }

                SudokuResolver resolver = new SudokuResolver(sudoku);

                // Demander la méthode de résolution
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

                break;

            case 2:
                System.out.println("Vous avez choisi l'interface graphique.");
                // Code spécifique à l'interface graphique peut être ajouté ici
                break;

            default:
                System.out.println("Choix invalide. Veuillez redémarrer le programme.");
                break;
        }

        // Fermer le scanner
        scanner.close();
    }
}