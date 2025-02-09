package sudoku;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import sudoku.classes.models.Sudoku;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class SudokuTest {
    private Sudoku sudoku4x4;
    private Sudoku sudoku9x9;
    private Sudoku sudoku16x16;

    @BeforeEach
    void setUp() {
        sudoku4x4 = new Sudoku(4, "1234");
        sudoku9x9 = new Sudoku(9, "123456789");
        sudoku16x16 = new Sudoku(16, "123456789ABCDEFG");
    }

    @Test
    void testSudokuInitialization() {
        assertEquals(4, sudoku4x4.getSize());
        assertEquals(9, sudoku9x9.getSize());
        assertEquals(16, sudoku16x16.getSize());
    }

    @Test
    void testEmptyGridIsValid() {
        assertEquals("OK", sudoku4x4.validateGrid());
        assertEquals("OK", sudoku9x9.validateGrid());
        assertEquals("OK", sudoku16x16.validateGrid());
    }

    @Test
    void testEnterGridManuallyValid() {
        String grid4x4 = "1200340021430000";
        assertDoesNotThrow(() -> sudoku4x4.enterGridManually(grid4x4));
    }

    @Test
    void testEnterGridManuallyInvalidSize() {
        String invalidGrid = "123"; // Trop court
        Exception exception = assertThrows(IllegalArgumentException.class, () -> sudoku4x4.enterGridManually(invalidGrid));
        assertEquals("Erreur : la chaîne doit contenir exactement 16 chiffres.", exception.getMessage());
    }

    @Test
    void testEnterGridManuallyInvalidSymbol() {
        String invalidGrid = "12A0340021430000";
        Exception exception = assertThrows(IllegalArgumentException.class, () -> sudoku4x4.enterGridManually(invalidGrid));
        assertTrue(exception.getMessage().contains("Symbole invalide"));
    }

    @Test
    void testImportGridFromFileThrowsExceptionOnInvalidSize() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> sudoku4x4.importGridFromFile("src/assets/good.txt"));
        assertTrue(exception.getMessage().contains("Erreur : la grille doit être de taille 4x4"));
    }

    @Test
    void testSymbolMapping() {
        Map<Integer, Character> symbolMap = sudoku4x4.getIntToSymbol();
        assertEquals('1', symbolMap.get(1));
        assertEquals('2', symbolMap.get(2));
        assertEquals('3', symbolMap.get(3));
        assertEquals('4', symbolMap.get(4));
    }
}

