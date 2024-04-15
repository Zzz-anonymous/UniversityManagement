/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Entity;

import adt.ListInterface;

/**
 *
 * @author Zy
 */
public class ProgrammeCourse {
    private String programmeId;
    private ListInterface<String> courseId;

    public ProgrammeCourse() {
    }

    // Constructor
    public ProgrammeCourse(String programmeId, ListInterface<String> courseId) {
        this.programmeId = programmeId;
        this.courseId = courseId;
    }

    // Getters and setters
    public String getProgrammeId() {
        return programmeId;
    }

    public void setProgrammeId(String programmeId) {
        this.programmeId = programmeId;
    }

    public ListInterface<String> getCourseId() {
        return courseId;
    }

    public void setCourseId(ListInterface<String> courseId) {
        this.courseId = courseId;
    }
}
