<%-- 
    Document   : addProgrammeCourse
    Created on : 21 Mar 2024, 5:17:33 pm
    Author     : Zy
--%>
<%@page import="Dao.ProgrammeDao"%>
<%@page import="adt.*"%>
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
            <h1>Programmes</h1>
        </header>
        <main>
           
            <%
                
                ListInterface<Programme> pList = ProgrammeDao.initializeProgrammes();
                if (pList != null && !pList.isEmpty()) {
                    
            %>
            <table border="1" style="width:80%;" class="table">
                <thead>
                    <tr>
                        <th>Programme ID</th>
                        <th>Programme Name</th>
                        <th>Assign Course</th>
                        <th>Details</th>
                    </tr>
                </thead>
                <tbody>
                <%
                        for (int i = 1; i <= pList.getTotalNumberOfData(); i++) {
                        Programme p = pList.getData(i);
                %>
                    <tr>
                        <td><%= p.getId() %></td>
                        <td><%= p.getName() %></td>
                        <td><a href="/UniversityManagement/courseServlet?action=courseForProgramme&id=<%= p.getId()%>">Assign Course</a></td>
                        <td><a href="/UniversityManagement/courseServlet?action=displayProgrammeCourses&id=<%= p.getId()%>">Details</a></td>                         
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
