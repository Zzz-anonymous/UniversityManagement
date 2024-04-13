/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Servlets;

import Dao.StudentCourseDao;
import Dao.StudentDao;
import Entity.Student;
import Entity.StudentCourse;
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
@WebServlet("/toolsServlet")
public class toolsServlet extends HttpServlet {
    private final static ListInterface<Student> mergedList = StudentDao.getAllStudents();
//    @Override
//    protected void doGet(HttpServletRequest request, HttpServletResponse response)
//            throws ServletException, IOException {
//         // student id 
//        String id = request.getParameter("id");
//        // Retrieve course information based on the ID
//        Student student = StudentDao.getStudentById(id);
//         
//       // Check if the student exists in the mergedList
//        int index = StudentDao.getIndex(id, mergedList);
//        if (index != -1) {
//            // If not found in mergedList, check in inactiveList
//            student = mergedList.getData(index);
//        }
//        
//
//        // Pass the course object and other necessary attributes to the JSP page
//        request.setAttribute("student", student);
//        
//        
//        RequestDispatcher dispatcher = request.getRequestDispatcher("addStudentCourse.jsp");
//        dispatcher.forward(request, response);
//    }
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
      // Forward the request to the students.jsp file
        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/views/students.jsp");
        dispatcher.forward(request, response);
    }

   
}
