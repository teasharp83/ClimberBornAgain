/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package climberbornagain;

/**
 *
 * @author Teagan
 */
public class ChoiceEvent extends Event {
    
    private int panelNumber;
    
    public ChoiceEvent(String grphc, boolean stts, int PN) {
        super(grphc, stts);
        panelNumber = PN;
    }

    public int getPanelNumber() {
        return panelNumber;
    }
}
