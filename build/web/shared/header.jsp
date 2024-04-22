<%-- 
    Document   : header
    Created on : 10 Mar 2024, 1:33:07 pm
    Author     : Zy
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>University Management System</title>
    <link rel="shortcut icon" href="/images/favicon.png">
    <link rel="stylesheet" href="https://fonts.googleapis.com/css2?family=Roboto:wght@400;700&display=swap">
    <link href='https://unpkg.com/boxicons@2.0.7/css/boxicons.min.css' rel='stylesheet'>
    <link rel="stylesheet" href="css/css.css">
</head>
<body>
    <div class="container">
        <nav class="sidebar close">
            <div class="logo-details">
                <i class='bx bxl-c-plus-plus'></i>
                <span class="logo_name">Wild Chicken University</span>
            </div>
            <ul class="nav-links">
                <li>
                    <a href="#">
                        <i class='bx bx-grid-alt'></i>
                        <span class="link_name">Dashboard</span>
                    </a>
                    <ul class="sub-menu blank">
                        <li><a class="link_name" href="#">Category</a></li>
                    </ul>
                </li>
                <li>
                    <div class="iocn-link">
                        <a href="#">
                            <i class='bx bx-collection'></i>
                            <span class="link_name">Student Registration Management</span>
                        </a>
                        <i class='bx bxs-chevron-down arrow'></i>
                    </div>
                    <ul class="sub-menu">
                        <li><a class="link_name" href="#">Student Registration Management</a></li>
                        <li><a href="insertStudentUI.jsp">Add student</a></li>
                        <li><a href="/UniversityManagement/studentServlet">View student Details</a></li>
                    </ul>
                </li>
                <li>
                    <div class="iocn-link">
                        <a href="#">
                            <i class='bx bx-collection'></i>
                            <span class="link_name">Tutor List</span>
                        </a>
                        <i class='bx bxs-chevron-down arrow'></i>
                    </div>
                    <ul class="sub-menu">
                        <li><a class="link_name" href="#">Tutor List</a></li>
                        <li><a href="displayTutorsUI.jsp">View Tutor List</a></li>
                    </ul>
                </li>
                <li>
                    <div class="iocn-link">
                        <a href="#">
                            <i class='bx bx-book-alt'></i>
                            <span class="link_name">Course Management</span>
                        </a>
                        <i class='bx bxs-chevron-down arrow'></i>
                    </div>
                    <ul class="sub-menu">
                        <li><a class="link_name" href="#">Course Management</a></li>
                        <li><a href="createCourseUI.jsp">Create Course</a></li>
                        <li><a href="/UniversityManagement/courseServlet">View Courses</a></li>
                    </ul>
                </li>
                <li>
                    <div class="iocn-link">
                        <a href="#">
                            <i class='bx bx-book-alt'></i>
                            <span class="link_name">Programme Management</span>
                        </a>
                        <i class='bx bxs-chevron-down arrow'></i>
                    </div>
                    <ul class="sub-menu">
                        <li><a class="link_name" href="#">Programme Management</a></li>
                        <li><a href="programmeCourseUI.jsp">View Programme</a></li>
                        
                    </ul>
                </li>
                
                <li>
                    <a href="#">
                        <i class='bx bx-line-chart'></i>
                        <span class="link_name">Report</span>
                    </a>
                   
                    <ul class="sub-menu">
                        <li><a class="link_name" href="#">Report</a></li>
                        <li><a href="/UniversityManagement/studentServlet?action=studentChart">Students Chart</a></li>
                        <li><a href="/UniversityManagement/courseServlet?action=showHistory">Show History</a></li>
                        <li><a href="/UniversityManagement/courseServlet?action=courseChart">Courses Chart </a></li>
                    </ul>

                </li>
                
                
                <li>
                    <div class="profile-details">
                        <div class="profile-content">
                            <!--<img src="image/profile.jpg" alt="profileImg">-->
                        </div>
                        <div class="name-job">
                            <div class="profile_name">Leong Zhi Yen</div>
                            <div class="job">Web Desginer</div>
                        </div>
                        <i class='bx bx-log-out'></i>
                    </div>
                </li>
            </ul>
        </nav>

    
    


</body>

</html>
