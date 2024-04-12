/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Entity;

/**
 *
 * @author Zy
 */
public class ProgrammeCourse {
    private String programmeId;
    private String courseId;

    public ProgrammeCourse() {
    }

    // Constructor
    public ProgrammeCourse(String programmeId, String courseId) {
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

    public String getCourseId() {
        return courseId;
    }

    public void setCourseId(String courseId) {
        this.courseId = courseId;
    }
}
