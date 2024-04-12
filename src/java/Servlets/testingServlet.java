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
import adt.LinkedList;
import adt.LinkedListInterface;
import adt.ListInterface;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Zy
 */
public class testingServlet extends HttpServlet {

    private static ListInterface<Programme> pList = Tools.initializeProgrammes();
    private static LinkedListInterface<Course> cList = CourseDao.getAllCourses();
    private static final LinkedListInterface<ProgrammeCourse> programmeCourses = ProgrammeCourseDao.getProgrammeCourse();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String id = request.getParameter("id");

        // Retrieve programme information
        Programme programme = ProgrammeDao.findProgrammeById(id); // Assuming this method exists in your ProgrammeDao class
        int index = ProgrammeDao.getIndex(id, pList);
        if (index != -1) {
            // If not found in mergedList, check in inactiveList
            programme = pList.getData(index);
        }

        // Create a new LinkedListInterface and add the programme to it
        LinkedListInterface<Programme> programmeList = new LinkedList<>();
        programmeList.add(programme);

        
        

        // Pass the programme, course list, and pcList to the JSP page
        request.setAttribute("programme", programmeList);
        

        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/views/amendProgramme.jsp");
        dispatcher.forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String id = request.getParameter("id");

        // Retrieve programme information based on the ID
        Programme programme = ProgrammeDao.findProgrammeById(id);

        int index = ProgrammeDao.getIndex(id, pList);
        if (index != -1) {
            // If not found in mergedList, check in inactiveList
            programme = pList.getData(index);
        }

        // Obtain the selected course IDs from the request parameters
        String[] courseIds = request.getParameterValues("courseName");

        
        // Set the courseIds attribute for the JSP page
        request.setAttribute("courseIds", courseIds);
        // Pass the updated pcList to the JSP page
        

        // Redirect back to the page with a success message or any other necessary action
        PrintWriter out = response.getWriter();
        out.println("<script>alert('Record saved successfully!');</script>");
        out.println("<script>window.location.href = '" + request.getContextPath() + "/amendProgrammeServlet?id=" + id + "';</script>");
        out.close();
    }

}
