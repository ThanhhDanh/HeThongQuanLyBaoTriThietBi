<%-- 
    Document   : manageEquipmentMaintenanceSchedules
    Created on : Aug 10, 2024, 7:51:44 PM
    Author     : Acer
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<h1 class="text-center mt-1" style="color:#777d91;">QUẢN LÝ LỊCH BẢO TRÌ</h1>

<div class="row">
    <div class="col-xs-12 col-sm-12 col-md-12 col-lg-12 infos">
        <div class="info-list">
            <p class="label p-2">Tìm ngày bảo trì hiện tại</p>
            <c:url value="/maintenanceschedules" var="action"/>
            <form action="${action}" method="get">
                <label class="p-2" for="startDate">Từ ngày:</label>
                <input class="p-1 ps-2 border-0 border-bottom" type="date" name="startDate"  value="${param.startDate}" id="startDate">

                <label class="p-2 ms-4" for="endDate">Đến ngày:</label>
                <input class="p-1 ps-2 border-0 border-bottom" type="date" name="endDate" value="${param.endDate}" id="endDate">

                <label class="p-2 ms-4" for="currentLocation" class="form-label">Vị trí hiện tại:</label>
                <select name="currentLocation" id="currentLocation" class="form-select">
                    <option value="0">--Tất cả vị trí--</option>
                    <c:forEach items="${equipmentlocations}" var="l">
                        <option value="${l.currentLocation}">${l.currentLocation}</option>
                    </c:forEach>
                </select>

                <button class="ms-4 btn-action" onclick="generateTable()">Lọc</button>
            </form>

            <ul class="pagination justify-content-end">
                <c:forEach begin="1" end="${Math.ceil(maintenanceScheduleCounter / 9)}" var="i">
                    <li class="page-item me-2 <c:if test="${param.page == i}">active</c:if>">
                        <a class="page-link" href="?page=${i}">${i}</a>
                    </li>
                </c:forEach>
            </ul>
        </div>
    </div>
    <div class="col-xs-12 col-sm-12 col-md-12 col-lg-12 infos">
        <div class="info-list">
            <table class="table table-hover table-header" id="scheduleTable">
                <thead style="background-color: rgba(0,0,0,0.4)">
                    <tr class="text-center" id="dateRow">
                        <td>Mã thiết bị</td>
                        <td>Tên thiết bị</td>
                        <td>Vị trí hiện tại</td>
                        <td>Ngày bảo trì gần nhất</td>
                        <td>Ngày bảo trì hiện tại</td>
                        <td>Ngày nhắc nhở</td>
                        <td>Tần suất bảo trì</td>
                        <td>Loại bảo trì</td>
                        <td></td>
                    </tr>
                </thead>
                <tbody id="equipmentRows">
                    <c:forEach items="${maintenanceSchedule}" var="m">
                        <tr class="text-center">
                            <td>${m.equipmentId.code}</td>
                            <td>${m.equipmentId.name}</td>
                            <c:forEach items="${equipmentlocations}" var="e">
                                <c:if test="${e.equipmentId.id == m.equipmentId.id && m.equipmentId != null}">
                                    <td>${e.currentLocation}</td>
                                </c:if>
                            </c:forEach>
                            <td>${m.maintenanceDate}</td>
                            <td>${m.nextMaintenanceDate}</td>
                            <td>${m.reminderDate}</td>
                            <td>${m.maintenanceFrequency}</td>
                            <td>${m.maintenanceType}</td>
                            <td class="${m.maintenanceStatus == 'Đã quá hạn' 
                                         ? 'status-overdue' : (m.maintenanceStatus == 'Sắp đến hạn' 
                                         ? 'status-upcoming' : 'status-normal')}">${m.maintenanceStatus}</td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
        </div>
    </div>
</div>

<style>

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
        padding: 10px;
    }

    .label {
        font-size: 16px;
        font-weight: 500;
    }

    .btn-action {
        padding: 6px 10px;
        margin: 2px 0;
        border-radius: 20px;
        border: 1px solid #ccc;
        cursor: pointer;
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

    /* Trạng thái quá hạn */
    .table .status-overdue {
        background-color: #f56d79; /* Màu đỏ nhạt */
        color: #fff; /* Màu chữ đỏ đậm */
    }

    /* Trạng thái sắp đến hạn */
    .table .status-upcoming {
        background-color: #f7e4a7; /* Màu vàng nhạt */
        color: #856404; /* Màu chữ vàng đậm */
    }

    /* Trạng thái bình thường */
    .table .status-normal {
        background-color: #d4edda; /* Màu xanh nhạt */
        color: #155724; /* Màu chữ xanh đậm */
    }

    .info-list .form-select {
        text-align: center;
        display: inline-flex;
        width: 200px;
        cursor: pointer;
    }

</style>