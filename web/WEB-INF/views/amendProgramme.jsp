<%-- 
    Document   : amendProgramme
    Created on : 22 Mar 2024, 3:59:40 pm
    Author     : Zy
--%>

<%@page import="Dao.ProgrammeCourseDao"%>
<%@page import="Dao.ProgrammeDao"%>
<%@page import="Entity.ProgrammeCourse"%>
<%@page import="adt.LinkedListInterface"%>
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
            <h1>Programme Courses</h1>
        </header>
        <main>
            <form method="post" action="amendProgrammeServlet">
                <input type="hidden" name="id" value="${programme.getData(1).getId()}">
                <div>
                    <table width="100%" class="table">
                        <%
                            LinkedListInterface<Programme> pList = (LinkedListInterface<Programme>) request.getAttribute("programme");
                            if (pList != null && !pList.isEmpty()) {
                                for (int i = 1; i <= pList.getTotalNumberOfData(); i++) {
                                    Programme p = pList.getData(i);
                        %>

                        <tr>
                            <th>Programme ID</th> 
                            <td><%= p.getId()%></td>
                        </tr>
                        <tr>
                            <th>Programme Name</th>
                            <td><%= p.getName()%></td>
                        </tr>
                       
                        <tr>
                            <th><label for="courseName">Chosen Course Names:</label></th>
                            <td>
                                <%
                                    LinkedListInterface<Course> courseList = (LinkedListInterface<Course>) request.getAttribute("cList");
                                    LinkedListInterface<ProgrammeCourse> pcourse = (LinkedListInterface<ProgrammeCourse>) request.getAttribute("pcList");

                                    if (courseList != null && !courseList.isEmpty()) {
                                        for (int j = 1; j <= courseList.getTotalNumberOfData(); j++) {
                                            Course c = courseList.getData(j);
                                            Course chosenCourse = ProgrammeCourseDao.getCourseById(courseList, c.getId());
                                              boolean courseFound = false;
                                            if (chosenCourse != null) {
                                                String cName = chosenCourse.getName();
                                %>
                                <input id="courseName<%= j%>"
                                       type="checkbox" 
                                       name="courseName"                       
                                       value="<%= chosenCourse.getId()%>" 
                                       checked>
                                <%= cName%>
                                <br />
                                <%
                                        }
                                    }
                                } else {
                                %>
                                <span>No Chosen Course</span>
                                <% } %>
                            </td>
                        </tr>

                        <tr>
    <th><label for="unchosenCourses">Unchosen Courses</label></th>
    <td>
        <%
            LinkedListInterface<ProgrammeCourse> pcList = (LinkedListInterface<ProgrammeCourse>) request.getAttribute("pcList");

            // Check if pcList is not null and not empty
            if (pcList != null && !pcList.isEmpty()) {
                boolean found = false;

                // Iterate over the pcList to display unchosen courses
                for (int k = 1; k <= pcList.getTotalNumberOfData(); k++) {
                    ProgrammeCourse pc = pcList.getData(k);

                    // Check if the course is unchosen
                    if (!ProgrammeCourseDao.isCourseChosen(pcList, pc.getCourseId())) {
                        Course unchosenCourse = ProgrammeCourseDao.getCourseById(pc.getCourseId(), pc.getProgrammeId());

                        // Display the unchosen course
                        if (unchosenCourse != null) {
        %>
        <input id="courseName<%= k %>"
               type="checkbox"
               name="courseName"
               value="<%= pc.getCourseId() %>">
        <%= unchosenCourse.getName() %>
        <br />
        <%
                            found = true;
                        }
                    }
                }

                // If no unchosen course is found, display "No Course"
                if (!found) {
        %>
        <span>No Unchosen Course</span>
        <% 
                }
            } else {
        %>
        <span>No Unchosen Course</span>
        <% } %>
    </td>
</tr>



                        <%}
                            }
                        %> 
                        <tr>
                            <td></td>
                            <td>
                                <input type="submit" value="Update"/>
                            </td>
                        </tr>
                    </table>
                </div>
            </form>

        </main>
        <%@include file="/shared/footer.jsp"%>
    </div>
</section>

</body>
</html>
