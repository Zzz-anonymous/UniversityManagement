<%-- 
    Document   : students
    Created on : 5 Mar 2024, 6:26:52 pm
    Author     : Zy
--%>
<%@page import = "adt.*"%>
<%@page import = "Entity.Student"%>
<%@page import = "Control.*"%>
<%@include file = "/shared/header.jsp"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<section class="home-section">
    <div class="home-content">
        <i class='bx bx-menu'></i>
        <header>
            <h1>Student Details</h1>
        </header>
        <main>
            <form style="padding-bottom:10px" action="studentSearchServlet" method="post">
                <input type="search" id="search" name="search" placeholder="Search Names" autofocus>        
            </form>

            <%
                LinkedListInterface<Student> sList = (LinkedListInterface<Student>) request.getAttribute("mergedList");
                LinkedListInterface<Student> sr = (LinkedListInterface<Student>) request.getAttribute("searchResults");
                boolean showSearchResults = sr != null && !sr.isEmpty();
            %>
            <table border="1" style="width:80%;">
                <thead>
                    <tr>
                        <th>Student ID</th>
                        <th>Name</th>      
                        <th>Gender</th>
                        <th>Email</th>
                        <th>Student Status</th>
                        <th>Payment Status</th>
                        <th>Programme ID</th>
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
                        <td><%= s.getGender()%></td>
                        <td><%= s.getEmail()%></td>
                        <td><%= s.getStatus() == 1 ? "Active" : "Inactive"%></td>
                        <td><%= s.getPaymentStatus()%></td>
                        <td><%= s.getProgrammeId()%></td>
                        <td><a href="/UniversityManagement/studentAmendServlet?id=<%= s.getId()%>">Edit</a></td>
                        <td><a href="/UniversityManagement/studentDeleteServlet?id=<%= s.getId()%>">Delete</a></td>                           
                    </tr>
                    <%
                        }
                    } else if (sList != null && !sList.isEmpty()) {
                        for (int i = 1; i <= sList.getTotalNumberOfData(); i++) {
                            Student s = sList.getData(i);
                    %>
                    <tr>
                        <td><%= s.getId()%></td>
                        <td><%= s.getName()%></td>
                        <td><%= s.getGender()%></td>
                        <td><%= s.getEmail()%></td>
                        <td><%= s.getStatus() == 1 ? "Active" : "Inactive"%></td>
                        <td><%= s.getPaymentStatus()%></td>
                        <td><%= s.getProgrammeId()%></td>
                        <td><a href="/UniversityManagement/studentAmendServlet?id=<%= s.getId()%>">Edit</a></td>
                        <td><a href="/UniversityManagement/studentDeleteServlet?id=<%= s.getId()%>">Delete</a></td>                           
                    </tr>
                    <%
                        }
                    } else {
                    %>
                    <tr>
                        <td colspan="10">No student records found</td>
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