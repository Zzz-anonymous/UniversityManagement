/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Dao;

import Entity.Course;
import Entity.Faculty;
import Entity.Programme;
import Entity.StudentCourse;
import Entity.Tutor;
import Utility.Tools;
import adt.*;
import java.util.Iterator;
import java.util.function.Predicate;

/**
 *
 * @author Zy
 */
public class CourseDao {

    // Create an LinkedList to store course objects
    private static ListInterface<Course> cList = new LinkedList<>();
    private static ListInterface<Course> inactiveList = new LinkedList<>();

    // add new course
    public static void addCourse(Course s) {
        cList.add(s);
    }

    // get all course details
    public static ListInterface<Course> getAllCourses() {
        return cList;
    }

    // get inactive Course list
    public static ListInterface<Course> getInactiveCourses() {
        // Return the inactive list
        return inactiveList;
    }

    // delete course
    public static boolean deleteCourse(String id, ListInterface<Course> list) {
        // Check if the course ID is available in the given list
        int index = getIndex(id, list);
        if (index != -1) {
            // Get the course from the list
            Course course = list.getData(index);

            // Set the availability of the course to indicate deletion
            course.setAvailability(0); // set course availability as inactive(0)

            // Remove the course from the given list
            list.remove(index);
            inactiveList.add(course);
            return true; // Deletion successful
        } else {
            return false; // course not found
        }
    }

    // get inactive course list by faculty id
    public static ListInterface<Course> getInactiveCoursesByFid(String fId) {
        ListInterface<Course> filterResult = new LinkedList<>();

        // Iterate through the mergedList and add students with matching programmeId
        Iterator<Course> iterator = inactiveList.getIterator();
        while (iterator.hasNext()) {
            Course c = iterator.next();
            if (c.getFaculty().getId().equals(fId)) {
                filterResult.add(c);
            }
        }

        return filterResult;
    }

    // update course
    public static boolean updateCourse(String id, Course updatedCourse, ListInterface<Course> list) {
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

    // get course info by course id
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
    
    // get course by tutor id
    public static Course getCourseBytId(String tId) {
        // Iterate over the list of students to find the one with the matching ID
        for (int i = 1; i <= cList.getTotalNumberOfData(); i++) {
            Course c = cList.getData(i);
            if (c.getTutor().getId().equals(tId)) {
                return c;
            }
        }
        // Return null if no course with the given tutor ID is found
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
            if (c.getAvailability() == 1) {
                availableCourses.add(c);
            }
        }

        return availableCourses;

    }

    // search courses name
//    public static ListInterface<Course> searchCourses(String name) {
//        ListInterface<Course> searchResults = new LinkedList<>(); // Using LinkedList instead of ArrayList
//
//        Iterator<Course> iterator = cList.getIterator(); // Assuming displaySList is an instance of ListInterface<Student>
//
//        while (iterator.hasNext()) {
//            Course c = iterator.next();
//            // Check if the student's name contains the search term (case-insensitive)
//            if (c.getName().toLowerCase().contains(name.toLowerCase())) {
//                searchResults.add(c);
//            }
//        }
//
//        return searchResults;
//    }
    
    public static ListInterface<Course> searchCourses(String name) {
    ListInterface<Course> searchResults = new LinkedList<>(); // Using LinkedList instead of ArrayList

    // Check if cList is not null
    if (cList != null) {
        // Define a predicate to check if course name contains the search term
        Predicate<Course> nameContains = course -> course.getName().toLowerCase().contains(name.toLowerCase());

        // Use the search method with the defined predicate
        searchResults = cList.search(nameContains);
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

    //----------------------- programmeCourse ---------------------------------------
    // get course object by programmeId
    public static LinkedList<Course> getCoursesByProgrammeId(String programmeId) {
        LinkedList<Course> courses = new LinkedList<>();

        // Iterate over the list of courses
        for (int i = 1; i <= cList.getTotalNumberOfData(); i++) {
            Course c = cList.getData(i);
            // Iterate over the list of programmes for each course
            for (int j = 1; j <= c.getProgramme().getTotalNumberOfData(); j++) {
                Programme p = c.getProgramme().getData(j);
                // Check if the current programme's ID matches the desired ID
                if (p.getId().equals(programmeId)) {
                    courses.add(c); // Add the course containing the matching programme to the list
                    break; // No need to continue checking other programmes for this course
                }
            }
        }

        // Return the list of courses with the given programme ID
        return courses;
    }

    // get available time for taken course
    public static Course getCourseForTimeslotAndDay(String sId, String cId, String day, String startTime) {
        StudentCourse sc = StudentCourseDao.getStudentCourseBysIdAndcId(sId, cId);
        if (sc != null) {
            for (int i = 1; i <= cList.getTotalNumberOfData(); i++) {
                Course c = cList.getData(i);
                // Check if the tutor name matches and the start time and day of the week match
                if (c.getStartTime().equals(startTime) && c.getDayOfWeek().equals(day)) {
                    return c; // Tutor is not available at this time
                }
            }
        }
            // If no conflicting course is found, course is available to assigned
            return null;
    }
        //----------------------- Tutor ---------------------------------------
        // find course by tutor id
    public static LinkedList<Course> getCoursesBytId(String tId) {
        LinkedList<Course> courses = new LinkedList<>();

        // Iterate over the list of courses
        for (int i = 1; i <= cList.getTotalNumberOfData(); i++) {
            Course c = cList.getData(i);
            // Iterate over the list of programmes for each course
            for (int j = 1; j <= c.getProgramme().getTotalNumberOfData(); j++) {
                Programme p = c.getProgramme().getData(j);
                // Check if the current programme's ID matches the desired ID
                if (p.getId().equals(tId)) {
                    courses.add(c); // Add the course containing the matching programme to the list
                    break; // No need to continue checking other programmes for this course
                }
            }
        }

        // Return the list of courses with the given programme ID
        return courses;
    }

    // check the available time for tutor when create a course
    public static boolean getTutorAvailableTime(String tutorName, String startTime, String day) {
        for (int i = 1; i <= cList.getTotalNumberOfData(); i++) {
            Course c = cList.getData(i);
            Tutor tutor = c.getTutor();
            // Check if the tutor name matches and the start time and day of the week match
            if (tutor != null && tutor.getName().equals(tutorName) && c.getStartTime().equals(startTime) && c.getDayOfWeek().equals(day)) {
                return true; // Tutor is not available at this time
            }
        }
        // If no conflicting course is found, tutor is available at this time
        return false;
    }
    
}
