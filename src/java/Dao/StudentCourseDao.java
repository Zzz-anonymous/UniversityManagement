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

    private static ListInterface<Course> cList = (ListInterface<Course>) CourseDao.getAllAvailableCourses();
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

    // get studentCourse list by student id
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

    // get Student Course 1 object by student id
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

    // get Student Course 1 object by course id
    public static StudentCourse getStudentCourseBycId(String cId) {
        // Iterate over the list of studentCpurse to find the one with the matching ID
        for (int i = 1; i <= scList.getTotalNumberOfData(); i++) {
            StudentCourse sc = scList.getData(i);
            if (sc.getCourseId().contains(cId)) {
                return sc;
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

    public static StudentCourse getStudentCourseBysIdAndcId(String studentId, String courseId) {
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

    public static boolean checkValidCourseTime(ListInterface<StudentCourse> list, String studentId, String dayOfWeek, String startTime) {
        // Iterate over all courses taken by the student
        for (int i = 1; i <= list.getTotalNumberOfData(); i++) {
            StudentCourse enrolledCourse = list.getData(i);
            ListInterface<String> courseIds = enrolledCourse.getCourseId();
            // Iterate over the course IDs associated with the enrolled course
            for (int j = 1; j <= courseIds.getTotalNumberOfData(); j++) {
                String courseId = courseIds.getData(j);
                // Get the course details of the enrolled course
                Course enrolledCourseDetails = CourseDao.getCourseById(courseId);
                // Check if the start time and day of the week of any enrolled course match the proposed course timing
                if (enrolledCourseDetails != null && enrolledCourseDetails.getDayOfWeek().equals(dayOfWeek)
                        && enrolledCourseDetails.getStartTime().equals(startTime)) {
                    return true; // Course time conflict
                }
            }
        }
        // If no conflicting course is found, the course time is valid
        return false;
    }

  
    public static ListInterface<Course> filterCourses(ListInterface<StudentCourse> scList, ListInterface<Course> cList, String id) {
        ListInterface<Course> filteredCourses = new LinkedList<>();
        if (scList != null && cList != null) {
            for (int k = 1; k <= scList.getTotalNumberOfData(); k++) {
                StudentCourse sc = scList.getData(k);
                if (sc.getStudentId().equals(id)) {

                    for (int j = 1; j <= cList.getTotalNumberOfData(); j++) {
                        Course c = cList.getData(j);
                        if (sc.getCourseId().contains(c.getId())) {
                            filteredCourses.add(c);
                            break;

                        }

                    }

                }
            }

        }
        // Check if no courses were found, return null
        if (filteredCourses.isEmpty()) {
            return null;
        }

        return filteredCourses;
}

}
