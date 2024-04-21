/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Servlets;

import Dao.*;
import static Dao.ProgrammeDao.initializeProgrammes;
import Entity.*;
import Servlets.studentServlet.studentCourseServices;
import Servlets.studentServlet.studentServices;
import Servlets.tutorServlet.tutorServices;
import Utility.Tools;
import adt.LinkedList;
import adt.ListInterface;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Iterator;
import java.util.function.Predicate;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Zy
 */
@WebServlet("/courseServlet")
public class courseServlet extends HttpServlet {

    private static final ListInterface<Course> cList = new LinkedList<>();
    private static final ListInterface<ProgrammeCourse> programmeCourses = new LinkedList<>();
    private final static ListInterface<Course> inactiveList = new LinkedList<>();
    private static ListInterface<Programme> pList = ProgrammeDao.initializeProgrammes();

    // Courses Controller - servlet path navigation
    // path for handling get request
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String url = request.getServletPath();
        switch (url) {
            case "/courseServlet":
                String action = request.getParameter("action");
                if (null == action) {
                    displayCourseList(request, response);
                } else {
                    switch (action) {
                        case "deleteCourse":
                            deleteCourse(request, response);
                            break;

                        case "filterCourses":
                            filterCourses(request, response);
                            break;

                        case "filterInactiveCourses":
                            filterInactiveCourses(request, response);
                            break;

                        case "amendCourse":
                            amendCourse(request, response);
                            break;

                        case "courseForProgramme":
                            courseForProgramme(request, response);
                            break;

                        case "displayProgrammeCourses":
                            displayProgrammeCoursesDetails(request, response);
                            break;

                        // course report 1
                        case "showHistory":
                            showHistory(request, response);
                            break;

                        // course report 2
                        case "tutorDetails":
                            tutorDetails(request, response);
                            break;

                        // course report 3
                        case "courseChart":
                            courseChart(request, response);
                            break;

                        default:
                            displayCourseList(request, response);
                            break;
                    }
                }
                break;

            default:
                break;
        }
    }

    // path for handling post request
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String url = request.getServletPath();
        switch (url) {
            case "/courseServlet":
                String action = request.getParameter("action");
                if (null == action) {
                    // create a course
                    createCourses(request, response);
                } else {
                    switch (action) {
                        case "viewInactive":
                            viewInactiveCourse(request, response);
                            break;
                        case "searchCourses":
                            courseSearchResult(request, response);
                            break;
                        case "amendResult":
                            amendCourseResult(request, response);
                            break;
                        case "assignCourseForProgramme":
                            assignCourseForProgramme(request, response);
                            break;

                        default:
                            // create a course
                            createCourses(request, response);
                            break;
                    }
                }
                break;

            default:
                break;
        }

    }

    // ---------------------------------------------------------------------
    //                              GET request
    // ---------------------------------------------------------------------
    // display course list
    private void displayCourseList(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String pathInfo = request.getPathInfo();

        if (pathInfo == null || pathInfo.equals("/")) {

            request.setAttribute("resultList", cList);
            RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/views/displayCourses.jsp");
            dispatcher.forward(request, response);

        } else {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
    }

    // delete course
    private void deleteCourse(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Get the Course ID from the request (url)
        String id = request.getParameter("id");

        StudentCourse studentCourse = studentCourseServices.getStudentCourseBycId(id);
        if (studentCourse != null) {
            PrintWriter out = response.getWriter();
            out.println("<script>alert('Students are enrolled in this course! Can\\'t remove it');</script>");
            out.println("<script>window.location.href = '" + request.getContextPath() + "/courseServlet';</script>");
            out.close();
            return;
        }

        // Call the deleteCourse method by passing the course ID and the list from which you want to delete
        boolean deletionSuccessful = CourseServices.deleteCourse(id, cList);
        Tools.addHistory("Course '" + id + "' has been deleted");

        if (!deletionSuccessful) {
            // Handle the case where course List is empty
            PrintWriter out = response.getWriter();
            out.println("<script>alert('No courses found!');</script>");
            out.println("<script>window.location.replace('courseServlet');</script>");
            out.close();
        }

        RequestDispatcher dispatcher = request.getRequestDispatcher("courseServlet?action=viewInactive");
        dispatcher.forward(request, response);
    }

    // filter active courses
    private void filterCourses(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Get the facultyId parameter from the request
        String facultyId = request.getParameter("facultyId");

        // If facultyId is null or empty, retrieve all courses
        if (facultyId == null || facultyId.isEmpty()) {
            //ListInterface<Course> cList = CourseDao.getAllCourses();
            request.setAttribute("resultList", cList);
        } else {
            // Retrieve courses based on the specified facultyId
            ListInterface<Course> resultList = CourseServices.getCoursesByFid(facultyId);
            request.setAttribute("resultList", resultList);
        }

        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/views/displayCourses.jsp");
        dispatcher.forward(request, response);
    }

    // filter inactive courses
    private void filterInactiveCourses(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Get the facultyId parameter from the request
        String facultyId = request.getParameter("facultyId");

        // If facultyId is null or empty, retrieve all courses
        if (facultyId == null || facultyId.isEmpty()) {
            ListInterface<Course> resultList = CourseServices.getInactiveCourses();
            request.setAttribute("resultList", resultList);
        } else {
            // Retrieve courses based on the specified facultyId
            ListInterface<Course> resultList = CourseServices.getInactiveCoursesByFid(facultyId);
            request.setAttribute("resultList", resultList);
        }

        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/views/inactiveCourses.jsp");
        dispatcher.forward(request, response);

    }

    // modify course records
    private void amendCourse(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String id = request.getParameter("id");
        // Retrieve course information based on the ID
        Course course = CourseServices.getCourseById(id);
        int creditHours = course.getCreditHours();
        int duration = course.getDuration();

        // Retrieve selected course types
        ListInterface<String> selectedCTypes = course.getCourseTypes();
        ListInterface<Programme> selectedProgrammes = course.getProgramme();
        String tutorName = course.getTutor().getName();
        String dayOfWeek = course.getDayOfWeek();
        String startTime = course.getStartTime();

        // Pass the course object and other necessary attributes to the JSP page
        request.setAttribute("course", course);
        request.setAttribute("creditHours", creditHours);
        request.setAttribute("duration", duration);
        request.setAttribute("selectedCTypes", selectedCTypes);
        request.setAttribute("selectedProgrammes", selectedProgrammes);
        request.setAttribute("tutorName", tutorName);
        request.setAttribute("dayOfWeek", dayOfWeek);
        request.setAttribute("startTime", startTime);

        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/views/amendCourse.jsp");
        dispatcher.forward(request, response);
    }

    // display the available course for the programme to take
    private void courseForProgramme(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // programme id
        String id = request.getParameter("id");

        // Retrieve programme information
        Programme programme = programmeServices.findProgrammeById(id); // Assuming this method exists in your ProgrammeDao class
        int index = programmeServices.getIndex(id, pList);
        if (index != -1) {
            programme = pList.getData(index);
        }

        // Create a new ListInterface and add the programme to it
        ListInterface<Programme> programmeList = new LinkedList<>();
        programmeList.add(programme);
        request.setAttribute("programme", programmeList);

        // Retrieve the availableCourses for the program
        ListInterface<ProgrammeCourse> availableCourses = programmeCourseServices.getProgrammeCoursesBypId(id);
        ListInterface<String> existingCourses = new LinkedList<>();

        if (availableCourses != null && !availableCourses.isEmpty()) {
            for (int i = 1; i <= availableCourses.getTotalNumberOfData(); i++) {
                ProgrammeCourse pc = availableCourses.getData(i);
                ListInterface<String> courseIds = pc.getCourseId();
                if (courseIds != null && !courseIds.isEmpty()) {
                    for (int j = 1; j <= courseIds.getTotalNumberOfData(); j++) {
                        existingCourses.add(courseIds.getData(j));
                    }
                }
            }
            // Set attributes and forward request only if availableCourses is not null and not empty
            request.setAttribute("existingCourses", existingCourses);

            RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/views/addCourseProgramme.jsp");
            dispatcher.forward(request, response);
        }
        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/views/addCourseProgramme.jsp");
        dispatcher.forward(request, response);

    }

    // display course taken by programme based on programmeId
    private void displayProgrammeCoursesDetails(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String id = request.getParameter("id");

        Programme programme = programmeServices.findProgrammeById(id);

        int index = programmeServices.getIndex(id, pList);
        if (index != -1) {
            programme = pList.getData(index);
        }

        // Create a new ListInterface and add the programme to it
        ListInterface<Programme> programmeList = new LinkedList<>();
        programmeList.add(programme);
        // Pass the student object to the JSP page
        request.setAttribute("programme", programmeList);

        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/views/displayProgrammeDetails.jsp");
        dispatcher.forward(request, response);
    }

    // display overall action on university management - course report 1
    private void showHistory(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/views/courseReport1.jsp");
        dispatcher.forward(request, response);
    }

    // display the tutor details - course report 2
    private void tutorDetails(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // tutor id
        String id = request.getParameter("id");
        // Retrieve tutor information based on the tutor ID
        Tutor tutor = tutorServices.getTutorById(id);

        request.setAttribute("tutor", tutor);

        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/views/displayTutorDetails.jsp");
        dispatcher.forward(request, response);
    }

    // display the available course for programmes - course report 3
    private void courseChart(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        if (cList.isEmpty()) {
            PrintWriter out = response.getWriter();
            out.println("<script>alert('No course is created. Please create a course to view the chart.');</script>");
            out.println("<script>window.location.href = 'createCourse.jsp';</script>");
            out.close();
            return;
        }

        // Set attributes and forward request only if availableCourses is not null and not empty
        request.setAttribute("cList", cList);

        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/views/courseReport2.jsp");
        dispatcher.forward(request, response);

    }

    // ---------------------------------------------------------------------
    //                              POST request
    // ---------------------------------------------------------------------
    // create courses
    private void createCourses(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html");

        String id = request.getParameter("id");

        // Check if the ID already exists in the courseList
        boolean idExists = CourseServices.availableId(id, cList);

        if (idExists) {
            // show error message when the ID is already exists
            PrintWriter out = response.getWriter();
            out.println("<script>alert('ID already exists. Please choose a different ID.');</script>");
            out.println("<script>window.location.href = 'createCourse.jsp';</script>");
            out.close();
            return;
        }

        String name = request.getParameter("name");
        String details = request.getParameter("details");

        // to store selected courseType (T,P,L)
        ListInterface<String> selectedCTypes = new LinkedList<>();
        // Obtain the selected course types from the request parameters
        String[] courseTypes = request.getParameterValues("courseTypes");

        // Check if any course types are selected
        if (courseTypes != null) {
            // Add each course type to the LinkedList
            for (String courseType : courseTypes) {
                selectedCTypes.add(courseType);
            }
        } else {
            // No course types selected, display an alert message
            PrintWriter out = response.getWriter();
            out.println("<script>alert('Please select at least one course type!');</script>");
            out.println("<script>window.location.href = '" + request.getContextPath() + "/courseServlet';</script>");
            out.close();
            return;
        }

        String ch = request.getParameter("creditHours");
        int creditHours = Integer.parseInt(ch);

        String t = request.getParameter("tutorName");
        // Get the tutor object using the name
        Tutor tutorName = tutorServices.findTutorByName(t);
        String tName = tutorName.getName();

        String day = request.getParameter("dayOfWeek");

        String startTime = request.getParameter("startTime");

        String d = request.getParameter("duration");
        int duration = Integer.parseInt(d);

        ListInterface<Programme> programmes = new LinkedList<>();
        // Obtain the selected programme names from the request parameters
        String[] programmeNames = request.getParameterValues("programmeName[]");

        // Check if any programme names are selected
        if (programmeNames != null) {
            // Iterate over the selected programme names and find the corresponding Programme objects
            for (String programmeName : programmeNames) {
                Programme p = programmeServices.findProgrammeByName(programmeName);
                if (p != null) {
                    programmes.add(p);
                }
            }
            // Now you have a list of Programme objects corresponding to the selected programme names
        } else {
            // No course types selected, display an alert message
            PrintWriter out = response.getWriter();
            out.println("<script>alert('Please select at least one programme!');</script>");
            out.println("<script>window.location.href = '" + request.getContextPath() + "/courseServlet';</script>");
            out.close();
            return; // Exit the method
        }

        String f = request.getParameter("facultyName");
        // Get the faculty object using the name
        Faculty facultyName = CourseServices.findfacultiesByName(f);

        boolean invalid = CourseServices.getTutorAvailableTime(tName, startTime, day);
        if (invalid) {
            PrintWriter out = response.getWriter();
            out.println("<script>alert('Time conflict! Please select another time slot');</script>");
            out.println("<script>window.location.href = '" + request.getContextPath() + "/courseServlet';</script>");
            out.close();
            return; // Exit the method
        }

        // Create a new Course object with the provided data
        Course c = new Course(id, name, details, selectedCTypes, creditHours, tutorName, day, startTime, duration, programmes, facultyName, 1);

        try {
            // Attempt to add the course
            cList.add(c);
            Tools.addHistory("Course '" + id + "' has been added");

            // If no exceptions are thrown, the addition was successful
            PrintWriter out = response.getWriter();
            out.println("<script>alert('Record saved successfully!');</script>");
            out.println("<script>window.location.href = '" + request.getContextPath() + "/courseServlet';</script>");
            out.close();
        } catch (Exception e) {
            // If an exception occurs, it means the addition failed
            PrintWriter out = response.getWriter();
            out.println("<script>alert('Failed to create course!');</script>");
            out.println("<script>window.location.replace('createCourse.jsp');</script>");
            out.close();
        }
    }

    // display inactive list
    private void viewInactiveCourse(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Check if course inactive List is empty
        if (inactiveList.isEmpty()) {
            // Handle the case where studentList is empty
            PrintWriter out = response.getWriter();
            out.println("<script>alert('No Courses are found!');</script>");
            out.println("<script>window.location.replace('courseServlet');</script>");
            out.close();
            return;
        } else {
            request.setAttribute("resultList", inactiveList);
            RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/views/inactiveCourses.jsp");
            dispatcher.forward(request, response);
        }
    }

    // search courses by name
    private void courseSearchResult(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Retrieve the search query from the request parameter
        String name = request.getParameter("search");

        // Search for students with the given name
        ListInterface<Course> searchResults = CourseServices.searchCourses(name);
        Tools.addHistory("Search '" + name + "' for course's name");

        // Forward the search results to a JSP for display
        request.setAttribute("searchResults", searchResults);
        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/views/displayCourses.jsp");
        dispatcher.forward(request, response);
    }

    // display the modify course's result
    private void amendCourseResult(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String id = request.getParameter("id");
        String name = request.getParameter("name");
        String details = request.getParameter("details");

        // to store selected courseType (T,P,L)
        ListInterface<String> selectedCTypes = new LinkedList<>();
        // Obtain the selected course types from the request parameters
        String[] courseTypes = request.getParameterValues("courseTypes");

        // Check if any course types are selected
        if (courseTypes != null) {
            // Add each course type to the LinkedList
            for (String courseType : courseTypes) {
                selectedCTypes.add(courseType);
            }
        } else {
            // No course types selected, display an alert message
            PrintWriter out = response.getWriter();
            out.println("<script>alert('Please select at least one course type!');</script>");
            out.println("<script>window.location.href = '" + request.getContextPath() + "/courseServlet';</script>");
            out.close();
            return; // Exit the method
        }

        String ch = request.getParameter("creditHours");
        int creditHours = Integer.parseInt(ch);

        String t = request.getParameter("tutorName");
        // Get the tutor object using the name
        Tutor tutorName = tutorServices.findTutorByName(t);

        String day = request.getParameter("dayOfWeek");

        String startTime = request.getParameter("startTime");

        String d = request.getParameter("duration");
        int duration = Integer.parseInt(d);

        ListInterface<Programme> programmes = new LinkedList<>();
        // Obtain the selected programme names from the request parameters
        String[] programmeNames = request.getParameterValues("programmeName");

        // Check if any programme names are selected
        if (programmeNames != null) {
            // Iterate over the selected programme names and find the corresponding Programme objects
            for (String programmeName : programmeNames) {
                Programme p = programmeServices.findProgrammeByName(programmeName);
                if (p != null) {
                    programmes.add(p);
                }
            }

        } else {
            // No course types selected, display an alert message
            PrintWriter out = response.getWriter();
            out.println("<script>alert('Please select at least one programme!');</script>");
            out.println("<script>window.location.href = '" + request.getContextPath() + "/courseServlet';</script>");
            out.close();
            return; // Exit the method
        }

        String f = request.getParameter("facultyName");
        // Get the faculty object using the name
        Faculty facultyName = CourseServices.findfacultiesByName(f);

        // Create a new Course object with the provided data
        Course c = new Course(id, name, details, selectedCTypes, creditHours, tutorName, day, startTime, duration, programmes, facultyName, 1);

        // Check if courseList is empty
        if (cList.isEmpty()) {
            // Handle the case where studentList is empty
            PrintWriter out = response.getWriter();
            out.println("<script>alert('No Course found!');</script>");
            out.println("<script>window.location.replace('courseServlet');</script>");
            out.close();
            return;
        }

        // Check if the course with the provided ID exists (For Active courses)
        int index = CourseServices.getIndex(id, cList);
        if (index != -1) {
            // course exists, proceed with updating
            // Remove the existing course from the cList
            cList.remove(index);

            // Add the new course to the cList
            cList.add(index, c);

            boolean status = CourseServices.updateCourse(id, c, cList);
            if (status) {
                //add to history for course report 1
                Tools.addHistory("A course with the ID '" + id + "' has been updated");

                // course updated successfully
                PrintWriter out = response.getWriter();
                out.println("<script>alert('Record saved successfully!');</script>");
                out.println("<script>window.location.href = '" + request.getContextPath() + "/courseServlet';</script>");
                out.close();
            } else {
                // Failed to update course, display an error message
                PrintWriter out = response.getWriter();
                out.println("<script>alert('Failed to update course record!');</script>");
                out.println("<script>window.location.replace('courseServlet');</script>");
                out.close();
            }
        } else {
            // Course with the provided ID does not exist, display an error message
            PrintWriter out = response.getWriter();
            out.println("<script>alert('Course with ID " + id + " does not exist!');</script>");
            out.println("<script>window.location.replace('courseServlet');</script>");
            out.close();
        }

    }

    // programme choose the available course to take so the student on that can programme can enroll
    private void assignCourseForProgramme(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // programme id
        String id = request.getParameter("id");

        // Retrieve programme information based on the ID
        // Obtain the selected course IDs from the request parameters
        String[] courseIds = request.getParameterValues("courseName");

        // Validate the selected course IDs
        if (courseIds == null) {
            // Handle case where no course is selected
            PrintWriter out = response.getWriter();
            out.println("<script>alert('Please select at least 1 course!');</script>");
            out.println("<script>window.location.href = '" + request.getContextPath() + "/courseServlet?action=courseForProgramme&id=" + id + "';</script>");
            out.close();
            return;
        }

        try {
            // Create a new list to store the updated courses
            ListInterface<ProgrammeCourse> updatedCourses = new LinkedList<>();

            // Update or add courses
            for (String courseId : courseIds) {
                // Retrieve the course object corresponding to the course ID

                // If no course exists for the student and course ID, mark it for addition
                ListInterface<String> coursesIds = new LinkedList<>();
                coursesIds.add(courseId);
                ProgrammeCourse programmeCourse = new ProgrammeCourse(id, coursesIds);
                updatedCourses.add(programmeCourse);

                programmeCourseServices.replaceCourseList(updatedCourses);
                Tools.addHistory("Assigned course(s) " + courseId + " to '" + id + "' Programme");
            }

            // Redirect back to the page with a success message or any other necessary action
            PrintWriter out = response.getWriter();
            out.println("<script>alert('Record saved successfully!');</script>");
            out.println("<script>window.location.href = '" + request.getContextPath() + "/courseServlet?action=displayProgrammeCourses&id=" + id + "';</script>");
            out.close();

        } catch (Exception e) {
            // If any exception occurs during processing, show error message
            PrintWriter out = response.getWriter();
            out.println("<script>alert('Failed to add program course: " + e.getMessage() + "');</script>");
            out.println("<script>window.location.href = '" + request.getContextPath() + "/courseServlet?action=courseForProgramme&id=" + id + "';</script>");
            out.close();
        }
    }

//--------------------------------------------------------------------------------
//                               FUNCTIONS - to share with other servlet
//-------------------------------------------------------------------------------
    public class CourseServices {

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
                Course course = CourseServices.getCourseById(id); // Get the course object corresponding to the ID
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

        // search courses name
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
            ListInterface<Faculty> faculty = FacultyDao.initializeFaculties();

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

    // ------------------------------------------------------------------------------------
    //                             PROGRAM COURSE SERVICES
    //------------------------------------------------------------------------------------
    public class programmeCourseServices {

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
            programmeCourses.reset();
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
            StudentCourse sc = studentCourseServices.getStudentCourseBysIdAndcId(sId, cId);
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

    }

    // -------------------------------------------------------------------------------------------
    //                                  PROGRAMME SERVICES
    // -------------------------------------------------------------------------------------------
    public class programmeServices {

        public static Programme findProgrammeByName(String name) {
            ListInterface<Programme> programme = initializeProgrammes();

            for (int i = 1; i <= programme.getTotalNumberOfData(); i++) {
                Programme p = programme.getData(i);
                if (p.getName().equals(name)) {
                    return p;
                }
            }

            // If no matching programme is found, return null or throw an exception
            return null;
        }

        public static Programme findProgrammeById(String id) {
            ListInterface<Programme> programme = initializeProgrammes();

            for (int i = 1; i <= programme.getTotalNumberOfData(); i++) {
                Programme p = programme.getData(i);
                if (p.getName().equals(id)) {
                    return p;
                }
            }

            // If no matching programme is found, return null or throw an exception
            return null;
        }

        public static Programme getProgrammeById(String id) {
            ListInterface<Programme> programme = initializeProgrammes();

            for (int i = 1; i <= programme.getTotalNumberOfData(); i++) {
                Programme p = programme.getData(i);
                if (p.getId().equals(id)) {
                    return p;
                }
            }

            // If no matching programme is found, return null or throw an exception
            return null;
        }

        // check the index number based on id
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
    }
}
