/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Servlets;

import Dao.CourseDao;
import Dao.ProgrammeCourseDao;
import Dao.StudentCourseDao;
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

/**
 *
 * @author Zy
 */
@WebServlet("/studentServlet")
public class studentServlet extends HttpServlet {

    private final static ListInterface<Student> mergedList = StudentDao.getAllStudents();
    private final static ListInterface<Student> inactiveList = StudentDao.getInactiveStudents();
    private static final ListInterface<StudentCourse> scList = StudentCourseDao.getAllCourses();

    // check students availability, display students records if not empty
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
                out.println("<script>window.location.href = 'insertStudent.jsp';</script>");
                out.close();
            } else {
                request.setAttribute("resultList", mergedList);
                RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/views/students.jsp");
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
            ListInterface<Student> resultList = StudentDao.getAllStudents();
            request.setAttribute("resultList", resultList);
        } else {
            // Retrieve students based on the specified programmeId
            ListInterface<Student> resultList = StudentDao.getStudentsByPid(programmeId);
            request.setAttribute("resultList", resultList);
        }

        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/views/students.jsp");
        dispatcher.forward(request, response);
    }

    // filtering inactive students based on program
    private void filterInactiveStudents(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Get the programmeId parameter from the request
        String programmeId = request.getParameter("programId");

        // If programmeId is null or empty, retrieve all students
        if (programmeId == null || programmeId.isEmpty()) {
            ListInterface<Student> resultList = StudentDao.getInactiveStudents();
            request.setAttribute("resultList", resultList);
        } else {
            // Retrieve students based on the specified programmeId
            ListInterface<Student> resultList = StudentDao.getInactiveStudentsByPid(programmeId);
            request.setAttribute("resultList", resultList);
        }

        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/views/inactiveStudents.jsp");
        dispatcher.forward(request, response);

    }
    // modify student records
    private void amendStudent(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Retrieve student information based on the ID
        String id = request.getParameter("id");
        Student student = StudentDao.getStudentById(id);
        // Retrieve the gender of the student's 
        String gender = student.getGender();
        String ProgrammeId = student.getProgrammeId();

        // Check if the student exists in the mergedList
        int index = StudentDao.getIndex(id, mergedList);
        if (index != -1) {
            // If not found in mergedList, check in inactiveList
            student = mergedList.getData(index);
        }

        // Pass the student object to the JSP page
        request.setAttribute("student", student);
        request.setAttribute("gender", gender);
        request.setAttribute("ProgrammeId", ProgrammeId);

        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/views/amendStudent.jsp");
        dispatcher.forward(request, response);

    }

    // delete a student to inactive list (withdraw)
    private void deleteStudent(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        //ListInterface<Student> mergedList = StudentDao.getAllStudents();
        // Get the student ID from the request
        String id = request.getParameter("id");

        // Call the deleteStudent method passing the student ID and the list from which you want to delete
        boolean deletionSuccessful = StudentDao.deleteStudent(id, mergedList);

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

        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/views/students.jsp");
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

        // Retrieve course information based on the ID
        Student student = StudentDao.getStudentById(id);

        // Check if the student exists in the mergedList
        int index = StudentDao.getIndex(id, mergedList);
        if (index != -1) {
            // If not found in mergedList, check in inactiveList
            student = mergedList.getData(index);
        }

        // request.setAttribute("courseStatus", courseStatus);
        // Pass the course object and other necessary attributes to the JSP page
        request.setAttribute("student", student);
        ListInterface<ProgrammeCourse> availableCourses = ProgrammeCourseDao.getProgrammeCoursesByCourseId(id);

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
                        String courseStatus = StudentCourseDao.getStudentCourseStatusById(courseIds.getData(j));
                        courseStatuses.add(courseStatus);
                    }
                }
            }
        }else{
            PrintWriter out = response.getWriter();
            out.println("<script>alert('No available courses to assign!');</script>");
            out.println("<script>window.location.href = '" + request.getContextPath() + "/studentServlet';</script>");
            out.close();
            return;
        }
        
        request.setAttribute("existingCourses", existingCourses); // Assuming existingCourses is a list of course IDs assigned to the student
        request.setAttribute("courseStatuses", courseStatuses);

        RequestDispatcher dispatcher = request.getRequestDispatcher("addStudentCourse.jsp");
        dispatcher.forward(request, response);
    }

    // display student payment details for their enrolled course
    private void displayPaymentDetails(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String id = request.getParameter("id");
        Student student = StudentDao.getStudentById(id);
        String pathInfo = request.getPathInfo();

        if (pathInfo == null || pathInfo.equals("/")) {
            request.setAttribute("student", student);
            request.setAttribute("scList", scList);
            RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/views/displayStudentBills.jsp?id=" + id);
            dispatcher.forward(request, response);

        } else {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
    }

    // display student details (Report 1)
    private void displayStudentDetails(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String id = request.getParameter("id");
        Student student = StudentDao.getStudentById(id);
        String pathInfo = request.getPathInfo();

        if (pathInfo == null || pathInfo.equals("/")) {
            request.setAttribute("student", student);
            request.setAttribute("scList", scList);
            RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/views/studentReport1.jsp?id=" + id);
            dispatcher.forward(request, response);

        } else {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
    }

    // view the total number of active students and inactive students
    private void studentChart(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        //ListInterface<Student> inactiveList = StudentDao.getInactiveStudents();
        //ListInterface<Student> activeList = StudentDao.getAllStudents();

        // Set the counts as attributes in the request
        request.setAttribute("activeList", mergedList);
        request.setAttribute("inactiveList", inactiveList);

        // Forward the request to a JSP page where you generate the chart
        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/views/studentReport2.jsp");
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
        boolean idExists = StudentDao.availableId(id, mergedList);

        if (idExists) {
            // ID already exists, handle accordingly (e.g., show error message)
            PrintWriter out = response.getWriter();
            out.println("<script>alert('ID already exists. Please choose a different ID.');</script>");
            out.println("<script>window.location.href = 'insertStudent.jsp';</script>");
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
            StudentDao.addStudent(s);

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
            out.println("<script>window.location.replace('insertStudent.jsp');</script>");
            out.close();
        }
    }

    // display the search results
    private void studentSearchResult(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Retrieve the search query from the request parameter
        String name = request.getParameter("search");

        // Search for students with the given name
        ListInterface<Student> searchResults = StudentDao.searchStudent(name);

        // Forward the search results to a JSP for display
        request.setAttribute("searchResults", searchResults);
        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/views/students.jsp");
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
            out.println("<script>window.location.replace('studentAmendServlet');</script>");
            out.close();
            return;
        }

        // Check if the student with the provided ID exists (For Active Students)
        int index = StudentDao.getIndex(id, mergedList);
        if (index != -1) {
            // Student exists, proceed with updating
            // Remove the existing student from the mergedList
            mergedList.remove(index);

            // Add the new student to the mergedList
            mergedList.add(index, s);

            boolean status = StudentDao.updateStudent(id, s, mergedList);
            if (status) {
                // Student updated successfully
                PrintWriter out = response.getWriter();
                out.println("<script>alert('Record saved successfully!');</script>");
                out.println("<script>window.location.href = '" + request.getContextPath() + "/studentServlet';</script>");
                out.close();
            } else {
                // Failed to update student, display an error message
                PrintWriter out = response.getWriter();
                out.println("<script>alert('Failed to update student record!');</script>");
                out.println("<script>window.location.replace('studentAmendServlet');</script>");
                out.close();
            }
        } else {
            // Student with the provided ID does not exist, display an error message
            PrintWriter out = response.getWriter();
            out.println("<script>alert('Student with ID " + id + " does not exist!');</script>");
            out.println("<script>window.location.replace('studentAmendServlet');</script>");
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
                Course c = CourseDao.getCourseById(courseId);
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
                    StudentCourse studentCourse = StudentCourseDao.getStudentCourseByIdAndCourseId(id, cId);
                    if (studentCourse == null) {
                        // If no course exists for the student and course ID, mark it for addition
                        ListInterface<String> newCourses = new LinkedList<>();
                        newCourses.add(cId);
                        StudentCourse newStudentCourse = new StudentCourse(id, newCourses, status); // Add status to the new course
                        updatedCourses.add(newStudentCourse);
                    } else {
                        // If a course already exists, update its status
                        studentCourse.setCourseStatus(status); // Update the status of the existing course
                        updatedCourses.add(studentCourse); // Add the updated course to the list
                    }
                }
            }

            // Replace the existing list of courses with the updated one
            StudentCourseDao.replaceCourseList(id, updatedCourses);

            // If the loop completes successfully (total credit hours <= 23), show success message
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
}
