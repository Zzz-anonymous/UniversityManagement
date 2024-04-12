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
                        <li><a href="insertStudent.jsp">Add student</a></li>
                        <li><a href="/UniversityManagement/studentServlet">View student Details</a></li>
                    </ul>
                </li>
                <li>
                    <div class="iocn-link">
                        <a href="#">
                            <i class='bx bx-collection'></i>
                            <span class="link_name">Tutor Details</span>
                        </a>
                        <i class='bx bxs-chevron-down arrow'></i>
                    </div>
                    <ul class="sub-menu">
                        <li><a class="link_name" href="#">Tutor Details</a></li>
                        <li><a href="insertStudent.jsp">Add student</a></li>
                        <li><a href="/UniversityManagement/studentServlet">View student Details</a></li>
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
                        <li><a href="createCourse.jsp">Create Course</a></li>
                        <li><a href="/UniversityManagement/createCourseServlet">View Courses</a></li>
                        <li><a href="#">Card Design</a></li>
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
                        <li><a href="programmeCourse.jsp">View Programme</a></li>
                        <li><a href="/UniversityManagement/createCourseServlet">View Courses</a></li>
                        <li><a href="#">Card Design</a></li>
                    </ul>
                </li>
                <li>
                    <a href="#">
                        <i class='bx bx-pie-chart-alt-2'></i>
                        <span class="link_name">Analytics</span>
                    </a>
                    <ul class="sub-menu blank">
                        <li><a class="link_name" href="#">Analytics</a></li>
                    </ul>
                </li>
                <li>
                    <a href="#">
                        <i class='bx bx-line-chart'></i>
                        <span class="link_name">Chart</span>
                    </a>
                    <ul class="sub-menu blank">
                        <li><a class="link_name" href="#">Chart</a></li>
                    </ul>
                </li>
                
                
                <li>
                    <div class="profile-details">
                        <div class="profile-content">
                            <!--<img src="image/profile.jpg" alt="profileImg">-->
                        </div>
                        <div class="name-job">
                            <div class="profile_name">Zibi Zhong</div>
                            <div class="job">Web Desginer</div>
                        </div>
                        <i class='bx bx-log-out'></i>
                    </div>
                </li>
            </ul>
        </nav>

    
    


</body>

</html>
