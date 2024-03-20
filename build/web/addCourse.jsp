<%-- 
    Document   : addCourse
    Created on : 10 Mar 2024, 3:17:20 pm
    Author     : Zy
--%>
<%@page import = "adt.*"%>
<%@page import = "Entity.Course"%>
<%@page import = "Dao.CourseDao"%>
<%@page import = "Utility.Tools"%>
<%@include file = "/shared/header.jsp"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<section class="home-section">
    <div class="home-content">
        <i class='bx bx-menu'></i>
        <header>
            <!--Throw programme Id into the Header-->
            <h1>Student Course Registration</h1>
        </header>
        <main>
            <form method="post" action="addCourseServlet">
                <div>
                    <table width="100%">


                        <tr>
                            <td><label for="name">Course Name:</label><br> </td>
                            <%
                                LinkedListInterface<Course> cList = (LinkedListInterface<Course>) CourseDao.getAllCourses();

                                if (cList != null && !cList.isEmpty()) {
                                    for (int i = 1; i <= cList.getTotalNumberOfData(); i++) {
                                        Course c = cList.getData(i);
                            %>
                            
                            <td style="display:inline-block;">
                                <input name="name" ID="name" type="checkbox"/>
                                <%= c.getName() %>
                            </td>
                        </tr>
                        <% }
                        }%>
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
                            <td><label for="status">Course Status:</label> </td>
                            <td><select name="status" id="status">
                                    <option value="Main">Main</option>
                                    <option value="Elective">Elective</option>
                                    <option value="Resit">Resit</option>
                                    <option value="Repeat">Repeat</option>
                                </select>
                            </td>
                        </tr>
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