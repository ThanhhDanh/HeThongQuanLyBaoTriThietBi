<%-- 
    Document   : maintenanceSchedule
    Created on : Aug 4, 2024, 9:40:57 PM
    Author     : Acer
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<h1 class="text-center mt-1" style="color:#f983cc;">CẬP NHẬT THÔNG TIN KỸ THUẬT</h1>

<div class="row">
    <div class="col-xs-12 col-sm-12 col-md-6 col-lg-6 infos">
        <div class="info-list">
            <h4>Thông tin chung</h4>
            <div class="info-img">
                <img class="img" src="${equipImg.image}" alt="Equipment Image">
            </div>
            <div class="d-flex">
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

                    </div>
                </div>
            </div>
        </div>
    </div>
    <div class="col-xs-12 col-sm-12 col-md-6 col-lg-6 infos">
        <div class="info-list">
            <div id="repairForm" class="form-section">
                <c:choose>
                    <c:when test="${not empty maintenanceSchedule}">
                        <c:forEach var="m" items="${maintenanceSchedule}">
                            <c:url value="/equipment/maintenanceschedule/${equipment.id}" var="action"/>
                            <form:form method="post" action="${action}" modelAttribute="maintenanceSchedules" enctype="multipart/form-data">
                                <div class="mb-3 mt-3">
                                    <label for="maintenanceDate" class="form-label">Ngày bảo trì gần nhất</label>
                                    <form:input path="maintenanceDate" type="date" 
                                                class="form-control" id="maintenanceDate" placeholder="Ngày bảo trì gần nhất" value="${m.maintenanceDate}"/>
                                    <form:errors path="maintenanceDate" cssClass="alertWarning" element="div"/>
                                </div>
                                <div class="mb-3 mt-3">
                                    <label for="nextMaintenanceDate" class="form-label">Ngày bảo trì</label>
                                    <form:input path="nextMaintenanceDate" type="date" class="form-control" 
                                                id="nextMaintenanceDate" placeholder="Ngày bảo trì" value="${m.nextMaintenanceDate}"/>
                                    <form:errors path="nextMaintenanceDate" cssClass="alertWarning" element="div"/>
                                </div>
                                <div class="mb-3 mt-3">
                                    <label for="maintenanceStatus" class="form-label">Trạng thái</label>
                                    <form:input path="maintenanceStatus" type="text" class="form-control" 
                                                id="maintenanceStatus" placeholder="Trạng thái" value="${m.maintenanceStatus}"/>
                                    <form:errors path="maintenanceStatus" cssClass="alertWarning" element="div"/>
                                </div>
                                <div class="mb-3 mt-3">
                                    <label for="maintenanceFrequency" class="form-label">Tần suất bảo trì</label>
                                    <form:input path="maintenanceFrequency" type="text" class="form-control" 
                                                id="maintenanceFrequency" placeholder="Tần suất bảo trì" value="${m.maintenanceFrequency}"/>
                                    <form:errors path="maintenanceFrequency" cssClass="alertWarning" element="div"/>
                                </div>
                                <div class="mb-3 mt-3">
                                    <label for="maintenanceType" class="form-label">Loại bảo trì</label>
                                    <form:input path="maintenanceType" type="text" class="form-control" 
                                                id="maintenanceType" placeholder="Loại bảo trì" value="${m.maintenanceType}"/>
                                    <form:errors path="maintenanceType" cssClass="alertWarning" element="div"/>
                                </div>
                                <div class="mb-3 mt-3">
                                    <label for="performedByUserId" class="form-label">Kỹ thuật viên</label>
                                    <form:select path="performedByUserId" class="form-select" id="performedByUserId">
                                        <form:options items="${technicians}" itemValue="id" itemLabel="fullName"/>
                                    </form:select>
                                    <form:errors path="performedByUserId" cssClass="alertWarning" element="div"/>
                                </div>
                                <div class="d-flex align-items-center justify-content-between me-4 ms-4">
                                    <form:hidden path="reminderDate"/>
                                    <form:hidden path="nextMaintenanceDate"/>
                                    <form:hidden path="id"/>
                                    <form:hidden path="equipmentId" value="${equipment.id}"/>
                                    <form:hidden path="assignedUserId" />
                                    <form:hidden path="performedByUserId" />
                                    <%--<form:hidden path=""/>--%>
                                    <button type="submit" class="btn-action mb-4">Lưu thông tin</button>
                                    <button type="button" class="btn-action" data-bs-toggle="modal" data-bs-target="#staticBackdrop">Tạo</button>
                                </div>
                            </form:form>
                        </c:forEach>
                    </c:when>
                    <c:otherwise>
                        <c:url value="/equipment/maintenanceschedule/${equipment.id}" var="action"/>
                        <form:form method="post" action="${action}" modelAttribute="maintenanceSchedules" enctype="multipart/form-data">
                            <div class="mb-3 mt-3">
                                <label for="maintenanceDate" class="form-label">Ngày bảo trì gần nhất</label>
                                <form:input path="maintenanceDate" type="date" class="form-control" id="maintenanceDate" placeholder="Ngày bảo trì gần nhất"/>
                                <form:errors path="maintenanceDate" cssClass="alertWarning" element="div"/>
                            </div>
                            <div class="mb-3 mt-3">
                                <label for="nextMaintenanceDate" class="form-label">Ngày bảo trì</label>
                                <form:input path="nextMaintenanceDate" type="date" class="form-control" id="nextMaintenanceDate" placeholder="Ngày bảo trì"/>
                                <form:errors path="nextMaintenanceDate" cssClass="alertWarning" element="div"/>
                            </div>
                            <div class="mb-3 mt-3">
                                <label for="maintenanceStatus" class="form-label">Trạng thái</label>
                                <form:input path="maintenanceStatus" type="text" class="form-control" id="maintenanceStatus" placeholder="Trạng thái"/>
                                <form:errors path="maintenanceStatus" cssClass="alertWarning" element="div"/>
                            </div>
                            <div class="mb-3 mt-3">
                                <label for="maintenanceFrequency" class="form-label">Tần suất bảo trì</label>
                                <form:input path="maintenanceFrequency" type="text" class="form-control" id="maintenanceFrequency" placeholder="Tần suất bảo trì"/>
                                <form:errors path="maintenanceFrequency" cssClass="alertWarning" element="div"/>
                            </div>
                            <div class="mb-3 mt-3">
                                <label for="maintenanceType" class="form-label">Loại bảo trì</label>
                                <form:input path="maintenanceType" type="text" class="form-control" id="maintenanceType" placeholder="Loại bảo trì"/>
                                <form:errors path="maintenanceType" cssClass="alertWarning" element="div"/>
                            </div>
                            <div class="mb-3 mt-3">
                                <label for="performedByUserId" class="form-label">Kỹ thuật viên</label>
                                <form:select path="performedByUserId" class="form-select" id="performedByUserId">
                                    <form:options items="${technicians}" itemValue="id" itemLabel="fullName"/>
                                </form:select>
                                <form:errors path="performedByUserId" cssClass="alertWarning" element="div"/>
                            </div>
                            <div class="d-flex align-items-center justify-content-between me-4 ms-4">
                                <form:hidden path="reminderDate"/>
                                <form:hidden path="nextMaintenanceDate"/>
                                <form:hidden path="id"/>
                                <form:hidden path="equipmentId" value="${equipment.id}"/>
                                <form:hidden path="assignedUserId" />
                                <form:hidden path="performedByUserId" />
                                <%--<form:hidden path=""/>--%>
                                <button type="submit" class="btn-action mb-4">Lưu thông tin</button>
                                <button type="button" class="btn-action" data-bs-toggle="modal" data-bs-target="#staticBackdrop">Tạo</button>
                            </div>
                        </form:form>
                    </c:otherwise>
                </c:choose>
            </div>
        </div>
    </div>
</div>



<div class="modal fade" id="staticBackdrop" data-bs-backdrop="static" data-bs-keyboard="false" tabindex="-1" aria-labelledby="staticBackdropLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
            </div>
            <c:url value="/equipment/maintenanceschedule/${equipment.id}" var="action"/>
            <form:form method="post" action="${action}" modelAttribute="maintenanceSchedules" enctype="multipart/form-data"> 
                <div class="modal-body">
                    <div class="info-list">
                        <div id="newRepairForm">
                            <div class="mb-3 mt-3">
                                <label for="newMaintenanceDate" class="form-label">Ngày bảo trì gần nhất</label>
                                <form:input path="maintenanceDate" type="date" class="form-control" id="newMaintenanceDate" placeholder="Ngày bảo trì gần nhất"/>
                                <form:errors path="maintenanceDate" cssClass="alertWarning" element="div"/>
                            </div>
                            <div class="mb-3 mt-3">
                                <label for="newNextmaintenanceDate" class="form-label">Ngày bảo trì</label>
                                <form:input path="nextMaintenanceDate" type="date" class="form-control" id="newNextmaintenanceDate" placeholder="Ngày bảo trì"/>
                                <form:errors path="nextMaintenanceDate" cssClass="alertWarning" element="div"/>
                            </div>
                            <div class="mb-3 mt-3">
                                <label for="newMaintenanceStatus" class="form-label">Trạng thái</label>
                                <form:input path="maintenanceStatus" type="text" class="form-control" id="newMaintenanceStatus" placeholder="Trạng thái"/>
                                <form:errors path="maintenanceStatus" cssClass="alertWarning" element="div"/>
                            </div>
                            <div class="mb-3 mt-3">
                                <label for="newMaintenanceFrequency" class="form-label">Tuần suất bảo trì</label>
                                <form:input path="maintenanceFrequency" type="text" class="form-control" id="newMaintenanceFrequency" placeholder="Tần suất bảo trì"/>
                                <form:errors path="maintenanceFrequency" cssClass="alertWarning" element="div"/>
                            </div>
                            <div class="mb-3 mt-3">
                                <label for="newMaintenanceType" class="form-label">Loại bảo trì</label>
                                <form:input path="maintenanceType" type="text" class="form-control" id="newMaintenanceType" placeholder="Loại bảo trì"/>
                                <form:errors path="maintenanceType" cssClass="alertWarning" element="div"/>
                            </div>
                            <div class="mb-3 mt-3">
                                <label for="performedByUserId" class="form-label">Kỹ thuật viên</label>
                                <form:select path="performedByUserId" class="form-select" id="performedByUserId">
                                    <form:options items="${technicians}" itemValue="id" itemLabel="fullName"/>
                                </form:select>
                                <form:errors path="performedByUserId" cssClass="alertWarning" element="div"/>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="modal-footer">
                    <form:hidden path="equipmentId" value="${equipment.id}"/>
                    <button type="submit" class="btn-action me-2">Tạo</button>
                    <button type="button" class="btn-action" data-bs-dismiss="modal">Close</button>
                </div>
            </form:form>
        </div>
    </div>
</div>


<script>
    document.getElementById('closeUpdateFormBtn').addEventListener('click', function () {
        window.location.reload();
    });
</script>

<style>
    input[type="date"]{
        background-color: #ccc;
        padding: 10px;
        font-family: "Roboto Mono",monospace;
        color: #ffffff;
        font-size: 18px;
        border: none;
        outline: none;
        border-radius: 5px;
    }
    ::-webkit-calendar-picker-indicator{
        background-color: #ffffff;
        padding: 5px;
        cursor: pointer;
        border-radius: 3px;
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
        font-size: 16px;
        cursor: pointer;
        transition: 0.3s color ease-in-out;

        &:hover {
            color: #0050ae;
        }
    }

    .equip-item {
        margin-right: 20px;
        margin-bottom: 0;
        font-size: 16px;
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

</style>
