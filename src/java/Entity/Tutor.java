/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Entity;

/**
 *
 * @author Zy
 */
import Utility.Person;

public class Tutor extends Person{
    private String level;

    public Tutor(String id, String name, String ic, String gender, String email, int status, String level) {
        super(id, name, ic, gender, email, status);
        this.level = level;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }
    
    
}
