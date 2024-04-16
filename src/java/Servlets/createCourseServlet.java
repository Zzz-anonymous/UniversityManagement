/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Servlets;

import Dao.CourseDao;
import Dao.ProgrammeDao;
import Dao.TutorDao;

import Entity.*;
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
@WebServlet("/createCourseServlet")
public class createCourseServlet extends HttpServlet {

    ListInterface<Course> cList = CourseDao.getAllCourses();

    // check courses availability + display courses
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String pathInfo = request.getPathInfo();

        if (pathInfo == null || pathInfo.equals("/")) {
            if (cList.isEmpty()) {
                // Display alert message if no students were found
                PrintWriter out = response.getWriter();
                out.println("<script>alert('No Course were found.');</script>");
                out.println("<script>window.location.href = 'createCourse.jsp';</script>");
                out.close();
            } else {

                request.setAttribute("cList", cList);
                RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/views/displayCourses.jsp");
                dispatcher.forward(request, response);
            }
        } else {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
    }

    // Create a course
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html");

        String id = request.getParameter("id");

        // Check if the ID already exists in the courseList
        boolean idExists = CourseDao.availableId(id, cList);

        if (idExists) {
            // ID already exists, handle accordingly (e.g., show error message)
            PrintWriter out = response.getWriter();
            out.println("<script>alert('ID already exists. Please choose a different ID.');</script>");
            out.println("<script>window.location.href = 'createCourse.jsp';</script>");
            out.close();
            return;
        }

        String name = request.getParameter("name");
        String details = request.getParameter("details");

        // to store selected courseType (T,P,L)
        ListInterface<String> selectedCTypes = new LinkedList<>();
        // Obtain the selected course types from the request parameters
        String[] courseTypes = request.getParameterValues("courseTypes");

        // Check if any course types are selected
        if (courseTypes != null) {
            // Add each course type to the LinkedList
            for (String courseType : courseTypes) {
                selectedCTypes.add(courseType);
            }
        } else {
            // No course types selected, display an alert message
            PrintWriter out = response.getWriter();
            out.println("<script>alert('Please select at least one course type!');</script>");
            out.println("<script>window.location.href = '" + request.getContextPath() + "/createCourseServlet';</script>");
            out.close();
            return; // Exit the method
        }

        String ch = request.getParameter("creditHours");
        int creditHours = Integer.parseInt(ch);

        String t = request.getParameter("tutorName");
        // Get the tutor object using the name
        Tutor tutorName = TutorDao.findTutorByName(t);

        ListInterface<Programme> programmes = new LinkedList<>();
        // Obtain the selected programme names from the request parameters
        String[] programmeNames = request.getParameterValues("programmeName[]");

        // Check if any programme names are selected
        if (programmeNames != null) {
            // Iterate over the selected programme names and find the corresponding Programme objects
            for (String programmeName : programmeNames) {
                Programme p = ProgrammeDao.findProgrammeByName(programmeName);
                if (p != null) {
                    programmes.add(p);
                }
            }
            // Now you have a list of Programme objects corresponding to the selected programme names
        } else {
            // No course types selected, display an alert message
            PrintWriter out = response.getWriter();
            out.println("<script>alert('Please select at least one programme!');</script>");
            out.println("<script>window.location.href = '" + request.getContextPath() + "/createCourseServlet';</script>");
            out.close();
            return; // Exit the method
        }

        String f = request.getParameter("facultyName");
        // Get the faculty object using the name
        Faculty facultyName = CourseDao.findfacultiesByName(f);

        

        // Create a new Course object with the provided data
        Course c = new Course(id, name, details, selectedCTypes, creditHours, tutorName, programmes, facultyName, 1);

        try {
            // Attempt to add the course
            CourseDao.addCourse(c);

            // If no exceptions are thrown, the addition was successful
            PrintWriter out = response.getWriter();
            out.println("<script>alert('Record saved successfully!');</script>");
            out.println("<script>window.location.href = '" + request.getContextPath() + "/createCourseServlet';</script>");
            out.close();
        } catch (Exception e) {
            // If an exception occurs, it means the addition failed
            PrintWriter out = response.getWriter();
            out.println("<script>alert('Failed to create course!');</script>");
            out.println("<script>window.location.replace('createCourse.jsp');</script>");
            out.close();
        }

    }

}
