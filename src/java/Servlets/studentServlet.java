/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Servlets;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.RequestDispatcher;

import adt.*;
import Entity.Student;
import Dao.StudentDao;

/**
 *
 * @author Zy
 */
@WebServlet("/studentServlet")
public class studentServlet extends HttpServlet {

    private static ListInterface<Student> studentList = StudentDao.getAllStudents();

    // check students availability, display students records if not empty
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String pathInfo = request.getPathInfo();

        if (pathInfo == null || pathInfo.equals("/")) {
            ListInterface<Student> sList = StudentDao.getAllStudents();
            if (sList.isEmpty()) {
                // Display alert message if no students were found
                PrintWriter out = response.getWriter();
                out.println("<script>alert('No students were found.');</script>");
                out.println("<script>window.location.href = 'insertStudent.jsp';</script>");
                out.close();
            } else {
                request.setAttribute("sList", sList);
                RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/views/students.jsp");
                dispatcher.forward(request, response);
            }
        } else {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
    }

    @Override
    // insert a student
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html");

        String id = request.getParameter("id");

        // Check if the ID already exists in the studentList
        boolean idExists = StudentDao.availableId(id);

        if (idExists) {
            // ID already exists, handle accordingly (e.g., show error message)
            PrintWriter out = response.getWriter();
            out.println("<script>alert('ID already exists. Please choose a different ID.');</script>");
            out.println("<script>window.location.href = 'insertStudent.jsp';</script>");
            out.close();
            return;
        }

        String name = request.getParameter("name");
        String ic = request.getParameter("ic");
        String gender = request.getParameter("gender");
        String email = request.getParameter("email");
        String programme = request.getParameter("programme");

        // Create a new Student object with the provided data
        Student s = new Student(id, name, ic, gender, email, 0, programme);

        try {
            // Attempt to add the student
            StudentDao.addStudent(s);
            
            // If no exceptions are thrown, the addition was successful
            // Save student records to file
            ListInterface<Student> updatedStudentList = StudentDao.getAllStudents();
            StudentDao.saveToFile(updatedStudentList, getServletContext());

            // If no exceptions are thrown, the addition was successful
            PrintWriter out = response.getWriter();
            out.println("<script>alert('Record saved successfully!');</script>");
            out.println("<script>window.location.href = '" + request.getContextPath() + "/studentServlet';</script>");
            out.close();
        } catch (Exception e) {
            // If an exception occurs, it means the addition failed
            PrintWriter out = response.getWriter();
            out.println("<script>alert('Failed to add student!');</script>");
            out.println("<script>window.location.replace('insertStudent.jsp');</script>");
            out.close();
        }

    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
