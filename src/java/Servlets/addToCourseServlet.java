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
@WebServlet("/addToCourseServlet")
public class addToCourseServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
       

        RequestDispatcher dispatcher = request.getRequestDispatcher("addCourse.jsp");
        dispatcher.forward(request, response);
    }


    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String ids = request.getParameter("ids");
        String[] studentIds = ids.split(",");
        
        // Validate the selected student IDs
        LinkedListInterface<Student> existingStudents = StudentDao.validateStudentIds(studentIds);
        
        if (existingStudents.isEmpty()) {
            // ID already exists, handle accordingly (e.g., show error message)
            PrintWriter out = response.getWriter();
            out.println("<script>alert('No ID is selected !');</script>");
              out.println("<script>window.location.href = '" + request.getContextPath() + "/studentServlet';</script>");
            out.close();
            return;
        }
        
        RequestDispatcher dispatcher = request.getRequestDispatcher("addCourse.jsp");
        dispatcher.forward(request, response);
    }

  

}
