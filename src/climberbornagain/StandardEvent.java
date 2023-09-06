/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package climberbornagain;

/**
 *
 * @author Teagan
 */
public class StandardEvent extends Event {
    
    private int heightInfluence;
    private int foodInfluence;
    
    public StandardEvent(String grphc, boolean stts, int HI, int FI) {
        super(grphc,stts);
        heightInfluence = HI;
        foodInfluence = FI;
    }

    public int getHeightInfluence() {
        return heightInfluence;
    }

    public int getFoodInfluence() {
        return foodInfluence;
    }
    
}
