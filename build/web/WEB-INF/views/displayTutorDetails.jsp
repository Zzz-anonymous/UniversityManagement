<%-- 
    Document   : displayTutorDetails
    Created on : 17 Apr 2024, 10:49:49 pm
    Author     : Zy
--%>

<%@page import="Entity.Tutor"%>
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
            <h1>Programmes</h1>
        </header>
        <main>
            <%
                ListInterface<Course> cList = CourseDao.getAllAvailableCourses();
                ListInterface<ProgrammeCourse> pcList = ProgrammeCourseDao.getProgrammeCourse();
                ListInterface<Tutor> tList = (ListInterface<Tutor>) request.getAttribute("tutor");
                if (tList != null && !tList.isEmpty()) {
                    for (int i = 1; i <= tList.getTotalNumberOfData(); i++) {
                        Tutor t = tList.getData(i);
            %>
            <table border="1" style="width:80%;" class="table">
                <tr>
                    <th>Tutor ID</th> 
                    <td><%= t.getId()%></td>
                </tr>
                <tr>
                    <th>Tutor Name</th>
                    <td><%= t.getName()%></td>
                </tr>

                <tr >
                    <th></th>
                    <th>CourseName</th>
                </tr>
                <%
                    boolean courseTaken = false;

                    int courseIndex = 1; // Initialize courseIndex variable
                    if (pcList != null) {
                        for (int k = 1; k <= pcList.getTotalNumberOfData(); k++) {
                            ProgrammeCourse pc = pcList.getData(k);
                            if (pc.getProgrammeId().equals(t.getId())) {
                                courseTaken = true;
                                for (int j = 1; j <= cList.getTotalNumberOfData(); j++) {
                                    Course c = cList.getData(j);
                                    String cName = ProgrammeCourseDao.getCourseNameById(c.getId(), t.getId());
                                    if (pc.getCourseId().contains(c.getId())) {
                %>
                <tr>
                    <td><%= courseIndex + ") "%></td>
                    <td><%= cName%></td>
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
                    <td colspan="2" style="text-align: center;">No Course</td>
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