<%-- 
    Document   : courseReport1
    Created on : 16 Apr 2024, 11:18:49 pm
    Author     : Zy
--%>

<%@page import="Dao.HistoryDao"%>
<%@page import = "adt.*"%>
<%@page import = "Entity.Student"%>
<%@page import = "Utility.Tools"%>
<%@include file = "/shared/header.jsp"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<section class="home-section">
    <div class="home-content">
        <i class='bx bx-menu'></i>
        <header>
            <h1>Course Report 1 - History</h1>
        </header>
        <main>
            <table border="1" style="width:80%; margin-top: 10px" class="table">
                <thead>
                    <tr>
                        <th>Timeline & Action</th>
                    </tr>
                </thead>    
                <tbody>
                    <%
                        ListInterface<String> history = (ListInterface<String>) HistoryDao.printHistory();
                        boolean showResults = history != null && !history.isEmpty();
                        if (showResults) {
                           
                            // Pop all items from the stack and store them in the temporary list
                           for(int i = 1; i<= history.getTotalNumberOfData();i++){
                            String h = history.getData(i);
                            
                    %>
                    <tr>
                        
                        <td><%= h%></td>
                    </tr>
                    <%
                        }
                    } else {
                    %>
                    <tr>
                        <td>No changes found</td>
                    </tr>
                    <%
                        }
                    %>
                </tbody>



            </table>
        </main>
        <%@include file = "/shared/footer.jsp"%>
    </div>
</section>


</body>

</html>