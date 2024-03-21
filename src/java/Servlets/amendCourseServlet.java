/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Servlets;

import Dao.CourseDao;
import Entity.Course;
import Entity.Programme;
import Entity.Tutor;
import adt.LinkedListInterface;

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
@WebServlet("/amendCourseServlet")
public class amendCourseServlet extends HttpServlet {

    // display amendCourse.jsp to do modification of course
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String id = request.getParameter("id");
        // Retrieve course information based on the ID
        Course course = CourseDao.getCourseById(id);
        int creditHours = course.getCreditHours();

        // Retrieve selected course types
        LinkedListInterface<String> selectedCTypes = course.getCourseTypes();
        LinkedListInterface<Programme> selectedProgrammes = course.getProgramme();
        String tutorName = course.getTutor().getName();

        // Pass the course object and other necessary attributes to the JSP page
        request.setAttribute("course", course);
        request.setAttribute("creditHours", creditHours);
        request.setAttribute("selectedCTypes", selectedCTypes);
        request.setAttribute("selectedProgrammes", selectedProgrammes);
        request.setAttribute("tutorName", tutorName);
        
        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/views/amendCourse.jsp");
        dispatcher.forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

    }

}
