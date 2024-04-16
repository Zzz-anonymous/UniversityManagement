<%-- 
    Document   : students
    Created on : 5 Mar 2024, 6:26:52 pm
    Author     : Zy
--%>
<%@page import = "adt.*"%>
<%@page import = "Entity.Student"%>
<%@page import = "Utility.Tools"%>
<%@include file = "/shared/header.jsp"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<section class="home-section">
    <div class="home-content">
        <i class='bx bx-menu'></i>
        <header>
            <h1>Student Lists</h1>
        </header>
        <main>
            <form style="padding-bottom:10px" action="/UniversityManagement/studentServlet" method="post">
                <input type="hidden" name="action" value="searchStudent">
                <input type="search" id="search" name="search" placeholder="Search Names" autofocus>        
            </form>

            <%
                ListInterface<Student> initializeStudent = Tools.initializeStudents();
                ListInterface<Student> mergedList = (ListInterface<Student>) request.getAttribute("resultList");
                ListInterface<Student> sr = (ListInterface<Student>) request.getAttribute("searchResults");
                boolean showSearchResults = sr != null && !sr.isEmpty();
            %>

            <div>
                <a href ="?">All</a>

                <%
                    for (int i = 1; i <= initializeStudent.getTotalNumberOfData(); i++) {
                        Student p = initializeStudent.getData(i);
                %>
                <span>|</span>
                <a href="studentServlet?action=filterStudents&programId=<%= p.getProgrammeId()%>"><%= p.getProgrammeId()%></a>
                <% } %>
            </div>
            <div style="margin-top: 10px;">
                <form action="/UniversityManagement/studentServlet" method="post">
                    <input type="hidden" name="action" value="viewInactive">
                    <button type="submit">Inactive List</button>
                </form>
            </div>

            <div style=" margin-top: 10px;">
                <%
                    if (showSearchResults) {
                        int records = sr.getTotalNumberOfData();
                %>
                <%= records%> record(s)
                <%
                } else {
                    int records = mergedList.getTotalNumberOfData();

                %>
                <%= records%> record(s)
                <%}%>
            </div>
            <table border="1" style="width:80%; margin-top: 10px" class="table">
                <thead>
                    <tr>
                        <th>Student ID</th>
                        <th>Name</th>      
                        <th>Student Status</th>
                        <th>Programme ID</th>
                        <th>Assign Courses</th>
                        <th>Fees</th>
                        <th>Details</th>
                        <th>Edit</th>
                        <th>Delete</th>
                    </tr>
                </thead>    
                <tbody>
                    <% if (showSearchResults) {
                            for (int i = 1; i <= sr.getTotalNumberOfData(); i++) {
                                Student s = sr.getData(i);
                    %>
                    <tr>
                        <td><%= s.getId()%></td>
                        <td><%= s.getName()%></td>
                        <td><%= s.getStatus() == 1 ? "Active" : "Inactive"%></td>          
                        <td><%= s.getProgrammeId()%></td>
                        <td><a href="/UniversityManagement/studentServlet?action=displayStudentCourses&id=<%= s.getId()%>&status=<%= s.getStatus()%>">Assign Courses</a></td>
                        <td><a href="/UniversityManagement/studentServlet?action=displayPaymentDetails&id=<%= s.getId()%>">Billing</a></td>
                        <td><a href="/UniversityManagement/studentServlet?action=displayStudentDetails&id=<%= s.getId()%>">Details</a></td>
                        <td><a href="/UniversityManagement/studentServlet?action=amendStudent&id=<%= s.getId()%>">Edit</a></td>
                        <td><a href="/UniversityManagement/studentServlet?action=delete&id=<%= s.getId()%>">Delete</a></td>                           
                    </tr>
                    <%
                        }
                    } else if (mergedList != null && !mergedList.isEmpty()) {
                        for (int i = 1; i <= mergedList.getTotalNumberOfData(); i++) {
                            Student s = mergedList.getData(i);
                    %>
                    <tr>
                        <td><%= s.getId()%></td>
                        <td><%= s.getName()%></td>
                        <td><%= s.getStatus() == 1 ? "Active" : "Inactive"%></td>
                        <td><%= s.getProgrammeId()%></td>
                        <td><a href="/UniversityManagement/studentServlet?action=displayStudentCourses&id=<%= s.getId()%>&status=<%= s.getStatus()%>">Assign Courses</a></td>
                        <td><a href="/UniversityManagement/studentServlet?action=displayPaymentDetails&id=<%= s.getId()%>">Billing</a></td>
                        <td><a href="/UniversityManagement/studentServlet?action=displayStudentDetails&id=<%= s.getId()%>">Details</a></td>
                        <td><a href="/UniversityManagement/studentServlet?action=amendStudent&id=<%= s.getId()%>">Edit</a></td>
                        <td><a href="/UniversityManagement/studentServlet?action=delete&id=<%= s.getId()%>">Delete</a></td>                           
                    </tr>
                    <%
                        }
                    } else {
                    %>
                    <tr>
                        <td colspan="9">No student records found</td>
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