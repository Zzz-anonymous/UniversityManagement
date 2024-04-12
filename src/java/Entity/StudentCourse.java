/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Entity;

import adt.LinkedListInterface;

/**
 *
 * @author Zy
 */
public class StudentCourse {
    private String studentId;
    private LinkedListInterface<String> courseId;
    private String courseStatus; 

    public StudentCourse(String studentId, LinkedListInterface<String> courseId, String courseStatus) {
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

    public LinkedListInterface<String> getCourseId() {
        return courseId;
    }

    public void setCourseId(LinkedListInterface<String> courseId) {
        this.courseId = courseId;
    }

    public String getCourseStatus() {
        return courseStatus;
    }

    public void setCourseStatus(String courseStatus) {
        this.courseStatus = courseStatus;
    }
}
