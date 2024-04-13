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
@WebServlet("/filterInactiveStudentServlet")
public class filterInactiveStudentServlet extends HttpServlet {

    // filtering students based on program
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Get the programmeId parameter from the request
        String programmeId = request.getParameter("programId");

        // If programmeId is null or empty, retrieve all students
        if (programmeId == null || programmeId.isEmpty()) {
            LinkedListInterface<Student> inactiveList = StudentDao.getInactiveStudents();
            request.setAttribute("inactiveList", inactiveList);
        } else {
            // Retrieve students based on the specified programmeId
            LinkedListInterface<Student> inactiveList = StudentDao.getInactiveStudentsByPid(programmeId);
            request.setAttribute("inactiveList", inactiveList);
        }

        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/views/inactiveStudents.jsp");
        dispatcher.forward(request, response);
    }

    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
    }

    
}
