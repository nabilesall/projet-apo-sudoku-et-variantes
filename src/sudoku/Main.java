package sudoku;

import sudoku.classes.fenetre.SudokuPanel;
import sudoku.classes.models.SudokuTextInterface;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Veuillez choisir une interface :");
        System.out.println("1. Interface textuelle");
        System.out.println("2. Interface graphique");

        int choiceInterface = scanner.nextInt();

        switch (choiceInterface) {
            case 1 -> new SudokuTextInterface(scanner).start();
            case 2 -> new SudokuPanel();
            default -> System.out.println("Choix invalide. Veuillez redémarrer le programme.");
        }

        scanner.close();
    }
}
