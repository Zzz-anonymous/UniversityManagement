<%-- 
    Document   : createCourse
    Created on : 10 Mar 2024, 2:58:01 pm
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
            <h1>Create Course</h1>
        </header>
        <main>
            <form method="post" action="createCourseServlet">
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
                            <td><label for="status">Course Status:</label> </td>
                            <td><select name="status" id="status">
                                    <option value="Main">Main</option>
                                    <option value="Elective">Elective</option>
                                    <option value="Resit">Resit</option>
                                    <option value="Repeat">Repeat</option>
                                </select>
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
                                    <option value="<%= t.getName() %>">
                                        <%= t.getName() %>
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
                            <td><label for="programmeName">programme Names:</label> </td>
                            <td>
                                <select name="programmeName" id="programmeName">
                                    <%      for (int i = 1; i <= pList.getTotalNumberOfData(); i++) {
                                            Programme p = pList.getData(i);
                                    %>
                                    <option value="<%= p.getName() %>">
                                        <%= p.getName() %>
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