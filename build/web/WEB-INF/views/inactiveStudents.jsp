<%-- 
    Document   : inactiveStudents
    Created on : 20 Mar 2024, 8:39:39 am
    Author     : Zy
--%>

<%@page import="Entity.Student"%>
<%@page import = "adt.*"%>
<%@page import = "Entity.Student"%>
<%@page import = "Utility.Tools"%>
<%@include file = "/shared/header.jsp"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<section class="home-section">
    <div class="home-content">
        <i class='bx bx-menu'></i>
        <header>
            <h1>Inactive Student Details</h1>
        </header>
        <main>
            <%
                ListInterface<Student> initializeStudent = Tools.initializeStudents();
                ListInterface<Student> inactiveList = (ListInterface<Student>) request.getAttribute("resultList");
            %>

            <div>
                <a href ="?">All</a>

                <%
                    for (int i = 1; i <= initializeStudent.getTotalNumberOfData(); i++) {
                        Student p = initializeStudent.getData(i);
                %>
                <span>|</span>
                <a href="studentServlet?action=filterInactiveStudents&programId=<%= p.getProgrammeId()%>"><%= p.getProgrammeId()%></a>
                <% } %>
            </div>
            <form action="studentServlet" method="get" style="margin-top: 10px">
                <button>Active List</button>
            </form>
            <div style=" margin-top: 10px;">
                <%
                    int records = inactiveList.countNode();
                %>
                <%= records%> record(s)
            </div>
            <table border="1" style="width:80%; margin-top: 10px;" class="table">
                <thead>
                    <tr>
                        <th>Student ID</th>
                        <th>Name</th>      
                        <th>Gender</th>
                        <th>Email</th>
                        <th>Student Status</th>
                        <th>Programme ID</th>
                    </tr>
                </thead>    
                <tbody>
                    <%
                        if (inactiveList != null && !inactiveList.isEmpty()) {
                            for (int i = 1; i <= inactiveList.getTotalNumberOfData(); i++) {
                                Student s = inactiveList.getData(i);
                    %>
                    <tr>
                        <td><%= s.getId()%></td>
                        <td><%= s.getName()%></td>
                        <td><%= s.getGender()%></td>
                        <td><%= s.getEmail()%></td>
                        <td><%= s.getStatus() == 1 ? "Active" : "Inactive"%></td>     
                        <td><%= s.getProgrammeId()%></td>
                    </tr>
                    <%
                        }
                    } else {
                    %>
                    <tr>
                        <td colspan="6">No student records found</td>
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