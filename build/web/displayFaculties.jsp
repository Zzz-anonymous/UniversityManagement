<%-- 
    Document   : displayFaculties
    Created on : 28 Mar 2024, 4:59:26 pm
    Author     : Zy
--%>

<%@page import="Dao.FacultyDao"%>
<%@page import="Entity.Faculty"%>
<%@page import="adt.*"%>
<%@page import="Entity.Course"%>
<%@page import="Entity.Programme"%>
<%@page import="Dao.CourseDao"%>

<%@include file="/shared/header.jsp"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<section class="home-section">
    <div class="home-content">
        <i class='bx bx-menu'></i>
        <header>
            <h1>Faculties</h1>
        </header>
        <main>
            <% 
                ListInterface<Faculty> fList = FacultyDao.initializeFaculties();
                if (fList != null && !fList.isEmpty()) {
                    
            %>
            <table border="1" style="width:80%;" class="table">
                <thead>
                    <tr>
                        <th>Faculty ID</th>
                        <th>Faculty Name</th>
                        <th>Details</th>
                    </tr>
                </thead>
                <tbody>
                <%
                        for (int i = 1; i <= fList.getTotalNumberOfData(); i++) {
                        Faculty f = fList.getData(i);
                %>
                    <tr>
                        <td><%= f.getId() %></td>
                        <td><%= f.getName() %></td>
                        <td><a href="/UniversityManagement/facultyServlet?id=<%= f.getId()%>">Details</a></td>                         
                    </tr>
                    <% }}%>
                </tbody>
            </table>
            
            
        </main>
        <%@include file="/shared/footer.jsp"%>
    </div>
</section>

</body>
</html>
