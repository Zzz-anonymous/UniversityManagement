/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Servlets;

import Dao.CourseDao;
import Dao.ProgrammeDao;
import Entity.Course;
import Entity.Programme;
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
@WebServlet("/programmeServlet")
public class programmeServlet extends HttpServlet {

    private static LinkedListInterface<Course> cList = CourseDao.getAllCourses();
    private static ListInterface<Programme> pList = Tools.initializeProgrammes();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String id = request.getParameter("id");

        Programme programme = ProgrammeDao.findProgrammeById(id);
        
        int index = ProgrammeDao.getIndex(id, pList);
        if (index != -1) {
            // If not found in mergedList, check in inactiveList
            programme = pList.getData(index);
        }
        
         // Create a new LinkedListInterface and add the programme to it
        LinkedListInterface<Programme> programmeList = new LinkedList<>();
        programmeList.add(programme);
        // Pass the student object to the JSP page
        request.setAttribute("programme", programmeList);

        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/views/displayProgrammeDetails.jsp");
        dispatcher.forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

    }

}
