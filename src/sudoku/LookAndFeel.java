package sudoku;/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author nabil
 */
public enum LookAndFeel {

    //<editor-fold defaultstate="collapsed" desc=" CONSTANTES">
    /**
     * Correspond au Look And Feel "Metal"
     */
    METAL("Metal"),

    /**
     * Correspond au Look And Feel "Nimbus"
     */
    NIMBUS("Nimbus"),

    /**
     * Correspond au Look And Feel "CDE/Motif"
     */
    CDE_MOTIF("CDE/Motif"),

    /**
     * Correspond au Look And Feel "Windows"
     */
    WINDOWS("Windows"),

    /**
     * Correspond au Look And Feel "Windows Classic"
     */
    WINDOWS_CLASSIC("Windows Classic");
    //</editor-fold>

    //ATTRIBUTS
    /**
     *
     */
    private final String name;

    //CONSTRUCTEUR
    /**
     * Crée un objet look and feel
     * @param name c'est le nom du look
     */
    private LookAndFeel(String name) {
        this.name = name;
    }

    //METHODE PUBLIQUE
    @Override
    public String toString() {
        return this.name;
    }




}