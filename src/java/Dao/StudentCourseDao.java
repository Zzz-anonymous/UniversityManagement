/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Dao;

import Entity.Course;
import Entity.Student;
import Entity.StudentCourse;
import adt.LinkedList;
import adt.ListInterface;
import java.util.Iterator;

/**
 *
 * @author Zy
 */
public class StudentCourseDao {
    // Create an LinkedList to store course objects

    private static ListInterface<StudentCourse> scList = new LinkedList<>();
    private static ListInterface<Student> sList = StudentDao.getAllStudents();

    // add new course
    public static void addCourse(StudentCourse sc) {
        scList.add(sc);
    }

    public static void addCourse(ListInterface<StudentCourse> courses) {
        // Iterate over the courses list and add each course individually
        for (int i = 1; i <= courses.getTotalNumberOfData(); i++) {
            StudentCourse course = courses.getData(i);
            scList.add(course);
        }
    }

    // get all course details
    public static ListInterface<StudentCourse> getAllCourses() {
        return scList;
    }

    // get a studentCourse info by id
    public static StudentCourse getStudentCourseById(String id) {
        // Iterate over the list of students to find the one with the matching ID
        for (int i = 1; i <= scList.getTotalNumberOfData(); i++) {
            StudentCourse sc = scList.getData(i);
            if (sc.getStudentId().equals(id)) {
                return sc;
            }
        }
        // Return null if no student with the given ID is found
        return null;
    }

    // get studentCourse list by id
    public static LinkedList<StudentCourse> getStudentCourseListById(String id) {
        LinkedList<StudentCourse> studentCourses = new LinkedList<>();
        // Iterate over the list of students to find the one with the matching ID
        for (int i = 1; i <= scList.getTotalNumberOfData(); i++) {
            StudentCourse sc = scList.getData(i);
            if (sc.getStudentId().equals(id)) {
                studentCourses.add(sc);
            }
        }
        // Return null if no student with the given ID is found
        return studentCourses;
    }

    // get Student Course 1 object by id
    public static Student getStudentById(String id) {
        // Iterate over the list of students to find the one with the matching ID
        for (int i = 1; i <= sList.getTotalNumberOfData(); i++) {
            Student s = sList.getData(i);
            if (s.getId().equals(id)) {
                return s;
            }
        }
        // Return null if no student with the given ID is found
        return null;
    }

    // delete course
    public static void deleteStudentCourse() {
        scList.clear();
    }

    // update Course
    public static void replaceCourseList(String studentId, ListInterface<StudentCourse> updatedCourses) {
        // Perform database operations to replace the existing list of courses with the updated one
        // For example, you might delete all existing courses associated with the student and then add the new courses
        deleteStudentCourse();
        addCourse(updatedCourses);
    }

   
    // retrieve course status by using courseId
    public static String getStudentCourseStatusById(String courseId) {
        // Iterate over the list of students to find the one with the matching ID
        for (int i = 1; i <= scList.getTotalNumberOfData(); i++) {
            StudentCourse sc = scList.getData(i);
            if (sc.getCourseId().contains(courseId)) {
                return sc.getCourseStatus();
            }
        }
        // Return null if no student with the given ID is found
        return null;
    }

    // retrieve course id by using studentId
    public static ListInterface<String> getCourseIdById(String studentId) {
        // Iterate over the list of students to find the one with the matching ID
        for (int i = 1; i <= scList.getTotalNumberOfData(); i++) {
            StudentCourse sc = scList.getData(i);
            if (sc.getStudentId().contains(studentId)) {
                return sc.getCourseId();
            }
        }
        // Return null if no student with the given ID is found
        return null;
    }

    public static StudentCourse getStudentCourseByIdAndCourseId(String studentId, String courseId) {
        // Iterate over the list of student courses to find the one with the matching student ID and course ID
        for (int i = 1; i <= scList.getTotalNumberOfData(); i++) {
            StudentCourse sc = scList.getData(i);
            // Check if both student ID and course ID match
            if (sc.getStudentId().equals(studentId) && sc.getCourseId().contains(courseId)) {
                return sc;
            }
        }
        // Return null if no matching student course is found
        return null;
    }


}
