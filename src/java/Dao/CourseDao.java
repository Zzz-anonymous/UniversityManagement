/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Dao;

import Entity.Course;
import adt.*;
import java.util.Iterator;

/**
 *
 * @author Zy
 */
public class CourseDao {
    // Create an ArrayList to store Student objects

    private static LinkedListInterface<Course> cList = new LinkedList<>();

    // add new student
    public static void addCourse(Course s) {
        cList.add(s);
    }

    // get all student details
    public static LinkedListInterface<Course> getAllCourses() {
        return cList;
    }

    // delete student
    public static boolean deleteCourse(String id) {
        // Check if the student ID is available
        int index = getIndex(id);
        if (index != -1) {
            // Remove the student from the list
            cList.remove(index);
            return true; // Deletion successful
        } else {
            return false; // Student not found
        }
    }

    // update student
    public static boolean updateStudent(String id, Course updatedCourse) {
        // Find the index of the student with the given ID
        int index = getIndex(id);

        // If the student is found, update its information
        if (index != -1) {
            // Replace the student at the found index with the updated student
            cList.replace(index, updatedCourse);
            return true; // Return true to indicate a successful update
        }

        // If the student is not found, return false
        return false;
    }
    
    // check the availability of the id 
    public static boolean availableId(String id) {
        // return value if the index number is available
        return getIndex(id) >= 0;
    }

    // check the index number based on id
    public static int getIndex(String id) {
        // Trim the provided ID to remove leading and trailing whitespace
        String trimmedId = id.trim();

        // Get an iterator for the LinkedList
        Iterator<Course> iterator = cList.getIterator();

        // Initialize index counter
        int index = 1;

        // Iterate over the list
        while (iterator.hasNext()) {
            Course c = iterator.next();
            if (c != null) {
                if (c.getId().equals(trimmedId)) {
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

    // get student info by id
    public static Course getCourseById(String id) {
        // Iterate over the list of students to find the one with the matching ID
        for (int i = 1; i <= cList.getTotalNumberOfData(); i++) {
            Course s = cList.getData(i);
            if (s.getId().equals(id)) {
                return s;
            }
        }
        // Return null if no student with the given ID is found
        return null;
    }

    // search students name
    public static ListInterface<Course> searchStudent(String name) {
        ListInterface<Course> searchResults = new ArrayList<>();
        // Loop through all students to find matches
        for (int i = 1; i <= cList.getTotalNumberOfData(); i++) {
            Course s = cList.getData(i);
            // Check if the student's name contains the search term (case-insensitive)
            if (s.getName().toLowerCase().contains(name.toLowerCase())) {
                searchResults.add(s);
            }
        }
        return searchResults;
    }

}
