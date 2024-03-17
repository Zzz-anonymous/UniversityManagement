<%-- 
    Document   : amendStudent
    Created on : 7 Mar 2024, 1:31:20 pm
    Author     : Zy
--%>

<%@page import = "adt.*"%>
<%@page import = "Entity.Student"%>
<%@page import = "Control.*"%>

<%@include file = "/shared/header.jsp"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<section class="home-section">
    <div class="home-content">
        <i class='bx bx-menu'></i>
        <header>
            <h1>Update Student Details</h1>
        </header>
                    <main>
                        <form action="studentAmendServlet" method="post">
                            <input type="hidden" name="id" value="${student.id}">
                            <table width="100%">
                                <tr>
                                    <td><label for="name">Full Name as in IC / Passport:</label> </td>
                                    <td><input type="text" name="name" value="${student.name}" maxlength="30"></td>
                                </tr>
                                <tr>
                                    <td><label for="ic">Identity Card:</label> </td>
                                    <td><input type="text" name="ic" value="${student.ic}" maxlength="12"></td>
                                </tr>
                                <tr>
                                    <td><label for="gender">Gender:</label></td>
                                    <td>     
                                        <select name="gender" id="gender">
                                            <option value="Female">Female</option>
                                            <option value="Male">Male</option>
                                        </select>
                                    </td>
                                <tr>
                                    <td><label for="email">Email:</label> </td>
                                    <td><input name="email" ID="email" type="email"  value="${student.email}"/></td>
                                </tr>
                                <tr>   
                                    <td>
                                    <label for="status">Status:</label></td>
                                    <td>     
                                        <select name="status" id="status">
                                            <option value="1">Active</option>
                                            <option value="0">Inactive</option>
                                        </select>
                                    </td>
                                
                                <tr>
                                    <!-- TODO -->
                                    <td><label for="programme">Choose a Course:</label></td>
                                    <td>     
                                        <select name="programme" id="programme">
                                            <option value="volvo">Volvo</option>
                                            <option value="saab">Saab</option>
                                            <option value="mercedes">Mercedes</option>
                                            <option value="audi">Audi</option>
                                        </select>
                                    </td>
                                </tr>
                                <tr>
                                    <td></td>
                                    <td>
                                        <input type="submit" value="Save Changes"/>
                                    </td>
                                </tr>
                            </table>
                        </form>
                    </main>
                    <%@include file = "/shared/footer.jsp"%>
            </div>
        </section>
    </body>

</html>
