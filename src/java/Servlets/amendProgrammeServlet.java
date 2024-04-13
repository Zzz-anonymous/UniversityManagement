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
    private static final ListInterface<ProgrammeCourse> programmeCourses = ProgrammeCourseDao.getProgrammeCourse();

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

        // Create a new ListInterface and add the programme to it
        ListInterface<Programme> programmeList = new LinkedList<>();
        programmeList.add(programme);

        // Create pcList to store ProgrammeCourses that are in unchosenList
        ListInterface<ProgrammeCourse> pcList = new LinkedList<>();

        // Retrieve the ids from cList and get corresponding unchosenList for each course
        for (int i = 1; i <= cList.getTotalNumberOfData(); i++) {
            Course course = cList.getData(i);
            ListInterface<ProgrammeCourse> unchosenList = ProgrammeCourseDao.getUnchosenListById(programme.getId(), course.getId());

            // Check if unchosenList is not null before using it
            if (unchosenList != null) {
                // Iterate through unchosenList and add each element to pcList
                for (int j = 1; j <= unchosenList.getTotalNumberOfData(); j++) {
                    ProgrammeCourse programmeCourse = unchosenList.getData(j);
                    pcList.add(programmeCourse);
                }
            } else {
                for (int j = 1; j <= programmeCourses.getTotalNumberOfData(); j++) {
                    ProgrammeCourse pc = programmeCourses.getData(j);
                    pcList.add(pc);
                }
                
            }
        }

        // Pass the programme, course list, and pcList to the JSP page
        request.setAttribute("programme", programmeList);
        request.setAttribute("cList", cList);
        request.setAttribute("pcList", pcList);

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

    // Check if any course IDs are selected
    if (courseIds != null && courseIds.length > 0) {
        // Iterate over the selected course IDs
        for (String courseId : courseIds) {
            // Remove the association between the course and the program
            ProgrammeCourseDao.removeCourseFromProgramme(courseId, id);

            // Retrieve the course object corresponding to the course ID
            Course course = ProgrammeCourseDao.getCourseById(courseId,id);

            // If the course object is not null, add it to the programme's course list
            if (course != null) {
                ProgrammeCourseDao.addProgrammeCourse(programme, course);
            }
        }
    } else {
        // No course types selected, display an alert message
        PrintWriter out = response.getWriter();
        out.println("<script>alert('Please select at least one course!');</script>");
        out.println("<script>window.location.href = '" + request.getContextPath() + "/amendProgrammeServlet?id=" + id + "';</script>");
        out.close();
        return; // Exit the method
    }

    // Now update the pcList with unchosen courses
    ListInterface<ProgrammeCourse> pcList = new LinkedList<>();
    for (int i = 1; i <= programmeCourses.getTotalNumberOfData(); i++) {
        ProgrammeCourse course = programmeCourses.getData(i);
        ListInterface<ProgrammeCourse> unchosenList = ProgrammeCourseDao.getUnchosenListById(course.getCourseId(), course.getProgrammeId());
        if (unchosenList != null && unchosenList.contains(course)) {
            pcList.add(course);
        }
    }

    // Set the courseIds attribute for the JSP page
    request.setAttribute("courseIds", courseIds);
    // Pass the updated pcList to the JSP page
    request.setAttribute("pcList", pcList);

    // Redirect back to the page with a success message or any other necessary action
    PrintWriter out = response.getWriter();
    out.println("<script>alert('Record saved successfully!');</script>");
    out.println("<script>window.location.href = '" + request.getContextPath() + "/amendProgrammeServlet?id=" + id + "';</script>");
    out.close();
}

}
