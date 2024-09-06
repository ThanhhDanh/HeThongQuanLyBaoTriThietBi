<%-- 
    Document   : EquipmentDetails
    Created on : Jul 31, 2024, 11:54:31 PM
    Author     : Acer
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>


<h1 class="text-center mt-1" style="color:#f983cc;">CHI TIẾT THIẾT BỊ</h1>

<c:if test="${not empty message}">
    <div id="message" class="alert show">
        ${message}
    </div>
</c:if>

<div class="row">
    <div class="col-xs-12 col-sm-12 col-md-6 col-lg-6 infos">
        <div class="info-list">
            <h4>Thông tin chung</h4>
            <div class="info-img">
                <img class="img" src="${equipImg.image}" alt="Equipment Image">
            </div>
            <div class=" d-flex">
                <div>
                    <span class="info-item">
                        <p class="equip-item">ID:</p> ${equipment.id}
                    </span>
                    <span class="info-item">
                        <p class="equip-item">Mã thiết bị:</p> ${equipment.code}
                    </span>
                    <span class="info-item">
                        <p class="equip-item">Loại thiết bị:</p> ${equipment.type}
                    </span>
                    <span class="info-item">
                        <p class="equip-item">Tên thiết bị:</p> ${equipment.name}
                    </span>
                </div>
                <div class="flex-grow-1 text-end">
                    <div class="btn-actions">
                        <a class="btn-action"  href="<c:url value="/equipment/repairhistory/${equipment.id}"/>">Sửa chữa thiết bị</a>
                        <c:choose>
                            <c:when test="${not empty incident.incidentDescription}">
                                <button  selected class="btn-action btn-danger" >Máy đã bị lỗi</button>
                            </c:when>
                            <c:otherwise>
                                <a  class="btn-action" href="<c:url value="/equipment/incident/${equipment.id}"/>" >Báo lỗi thiết bị</a>
                            </c:otherwise>
                        </c:choose>

                        <a class="btn-action" href="<c:url value="/equipment/maintenanceschedule/${equipment.id}"/>">Tạo phiếu bảo trì</a>
                        <a class="btn-action" href="<c:url value="/equipment/devicehistories/${equipment.id}"/>">Lịch sử thiết bị</a>
                        <button class="btn-action" onclick="window.location.href = '/equipment/${equipment.id}/delete'">Xóa thiết bị</button>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <div class="col-xs-12 col-sm-12 col-md-6 col-lg-6 infos">
        <div class="info-list">
            <h4>Thông tin kỹ thuật</h4>
            <div>
                <div class="border border-3 rounded-3 mb-2">
                    <span class="info-item">
                        <p class="equip-item">Ngày tháng sản xuất:</p> ${equipment.purchaseDate}
                    </span>
                    <span class="info-item">
                        <p class="equip-item">Nhà cung cấp:</p> ${equipment.manufacturer}
                    </span>
                    <span class="info-item">
                        <p class="equip-item">Trạng thái hoạt động:</p> ${equipment.currentStatus}

                    </span>
                    <span class="info-item">
                        <p class="equip-item">Đơn vị làm việc:</p> ${equipLocation.currentLocation}
                    </span>
                </div>
                <div class="border border-3 rounded-3 mb-2">
                    <c:choose>
                        <c:when test="${not empty mainSchedule}">
                            <c:forEach var="mSchedule" items="${mainSchedule}">
                                <span class="info-item">
                                    <p class="equip-item">Ngày bảo trì gần nhất:</p> ${mSchedule.maintenanceDate}
                                </span>
                                <span class="info-item">
                                    <p class="equip-item">Lịch bảo trì:</p> ${mSchedule.maintenanceFrequency}
                                </span>
                                <span class="info-item">
                                    <p class="equip-item">Loại bảo trì:</p> ${mSchedule.maintenanceType}
                                </span>
                                <span class="info-item">
                                    <p class="equip-item">Kỹ thuật viên:</p> ${mSchedule.performedByUserId.fullName}
                                </span>
                            </c:forEach>
                        </c:when>
                        <c:otherwise>
                            <form:form id="editFormMain" method="get" modelAttribute="maintenanceSchedules">
                                <span class="info-item">
                                    <p class="equip-item">Ngày bảo trì gần nhất:</p> ${maintenanceSchedules.maintenanceDate}
                                </span>
                                <span class="info-item">
                                    <p class="equip-item">Lịch bảo trì:</p> ${maintenanceSchedules.maintenanceFrequency}
                                </span>
                                <span class="info-item">
                                    <p class="equip-item">Loại bảo trì:</p> ${maintenanceSchedules.maintenanceType}
                                </span>
                                <span class="info-item">
                                    <p class="equip-item">Kỹ thuật viên:</p> ${maintenanceSchedules.performedByUserId.fullName}
                                </span>
                            </form:form>
                        </c:otherwise>
                    </c:choose>
                </div>
                <div class="border border-3 rounded-3 mb-2 position-relative">
                    <span class="info-item">
                        <p class="equip-item">Thời gian hư hỏng:</p> ${incident.incidentTime}
                    </span>
                    <form:form id="incidentFormMain"  method="get" 
                               modelAttribute="incident">
                        <span class="info-item">
                            <p class="equip-item">Lỗi hư hỏng:</p>${incident.incidentDescription}
                        </span>
                        <span class="info-item">
                            <p class="equip-item">Mực độ hư hỏng:</p>${incident.severityLevel}
                        </span>
                        <span class="info-item">
                            <p class="equip-item">Trạng thái và tiến độ xử lý:</p> ${incident.incidentStatus}
                        </span>
                    </form:form>
                </div>
                <div class="border border-3 rounded-3 mb-2 position-relative">
                    <c:choose>
                        <c:when test="${not empty repairHistories}">
                            <c:forEach var="repair" items="${repairHistories}">
                                <div class="border-bottom">
                                    <span class="info-item">
                                        <p class="equip-item">Ngày sửa chữa:</p> ${repair.repairDate}
                                    </span>
                                    <span class="info-item">
                                        <p class="equip-item">Loại sửa chữa:</p> ${repair.repairType}
                                    </span>
                                    <span class="info-item">
                                        <p class="equip-item">Kỹ thuật viên:</p> ${repair.performedByUserId.fullName}
                                    </span>
                                    <span class="info-item">
                                        <p class="equip-item">Chi phí sửa chữa:</p>
                                        <fmt:formatNumber value="${repair.repairCost}" type="currency" currencySymbol="" pattern="#,##0.000 VNĐ" />
                                    </span>
                                </div>
                            </c:forEach>
                        </c:when>
                        <c:otherwise>
                            <span class="info-item">
                                <p class="equip-item">Ngày sửa chữa:</p> ${repairHistory.repairDate}
                            </span>
                            <form:form id="editFormMain" method="get" modelAttribute="repairHistory">
                                <span class="info-item">
                                    <p class="equip-item">Loại sửa chữa:</p> ${repairHistory.repairType}
                                </span>
                                <span class="info-item">
                                    <p class="equip-item">Kỹ thuật viên:</p> ${repair.performedByUserId.fullName}
                                </span>
                                <span class="info-item">
                                    <p class="equip-item">Chi phí sửa chữa:</p>
                                    <fmt:formatNumber value="${repairHistory.repairCost}" type="currency" currencySymbol="" pattern="#,##0.000 VNĐ" />
                                </span>
                            </form:form>
                        </c:otherwise>
                    </c:choose>
                </div>
            </div>
        </div>
    </div>
</div>


<script>
    /*Hiệu ứng thông báo*/
    function showMessage(message) {
        const mess = document.getElementById("message");
        mess.textContent = message;
        mess.classList.add("show");

        // Ẩn thông báo sau 6 giây
        setTimeout(() => {
            mess.classList.add("hide");
        }, 6000);

        // Xóa lớp "hide" sau khi đã ẩn hoàn toàn để có thể hiển thị lại thông báo sau đó
        mess.addEventListener('transitionend', function () {
            if (mess.classList.contains("hide")) {
                mess.classList.remove("show", "hide");
                mess.style.left = "-100%"; // Đặt lại vị trí của thông báo
            }
        });
    }

    document.addEventListener('DOMContentLoaded', function () {
        const messageElement = document.getElementById('message');
        if (messageElement && messageElement.textContent.trim() !== '') {
            showMessage(messageElement.textContent.trim());
        }
    });
</script>


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
        padding: 30px;
    }

    .info-img {
        width: 100%;
        height: 300px;
        transition: 0.4s opacity ease-in-out;

        &:hover {
            opacity: 0.8;
        }
    }

    .img {
        width: 100%;
        height: 100%;
        object-fit: contain;
        border-style: none;
    }

    .info-item {
        width: 100%;
        display: flex;
        align-items: center;
        padding: 10px 30px;
        font-size: 19px;
        cursor: pointer;
        transition: 0.3s color ease-in-out;

        &:hover {
            color: #0050ae;
        }
    }

    .equip-item {
        margin-right: 20px;
        margin-bottom: 0;
        font-size: 20px;
        font-weight: 500;
    }

    .btn-actions {
        display: flex;
        flex-direction: column;
        width: 100%;
        max-width: 100%;
        padding: 10px 30px;
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

    .btn-action.btn-danger {
        &:hover{
            background: red;
        }
    }

    .btn-danger {
        background-color: red;
        color: white;
        border: 1px solid transparent;
    }

    .btn-danger:hover {
        background-color: darkred;
        border: 1px solid darkred;
    }

    .inputRepair {
        padding: 6px 18px;
        border-radius: 20px;
        border: 1px solid transparent;
    }

    /*Hiệu ứng thông báo*/
    .alert {
        background-color: #d4edda;
        color: #155724;
        padding: 15px;
        border-radius: 5px;
        border: 1px solid #c3e6cb;
        position: fixed;
        top: 20px;
        left: -100%;
        transition: left 0.5s ease, opacity 0.5s ease;
        opacity: 1;
        z-index: 1000;
    }

    .alert.show {
        left: 20px; /* Vị trí thông báo sau khi chuyển động */
    }

    .alert.hide {
        opacity: 0;
        left: -100%; /* Ẩn thông báo ra ngoài màn hình */
    }

</style>


