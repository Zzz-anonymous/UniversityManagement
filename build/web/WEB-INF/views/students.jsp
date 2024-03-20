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
            <h1>Student Details</h1>
        </header>
        <main>
            <form style="padding-bottom:10px" action="studentSearchServlet" method="post">
                <input type="search" id="search" name="search" placeholder="Search Names" autofocus>        
            </form>

            <%
                ListInterface<Student> initializeStudent = Tools.initializeStudents();
                LinkedListInterface<Student> mergedList = (LinkedListInterface<Student>) request.getAttribute("mergedList");
                LinkedListInterface<Student> sr = (LinkedListInterface<Student>) request.getAttribute("searchResults");
                boolean showSearchResults = sr != null && !sr.isEmpty();
            %>

            <div>
                <a href ="?">All</a>
                
                <%
                    for (int i = 1; i <= initializeStudent.getTotalNumberOfData(); i++) {
                        Student p = initializeStudent.getData(i);
                %>
                    <span>|</span>
                    <a href="studentSearchServlet?programId=<%= p.getProgrammeId()%>"><%= p.getProgrammeId()%></a>
                <% } %>
            </div>
            <div style="display:flex; ">
                <button data-check="ids">Check All</button>
                <button data-uncheck="ids">Uncheck All</button>
                <form action="studentDeleteServlet" method="post">
                    <button>Inactive List</button>
                </form>
                <form id="studentsForm" action="addToCourseServlet" method="post">
                    <button id="addToCourseBtn">Add to course</button>
                    <input type="hidden" id="ids" name="ids">
                </form>
            </div>
            <table border="1" style="width:80%; padding-top: 20px" class="table">
                <thead>
                    <tr>
                        <th></th>
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
                    <tr data-checkable>
                        <td>
                            <input type="checkbox" class="studentCheckbox" name="ids" value="<%= s.getId()%>" >
                        </td>
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
                    } else if (mergedList != null && !mergedList.isEmpty()) {
                        for (int i = 1; i <= mergedList.getTotalNumberOfData(); i++) {
                            Student s = mergedList.getData(i);
                    %>
                    <tr data-checkable>
                        <td>
                            <input type="checkbox" name="ids" class="studentCheckbox" value="<%= s.getId()%>">
                        </td>
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