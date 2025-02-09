package sudoku.classes.fenetre;

//<editor-fold defaultstate="collapsed" desc=" IMPORTS">
import sudoku.classes.models.Sudoku;
import sudoku.classes.models.SudokuResolver;
import sudoku.enums.SudokuSize;
import sudoku.enums.SudokuSymbols;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Map;
//</editor-fold>

/**
 * The `SudokuPanel` class represents the main panel for the Sudoku application.
 * It handles the user interface components and interactions for creating, solving,
 * and resetting Sudoku grids.
 *
 * @author Idrissa and Marouane
 */
public class SudokuPanel {

    // <editor-fold defaultstate="collapsed" desc="ATTRIBUTES">
    /**
     * The main content pane for the Sudoku application.
     */
    private JPanel contentPane;

    /**
     * The panel for displaying the Sudoku grid.
     */
    private JPanel gridPanel;

    /**
     * The panel for displaying the menu options.
     */
    private JPanel menuPanel;

    /**
     * The combo box for selecting the Sudoku size.
     */
    private JComboBox<String> sizeCombobox;

    /**
     * The combo box for selecting the Sudoku symbols.
     */
    private JComboBox<String> symbolsCombobox;

    /**
     * The label for displaying the status message.
     */
    private JLabel statusLabel;

    /**
     * The Sudoku model for the application.
     */
    private Sudoku sudoku;

    /**
     * The mapping of integers to symbols for the Sudoku grid.
     */
    private Map<Integer, Character> intToSymbol;

    /**
     * The buttons for solving, resetting, and uploading a Sudoku grid.
     */
    JButton solveButton = new JButton("Résoudre");

    /**
     * The buttons for resetting the Sudoku grid.
     */
    JButton resetButton = new JButton("Réinitialiser");

    /**
     * The buttons for uploading a Sudoku grid from a file.
     */
    JButton uploadButton = new JButton("Téléverser un fichier");

    //</editor-fold>

    // <editor-fold defaultstate="collapsed" desc="CONSTRUCTORS">
    /**
     * Creates a new `SudokuPanel` instance with the default Sudoku size and symbols.
     */
    public SudokuPanel() {

        sizeCombobox = new JComboBox<>();
        for (SudokuSize size : SudokuSize.values()) {
            sizeCombobox.addItem(size.getLabel());
        }
        sizeCombobox.setSelectedItem(SudokuSize.SMALL.getLabel());

        sizeCombobox.addActionListener(e -> {
            createEmptyGrid((String) sizeCombobox.getSelectedItem(), solveButton);
            SudokuSize selectedSize = SudokuSize.fromLabel((String) sizeCombobox.getSelectedItem());
            String symbols = (selectedSize == SudokuSize.LARGE)
                    ? SudokuSymbols.HEXADECIMAL.getSymbols(selectedSize.getSize())
                    : symbols((String) symbolsCombobox.getSelectedItem(), selectedSize.getSize());
            sudoku = new Sudoku(selectedSize.getSize(), symbols);
        });

        // The combo box for selecting the Sudoku symbols
        symbolsCombobox = new JComboBox<>();
        for (SudokuSymbols symbolSet : SudokuSymbols.values()) {
            if (symbolSet.getLabel().equals("Hexadécimal")) continue;
            symbolsCombobox.addItem(symbolSet.getLabel());
        }
        symbolsCombobox.setSelectedItem(SudokuSymbols.DIGITS.getLabel());
        symbolsCombobox.addActionListener(e -> {
            createEmptyGrid((String) sizeCombobox.getSelectedItem(), solveButton);
            SudokuSize selectedSize = SudokuSize.fromLabel((String) sizeCombobox.getSelectedItem());
            String symbols = (selectedSize == SudokuSize.LARGE)
                    ? SudokuSymbols.HEXADECIMAL.getSymbols(selectedSize.getSize())
                    : symbols((String) symbolsCombobox.getSelectedItem(), selectedSize.getSize());
            sudoku = new Sudoku(selectedSize.getSize(), symbols);
        });

        // The label for displaying the status message
        statusLabel = new JLabel("Prêt", SwingConstants.RIGHT);
        statusLabel.setFont(new Font("Arial", Font.BOLD, 12));
        statusLabel.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 0));

        // Create a new Sudoku instance with the default size and symbols
        SudokuSize selectedSize = SudokuSize.fromLabel((String) sizeCombobox.getSelectedItem());
        String symbols = (selectedSize == SudokuSize.LARGE)
                ? SudokuSymbols.HEXADECIMAL.getSymbols(selectedSize.getSize())
                : symbols((String) symbolsCombobox.getSelectedItem(), selectedSize.getSize());
        sudoku = new Sudoku(selectedSize.getSize(), symbols);

        // Set up the user interface components
        setupUI();
    }

    //</editor-fold>

    // <editor-fold defaultstate="collapsed" desc="METHODES">
    /**
     * Sets up the user interface components for the Sudoku application.
     */
    private void setupUI() {
        contentPane = new JPanel(new BorderLayout());

        // Control panel at the top
        JPanel controlPanel = new JPanel();
        controlPanel.add(new JLabel("Taille du Sudoku :"));
        controlPanel.add(sizeCombobox);

        controlPanel.add(new JLabel("Symboles :"));
        controlPanel.add(symbolsCombobox);

        // Grid panel in the center
        gridPanel = new JPanel();

        // Menu panel on the right
        menuPanel = new JPanel();
        menuPanel.setPreferredSize(new Dimension(500, 0));
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

        // Create the main frame
        JFrame frame = new JFrame("Résolution de Sudoku");
        frame.setContentPane(contentPane);

        frame.setSize(Toolkit.getDefaultToolkit().getScreenSize());
        frame.setPreferredSize(Toolkit.getDefaultToolkit().getScreenSize());
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        createEmptyGrid((String) sizeCombobox.getSelectedItem(), solveButton);
    }

    /**
     * Returns the size of the Sudoku grid based on the selected label.
     *
     * @param sizeLabel the label for the Sudoku size
     * @return the size of the Sudoku grid
     */
    private int gridSize(String sizeLabel) {
        return SudokuSize.fromLabel(sizeLabel).getSize();
    }

    /**
     * Returns the symbols for the Sudoku grid based on the selected label and size.
     *
     * @param label the label for the Sudoku symbols
     * @param size the size of the Sudoku grid
     * @return the symbols for the Sudoku grid
     */
    private String symbols(String label, int size) {
        return SudokuSymbols.fromLabel(label).getSymbols(size);
    }

    /**
     * Creates an empty Sudoku grid based on the selected size and symbols.
     *
     * @param size the label for the Sudoku size
     * @param solveButton the button for solving the Sudoku grid
     */
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

    /**
     * Draws a line to separate the Sudoku grid cells visually.
     *
     * @param gridSize the size of the Sudoku grid
     * @param boxSize the size of the Sudoku grid box
     * @param row the row index of the grid cell
     * @param col the column index of the grid cell
     * @param cell the text field for the grid cell
     */
    private void drawLine(int gridSize, int boxSize, int row, int col, JTextField cell) {
        int top = (row % boxSize == 0) ? 3 : 1;
        int left = (col % boxSize == 0) ? 3 : 1;
        int bottom = (row == gridSize - 1) ? 3 : 1;
        int right = (col == gridSize - 1) ? 3 : 1;

        cell.setBorder(BorderFactory.createMatteBorder(top, left, bottom, right, Color.BLACK));

        gridPanel.add(cell);
    }

    /**
     * Uploads a Sudoku grid from a file selected by the user.
     *
     * @param e the action event for uploading a file
     * @param solveButton the button for solving the Sudoku grid
     */
    private void uploadFile(ActionEvent e, JButton solveButton) {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Choisir un fichier de grille Sudoku");
        fileChooser.setFileFilter(new javax.swing.filechooser.FileNameExtensionFilter("Fichiers texte", "txt"));
        fileChooser.setPreferredSize(new Dimension(800, 600));
        // open the actual pwd + /src/assets
        fileChooser.setCurrentDirectory(new File(System.getProperty("user.dir") + "/src/assets"));
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

    /**
     * Populates the Sudoku grid with the values from the Sudoku model.
     */
    private void populateGridFromSudoku() {
        gridPanel.removeAll();
        int[][] grid = sudoku.getGrid();
        int gridSize = sudoku.getSize();

        Map<Integer, Character> intToSymbol = sudoku.getIntToSymbol();

        int boxSize = (int) Math.sqrt(gridSize);  // Determines the size of the box (3 for 9x9, 2 for 4x4)

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

                // Add borders to visually separate the blocks
                drawLine(gridSize, boxSize, row, col, cell);
            }
        }

        gridPanel.revalidate();
        gridPanel.repaint();
    }

    /**
     * Solves the Sudoku grid using the selected method.
     *
     * @param e the action event for solving the Sudoku grid
     */
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
            // JOptionPane.showMessageDialog(null, "Sudoku résolu avec succès !");
        } else {
            setStatusLabel("Impossible de résoudre le Sudoku.");
        }
    }

    /**
     * Sets the status message for the Sudoku application.
     *
     * @param message the status message to display
     */
    public void setStatusLabel(String message) {
        statusLabel.setText(message);
    }

    //</editor-fold>
}
