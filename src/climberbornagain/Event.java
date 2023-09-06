/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package climberbornagain;

/**
 *
 * @author Teagan
 */
public class Event {
    
    public String graphic;
    private boolean status;
    
    public Event(String grphc, boolean stts) {
        graphic = grphc;
        status = stts;
    }

    public String getGraphic() {
        return graphic;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }
    
}
