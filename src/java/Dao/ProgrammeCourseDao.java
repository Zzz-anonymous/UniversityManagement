/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Dao;

import Entity.Course;
import Entity.Programme;
import Entity.ProgrammeCourse;
import adt.LinkedList;
import adt.ListInterface;
import adt.ListInterface;
import java.util.Iterator;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 *
 * @author Zy
 */
public class ProgrammeCourseDao {

    private static final ListInterface<ProgrammeCourse> programmeCourses = new LinkedList<>();
    private static ListInterface<Course> cList = CourseDao.getAllCourses();
    private static ListInterface<ProgrammeCourse> unchosenList = new LinkedList<>();

    // Create operation
    public static void addProgrammeCourse(ListInterface<ProgrammeCourse> courses) {
        // Iterate over the courses list and add each course individually
        for (int i = 1; i <= courses.getTotalNumberOfData(); i++) {
            ProgrammeCourse programmeCourse = courses.getData(i);
            programmeCourses.add(programmeCourse);
        }
    }

    public static ListInterface<ProgrammeCourse> getProgrammeCourse() {
        return programmeCourses;
    }

    // Read unchosen list by programmeId and course id
    public static ListInterface<ProgrammeCourse> getProgrammeCoursesBypId(String programmeId) {
        ListInterface<ProgrammeCourse> filteredList = new LinkedList<>();
        for (int i = 1; i <= programmeCourses.getTotalNumberOfData(); i++) {
            ProgrammeCourse pc = programmeCourses.getData(i);
            if (pc.getProgrammeId().equals(programmeId)) {
                filteredList.add(pc);
            }
        }
        return filteredList;
    }

    // get course name from cList
    public static String getCourseNameById(String courseId, String programmeId) {
        // Iterate over the list of courses
        for (int i = 1; i <= cList.getTotalNumberOfData(); i++) {
            Course c = cList.getData(i);
            // Check if the courseId matches
            if (c.getId().equals(courseId)) {
                // Retrieve the associated Programme object
                ListInterface<Programme> programmeList = c.getProgramme();
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

    public static ProgrammeCourse getCourseById(String courseId, String programmeId) {
        for (int i = 1; i <= programmeCourses.getTotalNumberOfData(); i++) {
            ProgrammeCourse pc = programmeCourses.getData(i);
            // Check if the current ProgrammeCourse's IDs match the specified courseId and programmeId
            if (pc.getCourseId().contains(courseId) && pc.getProgrammeId().equals(programmeId)) {
                // Retrieve the course name using the courseId
                return pc;
            }
        }
        // Return null if no course with the given courseId and programmeId is found
        return null;
    }

    public static void removeCourseFromProgramme() {
        programmeCourses.clear();
    }

    public static int getIndex(String id, ListInterface<Programme> list) {
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

    // Helper method to check if a course is chosen
    public static boolean isCourseChosen(ListInterface<ProgrammeCourse> pcourse, String courseId) {
        for (int i = 1; i <= pcourse.getTotalNumberOfData(); i++) {
            ProgrammeCourse pc = pcourse.getData(i);
            if (pc.getCourseId().contains(courseId)) {
                return true;
            }
        }
        return false;
    }

    public static void replaceCourseList(ListInterface<ProgrammeCourse> updatedCourses) {
        // replace the existing list of courses with the updated one
        removeCourseFromProgramme();
        addProgrammeCourse(updatedCourses);
    }

    public static ListInterface<Course> filterCoursesBytId(String tutorId) {
        ListInterface<Course> filteredCourses = new LinkedList<>();

        if (cList != null && programmeCourses != null) {
            for (int j = 1; j <= cList.getTotalNumberOfData(); j++) {
                Course c = cList.getData(j);
                if (c.getTutor().getId().equals(tutorId)) {
                    for (int k = 1; k <= programmeCourses.getTotalNumberOfData(); k++) {
                        ProgrammeCourse pc = programmeCourses.getData(k);
                        if (pc.getCourseId().contains(c.getId())) {
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
