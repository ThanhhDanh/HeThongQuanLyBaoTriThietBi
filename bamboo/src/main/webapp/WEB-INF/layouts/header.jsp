<%-- 
    Document   : header
    Created on : Jul 23, 2024, 11:49:10 PM
    Author     : Acer
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/security/tags" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<nav class="navbar navbar-expand-sm bg-dark navbar-dark header">
    <div class="container-fluid">
        <a class="navbar-brand" href="<c:url value="/?equipPage=1&forumPage=1"/>">Quản lý bảo trì thiết bị</a>
        <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#collapsibleNavbar">
            <span class="navbar-toggler-icon"></span>
        </button>
        <div class="collapse navbar-collapse" id="collapsibleNavbar">
            <ul class="navbar-nav">
                <li class="nav-item">
                    <a class="nav-link" href="<c:url value="/?equipPage=1&forumPage=1"/>">Trang chủ</a>
                </li>
            </ul>
        </div>
    </div>
</nav>


<style>
    .header {
        position: fixed;
        top: 0;
        width: 96%;
        z-index: 100;
    }

    .bg-navbar {
        background-color: #fff;
        border-top-left-radius: 20px;
        border-bottom-left-radius: 20px;
        margin: 4px 0;
    }

    .btn-navbar {
        background-color: #777d91;
        border-radius: 20px;
        margin: 0 10px;
        padding: 0 6px;
        display: inline-flex;
        align-items: center;
        justify-content: center;

        &:hover {
            background-color: #ccc;
        }
    }


    .navbar-items {
        display: flex;
        align-items: center;
        position: absolute;
        right: 0;
        top: 0;
        bottom: 0;
    }

    .name {
        font-size: 18px;
        text-decoration: underline;

        &:hover {
            color: #777d91;
        }
    }
</style>
