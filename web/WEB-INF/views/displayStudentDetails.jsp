<%-- 
    Document   : displayStudentDetails
    Created on : 28 Mar 2024, 10:01:28 pm
    Author     : Zy
--%>

<%@page import="Entity.StudentCourse"%>
<%@page import="Dao.ProgrammeDao"%>
<%@page import="Dao.StudentDao"%>
<%@page import="Entity.Student"%>
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
            <h1>Student Details</h1>
        </header>
        <main>
            <%
                LinkedListInterface<Student> sList = StudentDao.getAllStudents();
                LinkedListInterface<StudentCourse> scList = (LinkedListInterface<StudentCourse>) request.getAttribute("scList");
                LinkedListInterface<Course> cList = (LinkedListInterface<Course>) CourseDao.getAllAvailableCourses();
                String studentId = request.getParameter("id");
                Student student = StudentDao.getStudentById(studentId);
                if (student != null && scList != null && cList != null) {
            %>
            <form>
                <input type="hidden" name="id" value="${student.id}">
                <div>
                    <table border="1" style="width:100%;" class="table">
                        <tr>
                            <th colspan="6">Student's Information</th>
                        </tr>
                        <tr>
                            <th>Student ID</th> 
                            <td colspan="2">${student.id}</td>
                            <th>Student Name</th>
                            <td colspan="2">${student.name}</td>
                        </tr>
                        <tr>
                            <th>Email</th>
                            <td colspan='5'>${student.email}</td>
                        </tr>
                        <tr>
                            <th>Gender</th>
                            <td colspan="2">${student.gender}</td>
                            <th>Student Status</th>
                            <td colspan="2">
                                <% if (student.getStatus() == 1) { %>
                                Active
                                <% } else { %>
                                Not Active
                                <% } %>
                            </td>
                        </tr>
                        <tr>
                            <th colspan="6">Student's Course Information</th>
                        </tr>
                        <tr>
                            <th></th>
                            <th colspan="2">Course Name</th>
                            <th colspan="2">Course Status</th>
                            <th>Fees</th>
                        </tr>
                        <%
                            boolean courseTaken = false;
                            double totalFees = 0.0;
                            int courseIndex = 1; // Initialize courseIndex variable
                            if (scList != null) {
                                for (int k = 1; k <= scList.getTotalNumberOfData(); k++) {
                                    StudentCourse sc = scList.getData(k);
                                    if (sc.getStudentId().equals(studentId)) {
                                        courseTaken = true;
                                        for (int j = 1; j <= cList.getTotalNumberOfData(); j++) {
                                            Course c = cList.getData(j);
                                            if (sc.getCourseId().contains(c.getId())) {
                        %>
                        <tr>
                            <td><%= courseIndex%></td> 
                            <td colspan="2"><%= c.getName()%></td>
                            <td colspan="2"><%= sc.getCourseStatus()%></td>
                            <td>RM <%= c.getFees()%></td>
                        </tr>
                        <%
                                                courseIndex++; // Increment courseIndex after displaying each course
                                                totalFees += c.getFees();
                                            }
                                        }
                                    }
                                }
                            }
                            if (!courseTaken) {
                        %>
                        <tr>
                            <th colspan="6">No Course Assigned</th>
                        </tr>
                        <%
                            }
                        %>
                        <tr>
                            <th colspan="5">Total Fees</th>
                            <td>RM <%= totalFees%></td>
                        </tr>


                    </table>
                </div>
            </form>
            <%
                }
            %>
        </main>
        <%@include file="/shared/footer.jsp"%>
    </div>
</section>
