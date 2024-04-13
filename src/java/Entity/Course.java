/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Entity;

import Utility.Tools;
import adt.ListInterface;

/**
 *
 * @author Zy
 */

public class Course {
    private String Id;
    private String name;
    private String details;
    private ListInterface<String> courseTypes;
    
    private int creditHours;
    private ListInterface<Programme> programme;
    private double fees;
    private int availability;
    private int maxCapacity = 200;
    private Tutor tutor;
    private Faculty faculty;
    
    
    public Course(String Id, String name, String details,ListInterface<String> courseTypes, int creditHours,Tutor tutor, ListInterface<Programme>  programme,Faculty faculty, int availability) {
        this.Id = Id;
        this.name = name;
        this.details = details;
        this.courseTypes = courseTypes;
        this.creditHours = creditHours;
        setFeesAutomatically(creditHours);
        this.tutor = tutor;
        this.programme = programme;
        this.faculty = faculty;
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

    public ListInterface<String> getCourseTypes() {
        return courseTypes;
    }

    public void setCourseTypes(ListInterface<String> courseTypes) {
        this.courseTypes = courseTypes;
    }

    
    public int getCreditHours() {
        return creditHours;
    }

    public void setCreditHours(int creditHours) {
        this.creditHours = creditHours;
        setFeesAutomatically(creditHours);
    }

    public Tutor getTutor() {
        return tutor;
    }

    public void setTutor(Tutor tutor) {
        this.tutor = tutor;
    }

    public double getFees() {
        return fees;
    }

    public void setFees(double fees) {
        this.fees = fees;
    }

    public ListInterface<Programme> getProgramme() {
        return programme;
    }

    public void setProgramme(ListInterface<Programme> programme) {
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

    public Faculty getFaculty() {
        return faculty;
    }

    public void setFaculty(Faculty faculty) {
        this.faculty = faculty;
    }
    

}
