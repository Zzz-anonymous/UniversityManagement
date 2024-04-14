/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Servlets;

import Dao.ProgrammeDao;
import Dao.StudentCourseDao;
import Dao.StudentDao;
import Entity.Programme;
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
@WebServlet("/studentDetailsServlet")
public class studentDetailsServlet extends HttpServlet {

    private static final ListInterface<StudentCourse> scList = StudentCourseDao.getAllCourses();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String id = request.getParameter("id");
        Student student = StudentDao.getStudentById(id);
        String pathInfo = request.getPathInfo();

        if (pathInfo == null || pathInfo.equals("/")) {
            request.setAttribute("student", student);
            request.setAttribute("scList", scList);
            RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/views/studentReport1.jsp?id=" + id);
            dispatcher.forward(request, response);

        } else {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String id = request.getParameter("id");
        Student student = StudentDao.getStudentById(id);

        RequestDispatcher dispatcher = request.getRequestDispatcher("/studentDetailsServlet");
        dispatcher.forward(request, response);
    }

}
