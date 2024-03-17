<%-- 
    Document   : insertStudent
    Created on : 10 Mar 2024, 2:34:31 pm
    Author     : Zy
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@include file = "/shared/header.jsp"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <section class="home-section">
            <div class="home-content">
                <i class='bx bx-menu'></i>
                <header>
                    <h1>Add New Student</h1>
                </header>
                <main>
                    <form method="post" action="studentServlet">
                        <div>
                            <table width="100%">
                                <tr>
                                    <td><label for="id">Student ID:</label> </td>
                                    <td><input name="id" ID="id" type="text" maxlength="5"/></td>
                                </tr>
                                <tr>
                                    <td><label for="name">Full Name as in IC / Passport:</label> </td>
                                    <td><input name="name" ID="name" type="text" maxlength="30"/></td>
                                </tr>
                                <tr>
                                    <td><label for="ic">Identity Card:</label> </td>
                                    <td><input name="ic" ID="ic" type="text" maxlength="12"/></td>
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
                                    <td><input name="email" ID="email" type="email"/></td>
                                </tr>

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
                                        <input type="submit" value="Insert"/>
                                    </td>
                                </tr>
                            </table>
                        </div>
                    </form>
                </main>
                <%@include file = "/shared/footer.jsp"%>
    </body>
</html>
