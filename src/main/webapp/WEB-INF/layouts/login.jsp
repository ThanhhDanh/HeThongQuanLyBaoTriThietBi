<%-- 
    Document   : login
    Created on : Aug 15, 2024, 11:35:38 PM
    Author     : Acer
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<%@ taglib prefix="spring"
           uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c"
           uri="http://java.sun.com/jsp/jstl/core" %>
<div class="alert alert-danger">
    <c:if test="${param.error != null}">
        <spring:message code="user.login.error1" />
    </c:if>
    <c:if test="${param.accessDenied != null}">
        <spring:message code="user.login.error2" />
    </c:if>
</div> 

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <title>
            <tiles:insertAttribute name="title"/>
        </title>
        <link href='https://unpkg.com/boxicons@2.1.4/css/boxicons.min.css' rel='stylesheet'/>
        <link rel="preconnect" href="https://fonts.googleapis.com"/>
        <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
        <link href="https://fonts.googleapis.com/css2?family=Roboto:wght@100;300;400;500;700&display=swap" rel="stylesheet"> 
    </head>

    <body>
        <div class="wrapper">
            <spring:url value="/login" var="action"/>
            <form action="${action}" method="post">
                <h1>Đăng nhập</h1>
                <div class="input-box">
                    <input type="text" placeholder="Username" required name="username"/>
                    <i class='bx bxs-user'></i>
                </div>
                <div class="input-box">
                    <input type="password" placeholder="Password" required  name="password"/>
                    <i class='bx bxs-lock-alt' ></i>
                </div>

                <div class="remember-forgot">
                    <label>
                        <input type="checkbox"/> Lưu
                    </label>
                    <a href="#">Quên mật khẩu?</a>
                </div>

                <button type="submit" class="btn">Đăng nhập</button>

                <div class="register-link">
                    <p>Chưa có tài khoản?
                        <a href="#">Đăng ký</a>
                    </p>
                </div>
            </form>
        </div>
    </body>
</html>


<style>
    * {
        margin: 0;
        padding: 0;
        box-sizing: border-box;
        font-family: "Roboto", sans-serif;
    }

    body {
        display: flex;
        justify-content: center;
        align-items: center;
        min-height: 100vh;
        background: url('https://img.freepik.com/premium-vector/technology-abstract-future-modern-gear-background_34679-887.jpg?w=900');
        background-size: cover;
        background-repeat: no-repeat;
        background-position: center;
    }

    .wrapper {
        width: 420px;
        background: transparent;
        border: 2px solid rgba(255,255,255,0.2);
        backdrop-filter: blur(20px);
        box-shadow: 0 0 10px rgba(0,0,0,0.2);
        color: #333;
        border-radius: 10px;
        padding: 30px 40px;
    }

    .wrapper h1 {
        font-size: 36px;
        text-align: center;
    }

    .wrapper .input-box {
        position: relative;
        width: 100%;
        height: 50px;
        margin: 30px 0;
    }

    .input-box input {
        width: 100%;
        height: 100%;
        background: transparent;
        border: none;
        outline: none;
        border: 2px solid rgba(255,255,255,0.2);
        border-radius: 40px;
        font-size: 16px;
        color: #fff;
        padding: 20px 45px 20px 20px;
    }

    .input-box input::placeholder {
        color: #fff;
    }

    .input-box i {
        position:  absolute;
        right: 20px;
        top: 50%;
        transform: translateY(-50%);
        font-size: 20px;
    }

    .wrapper .remember-forgot {
        display: flex;
        justify-content: space-between;
        font-size: 15px;
        margin: -15px 0 15px;
    }

    .remember-forgot label input {
        accent-color: #fff;
        margin-right: 3px;
    }

    .remember-forgot a {
        color: #fff;
        text-decoration: none;
    }

    .remember-forgot a:hover {
        text-decoration: underline;
    }

    .wrapper .btn {
        width: 100%;
        height: 45px;
        background-color: #fff;
        border: none;
        outline: none;
        border-radius: 40px;
        box-shadow: 0 0 10px rgba(0,0,0,0.1);
        cursor: pointer;
        font-size: 16px;
        color: #333;
        font-weight: 600;
    }

    .wrapper .register-link {
        font-size: 15px;
        text-align: center;
        margin: 20px 0 15px;
    }

    .register-link p a {
        color: #fff;
        text-decoration: none;
        font-weight: 600;
    }

    .register-link p a:hover {
        text-decoration: underline;
    }
</style>
