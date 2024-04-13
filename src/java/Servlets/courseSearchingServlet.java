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
@WebServlet("/courseSearchingServlet")
public class courseSearchingServlet extends HttpServlet {

   // filtering course based on faculty
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Get the programmeId parameter from the request
        String facultyId = request.getParameter("facultyId");

        // If programmeId is null or empty, retrieve all students
        if (facultyId == null || facultyId.isEmpty()) {
            ListInterface<Course> cList = CourseDao.getAllCourses();
            request.setAttribute("cList", cList);
        } else {
            // Retrieve students based on the specified programmeId
            ListInterface<Course> cList = CourseDao.getCoursesByFid(facultyId);
            request.setAttribute("cList", cList);
        }

        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/views/displayCourses.jsp");
        dispatcher.forward(request, response);
    }
    // display the search result
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Retrieve the search query from the request parameter
        String name = request.getParameter("search");

        // Search for students with the given name
        ListInterface<Course> searchResults = CourseDao.searchCourses(name);

        // Forward the search results to a JSP for display
        request.setAttribute("searchResults", searchResults);
        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/views/displayCourses.jsp");
        dispatcher.forward(request, response);
    }
}
