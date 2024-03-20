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

    private final static LinkedListInterface<Student> mergedList = StudentDao.getAllStudents();
    private final static LinkedListInterface<Student> inactiveList = StudentDao.getInactiveStudents();

    // pass id to amendStudent.jsp to modify that particular student
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String id = request.getParameter("id");
        // Retrieve student information based on the ID
        Student student = null;

        // Check if the student exists in the mergedList
        int index = StudentDao.getIndex(id, mergedList);
        if (index != -1) {
            student = mergedList.getData(index);
        } else {
            // If not found in mergedList, check in inactiveList
            index = StudentDao.getIndex(id, inactiveList);
            if (index != -1) {
                student = inactiveList.getData(index);
            }
        }

        // Pass the student object to the JSP page
        request.setAttribute("student", student);

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

        String stat = request.getParameter("status");
        int sStatus = Integer.parseInt(stat);

        String programme = request.getParameter("programmeId");

        // Create a new Student object with the provided data
        Student s = new Student(id, name, gender, email, sStatus, 0, programme);

        // Check if studentList is empty
        if (mergedList.isEmpty()) {
            // Handle the case where studentList is empty
            PrintWriter out = response.getWriter();
            out.println("<script>alert('No students found!');</script>");
            out.println("<script>window.location.replace('studentAmendServlet');</script>");
            out.close();
            return;
        } else if (inactiveList.isEmpty()) {
            // Handle the case where inactiveList is empty
            PrintWriter out = response.getWriter();
            out.println("<script>alert('No inactive students found!');</script>");
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
        
        // For inactive students
        int indexNum = StudentDao.getIndex(id, inactiveList);
    if (indexNum != -1) {
        // Student exists in the inactiveList, proceed with updating
        // Remove the existing student from the inactiveList
        inactiveList.remove(indexNum);

        // Add the new student to the inactiveList
        inactiveList.add(indexNum, s);

        boolean status = StudentDao.updateStudent(id, s, inactiveList);
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
        // Student with the provided ID does not exist in the inactiveList, display an error message
        PrintWriter out = response.getWriter();
        out.println("<script>alert('Inactive student with ID " + id + " does not exist!');</script>");
        out.println("<script>window.location.replace('studentAmendServlet');</script>");
        out.close();
    }
        
    }

}
