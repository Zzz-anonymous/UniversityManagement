<%-- 
    Document   : studentReport2
    Created on : 14 Apr 2024, 11:05:55 am
    Author     : Zy
--%>

<%@page import="Entity.Student"%>
<%@page import="adt.ListInterface"%>

<%@include file = "/shared/header.jsp"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<section class="home-section">
    <div class="home-content">
        <i class='bx bx-menu'></i>
        <header>
            <h1>Student Report 2 - Student Status</h1>
        </header>
        <main>
            <!-- Include Chart.js library -->
            <script type="text/javascript" src="https://www.gstatic.com/charts/loader.js"></script>
            <%
                ListInterface<Student> inactiveList = (ListInterface<Student>) request.getAttribute("inactiveList");
                int inactiveRecords = (inactiveList != null) ? inactiveList.getTotalNumberOfData() : 0;

                ListInterface<Student> activeList = (ListInterface<Student>) request.getAttribute("activeList");
                int activeRecords = activeList.getTotalNumberOfData();
            %>
            <script type="text/javascript">
                // Load Google Charts library
                google.charts.load('current', {'packages': ['corechart']});

                // Draw the chart when Google Charts library is loaded
                google.charts.setOnLoadCallback(drawChart);

                function drawChart() {
                    // Create data table
                    var data = google.visualization.arrayToDataTable([
                        ['Student Status', 'Number of Students', {role: 'style'}],
                        ['Active Students', <%= activeRecords%>, 'blue'],
                        ['Inactive Students', <%= inactiveRecords%>, 'red']
                    ]);

                    // Set chart options
                    var options = {
                        title: 'Student Status',
                        legend: {position: 'none'}, // Disable legend
                    };

                    // Instantiate and draw the chart
                    var chart = new google.visualization.BarChart(document.getElementById('studentStatusChart'));
                    chart.draw(data, options);
                }
            </script>



            <div style="width: 800px; height: 400px;">
                <!-- Create div for the chart -->
                <div id="studentStatusChart"></div>
                <!-- Display total number of students -->
                <p>Total number of students: <%= activeRecords + inactiveRecords%></p>
            </div>


        </main>
        <%@include file = "/shared/footer.jsp"%>
    </div>
</section>


</body>

</html>