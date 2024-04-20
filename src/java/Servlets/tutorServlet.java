/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Servlets;

import Dao.CourseDao;
import Dao.ProgrammeCourseDao;
import Dao.TutorDao;
import Entity.Course;
import Entity.ProgrammeCourse;
import Entity.Tutor;
import Utility.Tools;
import adt.LinkedList;
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
@WebServlet("/tutorServlet")
public class tutorServlet extends HttpServlet {

    private final static ListInterface<Tutor> tList = Tools.initializeTutors();
    private final static ListInterface<Course> cList = CourseDao.getAllAvailableCourses();
    private final static ListInterface<ProgrammeCourse> pcList = ProgrammeCourseDao.getProgrammeCourse();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // tutor id
        String id = request.getParameter("id");

        Tutor tutor = TutorDao.getTutorById(id);

        int index = TutorDao.getIndex(id, tList);
        if (index != -1) {
            // If not found in mergedList, check in inactiveList
            tutor = tList.getData(index);
        }

        // Create a new ListInterface and add the programme to it
        ListInterface<Tutor> tutorList = new LinkedList<>();
        tutorList.add(tutor);

        // Filter courses based on tutor ID and their presence in pcList
        ListInterface<Course> filteredCourses = ProgrammeCourseDao.filterCoursesBytId(id);
        if (filteredCourses == null) {
            PrintWriter out = response.getWriter();
            out.println("<script>alert('No available courses for the tutor!');</script>");
            out.println("<script>window.location.replace('courseServlet');</script>");
            out.close();
            return;
        }

        // Pass the available courses and tutor object to the JSP page
        request.setAttribute("tutor", tutorList);
        request.setAttribute("filteredCourses", filteredCourses);

        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/views/courseReport2.jsp");
        dispatcher.forward(request, response);
    }
}
