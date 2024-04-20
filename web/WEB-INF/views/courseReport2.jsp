<%-- 
    Document   : displayTutorDetails
    Created on : 17 Apr 2024, 10:49:49 pm
    Author     : Zy
--%>

<%@page import="Entity.Tutor"%>
<%@page import="Entity.ProgrammeCourse"%>
<%@page import="Dao.ProgrammeCourseDao"%>
<%@page import="adt.ListInterface"%>
<%@page import="adt.*"%>
<%@page import="Entity.Course"%>
<%@page import="Entity.Programme"%>
<%@page import="Dao.CourseDao"%>
<%@page import="Utility.Tools"%>

<%@include file="/shared/header.jsp"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<section class="home-section">
    <div class="home-content">
        <i class='bx bx-menu'></i>
        <header>
            <h1>Course Report 2 - Tutor Details</h1>
        </header>
        <main>
            <%
                String tName = "";
                ListInterface<Course> filteredCourses = (ListInterface<Course>) request.getAttribute("filteredCourses");             
                ListInterface<Tutor> tList = (ListInterface<Tutor>) request.getAttribute("tutor");
                if (tList != null && !tList.isEmpty()) {
                    for (int i = 1; i <= tList.getTotalNumberOfData(); i++) {
                        Tutor t = tList.getData(i);
                        tName = t.getName();
            %>
            <table border="1" style="width:80%;" class="table">
                <tr>
                    <th>Tutor ID</th> 
                    <td><%= t.getId()%></td>
                </tr>
                <tr>
                    <th>Tutor Name</th>
                    <td><%= t.getName()%></td>
                </tr>
            </table>
            <%}
                }%>
            <table border="1" class="table" style="margin-top: 10px">
                <thead>
                    <tr><th colspan="21"><%= tName %> 's Timetable</th></tr>
                    <tr>
                        <th>Time</th>
                        <!-- Loop through Monday to Friday to display the days of the week -->

                        <%    String[] dayOfWeek = {"Monday", "Tuesday", "Wednesday", "Thursday", "Friday"};%>
                        <%     for (int column = 8; column <= 20; column++) {%>
                        <th>
                            <%= String.format("%02d:00", column)%>
                        </th>
                        <% } %>
                    </tr>
                </thead>
                <tbody>
    <% // Loop through each day of the week
    for (int day = 0; day < 5; day++) {%>
    <tr>
        <!-- Display the day of the week -->
        <th><%= dayOfWeek[day]%></th>
        <% // Initialize the starting column for each day
            int column = 8;
            // Loop through each hour from 8 AM to 8 PM
            while (column <= 20) {
                // Initialize variables
                boolean courseFound = false;
                String courseId = "";
                String courseName = "";
                String cStartTime = "";
                String cEndTime = "";
                int duration = 0;
                // Iterate through filtered courses
                for (int i = 1; i <= filteredCourses.getTotalNumberOfData(); i++) {
                    Course c = filteredCourses.getData(i);
                    // Check if the course matches the current day and time slot
                    if (dayOfWeek[day].equals(c.getDayOfWeek()) && c.getStartTime().equals(String.format("%02d:00", column))) {
                        // Store the course information
                        courseId = c.getId();
                        courseName = c.getName();
                        duration = c.getDuration();
                        cStartTime = c.getStartTime();
                        cEndTime = c.getEndTime();
                        courseFound = true; // Set the flag to true
                        // Increment the column by duration to move to the next available time slot
                        column += duration + 1;
                        // Exit the inner loop once a course is found
                        break;
                    }
                }
                // Output table cell with course information if a course is found, otherwise, display an empty cell
                if (courseFound) {%>
        <td style="text-align: center; background-color:#f0f0f0" colspan="<%= duration + 1%>">
            <%= courseId%> <br>
            <%= courseName%><br>
            <%= cStartTime%> - <%= cEndTime%>
        </td>
        <% } else { %>
        <td></td>
        <% 
            // Increment the column by 1 if no course is found for the current time slot
            column++;
        %>
        <% } %>
    <% } // End loop for hours %>
    </tr>
    <% } // End loop for days %>
</tbody>
            </table>
        </main>
        <%@include file="/shared/footer.jsp"%>
    </div>
</section>

</body>
</html>