<%-- 
    Document   : courseReport2
    Created on : 17 Apr 2024, 11:13:04 pm
    Author     : Zy
--%>

<%@page import="Entity.Programme"%>
<%@page import="Entity.Course"%>
<%@page import="adt.ListInterface"%>

<%@include file = "/shared/header.jsp"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<section class="home-section">
    <div class="home-content">
        <i class='bx bx-menu'></i>
        <header>
            <h1>Course Report 2 - Available Course taken by Programmes</h1>
        </header>
<main>
    <!-- Include Chart.js library -->
    <script type="text/javascript" src="https://www.gstatic.com/charts/loader.js"></script>

    <!-- Script to dynamically generate chart -->
    <script type="text/javascript">
        // Load Google Charts library
        google.charts.load('current', {'packages': ['corechart']});

        // Draw the chart when Google Charts library is loaded
        google.charts.setOnLoadCallback(drawChart);

        function drawChart() {
            // Create data table
            var data = new google.visualization.DataTable();
            data.addColumn('string', 'Courses');
            data.addColumn('number', 'Programmes');

            <%-- Loop through each course in cList --%>
            <%
                ListInterface<Course> cList = (ListInterface<Course>) request.getAttribute("cList");
                if (cList != null) {
                    for (int i = 1; i <= cList.getTotalNumberOfData(); i++) {
                        Course c = cList.getData(i);
                        String cName = c.getName();
                        int noOfProgrammes = c.getProgramme().getTotalNumberOfData();
            %>
            // Add course and number of programs to data table
            data.addRow(['<%= cName %>', <%= noOfProgrammes %>]);
            <%-- End loop --%>
            <% } %>
            <% } %>

            // Set chart options
            var options = {
                title: 'Available courses and their number of programmes',
                legend: {position: 'none'}, // Disable legend
                chart: {
                    title: 'Available courses and their number of programmes',
                    subtitle: 'Number of programmes',
                }
            };

            // Instantiate and draw the chart
            var chart = new google.visualization.ColumnChart(document.getElementById('courseChart'));
            chart.draw(data, options);
        }
    </script>

    <div style="width: 800px; height: 400px;">
        <!-- Create div for the chart -->
        <div id="courseChart"></div>
    </div>

    <%@include file = "/shared/footer.jsp"%>
</main>


