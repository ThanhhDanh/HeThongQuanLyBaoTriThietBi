<%-- 
    Document   : index
    Created on : Jul 22, 2024, 9:22:15 PM
    Author     : Acer
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<h1 class="text-center mt-5" style="color: #777d91">DANH SÁCH THIẾT BỊ</h1>

<div class="row">
    <div class="col-md-12 col-12">
        <table class="table table-hover table-header">
            <thead style="background-color: rgba(0,0,0,0.4)">
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
                    <tr  class="text-center posotion-relative" style="cursor: pointer">
                        <td>${e.id}</td>
                        <td style="width: 120px; height: 120px;">
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
                                Cập nhật
                            </a>
                            <a href="<c:url value='/equipment/details/${e.id}'/>" class="btn mb-1 text-light" style="background-color: #777d91">
                                Quản lý thiết bị
                            </a>
                            <c:url value="/api/equipment/${e.id}" var="endpoint"/>
                            <button onclick="deleteEquipment('${endpoint}', ${e.id})" class="btn btn-danger">Xóa thiết bị</button>

                        </td>
                    </tr>
                </tbody>
            </c:forEach>
        </table>
        <ul class="pagination justify-content-center">
            <c:forEach begin="1" end="${Math.ceil(equipmentCounter / 9)}" var="i">
                <li class="page-item me-2 <c:if test="${param.page == i}">active</c:if>">
                    <a class="page-link" href="?page=${i}">${i}</a>
                </li>
            </c:forEach>
        </ul>
    </div>
</div>

<!-- Modal -->
<input type="hidden" id="hasSchedules" value="${!empty schedules}" />
<div class="modal fade" id="maintenanceModal" tabindex="-1" aria-labelledby="maintenanceModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title text-center" id="maintenanceModalLabel">Thông báo Bảo trì</h5>
                <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
            </div>
            <div class="modal-body">
                <c:if test="${not empty schedules}">
                    <ul class="w-100 h-100 ps-0 list-unstyled">
                        <c:forEach items="${schedules}" var="schedule">
                            <li class="schedule-item">
                                ${schedule.equipmentId.code} - 
                                ${schedule.equipmentId.name} 
                                <p class="${schedule.maintenanceStatus == 'Đã quá hạn' 
                                            ? 'status-overdue' : (schedule.maintenanceStatus == 'Sắp đến hạn' 
                                            ? 'status-upcoming' : 'status-normal')}"> ${schedule.maintenanceStatus}</p>
                            </li>

                        </c:forEach>
                    </ul>
                </c:if>
                <c:if test="${empty schedules}">
                    <p>Không có bảo trì nào cần chú ý.</p>
                </c:if>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn-action" data-bs-dismiss="modal">Đóng</button>
            </div>
        </div>
    </div>
</div>

<script>
    document.addEventListener("DOMContentLoaded", function () {
        // Lấy giá trị của biến ẩn
        var hasSchedules = document.getElementById('hasSchedules').value;

        // Kiểm tra nếu có lịch bảo trì để thông báo thì mới hiển thị modal
        if (hasSchedules === 'true') {
            var maintenanceModal = new bootstrap.Modal(document.getElementById('maintenanceModal'));
            maintenanceModal.show();
        }
    });
</script>

<style>
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


