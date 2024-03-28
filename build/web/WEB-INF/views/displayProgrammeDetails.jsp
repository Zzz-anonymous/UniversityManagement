<%-- 
    Document   : displayProgrammeDetails
    Created on : 21 Mar 2024, 10:07:54 pm
    Author     : Zy
--%>

<%@page import="Dao.ProgrammeCourseDao"%>
<%@page import="adt.LinkedListInterface"%>
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
                LinkedListInterface<Course> cList = CourseDao.getAllCourses();
                LinkedListInterface<Programme> pList = (LinkedListInterface<Programme>) request.getAttribute("programme");
                if (pList != null && !pList.isEmpty()) {
                    for (int i = 1; i <= pList.getTotalNumberOfData(); i++) {
                        Programme p = pList.getData(i);
            %>
            <table border="1" style="width:80%;" class="table">
                <tr>
                    <th>Programme ID</th> 
                    <td><%= p.getId()%></td>
                </tr>
                <tr>
                    <th>Programme Name</th>
                    <td><%= p.getName()%></td>
                </tr>
                <tr>
                    <th>Faculty Name</th>
                    <td><%= p.getFacultyName()%></td>
                </tr>
                <tr >
                    <th colspan="2">CourseName</th>
                </tr>
                <%
                    boolean courseFound = false; // Flag to track if any courses are found
                    for (int j = 1; j <= cList.getTotalNumberOfData(); j++) {
                        Course c = cList.getData(j);
                        if (ProgrammeCourseDao.getCourseNameById(c.getId(), p.getId()) != null) {
                            String cName = ProgrammeCourseDao.getCourseNameById(c.getId(), p.getId());
                %>
                <tr>
                    <td colspan="2"><%= cName%></td>
                </tr>
                <%
                            courseFound = true; // Set flag to true since a course is found
                        }
                    }
                    // If no course is found, display "No Course"
                    if (!courseFound) {
                %>
                <tr>
                    <td colspan="2">No Course</td>
                </tr>
                <%
                    }
                %>
            </table>
            <%}
            }%>

        </main>
        <%@include file="/shared/footer.jsp"%>
    </div>
</section>

</body>
</html>
