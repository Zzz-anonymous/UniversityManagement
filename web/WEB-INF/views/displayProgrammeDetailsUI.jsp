<%-- 
    Document   : displayProgrammeDetails
    Created on : 21 Mar 2024, 10:07:54 pm
    Author     : Zy
--%>

<%@page import="Servlets.courseServlet.programmeCourseServices"%>
<%@page import="Servlets.courseServlet.CourseServices"%>
<%@page import="Entity.ProgrammeCourse"%>
<%@page import="Dao.ProgrammeCourseDao"%>
<%@page import="adt.ListInterface"%>
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
            <h1>Programmes Details</h1>
        </header>
        <main>
            <%
                ListInterface<Course> cList = CourseServices.getAllAvailableCourses();
                ListInterface<ProgrammeCourse> pcList = programmeCourseServices.getProgrammeCourse();
                ListInterface<Programme> pList = (ListInterface<Programme>) request.getAttribute("programme");
                if (pList != null && !pList.isEmpty()) {
                    for (int i = 1; i <= pList.getTotalNumberOfData(); i++) {
                        Programme p = pList.getData(i);
            %>
            <table border="1" style="width:80%;" class="table">
                <tr>
                    <th>Programme ID</th> 
                    <td colspan="2"><%= p.getId()%></td>
                </tr>
                <tr>
                    <th>Programme Name</th>
                    <td colspan="2"><%= p.getName()%></td>
                </tr>

                <tr >
                    <th></th>
                    <th>Course Name</th>
                    <th>Tutor Name</th>
                </tr>
                <%
                    boolean courseTaken = false;

                    int courseIndex = 1; // Initialize courseIndex variable
                    if (pcList != null) {
                        for (int k = 1; k <= pcList.getTotalNumberOfData(); k++) {
                            ProgrammeCourse pc = pcList.getData(k);
                            if (pc.getProgrammeId().equals(p.getId())) {
                                courseTaken = true;
                                for (int j = 1; j <= cList.getTotalNumberOfData(); j++) {
                                    Course c = cList.getData(j);
                                    String cName = programmeCourseServices.getCourseNameById(c.getId(), p.getId());
                                    if (pc.getCourseId().contains(c.getId())) {
                %>
                <tr>
                    <td><%= courseIndex + ") "%></td>
                    <td><%= cName%></td>
                    <td><%= c.getTutor().getName() %></td>
                </tr>
                <%
                                        courseIndex++; // Increment courseIndex after displaying each course

                                    }
                                }
                            }
                        }
                    }
                    if (!courseTaken) {
                %>
                <tr>
                    <td colspan="3" style="text-align: center;">No Course</td>
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
