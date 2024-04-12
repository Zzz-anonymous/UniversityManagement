/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Dao;

import Entity.Course;
import Entity.Programme;
import Entity.ProgrammeCourse;
import adt.LinkedList;
import adt.LinkedListInterface;
import adt.ListInterface;
import java.util.Iterator;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 *
 * @author Zy
 */
public class ProgrammeCourseDao {

    private static final LinkedListInterface<ProgrammeCourse> programmeCourses = new LinkedList<>();
    private static LinkedListInterface<Course> cList = CourseDao.getAllCourses();
    private static LinkedListInterface<ProgrammeCourse> unchosenList = new LinkedList<>();

    // Create operation
    public static void addProgrammeCourse(Programme programme, Course course) {
        String programmeId = programme.getId();  // Assuming getId() returns the programme's identifier
        String courseId = course.getId();        // Assuming getId() returns the course's identifier
        ProgrammeCourse programmeCourse = new ProgrammeCourse(programmeId, courseId);
        programmeCourses.add(programmeCourse);
    }

    public static LinkedListInterface<ProgrammeCourse> getProgrammeCourse() {
        return programmeCourses;
    }

    // Read unchosen list by programmeId and course id
    public static LinkedListInterface<ProgrammeCourse> getUnchosenListById(String courseId, String programmeId) {
        // Iterate over the programmeCourses list
        for (int i = 1; i <= programmeCourses.getTotalNumberOfData(); i++) {
            ProgrammeCourse pc = programmeCourses.getData(i);
            // Check if the current ProgrammeCourse's IDs match the specified courseId and programmeId
            // If they don't match, add the ProgrammeCourse to the unchosenList
            if (!pc.getCourseId().equals(courseId) || !pc.getProgrammeId().equals(programmeId)) {
                unchosenList.add(pc);
            }
        }

        // Return the unchosenList
        return unchosenList;
    }

    // dont modify !!!!!!!!
    public static String getCourseNameById(String courseId, String programmeId) {
        // Iterate over the list of courses
        for (int i = 1; i <= cList.getTotalNumberOfData(); i++) {
            Course c = cList.getData(i);
            // Check if the courseId matches
            if (c.getId().equals(courseId)) {
                // Retrieve the associated Programme object
                LinkedListInterface<Programme> programmeList = c.getProgramme();
                // Iterate over the programmeList
                for (int j = 1; j <= programmeList.getTotalNumberOfData(); j++) {
                    Programme programme = programmeList.getData(j);
                    // Check if the programmeId of the Programme object matches
                    if (programme != null && programme.getId().equals(programmeId)) {
                        // Return the course if both courseId and programmeId match
                        return c.getName();
                    }
                }
            }
        }
        // Return null if no course with the given courseId and programmeId is found
        return null;
    }

    public static Course getCourseById(String courseId, String programmeId) {
        for (int i = 1; i <= programmeCourses.getTotalNumberOfData(); i++) {
            ProgrammeCourse pc = programmeCourses.getData(i);
            // Check if the current ProgrammeCourse's IDs match the specified courseId and programmeId
            if (pc.getCourseId().equals(courseId) && pc.getProgrammeId().equals(programmeId)) {
                // Retrieve the course name using the courseId
                Course course = CourseDao.getCourseById(courseId);
                if (course != null) {
                    // Return the course name
                    return course;
                }

            }
        }
        // Return null if no course with the given courseId and programmeId is found
        return null;
    }

    public static void removeCourseFromProgramme(String courseId, String programmeId) {
        // Iterate through the programmeCourses list
        Iterator<ProgrammeCourse> iterator = programmeCourses.getIterator();
        while (iterator.hasNext()) {
            ProgrammeCourse pc = iterator.next();
            // Check if the current ProgrammeCourse's IDs match the specified courseId and programmeId
            if (pc.getCourseId().equals(courseId) && pc.getProgrammeId().equals(programmeId)) {
                // Remove the ProgrammeCourse from the programmeCourses list
                iterator.remove(); // Safe removal using Iterator
                // No need to add the removed course back to the unchosen list
                break; // Exit loop after first occurrence is removed
            }
        }
    }

    public static int getIndex(String id, LinkedListInterface<Programme> list) {
        // Trim the provided ID to remove leading and trailing whitespace
        String trimmedId = id.trim();

        // Get an iterator for the LinkedList
        Iterator<Programme> iterator = list.getIterator();

        // Initialize index counter
        int index = 1; // Adjusted to zero-based indexing

        // Iterate over the list
        while (iterator.hasNext()) {
            Programme p = iterator.next();
            if (p != null) {
                if (p.getId().equals(trimmedId)) {
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

    // Helper method to get a Course object by ID from a list of courses
    public static Course getCourseById(LinkedListInterface<Course> courseList, String courseId) {
        for (int i = 1; i <= courseList.getTotalNumberOfData(); i++) {
            Course course = courseList.getData(i);
            if (course.getId().equals(courseId)) {
                return course;
            }
        }
        return null;
    }

    // Helper method to check if a course is chosen
    public static boolean isCourseChosen(LinkedListInterface<ProgrammeCourse> pcourse, String courseId) {
        for (int i = 1; i <= pcourse.getTotalNumberOfData(); i++) {
            ProgrammeCourse pc = pcourse.getData(i);
            if (pc.getCourseId().equals(courseId)) {
                return true;
            }
        }
        return false;
    }

}
