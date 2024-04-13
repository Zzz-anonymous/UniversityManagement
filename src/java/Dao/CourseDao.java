/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Dao;

import Entity.Course;
import Entity.Faculty;
import Entity.Programme;
import Utility.Tools;
import adt.*;
import java.util.Iterator;

/**
 *
 * @author Zy
 */
public class CourseDao {

    // Create an LinkedList to store course objects
    private static ListInterface<Course> cList = new LinkedList<>();

    private static ListInterface<Programme> pList = new LinkedList<>();

    // add new course
    public static void addCourse(Course s) {
        cList.add(s);
    }

    // get all course details
    public static ListInterface<Course> getAllCourses() {
        return cList;
    }

    // delete course
    public static boolean deleteCourse(String id, ListInterface<Course> list) {
        // Check if the student ID is available
        int index = getIndex(id, list);
        if (index != -1) {
            // Remove the student from the list
            cList.remove(index);
            return true; // Deletion successful
        } else {
            return false; // Student not found
        }
    }

    // update course
    public static boolean updateStudent(String id, Course updatedCourse, ListInterface<Course> list) {
        // Find the index of the student with the given ID
        int index = getIndex(id, list);

        // If the student is found, update its information
        if (index != -1) {
            // Replace the student at the found index with the updated student
            cList.update(index, updatedCourse);
            return true; // Return true to indicate a successful update
        }

        // If the student is not found, return false
        return false;
    }

    // check the availability of the id 
    public static boolean availableId(String id, ListInterface<Course> list) {
        // return value if the index number is available
        return getIndex(id, list) >= 0;
    }

    // check the index number based on id
    public static int getIndex(String id, ListInterface<Course> list) {
        // Trim the provided ID to remove leading and trailing whitespace
        String trimmedId = id.trim();

        // Get an iterator for the LinkedList
        Iterator<Course> iterator = list.getIterator();

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

    // get course info by id
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

    
    // get course info by id(array)
    public static ListInterface<Course> validateCourseIds(String[] ids) {
    ListInterface<Course> validCourses = new LinkedList<>(); // Create a list to store valid courses

    // Iterate over the array of course IDs
    for (String id : ids) {
        Course course = CourseDao.getCourseById(id); // Get the course object corresponding to the ID
        if (course != null) {
            validCourses.add(course); // If course object is not null, add it to the list of valid courses
        }
    }

    return validCourses; // Return the list of valid courses
}

    

    public static Course findCourseByName(String name) {
        for (int i = 1; i <= cList.getTotalNumberOfData(); i++) {
            Course c = cList.getData(i);
            if (c.getName().equals(name)) {
                return c;
            }
        }

        // If no matching programme is found, return null or throw an exception
        return null;
    }
    
    // use course object to get all available course
    public static Course getAvailableCourses() {
        for (int i = 1; i <= cList.getTotalNumberOfData(); i++) {
            Course c = cList.getData(i);
            if (c.getAvailability() == 1) {
                return c;
            }
        }

        // If no matching programme is found, return null or throw an exception
        return null;
        
    }
    
    // use course object to get all available course
    public static ListInterface<Course> getAllAvailableCourses() {
        ListInterface<Course> availableCourses = new LinkedList<>();

        // Iterate through the mergedList and add students with matching programmeId
        Iterator<Course> iterator = cList.getIterator();
        while (iterator.hasNext()) {
            Course c = iterator.next();
            if (c.getAvailability()== 1 ){
                availableCourses.add(c);
            }
        }

        return availableCourses;
    
        
    }
    
    
    // search courses name
    public static ListInterface<Course> searchCourses(String name) {
        ListInterface<Course> searchResults = new LinkedList<>(); // Using LinkedList instead of ArrayList

        Iterator<Course> iterator = cList.getIterator(); // Assuming displaySList is an instance of ListInterface<Student>

        while (iterator.hasNext()) {
            Course c = iterator.next();
            // Check if the student's name contains the search term (case-insensitive)
            if (c.getName().toLowerCase().contains(name.toLowerCase()) && c.getAvailability() == 1 ) {
                searchResults.add(c);
            }
        }

        return searchResults;
    }
    
    // find faculties name
    public static Faculty findfacultiesByName(String name) {
        ListInterface<Faculty> faculty = Tools.initializeFaculties();

        for (int i = 1; i <= faculty.getTotalNumberOfData(); i++) {
            Faculty f = faculty.getData(i);
            if (f.getName().equals(name)) {
                return f;
            }
        }

        // If no matching programme is found, return null or throw an exception
        return null;
    }
    
        // get Course info by FacultyId
    public static ListInterface<Course> getCoursesByFid(String fid) {
        ListInterface<Course> filterResult = new LinkedList<>();

        // Iterate through the mergedList and add students with matching programmeId
        Iterator<Course> iterator = cList.getIterator();
        while (iterator.hasNext()) {
            Course c = iterator.next();
            if (c.getFaculty().getId().equals(fid)) {
                filterResult.add(c);
            }
        }

        return filterResult;
    }

}
