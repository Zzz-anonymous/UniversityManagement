<%-- 
    Document   : displayCourses
    Created on : 10 Mar 2024, 3:45:28 pm
    Author     : Zy
--%>

<%@page import = "adt.*"%>
<%@page import = "Entity.Course"%>
<%@page import = "Dao.CourseDao"%>
<%@page import = "Utility.Tools"%>

<%@include file = "/shared/header.jsp"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<section class="home-section">
    <div class="home-content">
        <i class='bx bx-menu'></i>
        <header>
            <h1>Course Details</h1>
        </header>
        <main>
            <form style="padding-bottom:10px" action="studentSearchServlet" method="post">
                <input type="search" id="search" name="search" placeholder="Search Names" autofocus>        
            </form>

            <%
                LinkedListInterface<Course> cList = (LinkedListInterface<Course>) request.getAttribute("cList");
                LinkedListInterface<Course> cr = (LinkedListInterface<Course>) request.getAttribute("searchResults");
                boolean showSearchResults = cr != null && !cr.isEmpty();
            %>
            <table border="1" style="width:80%;">
                <thead>
                    <tr>
                        <th>Course ID</th>
                        <th>Course Name</th>
                        <th>Course Details</th>
                        <th>Course Types</th>
                        <th>Credit Hours</th>
                        <th>Tutors</th>
                        <th>Programmes</th>
                        <th>Course Status</th>
                        <th>Edit</th>
                        <th>Delete</th>
                    </tr>
                </thead>    
                <tbody>
                    <% if (showSearchResults) {
                            for (int i = 1; i <= cr.getTotalNumberOfData(); i++) {
                                Course c = cr.getData(i);
                    %>
                    <tr>
                        <td><%= c.getId()%></td>
                        <td><%= c.getName()%></td>
                        <td><%= c.getDetails()%></td>
                        <td>
                            <% 
                                String[] courseTypes = c.getCourseTypes();
                                for (String courseType : courseTypes) {
                            %>
                                <%= courseType %><br>
                            <% } %>
                        </td>
                        <td><%= c.getCreditHours()%></td>
                        <td>
                            <%= c.getTutor().getName()%>
                        </td>
                        <td>
                            <%= c.getProgramme().getName()%>
                        </td>
                        <td><%= c.getAvailability() == 1 ? "Available" : "Not Available"%></td>

                        <td><a href="/UniversityManagement/amendCourseServlet?id=<%= c.getId()%>">Edit</a></td>
                        <td><a href="/UniversityManagement/deleteCourseServlet?id=<%= c.getId()%>">Delete</a></td>                           
                    </tr>
                    <%
                        }
                    } else if (cList != null && !cList.isEmpty()) {
                        for (int i = 1; i <= cList.getTotalNumberOfData(); i++) {
                            Course c = cList.getData(i);
                    %>
                    <tr>
                        <td><%= c.getId()%></td>
                        <td><%= c.getName()%></td>
                        <td><%= c.getDetails()%></td>
                        <td>
                            <% 
                                String[] courseTypes = c.getCourseTypes();
                                for (String courseType : courseTypes) {
                            %>
                                <%= courseType %><br>
                            <% } %>
                        </td>
                        <td><%= c.getCreditHours()%></td>
                       
                        <td>
                            <%= c.getTutor().getName()%>
                        </td>
                        <td>
                            <%= c.getProgramme().getName() %>
                        </td>
                      
                        <td><%= c.getAvailability() == 1 ? "Available" : "Not Available"%></td>
                        <!--TODO-->
                        <!-- delete and modification havent done  -->
                        <td><a href="/UniversityManagement/studentAmendServlet?id=<%= c.getId()%>">Edit</a></td>
                        <td><a href="/UniversityManagement/studentDeleteServlet?id=<%= c.getId()%>">Delete</a></td>                           
                    </tr>
                    <%
                        }
                    } else {
                    %>
                    <tr>
                        <td colspan="10">No Course records found</td>
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