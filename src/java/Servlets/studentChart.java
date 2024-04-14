/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Servlets;

import Dao.StudentDao;
import Entity.Student;
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
@WebServlet(name = "studentChart", urlPatterns = {"/studentChart"})
public class studentChart extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        ListInterface<Student> inactiveList = StudentDao.getInactiveStudents();
        ListInterface<Student> activeList = StudentDao.getAllStudents();
        
        // Set the counts as attributes in the request
        request.setAttribute("activeList", activeList);
        request.setAttribute("inactiveList", inactiveList);

        // Forward the request to a JSP page where you generate the chart
        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/views/studentReport2.jsp");
        dispatcher.forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

    }

}
