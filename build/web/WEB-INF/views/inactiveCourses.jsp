<%-- 
    Document   : inactiveCourses
    Created on : 15 Apr 2024, 9:40:00 pm
    Author     : Zy
--%>

<%@page import="Entity.Programme"%>
<%@page import="Entity.Faculty"%>
<%@page import="Entity.Course"%>
<%@page import = "adt.*"%>
<%@page import = "Entity.Student"%>
<%@page import = "Utility.Tools"%>
<%@include file = "/shared/header.jsp"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<section class="home-section">
    <div class="home-content">
        <i class='bx bx-menu'></i>
        <header>
            <h1>Inactive Course Details</h1>
        </header>
        <main>

            <%
                ListInterface<Faculty> fList = (ListInterface<Faculty>) Tools.initializeFaculties();
                ListInterface<Course> inactiveList = (ListInterface<Course>) request.getAttribute("inactiveList");
            %>

            <div>
                <a href="?">All</a>

                <% for (int i = 1; i <= fList.getTotalNumberOfData(); i++) {
                        Faculty f = fList.getData(i);
                %>
                <span>|</span>
                <a href="courseSearchingServlet?facultyId=<%= f.getId()%>"><%= f.getId()%></a>
                <% } %>
            </div>
            <form action="createCourseServlet" method="get" style="margin-top: 10px">
                <button>Active List</button>
            </form>
            <div style=" margin-top: 10px;">
                <%
                    int records = inactiveList.getTotalNumberOfData();
                %>
                <%= records%> record(s)
            </div>
            <table border="1" style="width:80%; margin-top: 10px;" class="table">
                <thead>
                    <tr>
                        <th>Course ID</th>
                        <th>Course Name</th>
                        <th>Course Details</th>
                        <th>Course Types</th>
                        <th>Credit Hours</th>
                        <th>Course Status</th>
                        <th>Tutors</th>
                        <th>Programmes</th>
                        <th>Faculties</th>
                    </tr>
                </thead>    
                <tbody>
                    <%
                        if (inactiveList != null && !inactiveList.isEmpty()) {
                            for (int i = 1; i <= inactiveList.getTotalNumberOfData(); i++) {
                                Course c = inactiveList.getData(i);
                    %>
                    <tr>
                        <td><%= c.getId()%></td>
                        <td><%= c.getName()%></td>
                        <td><%= c.getDetails()%></td>
                        <td>
                            <%
                                ListInterface<String> courseTypes = c.getCourseTypes();
                                for (int j = 1; j <= courseTypes.getTotalNumberOfData(); j++) {
                                    String courseType = courseTypes.getData(j);
                            %>
                            <%= courseType%><br>
                            <% }%>
                        </td>
                        <td><%= c.getCreditHours()%></td>

                        <td>
                            <%= c.getTutor().getName()%>
                        </td>
                        <td>
                            <%
                                ListInterface<Programme> programmes = c.getProgramme();
                                for (int j = 1; j <= programmes.getTotalNumberOfData(); j++) {
                                    Programme p = programmes.getData(j);
                            %>
                            <%= j + ") " + p.getName()%><br>
                            <% }%>
                        </td>
                        <td>
                            <%= c.getFaculty().getName()%>
                        </td>
                        <td><%= c.getAvailability() == 1 ? "Available" : "Not Available"%></td>
                        <%
                            }
                        } else {
                        %>
                    <tr>
                        <td colspan="9">No Courses records found</td>
                    </tr>
                    <% }%>
                </tbody>
            </table>
        </main>
        <%@include file = "/shared/footer.jsp"%>
    </div>
</section>


</body>

</html>