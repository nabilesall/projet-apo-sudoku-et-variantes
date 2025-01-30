package sudoku.classes.fenetre;

import javax.swing.*;

public class SudokuPanel {
    private JPanel contentPane;
    private JPasswordField passwordField1;
    private JLabel mdpLabel;
    private JPanel panel1;
    private JComboBox<String> genreComboBox;

    public SudokuPanel() {
        // Initialisation des tailles de Sudoku
        String[] sudokuSizes = {"4x4", "9x9", "16x16"};

        // Ajout des tailles dans le JComboBox
        genreComboBox = new JComboBox<>(sudokuSizes);

        // Configuration de l'interface utilisateur (si nécessaire)
        setupUI();
    }

    private void setupUI() {
        contentPane = new JPanel();
//        messageTextField = new JTextField();
//        isTakenCheckBox = new JCheckBox("Is Taken");
//
//        // Ajout des composants au JPanel
//        contentPane.add(new JLabel("Message:"));
//        contentPane.add(messageTextField);
//        contentPane.add(new JLabel("Sudoku Size:"));
        contentPane.add(genreComboBox);
//        contentPane.add(isTakenCheckBox);
//
//        // Création d'un JFrame pour afficher le JPanel
        JFrame frame = new JFrame("Sudoku Panel");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setContentPane(contentPane);
        frame.pack();
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        // Lancement de l'application
        SwingUtilities.invokeLater(SudokuPanel::new);
    }

    private void createUIComponents() {
        // TODO: place custom component creation code here
    }
}
