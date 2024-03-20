<%-- 
    Document   : amendCourse
    Created on : 19 Mar 2024, 6:52:51 pm
    Author     : Zy
--%>
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
                            <td><input type="text" name="name" value="${course.details}" maxlength="30"></td>
                        </tr>
                        <tr>
                            <td>Course Types</td>
                            <td>
                                <input type="checkbox" id="Tutorial" name="courseTypes" value="Tutorial" checked="checked">
                                <label for="Tutorial"> Tutorial</label>
                                <input type="checkbox" id="Lecture" name="courseTypes" value="Lecture">
                                <label for="Lecture"> Lecture</label>
                                <input type="checkbox" id="Practical" name="courseTypes" value="Practical">
                                <label for="Practical"> Practical</label>
                            </td>
                        </tr>

                        <tr>
                            <td><label for="creditHours">Credit Hours:</label> </td>
                            <td><select name="creditHours" id="creditHours">
                                    <option value="2">2</option>
                                    <option value="3">3</option>
                                    <option value="4">4</option>
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
                                    <option value="<%= t.getName()%>">
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

                        <%
                            ListInterface<Programme> pList = Tools.initializeProgrammes();
                            if (pList != null && !pList.isEmpty()) {

                        %>           
                        <tr>
                            <td><label for="programmeName">programme Names:</label></td>
                            <td>
                                <% for (int i = 1; i <= pList.getTotalNumberOfData(); i++) {
                                        Programme p = pList.getData(i);
                                %>
                                <input type="checkbox" id="programme" name="programmeName[]" value="<%= p.getName()%>">
                                <label for="programme"><%= p.getName()%></label><br>
                                <% }
                                }%>
                            </td>
                        </tr>



                        <tr>
                            <td><label for="avail">Availability:</label> </td>
                            <td><select name="avail" id="avail">
                                    <option value="1">Yes</option>
                                    <option value="0">No</option>           
                                </select>
                            </td>
                        </tr>
                        <tr>
                            <td></td>
                            <td>
                                <input type="submit" value="Insert"/>
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