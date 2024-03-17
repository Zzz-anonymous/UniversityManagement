/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Entity;

/**
 *
 * @author Zy
 */
import adt.CircularArrayQueue;

public class Course {
    private String Id;
    private String name;
    private String details;
    private String[] courseTypes;
    private String status;
    private int creditHours;
    private Programme programme;
    private double fees;
    private int availability;
    private int maxCapacity = 200;

    public Course(String Id, String name, String details,String status,String[] courseTypes, int creditHours,Programme programme, int availability) {
        this.Id = Id;
        this.name = name;
        this.details = details;
        this.status = status;
        this.courseTypes = courseTypes;
        this.creditHours = creditHours;
        setFeesAutomatically(creditHours);
        this.programme = programme;
        this.availability = availability;
    }

    public String getId() {
        return Id;
    }

    public void setId(String Id) {
        this.Id = Id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public String[] getCourseTypes() {
        return courseTypes;
    }

    public void setCourseTypes(String[] courseTypes) {
        this.courseTypes = courseTypes;
    }

    

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getCreditHours() {
        return creditHours;
    }

    public void setCreditHours(int creditHours) {
        this.creditHours = creditHours;
        setFeesAutomatically(creditHours);
    }

    public double getFees() {
        return fees;
    }

    public void setFees(double fees) {
        this.fees = fees;
    }

    public Programme getProgramme() {
        return programme;
    }

    public void setProgramme(Programme programme) {
        this.programme = programme;
    }

    public int getAvailability() {
        return availability;
    }

    public void setAvailability(int availability) {
        this.availability = availability;
    }

    public int getMaxCapacity() {
        return maxCapacity;
    }

    public void setMaxCapacity(int maxCapacity) {
        this.maxCapacity = maxCapacity;
    }

    private void setFeesAutomatically(int creditHours){
        switch (creditHours) {
            case 2:
                this.fees = 518;
                break;
            case 3:
                this.fees = 777;
                break;
            case 4:
                this.fees = 1036;
                break;
            default:
                break;
        }
    }
    

}
