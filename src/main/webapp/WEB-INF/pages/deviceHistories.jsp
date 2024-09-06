<%-- 
    Document   : deviceHistories
    Created on : Aug 4, 2024, 10:40:12 PM
    Author     : Acer
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<h1 class="text-center mt-1" style="color:#777d91;">Lịch sử thiết bị ${equipment.name}</h1>
<c:url value="/equipment/devicehistories/${equipment.id}" var="action"/>
<form:form method="get" action="${action}" modelAttribute="repairHistory" enctype="multipart/form-data">
    <div class="row">
        <div class="col-xs-12 col-sm-12 col-md-12 col-lg-12 infos">
            <div class="info-list p-5">
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
        <div class="col-xs-12 col-sm-12 col-md-12 col-lg-12 infos">
            <div class="info-list">
                <div class="info-item">
                    <span class="me-3 ">Tổng chi phí sửa chữa: </span>
                    <fmt:formatNumber  value="${totalRepairCost}" type="currency" 
                                       currencySymbol="" pattern="#,##0.000 VNĐ" />
                </div>

                <table class="table table-hover table-header">
                    <thead style="background-color: rgba(0,0,0,0.4)">
                        <tr class="text-center">
                            <td>Ngày sửa chữa</td>
                            <td>Bộ phận/Người yêu cầu</td>
                            <td>Loại sửa chữa</td>
                            <td>Người/Đơn vị sửa</td>
                            <td>Chi phí</td>
                            <td>Tình trạng</td>
                        </tr>
                    </thead>
                    <tbody id="equipment${e.id}" data-name="${e.name}" data-code="${e.code}">
                        <c:choose>
                            <c:when test="${not empty repairHistories}">
                                <c:forEach var="repair" items="${repairHistories}">
                                    <tr  class="text-center" style="cursor: pointer">
                                        <td>${repair.repairDate}</td>
                                        <td>${repair.assignedUserId.fullName}</td>
                                        <td>${repair.repairType}</td>
                                        <td>${repair.performedByUserId.fullName}</td>
                                        <td>
                                            <fmt:formatNumber  value="${repair.repairCost}" type="currency" 
                                                               currencySymbol="" pattern="#,##0.000 VNĐ" />
                                        </td>
                                        <td>Trạng thái</td>
                                    </tr>
                                </c:forEach>
                            </c:when>
                            <c:otherwise>
                                <tr  class="text-center" style="cursor: pointer">
                                    <td>${repairHistory.repairDate}</td>
                                    <td>${repairHistory.assignedUserId.fullName}</td>
                                    <td>${repairHistory.repairType}</td>
                                    <td>${repairHistory.performedByUserId.fullName}</td>
                                    <td>
                                        <fmt:formatNumber  value="${repairHistory.repairCost}" type="currency" 
                                                           currencySymbol="" pattern="#,##0.000 VNĐ" />
                                    </td>
                                    <td>Trạng thái</td>
                                </tr>
                            </c:otherwise>
                        </c:choose>
                    </tbody>
                </table>
            </div>
        </div>
        <div class="col-xs-12 col-sm-12 col-md-12 col-lg-12 infos">
            <div class="info-list">
                <h5 class="text-center mt-4 mb-4" style="color:#777d91;">Bảng Phân Tích Chi Phí ${equipment.name} hoạt động</h5>
                <table class="table table-hover table-header">
                    <thead style="background-color: rgba(0,0,0,0.4)">
                        <tr class="text-center">
                            <td>Ngày mua</td>
                            <td>Tên thiết bị</td>
                            <td>Mã thiết bị</td>
                            <td>Loại thiết bị</td>
                            <td>Nhà cung cấp</td>
                            <td>Chí phí bán</td>
                            <td>Trạng thái hiện tại</td>
                        </tr>
                    </thead>
                    <tbody id="equipment${e.id}" data-name="${e.name}" data-code="${e.code}">
                        <tr  class="text-center" style="cursor: pointer">
                            <td>${equipment.purchaseDate}</td>
                            <td>${equipment.name}</td>
                            <td>${equipment.code}</td>
                            <td>${equipment.type}</td>
                            <td>${equipment.manufacturer}</td>
                            <c:choose>
                                <c:when test="${not empty invoiceDetails}">
                                    <td>
                                        <fmt:formatNumber value="${totalInvoiceDetails}" type="currency" currencySymbol="" pattern="#,##0.000 VNĐ" />
                                    </td>
                                </c:when>
                                <c:otherwise>
                                    <td>
                                        <fmt:formatNumber value="${totalInvoiceDetails}" type="currency" currencySymbol="" pattern="#,##0.000 VNĐ" />
                                    </td>
                                </c:otherwise>
                            </c:choose>
                            <td>${equipment.currentStatus}</td>
                        </tr>
                    </tbody>
                </table>
            </div>
        </div>
    </div>
</form:form>




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
        padding: 10px 0;
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
