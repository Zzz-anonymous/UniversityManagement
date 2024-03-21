/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Servlets;

import Dao.CourseDao;
import Dao.ProgrammeDao;
import Dao.TutorDao;
import Entity.Course;
import Entity.Programme;
import Entity.Tutor;
import adt.LinkedList;
import adt.LinkedListInterface;

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
@WebServlet("/amendCourseServlet")
public class amendCourseServlet extends HttpServlet {
    LinkedListInterface<Course> cList = CourseDao.getAllCourses();
    
    // display amendCourse.jsp to do modification of course
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String id = request.getParameter("id");
        // Retrieve course information based on the ID
        Course course = CourseDao.getCourseById(id);
         int creditHours = course.getCreditHours();

        // Retrieve selected course types
        LinkedListInterface<String> selectedCTypes = course.getCourseTypes();
        LinkedListInterface<Programme> selectedProgrammes = course.getProgramme();
        String tutorName = course.getTutor().getName();
        int available = course.getAvailability();

        // Pass the course object and other necessary attributes to the JSP page
        request.setAttribute("course", course);
        request.setAttribute("creditHours", creditHours);
        request.setAttribute("selectedCTypes", selectedCTypes);
        request.setAttribute("selectedProgrammes", selectedProgrammes);
        request.setAttribute("tutorName", tutorName);
        request.setAttribute("available",available);
        
        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/views/amendCourse.jsp");
        dispatcher.forward(request, response);
    }

        @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String id = request.getParameter("id");
        String name = request.getParameter("name");
        String details = request.getParameter("details");

        
        // to store selected courseType (T,P,L)
        LinkedListInterface<String> selectedCTypes = new LinkedList<>();
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

        LinkedListInterface<Programme> programmes = new LinkedList<>();
        // Obtain the selected programme names from the request parameters
        String[] programmeNames = request.getParameterValues("programmeName");
         

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
        }else {
            // No course types selected, display an alert message
            PrintWriter out = response.getWriter();
            out.println("<script>alert('Please select at least one programme!');</script>");
            out.println("<script>window.location.href = '" + request.getContextPath() + "/createCourseServlet';</script>");
            out.close();
            return; // Exit the method
        }

        String avail = request.getParameter("available");
        int available = Integer.parseInt(avail);

        // Create a new Course object with the provided data
        Course c = new Course(id, name, details, selectedCTypes, creditHours, tutorName, programmes, available);

        // Check if studentList is empty
        if (cList.isEmpty()) {
            // Handle the case where studentList is empty
            PrintWriter out = response.getWriter();
            out.println("<script>alert('No students found!');</script>");
            out.println("<script>window.location.replace('amendCourseServlet');</script>");
            out.close();
            return;
        } 

        // Check if the student with the provided ID exists (For Active Students)
        int index = CourseDao.getIndex(id, cList);
        if (index != -1) {
            // Student exists, proceed with updating
            // Remove the existing student from the mergedList
            cList.remove(index);

            // Add the new student to the mergedList
            cList.add(index, c);

            boolean status = CourseDao.updateStudent(id, c, cList);
            if (status) {
                // Student updated successfully
                PrintWriter out = response.getWriter();
                out.println("<script>alert('Record saved successfully!');</script>");
                out.println("<script>window.location.href = '" + request.getContextPath() + "/createCourseServlet';</script>");
                out.close();
            } else {
                // Failed to update student, display an error message
                PrintWriter out = response.getWriter();
                out.println("<script>alert('Failed to update student record!');</script>");
                out.println("<script>window.location.replace('amendCourseServlet');</script>");
                out.close();
            }
        } else {
            // Student with the provided ID does not exist, display an error message
            PrintWriter out = response.getWriter();
            out.println("<script>alert('Student with ID " + id + " does not exist!');</script>");
            out.println("<script>window.location.replace('studentAmendServlet');</script>");
            out.close();
        }
    }

}
