<%-- 
    Document   : amendStudent
    Created on : 7 Mar 2024, 1:31:20 pm
    Author     : Zy
--%>

<%@page import = "adt.*"%>
<%@page import = "Entity.Student"%>
<%@page import = "Control.*"%>

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
        <style>
            /* Font ----------------------------------------------------------------------- */

            body, input, select, textarea, button {
                font: 16px Roboto, sans-serif;
            }

            ul{
                list-style:none;
            }

            a{
                text-decoration:none;
            }

            /* Layout --------------------------------------------------------------------- */

            body {
                margin: 0;
                height: 100vh;
                /*display: grid;
                grid: auto auto 1fr auto / auto;*/
            }

            header, footer {
                background: #ccc;
                padding: 10px 50px;
            }

            header h1 {
                margin: 0;
                padding-left: 40px;
                background: url(/images/favicon.png) no-repeat left / 32px;
            }

            header a {
                color: inherit;
                text-decoration: none;
            }

            .container {
                display: flex;
            }

            .sidebar{
                position: fixed;
                top: 0;
                left: 0;
                height: 100%;
                width: 300px;
                background: #11101d;
                z-index: 100;
                transition: all 0.5s ease;
            }
            .sidebar.close{
                width: 78px;
            }
            .sidebar .logo-details{
                height: 60px;
                width: 100%;
                display: flex;
                align-items: center;
            }
            .sidebar .logo-details i{
                font-size: 30px;
                color: #fff;
                height: 50px;
                min-width: 78px;
                text-align: center;
                line-height: 50px;
            }
            .sidebar .logo-details .logo_name{
                font-size: 22px;
                color: #fff;
                font-weight: 600;
                transition: 0.3s ease;
                transition-delay: 0.1s;
            }
            .sidebar.close .logo-details .logo_name{
                transition-delay: 0s;
                opacity: 0;
                pointer-events: none;
            }
            .sidebar .nav-links{
                height: 100%;
                padding: 30px 0 150px 0;
                overflow: auto;
            }
            .sidebar.close .nav-links{
                overflow: visible;
            }
            .sidebar .nav-links::-webkit-scrollbar{
                display: none;
            }
            .sidebar .nav-links li{
                position: relative;
                list-style: none;
                transition: all 0.4s ease;
            }
            .sidebar .nav-links li:hover{
                background: #1d1b31;
            }
            .sidebar .nav-links li .iocn-link{
                display: flex;
                align-items: center;
                justify-content: space-between;
            }
            .sidebar.close .nav-links li .iocn-link{
                display: block
            }
            .sidebar .nav-links li i{
                height: 50px;
                min-width: 78px;
                text-align: center;
                line-height: 50px;
                color: #fff;
                font-size: 20px;
                cursor: pointer;
                transition: all 0.3s ease;
            }
            .sidebar .nav-links li.showMenu i.arrow{
                transform: rotate(-180deg);
            }
            .sidebar.close .nav-links i.arrow{
                display: none;
            }
            .sidebar .nav-links li a{
                display: flex;
                align-items: center;
                text-decoration: none;
            }
            .sidebar .nav-links li a .link_name{
                font-size: 15px;
                font-weight: 400;
                color: #fff;
                transition: all 0.4s ease;
            }
            .sidebar.close .nav-links li a .link_name{
                opacity: 0;
                pointer-events: none;
            }
            .sidebar .nav-links li .sub-menu{
                padding: 6px 6px 14px 80px;
                margin-top: -10px;
                background: #1d1b31;
                display: none;
            }
            .sidebar .nav-links li.showMenu .sub-menu{
                display: block;
            }
            .sidebar .nav-links li .sub-menu a{
                color: #fff;
                font-size: 13px;
                padding: 5px 0;
                white-space: nowrap;
                opacity: 0.6;
                transition: all 0.3s ease;
            }
            .sidebar .nav-links li .sub-menu a:hover{
                opacity: 1;
            }
            .sidebar.close .nav-links li .sub-menu{
                position: absolute;
                left: 100%;
                top: -10px;
                margin-top: 0;
                padding: 10px 20px;
                border-radius: 0 6px 6px 0;
                opacity: 0;
                display: block;
                pointer-events: none;
                transition: 0s;
            }
            .sidebar.close .nav-links li:hover .sub-menu{
                top: 0;
                opacity: 1;
                pointer-events: auto;
                transition: all 0.4s ease;
            }
            .sidebar .nav-links li .sub-menu .link_name{
                display: none;
            }
            .sidebar.close .nav-links li .sub-menu .link_name{
                font-size: 15px;
                opacity: 1;
                display: block;
            }
            .sidebar .nav-links li .sub-menu.blank{
                opacity: 1;
                pointer-events: auto;
                padding: 3px 20px 6px 16px;
                opacity: 0;
                pointer-events: none;
            }
            .sidebar .nav-links li:hover .sub-menu.blank{
                top: 50%;
                transform: translateY(-50%);
            }

            .one {
                width: 80%;
                margin-left: 10%;
                background-color: black;
                height: 400px;
            }

            .sidebar .profile-details{
                position: fixed;
                bottom: 0;
                width: 260px;
                display: flex;
                align-items: center;
                justify-content: space-between;
                background: #1d1b31;
                padding: 12px 0;
                transition: all 0.5s ease;
            }
            .sidebar.close .profile-details{
                background: none;
            }
            .sidebar.close .profile-details{
                width: 78px;
            }
            .sidebar .profile-details .profile-content{
                display: flex;
                align-items: center;
            }
            .sidebar .profile-details img{
                height: 52px;
                width: 52px;
                object-fit: cover;
                border-radius: 16px;
                margin: 0 14px 0 12px;
                background: #1d1b31;
                transition: all 0.5s ease;
            }
            .sidebar.close .profile-details img{
                padding: 10px;
            }
            .sidebar .profile-details .profile_name,
            .sidebar .profile-details .job{
                color: #fff;
                font-size: 18px;
                font-weight: 500;
                white-space: nowrap;
            }
            .sidebar.close .profile-details i,
            .sidebar.close .profile-details .profile_name,
            .sidebar.close .profile-details .job{
                display: none;
            }
            .sidebar .profile-details .job{
                font-size: 12px;
            }
            .home-section{
                position: relative;
                background: #E4E9F7;
                height: 100vh;
                left: 260px;
                width: calc(100% - 260px);
                transition: all 0.5s ease;
            }
            .sidebar.close ~ .home-section{
                left: 78px;
                width: calc(100% - 78px);
            }
            .home-section .home-content{
                height: 60px;
                display: flex;
                flex-direction: column;
                /*  align-items: center;*/
            }
            .home-section .home-content .bx-menu,
            .home-section .home-content .text{
                color: #11101d;
                font-size: 35px;
            }
            .home-section .home-content .bx-menu{
                margin: 0 25px;
                cursor: pointer;
            }
            .home-section .home-content .text{
                font-size: 26px;
                font-weight: 600;
            }
            @media (max-width: 420px) {
                .sidebar.close .nav-links li .sub-menu{
                    display: none;
                }
            }

            /* Main ---------------------------------------------------------------------- */

            main {
                padding: 20px 50px;
                flex:1;
            }

            main h1 {
                margin-top: 0;
            }

            /* Table ---------------------------------------------------------------------- */

            .table {
                border-collapse: collapse;
            }

            .table th, .table td {
                border: 1px solid #333;
                padding: 5px;
            }

            .table th {
                background: #666;
                color: #fff;
            }

            .table tr:hover td {
                background: #ccc;
            }

            .detail th {
                text-align: left;
            }

            .table th a {
                color: inherit;
                display: block;
            }

            .table th a.asc::after {
                content: ' ▴';
            }

            .table th a.des::after {
                content: ' ▾';
            }
        </style>
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
                            <li><a href="insertStudent.html">Add student</a></li>
                            <li><a href="/UniversityManagement/studentServlet">View student Details</a></li>
                            <li><a href="#">Admend student details</a></li>
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
                            <li><a href="#">Web Design</a></li>
                            <li><a href="#">Login Form</a></li>
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
                        <div class="iocn-link">
                            <a href="#">
                                <i class='bx bx-plug'></i>
                                <span class="link_name">Plugins</span>
                            </a>
                            <i class='bx bxs-chevron-down arrow'></i>
                        </div>
                        <ul class="sub-menu">
                            <li><a class="link_name" href="#">Plugins</a></li>
                            <li><a href="#">UI Face</a></li>
                            <li><a href="#">Pigments</a></li>
                            <li><a href="#">Box Icons</a></li>
                        </ul>
                    </li>
                    <li>
                        <a href="#">
                            <i class='bx bx-compass'></i>
                            <span class="link_name">Explore</span>
                        </a>
                        <ul class="sub-menu blank">
                            <li><a class="link_name" href="#">Explore</a></li>
                        </ul>
                    </li>
                    <li>
                        <a href="#">
                            <i class='bx bx-history'></i>
                            <span class="link_name">History</span>
                        </a>
                        <ul class="sub-menu blank">
                            <li><a class="link_name" href="#">History</a></li>
                        </ul>
                    </li>
                    <li>
                        <a href="#">
                            <i class='bx bx-cog'></i>
                            <span class="link_name">Setting</span>
                        </a>
                        <ul class="sub-menu blank">
                            <li><a class="link_name" href="#">Setting</a></li>
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
            <section class="home-section">
                <div class="home-content">
                    <i class='bx bx-menu'></i>
                    <header>
                        <h1>Amend Student Details</h1>
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
                    <footer>
                        Developed by <b>Zi Bi Zhong</b> &middot;
                        Copyrighted &copy; 2024
                    </footer>
                </div>
            </section>

        </div>





        <script>
            let arrow = document.querySelectorAll(".arrow");
            for (var i = 0; i < arrow.length; i++) {
                arrow[i].addEventListener("click", (e) => {
                    let arrowParent = e.target.parentElement.parentElement;//selecting main parent of arrow
                    arrowParent.classList.toggle("showMenu");
                });
            }
            let sidebar = document.querySelector(".sidebar");
            let sidebarBtn = document.querySelector(".bx-menu");
            console.log(sidebarBtn);
            sidebarBtn.addEventListener("click", () => {
                sidebar.classList.toggle("close");
            });
        </script>
    </body>

</html>
