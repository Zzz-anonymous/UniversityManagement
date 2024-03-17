<%-- 
    Document   : addCourse
    Created on : 10 Mar 2024, 3:17:20 pm
    Author     : Zy
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<script> window.onload = initializeProgrammes;</script>
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
                            <td><label for="id">Course ID:</label> </td>
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
                            <td><label for="creditHours">Credit Hours:</label> </td>
                            <td><select name="creditHours" id="creditHours">
                                    <option value="2">2</option>
                                    <option value="3">3</option>
                                    <option value="4">4</option>
                                </select>
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
                        <!--TODO Program, Instructor-->
                        <tr>
                            <td><label for="programme">Programme:</label></td>
                            <td>     
                                <select name="programme" id="programme">

                                </select>
                            </td>
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