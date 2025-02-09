package sudoku.classes.fenetre;

import sudoku.classes.models.Sudoku;
import sudoku.classes.models.SudokuResolver;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Map;

public class SudokuPanel {
    private JPanel contentPane;
    private JPanel gridPanel;
    private JPanel menuPanel;
    private JComboBox<String> sizeCombobox;
    private JComboBox<String> symbolsCombobox;
    private JLabel statusLabel;
    private Sudoku sudoku;
    private Map<Integer, Character> intToSymbol;

    JButton solveButton = new JButton("Résoudre");
    JButton resetButton = new JButton("Réinitialiser");
    JButton uploadButton = new JButton("Uploader un fichier");

    public SudokuPanel() {
        // Initialisation des tailles de Sudoku
        String[] sudokuSizes = {"4x4", "9x9", "16x16"};
        String[] sudokuSymbols = {"Chiffres", "Lettres"};

        // Ajout des tailles dans le JComboBox avec "4x4" sélectionné par défaut
        sizeCombobox = new JComboBox<>(sudokuSizes);
        sizeCombobox.setSelectedItem("4x4");
        sizeCombobox.addActionListener(e -> {
            createEmptyGrid((String) sizeCombobox.getSelectedItem(), solveButton);
            String symbols = (gridSize((String) sizeCombobox.getSelectedItem()) == 16) ? "123456789ABCDEFG" : symbols((String) symbolsCombobox.getSelectedItem(), gridSize((String) sizeCombobox.getSelectedItem()));
            sudoku = new Sudoku(gridSize((String) sizeCombobox.getSelectedItem()), symbols);
        });

        // Ajout des symboles dans le JComboBox avec "Chiffres" sélectionné par défaut
        symbolsCombobox = new JComboBox<>(sudokuSymbols);
        symbolsCombobox.setSelectedItem("Chiffres");
        symbolsCombobox.addActionListener(e -> {
//            String symbols = (String) symbolsCombobox.getSelectedItem();
//            sudoku = new Sudoku(gridSize((String) sizeCombobox.getSelectedItem()), symbols.equals("Chiffres") ? "123456789" : "ABCDEFGHIJKL");
            createEmptyGrid((String) sizeCombobox.getSelectedItem(), solveButton);
            String symbols = (gridSize((String) sizeCombobox.getSelectedItem()) == 16) ? "123456789ABCDEFG" : symbols((String) symbolsCombobox.getSelectedItem(), gridSize((String) sizeCombobox.getSelectedItem()));
            sudoku = new Sudoku(gridSize((String) sizeCombobox.getSelectedItem()), symbols);
        });

        // Étiquette de statut
        statusLabel = new JLabel("Prêt", SwingConstants.RIGHT);
        statusLabel.setFont(new Font("Arial", Font.BOLD, 12));
        statusLabel.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 0));

        // Initialisation de la grille de Sudoku (par défaut 9x9)
        String symbols = (gridSize((String) sizeCombobox.getSelectedItem()) == 16) ? "123456789ABCDEFG" : symbols((String) symbolsCombobox.getSelectedItem(), gridSize((String) sizeCombobox.getSelectedItem()));
        sudoku = new Sudoku(gridSize((String) sizeCombobox.getSelectedItem()), symbols);

        // Configuration de l'interface utilisateur
        setupUI();
    }

    private void setupUI() {
        contentPane = new JPanel(new BorderLayout());

        // Panneau de contrôle en haut
        JPanel controlPanel = new JPanel();
        controlPanel.add(new JLabel("Taille du Sudoku :"));
        controlPanel.add(sizeCombobox);

        controlPanel.add(new JLabel("Symboles :"));
        controlPanel.add(symbolsCombobox);

        // Panneau de la grille au centre
        gridPanel = new JPanel();

        // Panneau de menu à droite
        menuPanel = new JPanel();
        menuPanel.setLayout(new BoxLayout(menuPanel, BoxLayout.Y_AXIS));

        uploadButton.addActionListener(e -> uploadFile(e, solveButton));
        resetButton.addActionListener(e ->
        {
            createEmptyGrid((String) sizeCombobox.getSelectedItem(), solveButton);
            sudoku.setGrid();
            solveButton.setEnabled(false);
        });
        solveButton.addActionListener(this::solveSudoku);

        // disable solve button if grid is empty
        solveButton.setEnabled(!sudoku.isEmpty());

        menuPanel.add(solveButton);
        menuPanel.add(resetButton);
        menuPanel.add(uploadButton);
        menuPanel.add(statusLabel);

        contentPane.add(controlPanel, BorderLayout.NORTH);
        contentPane.add(gridPanel, BorderLayout.CENTER);
        contentPane.add(menuPanel, BorderLayout.EAST);

        // Création d'un JFrame pour afficher le JPanel
        JFrame frame = new JFrame("Résolution de Sudoku");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setContentPane(contentPane);
        frame.setSize(1200, 700);
        frame.setVisible(true);

        // Créer une grille vide par défaut (9x9)
        createEmptyGrid((String) sizeCombobox.getSelectedItem(), solveButton);
    }

    private int gridSize(String size) {
        return switch (size) {
            case "4x4" -> 4;
            case "9x9" -> 9;
            case "16x16" -> 16;
            default -> 9;
        };
    }

    private String symbols(String symbols, int size) {
        System.out.println("Symbols: " + symbols);
        return switch (symbols) {
            case "Chiffres" -> "123456789".substring(0, size);
            case "Lettres" -> "ABCDEFGHIJKL".substring(0, size);
            default -> "123456789".substring(0, size);
        };
    }

    private void createEmptyGrid(String size, JButton solveButton) {
        solveButton.setEnabled(false);
        gridPanel.removeAll();

        int gridSize = gridSize(size);
        sudoku.setSize(gridSize);
        sudoku.setGrid();
        int boxSize = (int) Math.sqrt(gridSize);  // Taille du bloc (3 pour 9x9, 2 pour 4x4)

        gridPanel.setLayout(new GridLayout(gridSize, gridSize));

        for (int row = 0; row < gridSize; row++) {
            for (int col = 0; col < gridSize; col++) {
                JTextField cell = new JTextField();
                cell.setHorizontalAlignment(JTextField.CENTER);

                // Ajout des bordures pour séparer visuellement les blocs
                drawLine(gridSize, boxSize, row, col, cell);
            }
        }

        gridPanel.revalidate();
        gridPanel.repaint();
    }

    private void drawLine(int gridSize, int boxSize, int row, int col, JTextField cell) {
        int top = (row % boxSize == 0) ? 3 : 1;
        int left = (col % boxSize == 0) ? 3 : 1;
        int bottom = (row == gridSize - 1) ? 3 : 1;
        int right = (col == gridSize - 1) ? 3 : 1;

        cell.setBorder(BorderFactory.createMatteBorder(top, left, bottom, right, Color.BLACK));

        gridPanel.add(cell);
    }

    private void uploadFile(ActionEvent e, JButton solveButton) {
        JFileChooser fileChooser = new JFileChooser();
        int result = fileChooser.showOpenDialog(null);

        if (result == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            try {
                sudoku.importGridFromFile(selectedFile.getAbsolutePath());
                populateGridFromSudoku();
                setStatusLabel("Grille chargée avec succès !");
                solveButton.setEnabled(true);
            } catch (FileNotFoundException ex) {
                setStatusLabel("Erreur : fichier introuvable.");
                solveButton.setEnabled(false);
            } catch (IllegalArgumentException ex) {
                setStatusLabel(ex.getMessage());
                solveButton.setEnabled(false);
            }
        }
    }

    private void populateGridFromSudoku() {
        gridPanel.removeAll();
        int[][] grid = sudoku.getGrid();
        int gridSize = sudoku.getSize();

        Map<Integer, Character> intToSymbol = sudoku.getIntToSymbol();

        int boxSize = (int) Math.sqrt(gridSize);  // Détermine la taille des blocs (3 pour 9x9, 2 pour 4x4)

        gridPanel.setLayout(new GridLayout(gridSize, gridSize));

        for (int row = 0; row < gridSize; row++) {
            for (int col = 0; col < gridSize; col++) {
                JTextField cell = new JTextField();
                cell.setHorizontalAlignment(JTextField.CENTER);

                if (grid[row][col] != 0) {
                    cell.setText(intToSymbol.get(grid[row][col]).toString());
                    cell.setEditable(false);
                } else  {
                    cell.setText("");
                }

                // Ajout des bordures pour séparer visuellement les blocs
                drawLine(gridSize, boxSize, row, col, cell);
            }
        }

        gridPanel.revalidate();
        gridPanel.repaint();
    }


    private void solveSudoku(ActionEvent e) {
        // Validation de la grille
        String validationMessage = sudoku.validateGrid();
        if (!validationMessage.equals("OK")) {
            System.out.println("Erreur : la grille n'est pas valide ou n'est pas résolvable.");
            setStatusLabel(validationMessage);
            System.out.println(validationMessage);
            return;
        }

        String[] options = {"Retour sur trace", "Règle de déduction", "Mixte"};
        int choice = JOptionPane.showOptionDialog(
                null,
                "Veuillez choisir une méthode de résolution :",
                "Choix de la méthode",
                JOptionPane.DEFAULT_OPTION,
                JOptionPane.INFORMATION_MESSAGE,
                null,
                options,
                options[0]
        );

        if (choice == -1) return; // L'utilisateur a annulé

        setStatusLabel("Résolution en cours...");
        SudokuResolver resolver = new SudokuResolver(sudoku);
        if (resolver.solve(choice + 1)) { // Le choix correspond aux options (1, 2, 3)
            populateGridFromSudoku();
            setStatusLabel("Sudoku résolu avec succès !");
//            JOptionPane.showMessageDialog(null, "Sudoku résolu avec succès !");
        } else {
            setStatusLabel("Impossible de résoudre le Sudoku.");
        }
    }

    public static void main(String[] args) {
        // Lancement de l'application
        SwingUtilities.invokeLater(SudokuPanel::new);
    }

    public void setStatusLabel(String message) {
        statusLabel.setText(message);
    }
}
