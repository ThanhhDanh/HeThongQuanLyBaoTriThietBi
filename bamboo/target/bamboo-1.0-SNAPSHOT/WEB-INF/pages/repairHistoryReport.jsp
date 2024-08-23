<%-- 
    Document   : repairHistoryReport
    Created on : Aug 16, 2024, 11:24:06 PM
    Author     : Acer
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>


<div class="row">
    <div class="col-xs-12 col-sm-12 col-md-12 col-lg-12 infos">
        <div class="info-list">
            <div class="mb-3">
                <h2 class="">BÁO CÁO LỊCH SỬ SỬA CHỮA</h2>

                <!-- Form lọc thiết bị -->
                <c:url value="/repairhistoryreport" var="action"/>
                <form:form method="get" action="${action}">
                    <input class="form-control w-auto d-inline-block" type="text" name="q" placeholder="Tìm kiếm thiết bị..." value="${param.q}">
                    <button type="submit" class="btn-action d-inline-block">Lọc</button>
                </form:form>

            </div>

            <span id="day" class="d-block w-100 text-end pe-2 mb-2 fst-italic"></span>
            <table class="table table-hover table-header">
                <thead style="background-color: rgba(0,0,0,0.4)">
                    <tr  class="text-center">
                        <th>Ngày sửa chữa</th>
                        <th>Loại sửa chữa</th>
                        <th>Chi phí sửa chữa</th>
                        <th>Lỗi hư hỏng</th>
                        <th>Nhân viên</th>
                        <th>Kỹ thuật viên</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="equipment" items="${equipmentList}">
                        <c:forEach var="history" items="${repairHistory}">
                            <c:if test="${history.equipmentId.id == equipment.id}">
                                <tr  class="text-center" style="cursor: pointer">
                                    <td><fmt:formatDate value="${history.repairDate}" pattern="yyyy-MM-dd" /></td>
                                    <td>${history.repairType}</td>
                                    <td><fmt:formatNumber value="${history.repairCost}" type="currency" pattern="#,##0.000 VNĐ" currencySymbol=""/></td>
                                    <td>${history.incidentId.incidentDescription}</td>
                                    <td>${history.assignedUserId.username}</td>
                                    <td>${history.performedByUserId.username}</td>
                                </tr>
                            </c:if>
                        </c:forEach>
                    </c:forEach>
                </tbody>
            </table>
        </div>
    </div>
</div>

<script>
    const showDay = document.getElementById("day");
    const day = new Date();
    showDay.innerHTML = formatDate(day);
</script>