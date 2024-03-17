/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Dao;

/**
 *
 * @author Zy
 */
import Utility.*;
import Entity.Student;
import java.sql.*;
import adt.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.util.Iterator;
import java.util.NoSuchElementException;
import javax.servlet.ServletContext;

public class StudentDao {

    // Create an ArrayList to store Student objects
    private static LinkedListInterface<Student> sList = new LinkedList<>();
    private static LinkedListInterface<Student> inactiveList = new LinkedList<>();

    // add new student
    public static void addStudent(Student s) {
        // Add student to LinkedList
        sList.add(s);
    }

    // get all student details
    public static LinkedListInterface<Student> getAllStudents() {
        return sList;
    }

    // delete student
    // cannot directly student but move it into withdraw List
    public static boolean deleteStudent(String id) {
        // Check if the student ID is available
        int index = getIndex(id);
        if (index != -1) {
            Student student = sList.getData(index);

            // Remove the student from sList regardless of status
            sList.remove(index);

            if (student.getStatus() == 0) {
                // If the status is inactive, add the student to removeList
                inactiveList.add(student);
            }
            return true; // Deletion successful
        } else {
            return false; // Student not found
        }
    }

    // update student
    public static boolean updateStudent(String id, Student updatedStudent) {
        // Find the index of the student with the given ID
        int index = getIndex(id);

        // If the student is found, update its information
        if (index != -1) {
            // Replace the student at the found index with the updated student
            sList.replace(index, updatedStudent);
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
        Iterator<Student> iterator = sList.getIterator();

        // Initialize index counter
        int index = 1;

        // Iterate over the list
        while (iterator.hasNext()) {
            Student s = iterator.next();
            if (s != null) {
                if (s.getId().equals(trimmedId)) {
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
    public static Student getStudentById(String id) {
        Iterator<Student> iterator = sList.getIterator();
        while (iterator.hasNext()) {
            Student student = iterator.next();
            if (student.getId().equals(id)) {
                return student;
            }
        }
        return null; // Return null if no student with the given ID is found
    }

    // search students name
    public static LinkedListInterface<Student> searchStudent(String name) {
        LinkedListInterface<Student> searchResults = new LinkedList<>(); // Using LinkedList instead of ArrayList

        Iterator<Student> iterator = sList.getIterator(); // Assuming displaySList is an instance of ListInterface<Student>

        while (iterator.hasNext()) {
            Student s = iterator.next();
            // Check if the student's name contains the search term (case-insensitive)
            if (s.getName().toLowerCase().contains(name.toLowerCase())) {
                searchResults.add(s);
            }
        }

        return searchResults;
    }
}
