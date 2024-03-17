/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Servlets;

import Dao.StudentDao;
import Entity.Student;
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
@WebServlet("/studentAmendServlet")
public class studentAmendServlet extends HttpServlet {

    private final static LinkedListInterface<Student> sList = StudentDao.getAllStudents();

    
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String id = request.getParameter("id");
        // Retrieve student information based on the ID
        Student student = StudentDao.getStudentById(id);
        // Pass the student object to the JSP page
        request.setAttribute("student", student);
        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/views/amendStudent.jsp");
        dispatcher.forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String id = request.getParameter("id");
        String name = request.getParameter("name");
        String ic = request.getParameter("ic");
        String gender = request.getParameter("gender");
        String email = request.getParameter("email");
        
        String stat = request.getParameter("status");
        int sStatus = Integer.parseInt(stat);
        
        String programme = request.getParameter("programme");

        // Create a new Student object with the provided data
        Student s = new Student(id, name, ic, gender, email, sStatus, 0,programme);

        // Check if studentList is empty
        if (sList.isEmpty()) {
            // Handle the case where studentList is empty
            PrintWriter out = response.getWriter();
            out.println("<script>alert('No students found!');</script>");
            out.println("<script>window.location.replace('index.html');</script>");
            out.close();
            return;
        }
        
        // Check if the student with the provided ID exists
        int index = StudentDao.getIndex(id);
        if (index != -1) {
            // Student exists, proceed with updating
            boolean status = StudentDao.updateStudent( id, s);
            if (status) {
                // Student updated successfully
                PrintWriter out = response.getWriter();
                out.println("<script>alert('Record saved successfully!');</script>");
                out.println("<script>window.location.href = '" + request.getContextPath() + "/studentServlet';</script>");
                out.close();
            } else {
                // Failed to update student, display an error message
                PrintWriter out = response.getWriter();
                out.println("<script>alert('Failed to update student record!');</script>");
                out.println("<script>window.location.replace('index.html');</script>");
                out.close();
            }
        } else {
            // Student with the provided ID does not exist, display an error message
            PrintWriter out = response.getWriter();
            out.println("<script>alert('Student with ID " + id + " does not exist!');</script>");
            out.println("<script>window.location.replace('index.html');</script>");
            out.close();
        }
    }

}
