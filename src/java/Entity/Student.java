/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Entity;

/**
 *
 * @author Zy
 */
public class Student extends Person{
    
   
    private String programmeId;

    
    public Student(String id, String name,  String gender, String email, int status, String programmeId) {
        super(id,name,gender,email,status);
       
        this.programmeId = programmeId;
    }
    

    public String getProgrammeId() {
        return programmeId;
    }

    public void setProgrammeId(String programmeId) {
        this.programmeId = programmeId;
    }    
    
}
