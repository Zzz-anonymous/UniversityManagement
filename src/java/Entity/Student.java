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
public class Student extends Person{
    
    private int paymentStatus;
    private String programmeId;
//     private LinkedListInterface<Course> enrolledCourses; 

    
    public Student(String id, String name,  String gender, String email, int status, int paymentStatus, String programmeId) {
        super(id,name,gender,email,status);
        this.paymentStatus = paymentStatus;
        this.programmeId = programmeId;
//        this.enrolledCourses = new LinkedList<>(); // Initialize the list of enrolled courses
    }
    

    public int getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(int paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    public String getProgrammeId() {
        return programmeId;
    }

    public void setProgrammeId(String programmeId) {
        this.programmeId = programmeId;
    }
//
//    public LinkedListInterface<Course> getEnrolledCourses() {
//        return enrolledCourses;
//    }
//
//    public void setEnrolledCourses(LinkedListInterface<Course> enrolledCourses) {
//        this.enrolledCourses = enrolledCourses;
//    }
    
    
    
}
