/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Servlets;

import Dao.CourseDao;
import Entity.Course;
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
@WebServlet("/deleteCourseServlet")
public class deleteCourseServlet extends HttpServlet {

    private final static ListInterface<Course> inactiveList = CourseDao.getInactiveCourses();

    // delete course
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        ListInterface<Course> cList = CourseDao.getAllCourses();
        // Get the Course ID from the request
        String id = request.getParameter("id");

        // Call the deleteStudent method passing the student ID and the list from which you want to delete
        boolean deletionSuccessful = CourseDao.deleteCourse(id, cList);

        if (!deletionSuccessful) {

            // Handle the case where studentList is empty
            PrintWriter out = response.getWriter();
            out.println("<script>alert('No courses found!');</script>");
            out.println("<script>window.location.replace('createCourseServlet');</script>");
            out.close();
        }

        RequestDispatcher dispatcher = request.getRequestDispatcher("createCourseServlet");
        dispatcher.forward(request, response);

    }

    // view inactive students list in inactiveCourse.jsp
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Check if studentList is empty
        if (inactiveList.isEmpty()) {
            // Handle the case where studentList is empty
            PrintWriter out = response.getWriter();
            out.println("<script>alert('No Courses are found!');</script>");
            out.println("<script>window.location.replace('createCourseServlet');</script>");
            out.close();
            return;
        } else {
            request.setAttribute("inactiveList", inactiveList);
            RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/views/inactiveCourses.jsp");
            dispatcher.forward(request, response);
        }

    }

}
