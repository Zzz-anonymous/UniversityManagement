/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Servlets;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.RequestDispatcher;

import adt.*;
import Entity.Student;
import Dao.StudentDao;
import Entity.Course;
import Entity.ProgrammeCourse;
import Entity.StudentCourse;
import Servlets.courseServlet.CourseServices;
import Utility.Tools;
import java.util.Iterator;
import java.util.function.Predicate;

/**
 *
 * @author Zy
 */
@WebServlet("/studentServlet")
public class studentServlet extends HttpServlet {

    private static final ListInterface<StudentCourse> scList = new LinkedList<>();
    private static ListInterface<Student> inactiveList = new LinkedList<>();
    private final static ListInterface<Student> initialzeStudents = StudentDao.initializeStudents();
    private final static ListInterface<Student> mergedList = studentServices.getAllStudents();
    private static boolean isInitializeStudents = false;

    // Students Controller - servlet path navigation
    // path for handling get request
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String url = request.getServletPath();
        switch (url) {
            case "/studentServlet":
                String action = request.getParameter("action");
                if (null == action) {
                    displayStudentList(request, response);
                } else {
                    switch (action) {
                        case "delete":
                            deleteStudent(request, response);
                            break;

                        case "filterStudents":
                            filterStudents(request, response);
                            break;

                        case "filterInactiveStudents":
                            filterInactiveStudents(request, response);
                            break;

                        case "amendStudent":
                            amendStudent(request, response);
                            break;

                        case "displayStudentCourses":
                            displayStudentCourses(request, response);
                            break;

                        case "displayPaymentDetails":
                            displayPaymentDetails(request, response);
                            break;

                        // report 1
                        case "displayStudentDetails":
                            displayStudentDetails(request, response);
                            break;

                        // report 2
                        case "studentChart":
                            studentChart(request, response);
                            break;

                        default:
                            displayStudentList(request, response);
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
            case "/studentServlet":
                String action = request.getParameter("action");
                if (null == action) {
                    // insert a student
                    addStudents(request, response);
                } else {
                    switch (action) {
                        case "viewInactive":
                            viewInactiveStudent(request, response);
                            break;
                        case "searchStudent":
                            studentSearchResult(request, response);
                            break;
                        case "amendResult":
                            amendStudentResult(request, response);
                            break;
                        case "assignStudentCourses":
                            assignStudentCourses(request, response);
                        default:
                            // insert a student
                            addStudents(request, response);
                            break;
                    }
                }
                break;

            default:
                break;
        }

    }
    // --------------------------------------------------------------------
    //  get request
    // ---------------------------------------------------------------------

    // display student list
    private void displayStudentList(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String pathInfo = request.getPathInfo();

        if (pathInfo == null || pathInfo.equals("/")) {
            if (mergedList.isEmpty()) {
                // Display alert message if no students were found
                PrintWriter out = response.getWriter();
                out.println("<script>alert('No students were found.');</script>");
                out.println("<script>window.location.href = 'insertStudentUI.jsp';</script>");
                out.close();
            } else {
                request.setAttribute("resultList", mergedList);
                RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/views/studentsUI.jsp");
                dispatcher.forward(request, response);
            }
        } else {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
    }

    // filtering students based on program
    private void filterStudents(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Get the programmeId parameter from the request
        String programmeId = request.getParameter("programId");

        // If programmeId is null or empty, retrieve all students
        if (programmeId == null || programmeId.isEmpty()) {
            ListInterface<Student> resultList = studentServices.getAllStudents();
            request.setAttribute("resultList", resultList);
        } else {
            // Retrieve students based on the specified programmeId
            ListInterface<Student> resultList = studentServices.getStudentsByPid(programmeId);
            request.setAttribute("resultList", resultList);
        }

        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/views/studentsUI.jsp");
        dispatcher.forward(request, response);
    }

    // filtering inactive students based on program
    private void filterInactiveStudents(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Get the programmeId parameter from the request
        String programmeId = request.getParameter("programId");

        // If programmeId is null or empty, retrieve all students
        if (programmeId == null || programmeId.isEmpty()) {
            ListInterface<Student> resultList = studentServices.getInactiveStudents();
            request.setAttribute("resultList", resultList);
        } else {
            // Retrieve students based on the specified programmeId
            ListInterface<Student> resultList = studentServices.getInactiveStudentsByPid(programmeId);
            request.setAttribute("resultList", resultList);
        }

        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/views/inactiveStudentsUI.jsp");
        dispatcher.forward(request, response);

    }

    // modify student records
    private void amendStudent(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Retrieve student information based on the ID
        String id = request.getParameter("id");
        Student student = studentServices.getStudentById(id);

        // Check if the student exists in the mergedList
        int index = studentServices.getIndex(id, mergedList);
        if (index != -1) {
            // If not found in mergedList, check in inactiveList
            student = mergedList.getData(index);
        } else {
            // Student with the provided ID does not exist, display an error message
            PrintWriter out = response.getWriter();
            out.println("<script>alert('Student with ID " + id + " does not exist!');</script>");
            out.println("<script>window.location.replace('studentServlet?action=amendResult');</script>");
            out.close();
        }

        // Retrieve the gender of the student's 
        String gender = student.getGender();
        String ProgrammeId = student.getProgrammeId();

        // Pass the student object to the JSP page
        request.setAttribute("student", student);
        request.setAttribute("gender", gender);
        request.setAttribute("ProgrammeId", ProgrammeId);

        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/views/amendStudentUI.jsp");
        dispatcher.forward(request, response);

    }

    // delete a student to inactive list (withdraw)
    private void deleteStudent(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        //ListInterface<Student> mergedList = StudentDao.getAllStudents();
        // Get the student ID from the request
        String id = request.getParameter("id");

        // Call the deleteStudent method passing the student ID and the list from which you want to delete
        boolean deletionSuccessful = studentServices.deleteStudent(id, mergedList);
        Tools.addHistory("Student '" + id + "' has been deleted");

        if (deletionSuccessful) {
            // Update the studentList attribute in the request if deletion was successful
            request.setAttribute("resultList", mergedList);
        } else {
            // Handle the case where studentList is empty
            PrintWriter out = response.getWriter();
            out.println("<script>alert('No students found!');</script>");
            out.println("<script>window.location.replace('studentAmendServlet');</script>");
            out.close();
        }

        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/views/studentsUI.jsp");
        dispatcher.forward(request, response);
    }

    // display available courses for students to enroll
    private void displayStudentCourses(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // student id 
        String id = request.getParameter("id");

        // student status
        String statusStr = request.getParameter("status");
        int status = Integer.parseInt(statusStr);

        if (status == 0) {
            // Handle case where no course is selected
            PrintWriter out = response.getWriter();
            out.println("<script>alert('Student is inactive! Cannot assign courses !');</script>");
            out.println("<script>window.location.href = '" + request.getContextPath() + "/studentServlet';</script>");
            out.close();
            return;
        }

        String programId = request.getParameter("programId");

        // Retrieve course information based on the ID
        Student student = studentServices.getStudentById(id);

        // Check if the student exists in the mergedList
        int index = studentServices.getIndex(id, mergedList);
        if (index != -1) {
            // If not found in mergedList, check in inactiveList
            student = mergedList.getData(index);
        }

        // request.setAttribute("courseStatus", courseStatus);
        // Pass the course object and other necessary attributes to the JSP page
        request.setAttribute("student", student);
        ListInterface<ProgrammeCourse> availableCourses = courseServlet.programmeCourseServices.getProgrammeCoursesBypId(programId);

        ListInterface<String> existingCourses = new LinkedList<>();
        ListInterface<String> courseStatuses = new LinkedList<>();
        if (availableCourses != null && !availableCourses.isEmpty()) {
            for (int i = 1; i <= availableCourses.getTotalNumberOfData(); i++) {
                ProgrammeCourse pc = availableCourses.getData(i);
                ListInterface<String> courseIds = pc.getCourseId();
                if (courseIds != null && !courseIds.isEmpty()) {
                    for (int j = 1; j <= courseIds.getTotalNumberOfData(); j++) {
                        existingCourses.add(courseIds.getData(j));
                        // Retrieve course status for each individual course ID
                        String courseStatus = studentCourseServices.getStudentCourseStatusById(courseIds.getData(j));
                        courseStatuses.add(courseStatus);
                    }
                }
            }
        } else {
            PrintWriter out = response.getWriter();
            out.println("<script>alert('No available courses to assign!');</script>");
            out.println("<script>window.location.href = '" + request.getContextPath() + "/studentServlet';</script>");
            out.close();
            return;
        }

        request.setAttribute("existingCourses", existingCourses); // Assuming existingCourses is a list of course IDs assigned to the student
        request.setAttribute("courseStatuses", courseStatuses);

        RequestDispatcher dispatcher = request.getRequestDispatcher("addStudentCourseUI.jsp");
        dispatcher.forward(request, response);
    }

    // display student payment details for their enrolled course
    private void displayPaymentDetails(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String id = request.getParameter("id");
        Student student = studentServices.getStudentById(id);
        String pathInfo = request.getPathInfo();

        if (pathInfo == null || pathInfo.equals("/")) {
            request.setAttribute("student", student);
            request.setAttribute("scList", scList);
            RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/views/displayStudentBillsUI.jsp?id=" + id);
            dispatcher.forward(request, response);

        } else {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
    }

    // display student details (Report 1)
    private void displayStudentDetails(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String id = request.getParameter("id");
        Student student = studentServices.getStudentById(id);
        ListInterface<StudentCourse> sc = studentCourseServices.getStudentCourseListById(id);
        ListInterface<Course> cList = CourseServices.getAllAvailableCourses();

        // Filter courses if needed
        ListInterface<Course> filteredCourses = studentCourseServices.filterCourses(sc, cList, id);

        request.setAttribute("student", student);
        //request.setAttribute("scList", sc);
        request.setAttribute("filteredCourses", filteredCourses);

        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/views/studentReport1UI.jsp?id=" + id);
        dispatcher.forward(request, response);
    }

    // view the total number of active students and inactive students (Report 2)
    private void studentChart(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        //ListInterface<Student> inactiveList = StudentDao.getInactiveStudents();
        //ListInterface<Student> activeList = StudentDao.getAllStudents();

        // Set the counts as attributes in the request
        request.setAttribute("activeList", mergedList);
        request.setAttribute("inactiveList", inactiveList);

        // Forward the request to a JSP page where you generate the chart
        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/views/studentReport2UI.jsp");
        dispatcher.forward(request, response);
    }

// ---------------------------------------------------------------------
//                               POST request
// ---------------------------------------------------------------------
    private void addStudents(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html");

        String id = request.getParameter("id");

        // Check if the ID already exists in the studentList
        boolean idExists = studentServices.availableId(id, mergedList);

        if (idExists) {
            // ID already exists, handle accordingly (e.g., show error message)
            PrintWriter out = response.getWriter();
            out.println("<script>alert('ID already exists. Please choose a different ID.');</script>");
            out.println("<script>window.location.href = 'insertStudentUI.jsp';</script>");
            out.close();
            return;
        }

        String name = request.getParameter("name");
        String gender = request.getParameter("gender");
        String email = request.getParameter("email");
        String programmeId = request.getParameter("programmeId");

        // Create a new Student object with the provided data
        Student s = new Student(id, name, gender, email, 1, programmeId);

        try {
            // Attempt to add the student
            studentServices.addStudent(s);
            Tools.addHistory("Student '" + name + "' has been added");

            // If no exceptions are thrown, the addition was successful
            //ListInterface<Student> updatedStudentList = StudentDao.getAllStudents();     
            // If no exceptions are thrown, the addition was successful
            PrintWriter out = response.getWriter();
            out.println("<script>alert('Record saved successfully!');</script>");
            out.println("<script>window.location.href = '" + request.getContextPath() + "/studentServlet';</script>");
            out.close();
        } catch (Exception e) {
            // If an exception occurs, it means the addition failed
            PrintWriter out = response.getWriter();
            out.println("<script>alert('Failed to add student!');</script>");
            out.println("<script>window.location.replace('insertStudentUI.jsp');</script>");
            out.close();
        }
    }

    // display the search results
    private void studentSearchResult(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Retrieve the search query from the request parameter
        String name = request.getParameter("search");

        // Search for students with the given name
        ListInterface<Student> searchResults = studentServices.searchStudent(name);

        // Forward the search results to a JSP for display
        request.setAttribute("searchResults", searchResults);
        Tools.addHistory("Search '" + name + "' for student's name");

        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/views/studentsUI.jsp");
        dispatcher.forward(request, response);
    }

    // display the modify student results
    private void amendStudentResult(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String id = request.getParameter("id");
        String name = request.getParameter("name");
        String gender = request.getParameter("gender");
        String email = request.getParameter("email");
        String programme = request.getParameter("programmeId");

        // Create a new Student object with the provided data
        Student s = new Student(id, name, gender, email, 1, programme);

        // Check if studentList is empty
        if (mergedList.isEmpty()) {
            // Handle the case where studentList is empty
            PrintWriter out = response.getWriter();
            out.println("<script>alert('No students found!');</script>");
            out.println("<script>window.location.replace('studentServlet?action=amendResult');</script>");
            out.close();
            return;
        }

        // Check if the student with the provided ID exists (For Active Students)
        int index = studentServices.getIndex(id, mergedList);
        if (index != -1) {
            // Student exists, proceed with updating
            // Remove the existing student from the mergedList
            mergedList.remove(index);

            // Add the new student to the mergedList
            mergedList.add(index, s);

            boolean status = studentServices.updateStudent(id, s, mergedList);
            if (status) {
                // Student updated successfully
                Tools.addHistory("A student with the ID '" + id + "' has been updated");
                PrintWriter out = response.getWriter();
                out.println("<script>alert('Record saved successfully!');</script>");
                out.println("<script>window.location.href = '" + request.getContextPath() + "/studentServlet';</script>");
                out.close();
            } else {
                // Failed to update student, display an error message
                PrintWriter out = response.getWriter();
                out.println("<script>alert('Failed to update student record!');</script>");
                out.println("<script>window.location.replace('studentServlet?action=amendResult');</script>");
                out.close();
            }
        } else {
            // Student with the provided ID does not exist, display an error message
            PrintWriter out = response.getWriter();
            out.println("<script>alert('Student with ID " + id + " does not exist!');</script>");
            out.println("<script>window.location.replace('studentServlet?action=amendResult');</script>");
            out.close();
        }

    }

    // view removed students
    private void viewInactiveStudent(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Check if studentList is empty
        if (inactiveList.isEmpty()) {
            // Handle the case where studentList is empty
            PrintWriter out = response.getWriter();
            out.println("<script>alert('No students found!');</script>");
            out.println("<script>window.location.replace('studentServlet');</script>");
            out.close();
            return;
        } else {
            request.setAttribute("resultList", inactiveList);
            RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/views/inactiveStudents.jsp");
            dispatcher.forward(request, response);
        }
    }

    // assign students to available courses
    private void assignStudentCourses(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Student id
        String id = request.getParameter("id");

        // Retrieve selected courses (assuming checkboxes are used for course selection)
        String[] courseIds = request.getParameterValues("courseId");

        // Validate the selected course IDs
        if (courseIds == null) {
            // Handle case where no course is selected
            PrintWriter out = response.getWriter();
            out.println("<script>alert('No Course is selected !');</script>");
            out.println("<script>window.location.href = '" + request.getContextPath() + "/studentServlet';</script>");
            out.close();
            return;
        }

        int totalCreditHours = 0; // Initialize total credit hours counter

        for (String courseId : courseIds) {
            String status = request.getParameter(courseId + "_status"); // Retrieve status for the current courseId
            if (status != null) {
                Course c = CourseServices.getCourseById(courseId);
                //String dayOfWeek = c.getDayOfWeek();
                //String startTime = c.getStartTime();
                int creditHours = c.getCreditHours();
                totalCreditHours += creditHours; // Increment total credit hours

                if (totalCreditHours > 23) {
                    // If total credit hours exceed 23, display an error message
                    PrintWriter out = response.getWriter();
                    out.println("<script>alert('Total credit hours cannot exceed 23.');</script>");
                    out.println("<script>window.location.href = '" + request.getContextPath() + "/studentServlet';</script>");
                    out.close();
                    return;
                }

            }
        }

        try {
            // Create a new list to store the updated courses
            ListInterface<StudentCourse> updatedCourses = new LinkedList<>();

            // Update or add courses
            for (String cId : courseIds) {
                String status = request.getParameter(cId + "_status"); // Retrieve status for the current courseId
                if (status != null) {
                    StudentCourse studentCourse = studentCourseServices.getStudentCourseBysIdAndcId(id, cId);
                    Course c = CourseServices.getCourseById(cId);
                    String dayOfWeek = c.getDayOfWeek();
                    String startTime = c.getStartTime();
                    if (studentCourse == null) {
                        // If no course exists for the student and course ID, mark it for addition

                        ListInterface<String> newCourses = new LinkedList<>();
                        newCourses.add(cId);
                        StudentCourse newStudentCourse = new StudentCourse(id, newCourses, status); // Add status to the new course
                        boolean invalid = studentCourseServices.checkValidCourseTime(updatedCourses, id, dayOfWeek, startTime);
                        if (invalid) {
                            PrintWriter out = response.getWriter();
                            out.println("<script>alert('Time conflict. Please choose a different course or time slot.');</script>");
                            out.println("<script>window.location.href = '" + request.getContextPath() + "/studentServlet';</script>");
                            out.close();
                            return;
                        }
                        updatedCourses.add(newStudentCourse);
                        //StudentCourseDao.addCourse(newStudentCourse);
                    } else {
                        // If a course already exists, update its status
                        studentCourse.setCourseStatus(status); // Update the status of the existing course
                        boolean invalid = studentCourseServices.checkValidCourseTime(updatedCourses, id, dayOfWeek, startTime);
                        if (invalid) {
                            PrintWriter out = response.getWriter();
                            out.println("<script>alert('Time conflict. Please choose a different course or time slot.');</script>");
                            out.println("<script>window.location.href = '" + request.getContextPath() + "/studentServlet';</script>");
                            out.close();
                            return;
                        }
                        //StudentCourseDao.replaceCourseList(id, updatedCourses);
                        updatedCourses.add(studentCourse); // Add the updated course to the list
                    }
                }
            }

            // Replace the existing list of courses with the updated one
            studentCourseServices.replaceCourseList(id, updatedCourses);
            Tools.addHistory("Assigned course(s) to student '" + id + "'");

            // A successfull message prompt if course is able to assigned to the student 
            PrintWriter out = response.getWriter();
            out.println("<script>alert('Record added successfully!');</script>");
            out.println("<script>window.location.href = '" + request.getContextPath() + "/studentServlet?action=displayStudentDetails&id=" + id + "';</script>");
            out.close();
        } catch (Exception e) {
            // If any exception occurs during processing, show error message
            PrintWriter out = response.getWriter();
            out.println("<script>alert('Failed to added student course: " + e.getMessage() + "');</script>");
            out.println("<script>window.location.href = '" + request.getContextPath() + "/studentServlet';</script>");
            out.close();
        }

    }

    //----------------------------------------------------------------------------------------
    //                             FUNCTIONS
    //---------------------------------------------------------------------------------
    // ---------------------------------------------------------------------------
    //                            STUDENT SERVICES
    // ---------------------------------------------------------------------------
    public class studentServices {

        // add new student
        public static void addStudent(Student s) {
            // Add student to LinkedList
            mergedList.add(s);
            //mergeStudent(mergedList);
        }

        // Retrieve all student records
        public static ListInterface<Student> getAllStudents() {
            ListInterface<Student> mergedList = new LinkedList<>();
            mergeStudent(mergedList);
            return mergedList;
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
        // Retrieve all student records
        public static ListInterface<Student> getInactiveStudents() {

            // Return the inactive list
            return inactiveList;
        }

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

            // Check if mergedList is null
            if (mergedList != null) {
                // Define a predicate to check if student's name contains the search term
                Predicate<Student> nameContains = student -> student.getName().toLowerCase().contains(name.toLowerCase());

                // Use the search method with the defined predicate
                searchResults = mergedList.search(nameContains);
            }

            return searchResults;
        }
    }

    // add new course
//    public static void addCourse(StudentCourse sc) {
//        scList.add(sc);
//    }
    //----------------------------------------------------------------------------------------
    //                             student Course Services
    //---------------------------------------------------------------------------------
    public class studentCourseServices {

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
            for (int i = 1; i <= mergedList.getTotalNumberOfData(); i++) {
                Student s = mergedList.getData(i);
                if (s.getId().equals(id)) {
                    return s;
                }
            }
            // Return null if no student with the given ID is found
            return null;
        }

        // update Course
        public static void replaceCourseList(String studentId, ListInterface<StudentCourse> updatedCourses) {
            // Perform database operations to replace the existing list of courses with the updated one
            // For example, you might delete all existing courses associated with the student and then add the new courses
            scList.reset();
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
                    Course enrolledCourseDetails = CourseServices.getCourseById(courseId);
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
                    if (sc.getStudentId().equals(id) && !sc.getCourseStatus().equals("Resit")) {
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
    }

}
