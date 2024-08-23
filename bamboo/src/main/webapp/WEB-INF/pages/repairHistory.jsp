<%-- 
    Document   : repairTechnicalInformation
    Created on : Aug 3, 2024, 9:38:23 PM
    Author     : Acer
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<h1 class="text-center mt-1" style="color:#777d91;">CẬP NHẬT THÔNG TIN KỸ THUẬT</h1>

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
                    <c:when test="${not empty repairHistories}">
                        <c:forEach var="repair" items="${repairHistories}">
                            <c:url value="/equipment/repairhistory/${equipment.id}" var="action"/>
                            <form:form method="post" action="${action}" modelAttribute="repairHistory" enctype="multipart/form-data">
                                <div class="mb-3 mt-3">
                                    <span class="info-item">
                                        <p class="equip-item">Ngày sửa chữa:</p> ${repair.repairDate}
                                    </span>
                                </div>
                                <div class="mb-3 mt-3">
                                    <label for="repairType" class="form-label">Loại sửa chữa</label>
                                    <form:input path="repairType" type="text" class="form-control" id="repairType" placeholder="Loại sửa chữa" value="${repair.repairType}"/>
                                    <form:errors path="repairType" cssClass="alertWarning" element="div"/>
                                </div>
                                <div class="mb-3 mt-3">
                                    <label for="repairCost" class="form-label">Chi phí sửa chữa</label>
                                    <form:input path="repairCost" type="number" class="form-control" id="repairCost" placeholder="Chi phí sửa chữa" value="${repair.repairCost}"/>
                                    <form:errors path="repairCost" cssClass="alertWarning" element="div"/>
                                </div>
                                <div class="mb-3 mt-3">
                                    <label for="performedByUserId" class="form-label">Kỹ thuật viên</label>
                                    <form:select path="performedByUserId" class="form-select" id="performedByUserId">
                                        <form:options items="${technicians}" itemValue="id" itemLabel="fullName"/>
                                    </form:select>
                                    <form:errors path="performedByUserId" cssClass="alertWarning" element="div"/>
                                </div>
                                <form:hidden path="id" value="${repair.id}"/>
                                <form:hidden path="equipmentId" value="${equipment.id}"/>
                                <form:hidden path="incidentId" value="${incident.id}"/>
                                <button type="submit" class="btn-action">Lưu thông tin</button>
                            </form:form>
                        </c:forEach>
                    </c:when>
                    <c:otherwise>
                        <c:url value="/equipment/repairhistory/${equipment.id}" var="action"/>
                        <form:form method="post" action="${action}" modelAttribute="repairHistory" enctype="multipart/form-data">
                            <div class="mb-3 mt-3">
                                <span class="info-item">
                                    <p class="equip-item">Ngày sửa chữa:</p> ${repairHistory.repairDate}
                                </span>
                            </div>
                            <div class="mb-3 mt-3">
                                <label for="repairType" class="form-label">Loại sửa chữa</label>
                                <form:input path="repairType" type="text" class="form-control" id="repairType" placeholder="Loại sửa chữa"/>
                                <form:errors path="repairType" cssClass="alertWarning" element="div"/>
                            </div>
                            <div class="mb-3 mt-3">
                                <label for="repairCost" class="form-label">Chi phí sửa chữa</label>
                                <form:input path="repairCost" type="number" class="form-control" id="repairCost" placeholder="Chi phí sửa chữa"/>
                                <form:errors path="repairCost" cssClass="alertWarning" element="div"/>
                            </div>
                            <div class="mb-3 mt-3">
                                <label for="performedByUserId" class="form-label">Kỹ thuật viên</label>
                                <form:select path="performedByUserId" class="form-select" id="performedByUserId">
                                    <form:options items="${technicians}" itemValue="id" itemLabel="fullName"/>
                                </form:select>
                                <form:errors path="performedByUserId" cssClass="alertWarning" element="div"/>
                            </div>
                            <form:hidden path="id" value="${repair.id}"/>
                            <form:hidden path="equipmentId" value="${equipment.id}"/>
                            <form:hidden path="incidentId" value="${incident.id}"/>
                            <button type="submit" class="btn-action">Lưu thông tin</button>
                        </form:form>
                    </c:otherwise>
                </c:choose>
            </div>
        </div>
        <div class="d-flex align-items-center justify-content-between mb-3">
            <button type="button" class="btn-action" data-bs-toggle="modal" data-bs-target="#staticBackdrop">Tạo</button>
            <button type="button" class="btn-action" id="closeUpdateFormBtn">Xóa thông tin</button>
        </div>
    </div>
</div>


<div class="modal fade" id="staticBackdrop" data-bs-backdrop="static" data-bs-keyboard="false" tabindex="-1" aria-labelledby="staticBackdropLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
            </div>
            <c:url value="/equipment/repairhistory/${equipment.id}" var="action"/>
            <form:form method="post" action="${action}" modelAttribute="repairHistory" enctype="multipart/form-data"> 
                <div class="modal-body">
                    <div class="info-list">
                        <div id="newRepairForm">
                            <div class="mb-3 mt-3">
                                <label for="newRepairType" class="form-label">Loại sửa chữa</label>
                                <form:input path="repairType" type="text" class="form-control" id="newRepairType" placeholder="Loại sửa chữa"/>
                            </div>
                            <div class="mb-3 mt-3">
                                <label for="newRepairCost" class="form-label">Chi phí sửa chữa</label>
                                <form:input path="repairCost" type="text" class="form-control" id="newRepairCost" placeholder="Chi phí sửa chữa"/>
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
                    <form:hidden path="incidentId" value="${incident.id}"/>
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