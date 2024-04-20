/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Servlets;

import Dao.CourseDao;
import Dao.HistoryDao;
import Dao.ProgrammeCourseDao;
import Dao.ProgrammeDao;
import Dao.StudentCourseDao;
import Dao.TutorDao;
import Entity.Course;
import Entity.Faculty;
import Entity.Programme;
import Entity.ProgrammeCourse;
import Entity.StudentCourse;
import Entity.Tutor;
import Utility.Tools;
import adt.LinkedList;
import adt.ListInterface;
import java.io.IOException;
import java.io.PrintWriter;
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

    private static final ListInterface<Course> cList = CourseDao.getAllCourses();
    private final static ListInterface<Course> inactiveList = CourseDao.getInactiveCourses();
    private static ListInterface<Programme> pList = Tools.initializeProgrammes();

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
                        case "courseChart":
                            courseChart(request, response);
                            break;

                        // course report 3
                        case "tutorDetails":
                            tutorDetails(request, response);
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
        //ListInterface<Course> cList = CourseDao.getAllCourses();
        // Get the Course ID from the request
        String id = request.getParameter("id");

        StudentCourse studentCourse = StudentCourseDao.getStudentCourseBycId(id);
        if (studentCourse != null) {
            PrintWriter out = response.getWriter();
            out.println("<script>alert('Students are enrolled in this course! Can\\'t remove it');</script>");
            out.println("<script>window.location.href = '" + request.getContextPath() + "/courseServlet';</script>");
            out.close();
            return;
        }

        // Call the deleteStudent method passing the student ID and the list from which you want to delete
        boolean deletionSuccessful = CourseDao.deleteCourse(id, cList);
        HistoryDao.addHistory("Course '" + id + "' has been deleted");

        if (!deletionSuccessful) {

            // Handle the case where studentList is empty
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

        // If programmeId is null or empty, retrieve all students
        if (facultyId == null || facultyId.isEmpty()) {
            //ListInterface<Course> cList = CourseDao.getAllCourses();
            request.setAttribute("resultList", cList);
        } else {
            // Retrieve students based on the specified programmeId
            ListInterface<Course> resultList = CourseDao.getCoursesByFid(facultyId);
            request.setAttribute("resultList", resultList);
        }

        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/views/displayCourses.jsp");
        dispatcher.forward(request, response);
    }

    // filter inactive courses
    private void filterInactiveCourses(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Get the programmeId parameter from the request
        String facultyId = request.getParameter("facultyId");

        // If facultyId is null or empty, retrieve all students
        if (facultyId == null || facultyId.isEmpty()) {
            ListInterface<Course> resultList = CourseDao.getInactiveCourses();
            request.setAttribute("resultList", resultList);
        } else {
            // Retrieve students based on the specified programmeId
            ListInterface<Course> resultList = CourseDao.getInactiveCoursesByFid(facultyId);
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
        Course course = CourseDao.getCourseById(id);
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
        Programme programme = ProgrammeDao.findProgrammeById(id); // Assuming this method exists in your ProgrammeDao class
        int index = ProgrammeDao.getIndex(id, pList);
        if (index != -1) {
            programme = pList.getData(index);
        }

        // Create a new ListInterface and add the programme to it
        ListInterface<Programme> programmeList = new LinkedList<>();
        programmeList.add(programme);
        request.setAttribute("programme", programmeList);

        // Retrieve the availableCourses for the program
        ListInterface<ProgrammeCourse> availableCourses = ProgrammeCourseDao.getProgrammeCoursesBypId(id);
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

        Programme programme = ProgrammeDao.findProgrammeById(id);

        int index = ProgrammeDao.getIndex(id, pList);
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

    // display the available course for programmes - course report 2
    private void courseChart(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Set attributes and forward request only if availableCourses is not null and not empty
        request.setAttribute("cList", cList);

        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/views/courseReport2.jsp");
        dispatcher.forward(request, response);

    }
    
    private void tutorDetails(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // tutor id
        String id = request.getParameter("id");
        // Retrieve course information based on the ID
        Tutor tutor = TutorDao.getTutorById(id);
        
        
        
        request.setAttribute("tutor", tutor);
        
    
        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/views/displayTutorDetails.jsp");
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
        boolean idExists = CourseDao.availableId(id, cList);

        if (idExists) {
            // ID already exists, handle accordingly (e.g., show error message)
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
            return; // Exit the method
        }

        String ch = request.getParameter("creditHours");
        int creditHours = Integer.parseInt(ch);

        String t = request.getParameter("tutorName");
        // Get the tutor object using the name
        Tutor tutorName = TutorDao.findTutorByName(t);
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
                Programme p = ProgrammeDao.findProgrammeByName(programmeName);
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
        Faculty facultyName = CourseDao.findfacultiesByName(f);

        boolean invalid = CourseDao.getTutorAvailableTime(tName, startTime, day);
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
            CourseDao.addCourse(c);
            HistoryDao.addHistory("Course '" + id + "' has been added");

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
        // Check if studentList is empty
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
        ListInterface<Course> searchResults = CourseDao.searchCourses(name);
        HistoryDao.addHistory("Search '" + name + "' for course's name");

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
        Tutor tutorName = TutorDao.findTutorByName(t);

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
                Programme p = ProgrammeDao.findProgrammeByName(programmeName);
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
        Faculty facultyName = CourseDao.findfacultiesByName(f);

       

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
        int index = CourseDao.getIndex(id, cList);
        if (index != -1) {
            // course exists, proceed with updating
            // Remove the existing course from the cList
            cList.remove(index);

            // Add the new course to the cList
            cList.add(index, c);

            boolean status = CourseDao.updateCourse(id, c, cList);
            if (status) {
                //add to history for course report 1
                HistoryDao.addHistory("A course with the ID '" + id + "' has been updated");

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
                //ProgrammeCourse pc = ProgrammeCourseDao.getCourseById(courseId, id);

               // if (pc == null) {
                    // If no course exists for the student and course ID, mark it for addition
                    ListInterface<String> coursesIds = new LinkedList<>();
                    coursesIds.add(courseId);
                    ProgrammeCourse programmeCourse = new ProgrammeCourse(id, coursesIds);
                    updatedCourses.add(programmeCourse);
                //}
                ProgrammeCourseDao.replaceCourseList(updatedCourses);
                HistoryDao.addHistory("Assigned course(s) " + courseId + " to '" + id + "' Programme");
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

}
