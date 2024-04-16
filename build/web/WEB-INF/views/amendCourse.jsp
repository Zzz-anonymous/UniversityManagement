<%-- 
    Document   : amendCourse
    Created on : 19 Mar 2024, 6:52:51 pm
    Author     : Zy
--%>
<%@page import="Entity.Faculty"%>
<%@page import="Entity.Course"%>
<%@page import="Dao.CourseDao"%>
<%@include file = "/shared/header.jsp"%>
<%@ page import = "Entity.Programme"%>
<%@ page import = "Entity.Tutor"%>
<%@page import = "Utility.Tools"%>
<%@page import = "adt.*"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<section class="home-section">
    <div class="home-content">
        <i class='bx bx-menu'></i>
        <header>
            <h1>Update Course Detail</h1>
        </header>
        <main>
            <form method="post" action="amendCourseServlet">
                <input type="hidden" name="id" value="${course.id}">
                <div>
                    <table width="100%">
                        <tr>
                            <td><label for="id">Course ID:</label></td>
                            <td><span>${course.id}</span></td>
                        </tr>
                        <tr>
                            <td><label for="name">Course Name:</label> </td>
                            <td><input type="text" name="name" value="${course.name}" maxlength="30"></td>
                        </tr>
                        <tr>
                            <td><label for="details">Course Details:</label> </td>
                            <td><input type="text" name="details" value="${course.details}" maxlength="30"></td>
                        </tr>
                        <tr>
                            <td>Course Types</td>
                            <td>
                                <%
                                    ListInterface<String> selectedCTypes = (ListInterface<String>) request.getAttribute("selectedCTypes");
                                    ListInterface<String> courseTypes = Tools.initializeCourseTypes(); // Load course types

                                    if (courseTypes != null && courseTypes.getTotalNumberOfData() > 0) {
                                        for (int i = 1; i <= courseTypes.getTotalNumberOfData(); i++) {
                                            String c = courseTypes.getData(i); 
                                %>
                                <label>
                                    <input type="checkbox" 
                                           name="courseTypes" 
                                           value="<%= c%>" 
                                           <% if (selectedCTypes != null && selectedCTypes.contains(c)) { %> checked <% }%>>
                                    <%= c%>
                                </label>
                                <br />
                                <%
                                        }
                                    }
                                %>
                            </td>
                        </tr>

                        <tr>
                            <td><label for="creditHours">Credit Hours:</label> </td>
                            <td><select name="creditHours" id="creditHours">
                                    <option ${creditHours == 2 ? "selected" : ""}>2</option>
                                    <option ${creditHours == 3 ? "selected" : ""}>3</option>
                                    <option ${creditHours == 4 ? "selected" : ""}>4</option>
                                </select>
                            </td>
                        </tr>


                        <%
                            ListInterface<Tutor> tList = Tools.initializeTutors();
                            if (tList != null && !tList.isEmpty()) {
                        %>           
                        <tr>
                            <td><label for="tutorName">Tutor Names:</label> </td>
                            <td>
                                <select name="tutorName" id="tutorName">
                                    <%      for (int i = 1; i <= tList.getTotalNumberOfData(); i++) {
                                            Tutor t = tList.getData(i);
                                    %>
                                    <option value="<%= t.getName()%>" <% if (t.getName().equals(request.getAttribute("tutorName"))) { %> selected <% }%>>
                                        <%= t.getName()%>
                                    </option>
                                    <%
                                        }
                                    %>
                                </select>
                            </td>
                        </tr>

                        <%
                            }
                        %>

                        <tr>
                            <td><label for="programmeName">programme Names:</label></td>
                            <td>
                                <%
                                    ListInterface<Programme> selectedProgrammes = (ListInterface<Programme>) request.getAttribute("selectedProgrammes");
                                    ListInterface<Programme> pList = Tools.initializeProgrammes();
                                    if (pList != null && !pList.isEmpty()) {
                                        for (int i = 1; i <= pList.getTotalNumberOfData(); i++) {
                                            Programme p = pList.getData(i);
                                %>                                           
                                <label>
                                    <input type="checkbox" 
                                           name="programmeName" 
                                           value="<%= p.getName()%>" 
                                           <% if (selectedProgrammes != null && selectedProgrammes.contains(p)) { %> checked <% }%>>
                                    <%= p.getName()%>
                                </label>
                                <br />
                                <% }
                                    }%>
                            </td>
                        </tr>

                        <%
                            ListInterface<Faculty> fList = Tools.initializeFaculties();
                            if (fList != null && !fList.isEmpty()) {

                        %>           
                              
                        <tr>
                            <td><label for="facultyName">Faculty Names:</label> </td>
                            <td>
                                <select name="facultyName" id="facultyName">
                                    <%      for (int i = 1; i <= fList.getTotalNumberOfData(); i++) {
                                            Faculty f = fList.getData(i);
                                    %>
                                    <option value="<%= f.getName()%>"<% if (f.getName().equals(request.getAttribute("facultyName"))) { %> selected <% }%>>
                                        <%= f.getName()%>
                                    </option>
                                    <%
                                        }
                                    %>
                                </select>
                            </td>
                        </tr>

                        <%
                            }
                        %>
                        
                        
                        <tr>
                            <td></td>
                            <td>
                                <input type="submit" value="Update"/>
                            </td>
                        </tr>
                    </table>
                </div>
            </form>
        </main>
        <%@include file = "/shared/footer.jsp"%>
    </div>
</section>


</body>

</html>