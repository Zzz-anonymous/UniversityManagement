<%-- 
    Document   : addCourse
    Created on : 10 Mar 2024, 3:17:20 pm
    Author     : Zy
--%>
<%@page import="Entity.Student"%>
<%@page import="Dao.ProgrammeCourseDao"%>
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
            <!--Credit hours cannot > 23-->
            <h1>Student Course Registration</h1>
        </header>
        <main>
            <form method="post" action="addToCourseServlet">
                <input type="hidden" name="id" value="${student.id}">
                <div>
                    <table width="100%" class="table">
                        <tr>
                            <th><label for="id">Student ID:</label></th>
                            <td colspan="2">
                                <span>${student.id}</span>
                            </td>

                        </tr>
                        <tr>
                            <th><label for="name">Student Name:</label></th>
                            <td colspan="2">
                                <span>${student.name}</span>
                            </td>

                        </tr>
                        <tr>
                            <th><label for="courseId">Course Name:</label><br> </th>
                            <th><label for="status">Course Status:</label> </th>
                            <th><label for="status">Credit Hours:</label> </th>
                        </tr>

                        <%
                            LinkedListInterface<Course> cList = (LinkedListInterface<Course>) CourseDao.getAllAvailableCourses();
                            Student student = (Student) request.getAttribute("student");
                            String programmeId = student.getProgrammeId();
                            LinkedListInterface<String> existingCourses = (LinkedListInterface<String>) request.getAttribute("existingCourses");
                            LinkedListInterface<String> courseStatuses = (LinkedListInterface<String>) request.getAttribute("courseStatuses"); // Retrieve courseStatuses from request attribute

                            // Check if there are available courses
                            if (cList != null && !cList.isEmpty()) {
                                // Iterate over the list of courses
                                for (int j = 1; j <= cList.getTotalNumberOfData(); j++) {
                                    Course c = cList.getData(j);
                                    String courseId = c.getId();
                                    int creditHours = c.getCreditHours();
                                    String cName = ProgrammeCourseDao.getCourseNameById(courseId, programmeId);
                                    if (cName != null) {
                                        // Check if the current course is already selected by the student
                                        boolean isCourseSelected = existingCourses != null && existingCourses.contains(courseId);
                                        // Retrieve status for the current courseId
                                        String status = courseStatuses != null ? courseStatuses.getData(j) : "Main"; // Use "Main" as default if status is not available
                        %>
                        <tr data-checkable>
                            <td>
                                <input name="courseId" type="checkbox" value="<%= courseId%>" <%= isCourseSelected ? "checked" : ""%> />
                                <%= cName + "\n"%>
                            </td>
                            <td>
                                <!-- Display course status options -->
                                <select name="<%= courseId%>_status" id="<%= courseId%>_status">
                                    <option value="Main" <%= status != null && status.equals("Main") ? "selected" : ""%>>Main</option>
                                    <option value="Elective" <%= status != null && status.equals("Elective") ? "selected" : ""%>>Elective</option>
                                    <option value="Resit" <%= status != null && status.equals("Resit") ? "selected" : ""%>>Resit</option>
                                    <option value="Repeat" <%= status != null && status.equals("Repeat") ? "selected" : ""%>>Repeat</option>
                                </select>
                            </td>
                            <td>
                                <%= creditHours%>
                            </td>
                        </tr>
                        <%
                        } else {
                        %>
                        <tr>
                            <td colspan="3">No Available courses</td>
                        </tr>
                        <%
                                }
                            }
                        } else {
                        %>
                        <tr>
                            <td colspan="3">No Available courses</td>
                        </tr>
                        <%
                            }
                        %>

                        <tr>
                            <td></td>
                            <td>
                                <input type="submit" value="Insert"/>
                            </td>
                            <td></td>
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