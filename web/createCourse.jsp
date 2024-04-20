<%-- 
    Document   : createCourse
    Created on : 10 Mar 2024, 2:58:01 pm
    Author     : Zy
--%>
<%@page import="Entity.Faculty"%>
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
            <h1>Create Course</h1>
        </header>
        <main>
            <form method="post" action="courseServlet">
                <div>
                    <table width="100%">
                        <tr>
                            <td><label for="id">Course ID:</label></td>
                            <td><input name="id" ID="id" type="text" maxlength="5"/></td>
                        </tr>
                        <tr>
                            <td><label for="name">Course Name:</label> </td>
                            <td><input name="name" ID="name" type="text" maxlength="30"/></td>
                        </tr>
                        <tr>
                            <td><label for="details">Course Details:</label> </td>
                            <td><input name="details" ID="details" type="text" maxlength="30"/></td>
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
                        <tr>
                            <td><label for="dayOfWeek">Day of Week</label> </td>
                            <td><select name="dayOfWeek" id="dayOfWeek">
                                    <option value="Monday">Monday</option>
                                    <option value="Tuesday">Tuesday</option>
                                    <option value="Wednesday">Wednesday</option>
                                    <option value="Thursday">Thursday</option>
                                    <option value="Friday">Friday</option>
                                </select>
                            </td>
                        </tr>
                        <tr>
                            <td><label for="startTime">Start Time:</label></td>
                            <td><input type="time" id="startTime" name="startTime" min="08:00" max="17:00" value="08:00"></td>
                        </tr>

                        <tr>
                            <td><label for="duration">Duration:</label> </td>
                            <td>
                                <select name="duration" id="duration">
                                    <option value="1">1</option>
                                    <option value="2">2</option>
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
                                    <option value="<%= f.getName()%>">
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
<script>
    const startTimeInput = document.getElementById("startTime");

    startTimeInput.addEventListener("input", function() {
        const isValid = /^([01]\d|2[0-3]):([0-5]\d)$/.test(startTimeInput.value);
        startTimeInput.setCustomValidity(isValid ? "" : "Please enter a valid time with minutes set to 00 or 30.");
    });
</script>


</body>

</html>