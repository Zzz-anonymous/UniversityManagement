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
import Utility.Tools;

/**
 *
 * @author Zy
 */
@WebServlet("/studentServlet")
public class studentServlet extends HttpServlet {

    // check students availability, display students records if not empty
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String pathInfo = request.getPathInfo();

        if (pathInfo == null || pathInfo.equals("/")) {
            // Get both userInput and hardcoded students
            LinkedListInterface<Student> mergedList = StudentDao.getAllStudents();

            if (mergedList.isEmpty()) {
                // Display alert message if no students were found
                PrintWriter out = response.getWriter();
                out.println("<script>alert('No students were found.');</script>");
                out.println("<script>window.location.href = 'insertStudent.jsp';</script>");
                out.close();
            } else {
                request.setAttribute("mergedList", mergedList);
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
        String gender = request.getParameter("gender");
        String email = request.getParameter("email");
        String programmeId = request.getParameter("programmeId");

        // Create a new Student object with the provided data
        Student s = new Student(id, name, gender, email, 1, 0, programmeId);

        try {
            // Attempt to add the student
            StudentDao.addStudent(s);

            // If no exceptions are thrown, the addition was successful
            //LinkedListInterface<Student> updatedStudentList = StudentDao.getAllStudents();     
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
