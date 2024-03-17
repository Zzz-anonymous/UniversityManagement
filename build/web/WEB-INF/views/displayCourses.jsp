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
                ListInterface<Course> cList = (ListInterface<Course>) request.getAttribute("cList");
                ListInterface<Course> cr = (ListInterface<Course>) request.getAttribute("searchResults");
                boolean showSearchResults = cr != null && !cr.isEmpty();



            %>
            <table border="1" style="width:80%;">
                <thead>
                    <tr>
                        <th>Course ID</th>
                        <th>Name</th>
                        <th>Course Details</th>
                        <th>Course Types</th>
                        <th>Credit Hours</th>
                        <th>Course Fees</th>
                        <th>Course Status</th>
                        <th>Edit</th>
                        <th>Delete</th>
                    </tr>
                </thead>    
                <tbody>
                    <% if (showSearchResults) {
                            for (int i = 1; i <= cr.getNumberOfEntries(); i++) {
                                Course c = cr.getEntry(i);
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
                        <td><%= c.getFees()%></td>
                        <td><%= c.getAvailability() == 1 ? "Yes" : "False"%></td>

                        <td><a href="/UniversityManagement/amendCourseServlet?id=<%= c.getId()%>">Edit</a></td>
                        <td><a href="/UniversityManagement/deleteCourseServlet?id=<%= c.getId()%>">Delete</a></td>                           
                    </tr>
                    <%
                        }
                    } else if (cList != null && !cList.isEmpty()) {
                        for (int i = 1; i <= cList.getNumberOfEntries(); i++) {
                            Course c = cList.getEntry(i);
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
                        <td><%= c.getFees()%></td>
                        <td><%= c.getAvailability() == 1 ? "Available" : "Not Available"%></td>
                        <td>
                            <%%>
                        </td>
                        
                        <td><a href="/UniversityManagement/studentAmendServlet?id=<%= c.getId()%>">Edit</a></td>
                        <td><a href="/UniversityManagement/studentDeleteServlet?id=<%= c.getId()%>">Delete</a></td>                           
                    </tr>
                    <%
                        }
                    } else {
                    %>
                    <tr>
                        <td colspan="9">No Course records found</td>
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