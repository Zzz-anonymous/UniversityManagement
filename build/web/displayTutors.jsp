<%-- 
    Document   : displayTutors
    Created on : 17 Apr 2024, 10:00:27 pm
    Author     : Zy
--%>

<%@page import="Dao.TutorDao"%>
<%@page import="Entity.Tutor"%>
<%@page import="adt.*"%>
<%@page import="Dao.CourseDao"%>

<%@include file="/shared/header.jsp"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<section class="home-section">
    <div class="home-content">
        <i class='bx bx-menu'></i>
        <header>
            <h1>Tutors Lists</h1>
        </header>
        <main>
           
            <%
                ListInterface<Tutor> tList = TutorDao.initializeTutors();
                if (tList != null && !tList.isEmpty()) {
                    
            %>
            <table border="1" style="width:80%;" class="table">
                <thead>
                    <tr>
                        <th>Tutor ID</th>
                        <th>Tutor Name</th>
                        <th>Details</th>
                    </tr>
                </thead>
                <tbody>
                <%
                        for (int i = 1; i <= tList.getTotalNumberOfData(); i++) {
                        Tutor t = tList.getData(i);
                %>
                    <tr>
                        <td><%= t.getId() %></td>
                        <td><%= t.getName() %></td>
                        <td><a href="/UniversityManagement/tutorServlet?id=<%= t.getId()%>">Details</a></td>                         
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