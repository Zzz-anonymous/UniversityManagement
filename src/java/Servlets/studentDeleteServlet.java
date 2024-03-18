/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Servlets;

import Dao.StudentDao;
import Entity.Student;
import adt.ArrayList;
import adt.ListInterface;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.RequestDispatcher;

/**
 *
 * @author Zy
 */
@WebServlet("/studentDeleteServlet")
public class studentDeleteServlet extends HttpServlet {
   
    // delete
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Get the student ID from the request
        String id = request.getParameter("id");

        // Call the deleteStudent method passing the student ID
        StudentDao.deleteStudent(id);
        
        // Update the studentList attribute in the request
        request.setAttribute("mergedList", StudentDao.getAllStudents());
        
        // Call the deleteStudent method passing the student ID
        StudentDao.deleteStudent(id);
        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/views/students.jsp");
        dispatcher.forward(request, response);

    }

}
