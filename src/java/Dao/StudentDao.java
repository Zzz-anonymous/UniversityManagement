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
import adt.*;


import java.util.Iterator;


public class StudentDao {

    // Create an ArrayList to store Student objects
    private static ListInterface<Student> inactiveList = new LinkedList<>();
    private final static ListInterface<Student> initialzeStudents = Tools.initializeStudents();
    private final static ListInterface<Student> mergedList = new LinkedList<>();
    private static boolean isInitializeStudents = false;

    // add new student
    public static void addStudent(Student s) {
        // Add student to LinkedList
        mergedList.add(s);
        //mergeStudent(mergedList);
    }

    // Retrieve all student records
    public static ListInterface<Student> getAllStudents() {
        mergeStudent(mergedList);
        return mergedList;
    }

        // Retrieve all student records
    public static ListInterface<Student> getInactiveStudents() {
        
        // Return the inactive list
        return inactiveList;
    }
    
    // Merge student lists into the provided list
    public static void mergeStudent(ListInterface<Student> resultList) {
        if (!isInitializeStudents && !initialzeStudents.isEmpty()) {
            for (int i = 1; i <= initialzeStudents.getTotalNumberOfData(); i++) {
                resultList.add(initialzeStudents.getData(i));
            }
            isInitializeStudents = true; // Set the flag to true after merging
        }
    }

    // delete student
    // cannot directly student but move it into withdraw List
    public static boolean deleteStudent(String id, ListInterface<Student> list) {
        // Check if the student ID is available in the given list
        int index = getIndex(id, list);
        if (index != -1) {
            // Get the student from the list
            Student student = list.getData(index);

            // Set the status of the student to indicate deletion
            student.setStatus(0); // set student status as inactive(0)

            // Remove the student from the given list
            list.remove(index);
            inactiveList.add(student);
            return true; // Deletion successful
        } else {
            return false; // Student not found
        }
    }

    // update student
    public static boolean updateStudent(String id, Student updatedStudent, ListInterface<Student> list) {
        // Find the index of the student with the given ID
        int index = getIndex(id, list);

        // If the student is found, update its information
        if (index != -1) {
            // Replace the student at the found index with the updated student
            mergedList.update(index, updatedStudent);
            return true; // Return true to indicate a successful update
        }

        // If the student is not found, return false
        return false;
    }

    // check the availability of the id 
    public static boolean availableId(String id, ListInterface<Student> list) {
        // return value if the index number is available
        return getIndex(id, list) >= 0;
    }

    // check the index number based on id
    public static int getIndex(String id, ListInterface<Student> list) {
        // Trim the provided ID to remove leading and trailing whitespace
        String trimmedId = id.trim();

        // Get an iterator for the LinkedList
        Iterator<Student> iterator = list.getIterator();

        // Initialize index counter
        int index = 1; // Adjusted to zero-based indexing

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

    // get student info by student id
    public static Student getStudentById(String id) {
        Iterator<Student> iterator = mergedList.getIterator();
        while (iterator.hasNext()) {
            Student student = iterator.next();
            if (student.getId().equals(id)) {
                return student;
            }
        }
        return null; // Return null if no student with the given ID is found
    }

    // get active student info by ProgrammeId
    public static ListInterface<Student> getStudentsByPid(String pid) {
        ListInterface<Student> filterResult = new LinkedList<>();

        // Iterate through the mergedList and add students with matching programmeId
        Iterator<Student> iterator = mergedList.getIterator();
        while (iterator.hasNext()) {
            Student s = iterator.next();
            if (s.getProgrammeId().equals(pid)) {
                filterResult.add(s);
            }
        }

        return filterResult;
    }

    // get inactive student info by ProgrammeId
    public static ListInterface<Student> getInactiveStudentsByPid(String pid) {
        ListInterface<Student> filterResult = new LinkedList<>();

        // Iterate through the mergedList and add students with matching programmeId
        Iterator<Student> iterator = inactiveList.getIterator();
        while (iterator.hasNext()) {
            Student s = iterator.next();
            if (s.getProgrammeId().equals(pid)) {
                filterResult.add(s);
            }
        }

        return filterResult;
    }
    
    // search students name
    public static ListInterface<Student> searchStudent(String name) {
        ListInterface<Student> searchResults = new LinkedList<>(); // Using LinkedList instead of ArrayList

        Iterator<Student> iterator = mergedList.getIterator(); // Assuming displaySList is an instance of ListInterface<Student>

        while (iterator.hasNext()) {
            Student s = iterator.next();
            // Check if the student's name contains the search term (case-insensitive)
            if (s.getName().toLowerCase().contains(name.toLowerCase())) {
                searchResults.add(s);
            }
        }

        return searchResults;
    }

//    public static LinkedList<Student> validateStudentIds(String[] selectedIds) {
//        LinkedList<Student> selectedStudents = new LinkedList<>();
//
//        // Get an iterator for the LinkedList
//        Iterator<Student> iterator = mergedList.getIterator();
//
//        // Iterate through the LinkedList
//        while (iterator.hasNext()) {
//            Student student = iterator.next();
//            String id = student.getId(); // Assuming getId() returns the student ID
//
//            // Check if the student ID is in the selected IDs array
//            for (String selectedId : selectedIds) {
//                if (id.equals(selectedId)) {
//                    selectedStudents.add(student);
//                    break; // Move to the next student once found
//                }
//            }
//        }
//
//        return selectedStudents;
//    }

}
