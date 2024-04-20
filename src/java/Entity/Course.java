/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Entity;

import java.time.LocalTime;
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
    private Tutor tutor;
    private String startTime;
    private String endTime;
    private int duration;
    private String dayOfWeek;
    private Faculty faculty;
    
    
    public Course(String Id, String name, String details,ListInterface<String> courseTypes, int creditHours,
            Tutor tutor, String dayOfWeek, String startTime, int duration,
            ListInterface<Programme>  programme,Faculty faculty, int availability) 
    {
        this.Id = Id;
        this.name = name;
        this.details = details;
        this.courseTypes = courseTypes;
        this.creditHours = creditHours;
        setFeesAutomatically(creditHours);
        this.tutor = tutor;
        this.dayOfWeek = dayOfWeek;
        this.startTime = startTime;
        this.duration = duration;
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

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public String getDayOfWeek() {
        return dayOfWeek;
    }

    public void setDayOfWeek(String dayOfWeek) {
        this.dayOfWeek = dayOfWeek;
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

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }
    
    public String getEndTime() {
        // Split the start time string into hours and minutes
        String[] startTimeParts = startTime.split(":");
        int startHour = Integer.parseInt(startTimeParts[0]);
        int startMinute = Integer.parseInt(startTimeParts[1]);

        // Add the duration to the start time
        int endHour = startHour + duration;
        int endMinute = startMinute;

        // Handle the case when the addition of the duration exceeds 60 minutes
        if (endHour >= 24) {
            endHour -= 24; // Reset to the next day if end time exceeds 24 hours
        }

        // Format the end time as a string
        String endHourStr = String.format("%02d", endHour); // Ensure two digits for hours
        String endMinuteStr = String.format("%02d", endMinute); // Ensure two digits for minutes
        endTime = endHourStr + ":" + endMinuteStr;

        return endTime;
    }
    

}
