<%-- 
    Document   : amendProgramme
    Created on : 22 Mar 2024, 3:59:40 pm
    Author     : Zy
--%>

<%@page import="Servlets.courseServlet.programmeCourseServices"%>
<%@page import="Servlets.courseServlet.CourseServices"%>
<%@page import="Dao.ProgrammeCourseDao"%>
<%@page import="Dao.ProgrammeDao"%>
<%@page import="Entity.ProgrammeCourse"%>
<%@page import="adt.ListInterface"%>
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
            <form style="padding-bottom:10px" action="/UniversityManagement/courseServlet" method="post">
                <input type="hidden" name="action" value="assignCourseForProgramme">
                <input type="hidden" name="id" value="${programme.getData(1).getId()}">
                <div>
                    <table width="100%" class="table">
                        <%
                            ListInterface<Programme> pList = (ListInterface<Programme>) request.getAttribute("programme");
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
                            <th><label for="unchosenCourses">Available Courses</label></th>
                            <td>
                                <%
                                    ListInterface<Course> cList = (ListInterface<Course>) CourseServices.getAllAvailableCourses();
                                    ListInterface<String> existingCourses = (ListInterface<String>) request.getAttribute("existingCourses");
                                    boolean foundCourses = false;

                                    // Check if there are available courses
                                    if (cList != null && !cList.isEmpty()) {
                                        // Iterate over the list of courses
                                        for (int j = 1; j <= cList.getTotalNumberOfData(); j++) {
                                            Course c = cList.getData(j);

                                            String cName = programmeCourseServices.getCourseNameById(c.getId(), p.getId());
                                            if (cName != null) {
                                                // Check if the current course is already selected by the student
                                                boolean isCourseSelected = existingCourses != null && existingCourses.contains(c.getId());
                                %>
                                <input id="courseName<%= j%>"
                                       type="checkbox"
                                       name="courseName"
                                       value="<%= c.getId()%>"
                                       <%= isCourseSelected ? "checked" : ""%>
                                       />
                                <%= cName%>
                                <br />
                                <%
                                                foundCourses = true; // Set the flag to true since at least one course is found
                                            }
                                        }
                                    }
                                    if (!foundCourses) {
                                %>  
                                <span>No Available Course</span>
                                <%
                                    }

                                %>
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
