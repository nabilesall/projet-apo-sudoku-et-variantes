package sudoku;

// <editor-fold defaultstate="collapsed" desc="IMPORTS">
import sudoku.classes.fenetre.SudokuPanel;
import sudoku.classes.models.SudokuTextInterface;
import sudoku.enums.LookAndFeel;

import java.util.Scanner;
// </editor-fold>

/**
 * The `Main` class represents the main class of the Sudoku.
 * It contains the main method to start the program.
 * It allows the user to choose the interface (text or graphical).
 */
public class Main {

    // <editor-fold defaultstate="collapsed" desc="MAIN">
    /**
     * Main method to start the program.
     * @param args The arguments of the program
     */
    public static void main(String[] args) {

        initLookAndFeel(LookAndFeel.NIMBUS);

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

    /**
     * Initialise le Look And Feel (le type d'affichage de la fenêtre)
     *
     * @param lookAndFeel Correspond au Look And Feel utilisé
     */
    private static void initLookAndFeel(LookAndFeel lookAndFeel) {
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if (lookAndFeel.toString().equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(SudokuPanel.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
    }
    // </editor-fold>
}
