/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Servlets;

import Dao.StudentDao;
import Entity.Student;
import adt.LinkedList;
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
        private final static ListInterface<Student> inactiveList = StudentDao.getInactiveStudents() ;
        
    // delete student
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        ListInterface<Student> mergedList = StudentDao.getAllStudents();
        // Get the student ID from the request
        String id = request.getParameter("id");

        // Call the deleteStudent method passing the student ID and the list from which you want to delete
        boolean deletionSuccessful = StudentDao.deleteStudent(id, mergedList);

        if (deletionSuccessful) {
            // Update the studentList attribute in the request if deletion was successful
            request.setAttribute("mergedList", mergedList);
        } else {
            // Handle the case where studentList is empty
            PrintWriter out = response.getWriter();
            out.println("<script>alert('No students found!');</script>");
            out.println("<script>window.location.replace('studentAmendServlet');</script>");
            out.close();
        }

        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/views/students.jsp");
        dispatcher.forward(request, response);

    }
    
    // view inactive students list in inactiveStudent.jsp
     @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
       

        // Check if studentList is empty
        if (inactiveList.isEmpty()) {
            // Handle the case where studentList is empty
            PrintWriter out = response.getWriter();
            out.println("<script>alert('No students found!');</script>");
            out.println("<script>window.location.replace('studentServlet');</script>");
            out.close();
            return;
        }else{   
            request.setAttribute("inactiveList", inactiveList);
            RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/views/inactiveStudents.jsp");
            dispatcher.forward(request, response);
        }
           
    }

}
