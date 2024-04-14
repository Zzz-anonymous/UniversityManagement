/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Servlets;

import Dao.StudentDao;
import Entity.Student;
import Utility.Tools;
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

    private final static ListInterface<Student> mergedList = StudentDao.getAllStudents();
    private final static ListInterface<Student> inactiveList = StudentDao.getInactiveStudents();

    // pass id to amendStudent.jsp to modify that particular student
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Retrieve student information based on the ID
        String id = request.getParameter("id");
        Student student = StudentDao.getStudentById(id);
        // Retrieve the gender of the student's 
        String gender = student.getGender();
        String ProgrammeId = student.getProgrammeId(); 
        
        // Check if the student exists in the mergedList
        int index = StudentDao.getIndex(id, mergedList);
        if (index != -1) {
            // If not found in mergedList, check in inactiveList
            student = mergedList.getData(index);
        }
        
        // Pass the student object to the JSP page
        request.setAttribute("student", student);
        request.setAttribute("gender", gender);
        request.setAttribute("ProgrammeId", ProgrammeId);
        
        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/views/amendStudent.jsp");
        dispatcher.forward(request, response);
    }

    // update student
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String id = request.getParameter("id");
        String name = request.getParameter("name");
        String gender = request.getParameter("gender");
        String email = request.getParameter("email");
        String programme = request.getParameter("programmeId");

        // Create a new Student object with the provided data
        Student s = new Student(id, name, gender, email, 1, programme);

        // Check if studentList is empty
        if (mergedList.isEmpty()) {
            // Handle the case where studentList is empty
            PrintWriter out = response.getWriter();
            out.println("<script>alert('No students found!');</script>");
            out.println("<script>window.location.replace('studentAmendServlet');</script>");
            out.close();
            return;
        } 

        // Check if the student with the provided ID exists (For Active Students)
        int index = StudentDao.getIndex(id, mergedList);
        if (index != -1) {
            // Student exists, proceed with updating
            // Remove the existing student from the mergedList
            mergedList.remove(index);

            // Add the new student to the mergedList
            mergedList.add(index, s);

            boolean status = StudentDao.updateStudent(id, s, mergedList);
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
                out.println("<script>window.location.replace('studentAmendServlet');</script>");
                out.close();
            }
        } else {
            // Student with the provided ID does not exist, display an error message
            PrintWriter out = response.getWriter();
            out.println("<script>alert('Student with ID " + id + " does not exist!');</script>");
            out.println("<script>window.location.replace('studentAmendServlet');</script>");
            out.close();
        }
        
    }

}
