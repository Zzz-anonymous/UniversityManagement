/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Servlets;

import Dao.StudentDao;
import Entity.Student;
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
@WebServlet("/studentSearchServlet")
public class studentSearchServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Get the programmeId parameter from the request
        String programmeId = request.getParameter("programId");

        // If programmeId is null or empty, retrieve all students
        if (programmeId == null || programmeId.isEmpty()) {
            LinkedListInterface<Student> studentList = StudentDao.getAllStudents();
            request.setAttribute("studentList", studentList);
        } else {
            // Retrieve students based on the specified programmeId
            LinkedListInterface<Student> studentList = StudentDao.getStudentsByPid(programmeId);
            request.setAttribute("studentList", studentList);
        }

        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/views/students.jsp");
        dispatcher.forward(request, response);
    }

    // display the search result
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Retrieve the search query from the request parameter
        String name = request.getParameter("search");

        // Search for students with the given name
        LinkedListInterface<Student> searchResults = StudentDao.searchStudent(name);

        // Forward the search results to a JSP for display
        request.setAttribute("searchResults", searchResults);
        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/views/students.jsp");
        dispatcher.forward(request, response);
    }

}
