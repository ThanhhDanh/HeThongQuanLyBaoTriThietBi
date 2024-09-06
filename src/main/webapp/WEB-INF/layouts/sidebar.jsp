<%-- 
    Document   : sidebar
    Created on : Aug 12, 2024, 5:07:57 PM
    Author     : Acer
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>

<div class="wrapper">
    <aside id="sidebar">
        <div class="d-flex">
            <button id="toggle-btn" type="button">
                <i class="fa-solid fa-bars"></i>
            </button>
            <div class="sidebar-logo">
                <a href="#">Danh</a>
            </div>
        </div>
        <ul class="sidebar-nav">
            <li class="sidebar-item">
                <a href="#" class="sidebar-link">Danh</a>
            </li>
            <li class="sidebar-item">
                <a href="#" class="sidebar-link">Danh</a>
            </li>
            <li class="sidebar-item">
                <a href="#" class="sidebar-link
                   has-dropdown collapsed" data-bs-toggle="collapse" data-bs-target="#auth" aria-expanded="false"
                   aria-controls="auth"
                   >Danh</a>
                <ul id="auth" class="sidebar-dropdown list-unstyled collapse" data-bs-parent="#sidebar">
                    <li class="sidebar-item">
                        <a href="#" class="sidebar-link">Danh</a>
                    </li>
                    <li class="sidebar-item">
                        <a href="#" class="sidebar-link">Danh</a>
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
        <div class="sidebar-footer">
            <a class="sidebar-link">
                <i class="fa-solid fa-arrow-right-from-bracket"></i>
                <span>Logout</span>
            </a>
        </div>
    </aside>
    <div class="main p-3">
        <div class="text-center">
            <h1>Body</h1>
        </div>
    </div>
</div>

