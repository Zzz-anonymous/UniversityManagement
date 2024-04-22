/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Servlets;

import Dao.CourseDao;
import Dao.ProgrammeCourseDao;
import Dao.TutorDao;
import static Dao.TutorDao.initializeTutors;
import Entity.Course;
import Entity.Tutor;
import Servlets.courseServlet.programmeCourseServices;
import adt.LinkedList;
import adt.ListInterface;

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
@WebServlet("/tutorServlet")
public class tutorServlet extends HttpServlet {

    private final static ListInterface<Tutor> tList = TutorDao.initializeTutors();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // tutor id
        String id = request.getParameter("id");

        Tutor tutor = tutorServices.getTutorById(id);

        int index = tutorServices.getIndex(id, tList);
        if (index != -1) {
            // If not found in mergedList, check in inactiveList
            tutor = tList.getData(index);
        }

        // Create a new ListInterface and add the programme to it
        ListInterface<Tutor> tutorList = new LinkedList<>();
        tutorList.add(tutor);

        // Filter courses based on tutor ID and their presence in pcList
        ListInterface<Course> filteredCourses = programmeCourseServices.filterCoursesBytId(id);
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

        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/views/courseReport2UI.jsp");
        dispatcher.forward(request, response);
    }

    // -----------------------------------------------------------------------------
    //                         TUTOR SERVICES
    // -----------------------------------------------------------------------------
    public class tutorServices {

        public static Tutor findTutorByName(String name) {
            ListInterface<Tutor> tutor = initializeTutors();

            for (int i = 1; i <= tutor.getTotalNumberOfData(); i++) {
                Tutor t = tutor.getData(i);
                if (t.getName().equals(name)) {
                    return t;
                }
            }

            // If no matching programme is found, return null or throw an exception
            return null;
        }

        // check the index number based on id
        public static int getIndex(String id, ListInterface<Tutor> list) {
            // Trim the provided ID to remove leading and trailing whitespace
            String trimmedId = id.trim();

            // Get an iterator for the LinkedList
            Iterator<Tutor> iterator = list.getIterator();

            // Initialize index counter
            int index = 1;

            // Iterate over the list
            while (iterator.hasNext()) {
                Tutor t = iterator.next();
                if (t != null) {
                    if (t.getId().equals(trimmedId)) {
                        // If student ID matches, return the index
                        return index;
                    }
                }
                // Increment index counter
                index++;
            }

            // If student ID is not found, return -1
            return -1;
        }

        // get tutor object by id
        public static Tutor getTutorById(String id) {
            ListInterface<Tutor> tutor = initializeTutors();

            for (int i = 1; i <= tutor.getTotalNumberOfData(); i++) {
                Tutor t = tutor.getData(i);
                if (t.getName().equals(id)) {
                    return t;
                }
            }

            // If no matching programme is found, return null or throw an exception
            return null;
        }
    }
}
