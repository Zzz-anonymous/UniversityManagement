<%-- 
    Document   : courseReport3
    Created on : 18 Apr 2024, 9:27:17 pm
    Author     : Zy
--%>

<%@include file = "/shared/header.jsp"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<section class="home-section">
    <div class="home-content">
        <i class='bx bx-menu'></i>
        <header>
            <h1>Timetable</h1>
        </header>
        <main>
            <table border="1" class="table">
                <thead>
                    <tr>
                        <th>Time</th>
                        <!-- Loop through Monday to Friday to display the days of the week -->
                        <% for (int day = 0; day < 5; day++) { %>
                        <th>
                            <% // Get the day of the week based on the loop index
                                String[] dayOfWeek = {"Monday", "Tuesday", "Wednesday", "Thursday", "Friday"};
                                // Output the day of the week
                                out.println(dayOfWeek[day]);
                            %>
                        </th>
                        <% } %>
                    </tr>
                </thead>
                <tbody>
                    <!-- Display the time slots vertically -->
                    <% for (int hour = 8; hour <= 20; hour++) {%>
                    <tr>
                        <!-- Display the time slot for each hour -->
                        <td><%= String.format("%02d:00", hour)%></td>
                        <!-- Loop through Monday to Friday to display the time slots for each day -->
                        <% for (int day = 0; day < 5; day++) { %>
                        <td>
                            <!-- You can display additional information here if needed -->
                        </td>
                        <% } %>
                    </tr>
                    <% }%>
                </tbody>

            </table>
        </main>
        <%@include file = "/shared/footer.jsp"%>
    </div>
</section>


</body>

</html>