/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Dao;

import Entity.Course;
import Entity.Student;
import Entity.StudentCourse;
import adt.LinkedList;
import adt.LinkedListInterface;
import java.util.Iterator;

/**
 *
 * @author Zy
 */
public class StudentCourseDao {
    // Create an LinkedList to store course objects

    private static LinkedListInterface<StudentCourse> scList = new LinkedList<>();
    private static LinkedListInterface<Student> sList = StudentDao.getAllStudents();

    // add new course
    public static void addCourse(StudentCourse sc) {
        scList.add(sc);
    }

    // get all course details
    public static LinkedListInterface<StudentCourse> getAllCourses() {
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

    // update course
    public static boolean updateStudentCourse(String studentId, String courseId, String status) {
        // Find the index of the student course with the given student ID and course ID
        for (int i = 1; i <= scList.getTotalNumberOfData(); i++) {
            StudentCourse sc = scList.getData(i);
            // Check if both student ID and course ID match
            if (sc.getStudentId().equals(studentId) && sc.getCourseId().contains(courseId)) {
                // Update the status of the found student course
                sc.setCourseStatus(status);
                return true; // Return true to indicate a successful update
            }
        }
        // If the student course is not found, return false
        return false;
    }

    // check the index number based on student id
    public static int getIndex(String id, LinkedListInterface<StudentCourse> list) {
        // Trim the provided ID to remove leading and trailing whitespace
        String trimmedId = id.trim();

        // Get an iterator for the LinkedList
        Iterator<StudentCourse> iterator = list.getIterator();

        // Initialize index counter
        int index = 1;

        // Iterate over the list
        while (iterator.hasNext()) {
            StudentCourse sc = iterator.next();
            if (sc != null) {
                if (sc.getStudentId().equals(trimmedId)) {
                    // If student ID matches, return the index
                    return index;
                }
            }
            // Increment index counter
            index++;
        }

        // If student ID is not found, return -1
        return -1;
    }

    public static int getIndex(String studentId, String courseId, LinkedListInterface<StudentCourse> list) {
        // Trim the provided IDs to remove leading and trailing whitespace
        String trimmedStudentId = studentId.trim();
        String trimmedCourseId = courseId.trim();

        // Get an iterator for the LinkedList
        Iterator<StudentCourse> iterator = list.getIterator();

        // Initialize index counter
        int index = 1;

        // Iterate over the list
        while (iterator.hasNext()) {
            StudentCourse sc = iterator.next();
            if (sc != null) {
                if (sc.getStudentId().equals(trimmedStudentId) && sc.getCourseId().contains(trimmedCourseId)) {
                    // If both student ID and course ID match, return the index
                    return index;
                }
            }
            // Increment index counter
            index++;
        }

        // If student course is not found, return -1
        return -1;
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

    // delete course
    public static boolean deleteStudentCourse(String studentId, String courseId, LinkedListInterface<StudentCourse> list) {
        // Find the index of the student course with the given student ID and course ID
        int index = getIndex(studentId, courseId, list);
        if (index != -1) {
            // Remove the student course from the list
            list.remove(index);
            return true; // Deletion successful
        } else {
            return false; // Student course not found
        }
    }

    // Method to check if the course ID is present in the selected course IDs array
    public static boolean containsCourseId(String courseId, String[] courseIds) {
        for (String id : courseIds) {
            if (id.equals(courseId)) {
                return true;
            }
        }
        return false;
    }

}
