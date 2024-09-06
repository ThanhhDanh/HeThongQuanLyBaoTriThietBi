<%-- 
    Document   : base
    Created on : Jul 23, 2024, 11:44:24 PM
    Author     : Acer
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/security/tags" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <title>
            <tiles:insertAttribute name="title"/>
        </title>

        <link href='https://unpkg.com/boxicons@2.1.4/css/boxicons.min.css' rel='stylesheet'>
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.6.0/css/all.min.css"/>
        <link href="https://res.cloudinary.com/dsyzahqsj/image/upload/v1721752599/anhweb_xgacjt.png" rel="icon"/>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC" crossorigin="anonymous">
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.bundle.min.js" integrity="sha384-MrcW6ZMFYlzcLA8Nl+NtUVF0sA7MsXsP1UyJoMp4YLEuNSfAP+JcXn/tWtIaxVXM" crossorigin="anonymous"></script>
        <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.6.0/css/all.min.css" rel="stylesheet"/>
        <script src="https://cdn.jsdelivr.net/npm/sweetalert2@11/dist/sweetalert2.all.min.js"></script>
        <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/sweetalert2@11/dist/sweetalert2.min.css">
        <link rel="preconnect" href="https://fonts.googleapis.com">
        <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
        <link href="https://fonts.googleapis.com/css2?family=Roboto:wght@100;300;400;500;700&display=swap" rel="stylesheet">
        <script src="<c:url value="/js/script.js"/>"></script>

        <script src="https://www.gstatic.com/firebasejs/8.10.0/firebase-app.js"></script>
        <script src="https://www.gstatic.com/firebasejs/8.10.0/firebase-database.js"></script>
        <script src="https://www.gstatic.com/firebasejs/8.10.0/firebase-auth.js"></script>
        <script src="https://www.gstatic.com/firebasejs/8.10.0/firebase-firestore.js"></script>

        <!--momentjs-->
        <script src="https://cdnjs.cloudflare.com/ajax/libs/moment.js/2.30.1/moment.min.js"></script>
        <script src="https://cdnjs.cloudflare.com/ajax/libs/moment.js/2.30.1/moment-with-locales.min.js"></script>

        <!-- css sidebar -->
        <link rel="stylesheet" href="<c:url value="/css/sidebar.css "/>"/>

        <style>
            html ::-webkit-scrollbar {
                border-radius: 0px;
                width: 8px;
            }

            html *::-webkit-scrollbar {
                border-radius: 0;
                width: 8px;
            }

            html ::-webkit-scrollbar-thumb {
                border-radius: 4px;
                background-color: rgba(22, 24, 35, 0.06);
            }

            html *::-webkit-scrollbar-thumb {
                border-radius: 4px;
                background-color: rgba(22, 24, 35, 0.06);
            }

            html ::-webkit-scrollbar-track {
                border-radius: 0px;
                background-color: rgba(0, 0, 0, 0);
            }

            html *::-webkit-scrollbar-track {
                border-radius: 0;
                background-color: rgba(0, 0, 0, 0);
            }

            * {
                box-sizing: border-box;
            }

            a {
                text-decoration: none;
            }

            li {
                list-style: none;
            }

            html, body {
                height: 100%;
                margin: 0;
                scroll-behavior: smooth;
            }

            body {
                font-family: "Roboto", sans-serif;
                user-select: none!important;
                -moz-user-select: none!important;
                -moz-user-select: none !important;
                -webkit-touch-callout: none!important;
                -webkit-user-select: none!important;
            }

            .roboto-thin {
                font-family: "Roboto", sans-serif;
                font-weight: 100;
                font-style: normal;
            }

            .roboto-light {
                font-family: "Roboto", sans-serif;
                font-weight: 300;
                font-style: normal;
            }

            .roboto-regular {
                font-family: "Roboto", sans-serif;
                font-weight: 400;
                font-style: normal;
            }

            .roboto-medium {
                font-family: "Roboto", sans-serif;
                font-weight: 500;
                font-style: normal;
            }

            .roboto-bold {
                font-family: "Roboto", sans-serif;
                font-weight: 700;
                font-style: normal;
            }

            .form-control {
                border-radius: 20px;
            }


            .form-control:focus, .form-select:focus {
                outline: none;
                border-color: #ccc;
                box-shadow: none;
            }

            .infos {
                background: linear-gradient(to top, rgba(60, 60, 60, 0.08), rgba(220, 220, 220, 0.2));
                border: 1px solid #ccc;
                border-image: -webkit-gradient(
                    linear,
                    left bottom,
                    left top,
                    from(rgba(60, 60, 60, 0.3)),
                    to(rgba(220, 220, 220, 0.2))
                    );
                border-image: linear-gradient(to top, rgba(60, 60, 60, 0.3), rgba(220, 220, 220, 0.2));
                border-image-slice: 1;
            }

            .info-list {
                padding: 30px;
            }

            .btn-action {
                padding: 6px 10px;
                margin: 2px 0;
                border-radius: 20px;
                border: 1px solid #ccc;
                cursor: pointer;
                display: inline-block;
                text-align: center;
                text-decoration: none;
                color: #333;

                transition: 1s background ease-in-out;

                &:hover {
                    background: #0050ae;
                    color: #fff;
                    border: 1px solid transparent;
                }
            }

            .alertWarning {
                margin-top: 5px;
                font-size: 14px;
                font-weight: 500;
                color: red;
                transition: opacityTextWarning ease-in 2s;
            }

            @keyframes opacityTextWarning {
                0% {
                    opacity: 0;
                }
                ,
                50% {
                    opacity: 0.8;
                }
                ,
                100% {
                    opacity: 1;
                }
            }

            .space-top-header {
                margin-top: 80px;
            }

            .table-header {
                border: 1px solid black;
                border-radius: 20px;
                overflow: hidden
            }

            .page-item.active .page-link{
                background-color: #777d91;
                border-color: #777d91;
                opacity: 0.8;
            }

        </style>

    </head>
    <body>
        <div class="wrapper">
            <aside id="sidebar">
                <div class="d-flex">
                    <button id="toggle-btn" type="button">
                        <i class="fa-solid fa-bars"></i>
                    </button>
                    <div class="sidebar-logo">
                        <a href="<c:url value="/"/>"><s:authentication property="principal.username"/></a>
                    </div>
                </div>
                <ul class="sidebar-nav">
                    <li class="sidebar-item">
                        <a href="<c:url value="/chat"/>" class="sidebar-link">
                            <i class="fa-solid fa-chart-column"></i>
                            <span>Chat tổng</span>
                        </a>
                    </li>
                    <c:forEach items="${cates}" var="c">
                        <c:url value="/" var="cateUrl">
                            <c:param name="cateId" value="${c.id}"/>
                        </c:url>
                        <li class="sidebar-item">
                            <a href="${cateUrl}" class="sidebar-link">
                                <i class="fa-solid fa-screwdriver-wrench"></i>
                                <span>${c.name}</span>
                            </a>
                        </li>
                    </c:forEach>
                    <li class="sidebar-item">
                        <a href="<c:url value="/stats"/>" class="sidebar-link">
                            <i class="fa-solid fa-chart-column"></i>
                            <span>Thống kê</span>
                        </a>
                    </li>
                    <li class="sidebar-item">
                        <a href="<c:url value="/incidents"/>" class="sidebar-link">
                            <i class="fa-solid fa-indent"></i>
                            <span>Quản lý sự cố</span>
                        </a>
                    </li>
                    <li class="sidebar-item">
                        <a href="<c:url value="/maintenanceschedules"/>" class="sidebar-link">
                            <i class="fa-solid fa-toolbox"></i>
                            <span>Quản lý lịch bảo trì</span>
                        </a>
                    </li>
                    <li class="sidebar-item">
                        <a href="<c:url value="/repairhistoryreport"/>" class="sidebar-link">
                            <i class='bx bxs-report'></i>
                            <span>Báo cáo lịch sử sửa chữa</span>
                        </a>
                    </li>
                    <li class="sidebar-item">
                        <a href="#" class="sidebar-link
                           has-dropdown collapsed" data-bs-toggle="collapse" data-bs-target="#auth" aria-expanded="false"
                           aria-controls="auth">
                            <i class="fa-solid fa-shield"></i>
                            <span>Xác thực</span>
                        </a>
                        <ul id="auth" class="sidebar-dropdown list-unstyled collapse" data-bs-parent="#sidebar">
                            <s:authorize access="!isAuthenticated()">
                                <li class="sidebar-item">
                                    <i class="fa-solid fa-arrow-right-to-bracket"></i>
                                    <a class="nav-link text-light" href="<c:url value="/login"/>">Đăng nhập</a>
                                </li>
                            </s:authorize>
                            <li class="sidebar-item">
                                <a href="#" class="sidebar-link">
                                    Đăng ký
                                </a>
                            </li>
                        </ul>
                    </li>
                    <li class="sidebar-item">
                        <a href="#" class="sidebar-link has-dropdown collapsed" data-bs-toggle="collapse" 
                           data-bs-target="#multi" aria-expanded="false"
                           aria-controls="multi">

                            <span>Multi</span>
                        </a>
                        <ul id="multi" class="sidebar-dropdown list-unstyled collapse" data-bs-parent="#sidebar">
                            <li class="sidebar-item">
                                <a href="#" class="sidebar-link collapsed" data-bs-toggle="collapse" 
                                   data-bs-target="#multi-two" aria-expanded="false"
                                   aria-controls="multi-two">
                                    <span>Multi two</span>
                                </a>
                                <ul id="multi-two" class="sidebar-dropdown list-unstyled collapse">
                                    <li class="sidebar-item">
                                        <a class="sidebar-link" href="#">Link 1</a>
                                    </li>
                                    <li class="sidebar-item">
                                        <a class="sidebar-link" href="#">Link 2</a>
                                    </li>
                                </ul>
                            </li>
                        </ul>
                    </li>
                    <li class="sidebar-item">
                        <a href="#" class="sidebar-link">
                            <i class="fa-regular fa-bell"></i>
                            <span>Notification</span>
                        </a>
                    </li>
                    <li class="sidebar-item">
                        <a href="#" class="sidebar-link">
                            <i class="fa-solid fa-gears"></i>
                            <span>Setting</span>
                        </a>
                    </li>
                </ul>
                <s:authorize access="isAuthenticated()">
                    <div class="sidebar-footer">
                        <a href="<c:url value="/logout"/>" class="sidebar-link">
                            <i class="fa-solid fa-arrow-right-to-bracket"></i>
                            <span>Đăng xuất</span>
                        </a>
                    </div>
                </s:authorize>
            </aside>
            <div class="main">
                <div class="text-center">
                    <tiles:insertAttribute name="header"/>
                    <tiles:insertAttribute name="content"/>
                </div>
            </div>
        </div>
        <tiles:insertAttribute name="footer"/>
    </body>
    <script src="<c:url value="/js/sidebar.js"/>"></script>
</html>
