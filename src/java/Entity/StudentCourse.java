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
public class StudentCourse {
    private String studentId;
    private ListInterface<String> courseId;
    private String courseStatus; 

    public StudentCourse(String studentId, ListInterface<String> courseId, String courseStatus) {
        this.studentId = studentId;
        this.courseId = courseId;
        this.courseStatus = courseStatus;
    }

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public ListInterface<String> getCourseId() {
        return courseId;
    }

    public void setCourseId(ListInterface<String> courseId) {
        this.courseId = courseId;
    }

    public String getCourseStatus() {
        return courseStatus;
    }

    public void setCourseStatus(String courseStatus) {
        this.courseStatus = courseStatus;
    }
}
