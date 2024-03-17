/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Servlets;

import Dao.CourseDao;
import static Dao.ProgrammeDao.findProgrammeByName;
import Entity.Course;
import Entity.Programme;
import adt.*;

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
@WebServlet("/createCourseServlet")
public class createCourseServlet extends HttpServlet {

    // check students availability
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String pathInfo = request.getPathInfo();

        if (pathInfo == null || pathInfo.equals("/")) {
            ListInterface<Course> cList = CourseDao.getAllCourses();
            if (cList.isEmpty()) {
                // Display alert message if no students were found
                PrintWriter out = response.getWriter();
                out.println("<script>alert('No Course were found.');</script>");
                out.println("<script>window.location.href = 'createCourse.jsp';</script>");
                out.close();
            } else {
                
                request.setAttribute("cList", cList);
                RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/views/displayCourses.jsp");
                dispatcher.forward(request, response);
            }
        } else {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    // Create a course
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html");

        String id = request.getParameter("id");

        // Check if the ID already exists in the studentList
        boolean idExists = CourseDao.availableId(id);

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
        String status = request.getParameter("status");

        // Obtain the selected course types from the request parameters
        String[] courseTypes = request.getParameterValues("courseTypes");

        // Check if any course types are selected
        if (courseTypes == null || courseTypes.length == 0) {
            // No course types selected, display an alert message
            PrintWriter out = response.getWriter();
            out.println("<script>alert('Please select at least one course type!');</script>");
            out.println("<script>window.location.href = '" + request.getContextPath() + "/createCourseServlet';</script>");
            out.close();
            return; // Exit the method
        }

        // Create an instance of CircularArrayQueue<CourseType> to store the selected course types
        //CircularArrayQueue<String> courseTypes = new CircularArrayQueue<>(selectedTypes.length);

        // Enqueue the selected course types into the circular array queue
//        for (String type : selectedTypes) {
//            courseTypes.enqueue(type);
//        }

        String ch = request.getParameter("creditHours");
        int creditHours = Integer.parseInt(ch);

        String p = request.getParameter("ProgrammeName");
        Programme programme = findProgrammeByName(p);
        
        String avail = request.getParameter("avail");
        int available = Integer.parseInt(avail);

        // Create a new Course object with the provided data
        Course c = new Course(id, name, details, status, courseTypes, creditHours, programme, available);

        try {
            // Attempt to add the course
            CourseDao.addCourse(c);

            // If no exceptions are thrown, the addition was successful
            PrintWriter out = response.getWriter();
            out.println("<script>alert('Record saved successfully!');</script>");
            out.println("<script>window.location.href = '" + request.getContextPath() + "/createCourseServlet';</script>");
            out.close();
        } catch (Exception e) {
            // If an exception occurs, it means the addition failed
            PrintWriter out = response.getWriter();
            out.println("<script>alert('Failed to create course!');</script>");
            out.println("<script>window.location.replace('createCourse.jsp');</script>");
            out.close();
        }

    }

}
