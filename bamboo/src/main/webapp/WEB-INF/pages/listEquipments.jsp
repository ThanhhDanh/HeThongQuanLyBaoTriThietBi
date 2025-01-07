<%-- 
    Document   : listEquipments
    Created on : Sep 13, 2024, 9:35:40 PM
    Author     : Acer
--%>

<%@page contentType="text/html, charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<ul class="navbar-list">
    <c:forEach items="${cates}" var="c">
        <c:url value="/listequipments" var="cateUrl">
            <c:param name="cateId" value="${c.id}"/>
        </c:url>
        <li class="navbar-item">
            <a href="${cateUrl}" class="navbar-link btn-action">
                <i class="fa-solid fa-screwdriver-wrench"></i>
                <span>${c.name}</span>
            </a>
        </li>
    </c:forEach>
    <li class="navbar-item">
        <a href="<c:url value="/equipment" />" class="navbar-link btn-action">Thêm thiết bị</a>
    </li>
    <li class="navbar-nav ms-4 me-auto">
        <c:url value="/listequipments" var="action"/>
        <form action="${action}" class="d-flex align-items-center">
            <div class="me-2">
                <input type="search" class="form-control" style="outline: 0;" id="q" placeholder="Tên thiết bị..." name="q">
            </div>
            <div class="">
                <input type="submit" class="btn-action" value="Tìm thiết bị"/>
            </div>
        </form>
    </li>

    <div class="pagination justify-content-center">
        <c:set var="currentPage" value="${param.equipPage != null ? param.equipPage : 1}" />
        <li class="page-item <c:if test="${currentPage == 1}">disabled</c:if>">
            <a class="page-link me-2" href="?equipPage=${currentPage - 1}" aria-label="Previous">
                <span aria-hidden="true">&laquo;</span>
            </a>
        </li>
        <c:forEach begin="1" end="${Math.ceil(equipmentCounter / 6)}" var="i">
            <li class="page-item me-2 ${currentPage == i ? 'active' : ''}">
                <a class="page-link rounded-circle" href="?equipPage=${i}">${i}</a>
            </li>
        </c:forEach>
        <li class="page-item <c:if test="${currentEquipPage == Math.ceil(equipmentCounter / 6)}">disabled</c:if>">
            <a class="page-link" href="?equipPage=${currentPage + 1}" aria-label="Next">
                <span aria-hidden="true">&raquo;</span>
            </a>
        </li>
    </div>
</ul>
<div class="row">
    <div class="col-md-12 col-12">
        <table class="table table-hover table-header">
            <thead style="background-color: rgba(0,0,0,0.2)">
                <tr class="text-center">
                    <td>Id</td>
                    <td>Ảnh thiết bị</td>
                    <td>Tên thiết bị</td>
                    <td>Mã thiết bị</td>
                    <td>Loại</td>
                    <td>Nhà cung cấp</td>
                    <td></td>
                </tr>
            </thead>
            <c:forEach items="${equipment}" var="e">
                <tbody id="equipment${e.id}" data-name="${e.name}" data-code="${e.code}">
                    <tr class="text-center posotion-relative" style="cursor: pointer">
                        <td>${e.id}</td>
                        <td  style="width: 120px; height: 120px;">
                            <c:forEach items="${equipImg}" var="img">
                                <c:if test="${e.id == img.equipmentId.id}">
                                    <img class="w-100 h-100 rounded-circle" style="object-fit: cover" src="${img.image}" alt="${e.name}"/>
                                </c:if>
                            </c:forEach>
                        </td>
                        <td>${e.name}</td>
                        <td>${e.code}</td>
                        <td>${e.type}</td>
                        <td>${e.manufacturer}</td>
                        <td class="btn-actions">
                            <a href="<c:url value="/equipment/${e.id}"/>" class="btn mb-1 text-light" style="background-color: #777d91">
                                <i class="fa-solid fa-pen"></i>
                            </a>
                            <a href="<c:url value='/equipment/details/${e.id}'/>" class="btn mb-1 text-light" style="background-color: #777d91">
                                <i class='bx bxs-devices'></i>
                            </a>
                            <c:url value="/api/equipment/${e.id}" var="endpoint"/>
                            <button onclick="deleteEquipment('${endpoint}', ${e.id})" class="btn btn-danger">
                                <i class="fa-solid fa-trash-can"></i>
                            </button>

                        </td>
                    </tr>
                </tbody>
            </c:forEach>
        </table>
    </div>
</div>

<style>
    .navbar-list {
        border: 1px solid #ccc;
        border-radius: 20px;
        box-shadow: rgb(0 0 0 / 12%) 0px 2px 12px;
        padding: 0;
        display: flex;
        align-items: center;
        justify-content: flex-start;
        margin: 16px 30px;
    }

    .navbar-item {
        padding: 18px;
    }

    .search {
        position: absolute;
        top: 100px;
        right: 30px;
        display: inline-flex;
        padding: 6px;
    }

    .btn-actions {
        display: flex;
        flex-direction: column;
        border-bottom-color: transparent !important;
    }

    .btn-action {
        padding: 6px 10px;
        margin: 2px 0;
        border-radius: 20px;
        border: 1px solid #ccc;
        cursor: pointer;
        display: block;
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

    .pagination {
        padding-right: 18px;
    }

    .page-link {
        padding: 4px 11px;
    }

    .schedule-item {
        padding: 5px 10px;
        border-bottom: 1px solid #ccc;
        cursor: pointer;

        &.schedule-item:last-child {
            border-bottom: 0;
        }

        &:hover {
            background-color: #ccc;
        }
    }

    /* Trạng thái quá hạn */
    .schedule-item .status-overdue {
        padding: 5px;
        background-color: #f56d79; /* Màu đỏ nhạt */
        color: #fff; /* Màu chữ đỏ đậm */
    }

    /* Trạng thái sắp đến hạn */
    .schedule-item .status-upcoming {
        padding: 5px;
        background-color: #f7e4a7; /* Màu vàng nhạt */
        color: #856404; /* Màu chữ vàng đậm */
    }

    /* Trạng thái bình thường */
    .schedule-item .status-normal {
        padding: 5px;
        background-color: #d4edda; /* Màu xanh nhạt */
        color: #155724; /* Màu chữ xanh đậm */
    }
</style>