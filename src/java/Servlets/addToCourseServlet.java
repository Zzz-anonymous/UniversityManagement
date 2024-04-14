/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Servlets;

import Dao.CourseDao;
import Dao.StudentDao;
import Dao.StudentCourseDao;
import Entity.Course;
import Entity.Student;
import Entity.StudentCourse;
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
@WebServlet("/addToCourseServlet")
public class addToCourseServlet extends HttpServlet {

    private final static ListInterface<Student> mergedList = StudentDao.getAllStudents();
    private static final ListInterface<StudentCourse> scList = StudentCourseDao.getAllCourses();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
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
        ListInterface<StudentCourse> studentCourseList = StudentCourseDao.getStudentCourseListById(id);

        ListInterface<String> existingCourses = new LinkedList<>();
        ListInterface<String> courseStatuses = new LinkedList<>();
        if (studentCourseList != null && !studentCourseList.isEmpty()) {
            for (int i = 1; i <= studentCourseList.getTotalNumberOfData(); i++) {
                StudentCourse sc = studentCourseList.getData(i);
                ListInterface<String> courseIds = sc.getCourseId();
                if (courseIds != null && !courseIds.isEmpty()) {
                    for (int j = 1; j <= courseIds.getTotalNumberOfData(); j++) {
                        existingCourses.add(courseIds.getData(j));
                        // Retrieve course status for each individual course ID
                        String courseStatus = StudentCourseDao.getStudentCourseStatusById(courseIds.getData(j));
                        courseStatuses.add(courseStatus);
                    }
                }
            }
        }

        request.setAttribute("existingCourses", existingCourses); // Assuming existingCourses is a list of course IDs assigned to the student
        request.setAttribute("courseStatuses", courseStatuses);

        RequestDispatcher dispatcher = request.getRequestDispatcher("addStudentCourse.jsp");
        dispatcher.forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
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
            out.println("<script>window.location.href = '" + request.getContextPath() + "/studentDetailsServlet?id=" + id + "';</script>");
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
