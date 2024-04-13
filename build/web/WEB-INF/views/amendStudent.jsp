<%-- 
    Document   : amendStudent
    Created on : 7 Mar 2024, 1:31:20 pm
    Author     : Zy
--%>

<%@page import="Utility.Tools"%>
<%@page import="Entity.Programme"%>
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
                        <td><label for="gender">Gender:</label></td>
                        <td>     
                            <select name="gender" id="gender">
                                <option value="Female" ${gender == "Female" ? "selected" : ""}>Female</option>
                                <option value="Male" ${gender == "Male" ? "selected" : ""}>Male</option>
                            </select>
                        </td>
                    <tr>
                        <td><label for="email">Email:</label> </td>
                        <td><input name="email" ID="email" type="email"  value="${student.email}"/></td>
                    </tr>
                    
                        <%
                            ListInterface<Programme> pList = Tools.initializeProgrammes();
                            if (pList != null && !pList.isEmpty()) {

                        %>           
                    <tr>
                        <td><label for="programmeId">programme Id:</label> </td>
                        <td>
                            <select name="programmeId" id="programmeId">
                                <%      for (int i = 1; i <= pList.getTotalNumberOfData(); i++) {
                                        Programme p = pList.getData(i);
                                %>
                                <option value="<%= p.getId()%>" <% if (p.getId().equals(request.getAttribute("ProgrammeId"))) { %> selected <% }%>>
                                    <%= p.getId()%>
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
