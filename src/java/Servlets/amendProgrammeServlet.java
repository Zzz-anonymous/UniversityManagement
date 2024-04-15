/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Servlets;

import Dao.CourseDao;
import Dao.ProgrammeCourseDao;
import Dao.ProgrammeDao;
import Entity.Course;
import Entity.Programme;
import Entity.ProgrammeCourse;
import Utility.Tools;
import adt.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Iterator;
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
@WebServlet("/amendProgrammeServlet")
public class amendProgrammeServlet extends HttpServlet {

    private static ListInterface<Programme> pList = Tools.initializeProgrammes();
    private static ListInterface<Course> cList = CourseDao.getAllCourses();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // programme id
        String id = request.getParameter("id");

        // Retrieve programme information
        Programme programme = ProgrammeDao.findProgrammeById(id); // Assuming this method exists in your ProgrammeDao class
        int index = ProgrammeDao.getIndex(id, pList);
        if (index != -1) {
            programme = pList.getData(index);
        }

        // Create a new ListInterface and add the programme to it
        ListInterface<Programme> programmeList = new LinkedList<>();
        programmeList.add(programme);
        request.setAttribute("programme", programmeList);

        // Retrieve the availableCourses for the program
        ListInterface<ProgrammeCourse> availableCourses = ProgrammeCourseDao.getProgrammeCoursesByCourseId(id);
        ListInterface<String> existingCourses = new LinkedList<>();

        if (availableCourses != null && !availableCourses.isEmpty()) {
            for (int i = 1; i <= availableCourses.getTotalNumberOfData(); i++) {
                ProgrammeCourse pc = availableCourses.getData(i);
                ListInterface<String> courseIds = pc.getCourseId();
                if (courseIds != null && !courseIds.isEmpty()) {
                    for (int j = 1; j <= courseIds.getTotalNumberOfData(); j++) {
                        existingCourses.add(courseIds.getData(j));
                    }
                }
            }
            // Set attributes and forward request only if availableCourses is not null and not empty
            request.setAttribute("existingCourses", existingCourses);

            RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/views/addCourseProgramme.jsp");
            dispatcher.forward(request, response);
        }
        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/views/addCourseProgramme.jsp");
        dispatcher.forward(request, response);

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // programme id
        String id = request.getParameter("id");

        // Retrieve programme information based on the ID
        // Obtain the selected course IDs from the request parameters
        String[] courseIds = request.getParameterValues("courseName");

        // Validate the selected course IDs
        if (courseIds == null) {
            // Handle case where no course is selected
            PrintWriter out = response.getWriter();
            out.println("<script>alert('Please select at least 1 course!');</script>");
            out.println("<script>window.location.href = '" + request.getContextPath() + "/amendProgrammeServlet?id=" + id + "';</script>");
            out.close();
            return;
        }

        try {
            // Create a new list to store the updated courses
            ListInterface<ProgrammeCourse> updatedCourses = new LinkedList<>();

            // Update or add courses
            for (String courseId : courseIds) {
                // Retrieve the course object corresponding to the course ID
                ProgrammeCourse pc = ProgrammeCourseDao.getCourseById(courseId,id);

                if (pc == null) {
                    // If no course exists for the student and course ID, mark it for addition
                    ListInterface<String> coursesIds = new LinkedList<>();
                    coursesIds.add(courseId);
                    ProgrammeCourse programmeCourse = new ProgrammeCourse(id, coursesIds);
                    updatedCourses.add(programmeCourse);
                }
                ProgrammeCourseDao.replaceCourseList(updatedCourses);
            }

            // Redirect back to the page with a success message or any other necessary action
            PrintWriter out = response.getWriter();
            out.println("<script>alert('Record saved successfully!');</script>");
            out.println("<script>window.location.href = '" + request.getContextPath() + "/programmeServlet?id=" + id + "';</script>");
            out.close();

        } catch (Exception e) {
            // If any exception occurs during processing, show error message
            PrintWriter out = response.getWriter();
            out.println("<script>alert('Failed to add program course: " + e.getMessage() + "');</script>");
            out.println("<script>window.location.href = '" + request.getContextPath() + "/amendProgrammeServlet?id=" + id + "';</script>");
            out.close();
        }
    }
}
