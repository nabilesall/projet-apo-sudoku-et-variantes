package sudoku.enums;

/**
 * The `LookAndFeel` enum represents the different look and feel of the application.
 * It contains the different look and feel of the application.
 *
 * @author Idrissa and Marouane
 */
public enum LookAndFeel {

    //<editor-fold defaultstate="collapsed" desc="CONSTANTS">
    /**
     * Represent the Look And Feel "Metal"
     */
    METAL("Metal"),

    /**
     * Represent the Look And Feel "Nimbus"
     */
    NIMBUS("Nimbus"),

    /**
     * Represent the Look And Feel "CDE/Motif"
     */
    CDE_MOTIF("CDE/Motif"),

    /**
     * Represent the Look And Feel "Windows"
     */
    WINDOWS("Windows"),

    /**
     * Represent the Look And Feel "Windows Classic"
     */
    WINDOWS_CLASSIC("Windows Classic");
    //</editor-fold>

    //ATTRIBUTS
    /**
     *
     */
    private final String name;

    //CONSTRUCTORS
    /**
     * Crée un objet look and feel
     * @param name c'est le nom du look
     */
    private LookAndFeel(String name) {
        this.name = name;
    }

    // PUBLIC METHODS
    @Override
    public String toString() {
        return this.name;
    }

}