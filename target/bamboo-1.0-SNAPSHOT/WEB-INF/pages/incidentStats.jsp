<%-- 
    Document   : incidentStats
    Created on : Aug 9, 2024, 3:31:17 PM
    Author     : Acer
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/xml" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<h1 class="text-center mt-1" style="color:#777d91;">KIỂM TRA GIÁM SÁT THIẾT BỊ</h1>

<c:url value="/incidents" var="i"/>
<form:form action="${i}" modelAttribute="" method="get">
    <div class="container">
        <div class="row">
            <div class="col-md-9 col-lg-9 col-xs-12 infos">
                <div class="info-list">

                    <c:url value="/incidents" var="action"/>
                    <form action="${action}" method="get">
                        <div class="mb-3 mt-3">
                            <label for="q" class="form-label">Tên thiết bị</label>
                            <input type="search" class="form-control" style="outline: 0;" id="q" 
                                   placeholder="Tên thiết bị..." name="q" value="${param.q}" aria-label="Close">
                        </div>
                        <div class="mb-3 mt-3">
                            <input type="submit" style="background-color: #777d91" 
                                   class="btn text-light" value="Tìm thiết bị"/>
                        </div>
                    </form>

                    <table class="table table-hover table-header">
                        <thead style="background-color: rgba(0,0,0,0.4)">
                            <tr  class="text-center">
                                <th>Tên kế hoạch</th>
                                <th>Thiết bị</th>
                                <th>Mô tả sự cố</th>
                                <th>Mức độ nghiêm trọng</th>
                                <th>Trạng thái</th>
                                <th>Thời gian xảy ra</th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach var="incident" items="${incidents}">
                                <c:forEach items="${repairHistory}" var="r">
                                    <c:if test="${incident.id == r.incidentId.id && r.incidentId.id != null}">
                                        <tr class="text-center" style="cursor: pointer">
                                            <td>${r.repairType}</td>
                                            <td>${incident.equipmentId.name}</td>
                                            <td>${incident.incidentDescription}</td>
                                            <td>${incident.severityLevel}</td>
                                            <td>${incident.incidentStatus}</td>
                                            <td><fmt:formatDate value="${incident.incidentTime}" pattern="dd-MM-yyyy"/></td>
                                        </tr>
                                    </c:if>
                                </c:forEach>
                            </c:forEach>
                        </tbody>
                    </table>
                </div>
            </div>
            <div class="col-md-3 col-lg-3 col-xs-12 infos">
                <div class="info-list">
                    <canvas id="incidentStatusChart"></canvas>
                </div>
            </div>
        </div>
    </div>
</form:form>

<script src="https://cdn.jsdelivr.net/npm/chart.js"></script>

<script>
    var ctx = document.getElementById('incidentStatusChart').getContext('2d');

    // Chuyển đổi dữ liệu từ backend sang JSON
    var incidentStatusData = JSON.parse('${incidentStatusData}');


    var incidentStatusChart = new Chart(ctx, {
        type: 'pie',
        data: {
            labels: ['Đã xử lý', 'Chưa xử lý', 'Đang xử lý'],
            datasets: [{
                    label: 'Trạng thái sự cố',
                    data: incidentStatusData,
                    backgroundColor: [
                        'rgba(75, 192, 192, 0.2)',
                        'rgba(255, 159, 64, 0.2)',
                        'rgba(255, 99, 132, 0.2)'
                    ],
                    borderColor: [
                        'rgba(75, 192, 192, 1)',
                        'rgba(255, 159, 64, 1)',
                        'rgba(255, 99, 132, 1)'
                    ],
                    borderWidth: 1
                }]
        },
        options: {
            responsive: true,
            plugins: {
                legend: {
                    position: 'top',
                },
                title: {
                    display: true,
                    text: 'Trạng thái sự cố'
                },
                tooltip: {
                    callbacks: {
                        label: function (context) {
                            var label = context.label || '';
                            var value = context.raw || 0;
                            return label + ': ' + value;
                        }
                    }
                }
            }
        },
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
        padding: 10px;
    }
</style>