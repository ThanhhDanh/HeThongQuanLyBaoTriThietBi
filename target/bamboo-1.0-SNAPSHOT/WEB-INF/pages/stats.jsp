<%-- 
    Document   : stats
    Created on : Jul 29, 2024, 2:00:29 PM
    Author     : Acer
--%>

<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<h1 class="text-center mt-1" style="color:#777d91;">THÔNG KÊ THIẾT Bị</h1>


<div class="d-flex align-items-center justify-content-center">
    <div class="mb-3">
        <label for="transform">Chọn loại:</label>
        <select name="type" id="transform" class="py-2 px-4">
            <option value="bar">Bar</option>
            <option value="doughnut">Doughnut</option>
            <option value="line">Line</option>
        </select>
    </div>
</div>

<c:url value="/stats" var="s"/>
<form:form method="get" action="${s}">
    <div class="d-flex align-items-center justify-content-center">
        <div class="mb-3 me-4">
            <label for="yearChart1">Chọn năm:</label>
            <select name="yearChart1" id="yearChart1" class="py-2 px-4">
                <option value="0">-- Tất cả năm --</option>
                <c:forEach begin="2020" end="2024" var="y">
                    <option value="${y}" <c:if test="${param.yearChart1 == y}">selected</c:if>>${y}</option>
                </c:forEach>
            </select>
        </div>

        <div class="mb-3 me-4">
            <label for="month">Chọn giai đoạn:</label>
            <select name="monthChart1" id="monthChart1" class="py-2 px-4">
                <option  value="0">-- Tất cả tháng --</option>
                <c:forEach begin="1" end="12" var="m">
                    <option  value="${m}" <c:if test="${param.monthChart1 == m}">selected</c:if>>${m}</option>
                </c:forEach>
            </select>
        </div>

        <div class="mb-3">
            <button type="submit" class="btn-action" id="filterChart1">Lọc</button>
        </div>
    </div>
</form:form>

<div class="container">
    <div class="row">
        <div class="col-md-6 col-lg-6 col-12 infos">
            <div class="info-list">
                <table class="table table-hover table-header">  
                    <thead style="background-color: rgba(0,0,0,0.4)">
                        <tr class="text-center">
                            <td>Mã thiết bị</td>
                            <td>Tên thiết bị</td>
                            <td>Ngày bán</td>
                            <td>Số lượng</td>
                            <td>Doanh thu</td>
                        </tr>
                    </thead>
                    <c:forEach items="${stats}" var="s">
                        <tbody>
                            <tr class="text-center">
                                <td>${s[1]}</td>
                                <td>${s[2]}</td>
                                <td>${s[6]}</td>
                                <td>${s[3]}</td>
                                <td>
                                    <fmt:formatNumber value="${s[4]}" type="currency"  
                                                      currencySymbol="" pattern="#,##0.000 VNĐ" />
                                </td>
                            </tr>
                        </tbody>
                    </c:forEach>
                </table>
            </div>
        </div> 
        <div class="col-md-6 col-lg-6 col-12 infos" id="divchart">
            <div class="info-list">
                <canvas id="myChart"></canvas>
            </div>
        </div>
    </div>
</div>


<c:url value="/stats" var="s2"/>
<form:form method="get" action="${s2}">
    <div class="d-flex align-items-center justify-content-center pt-4">
        <div class="mb-3 me-4">
            <label for="yearChart2">Chọn năm:</label>
            <select name="yearChart2" id="yearChart2" class="py-2 px-4">
                <option value="0">-- Tất cả năm --</option>
                <c:forEach begin="2020" end="2024" var="y">
                    <option value="${y}" <c:if test="${param.yearChart2 == y}">selected</c:if>>${y}</option>
                </c:forEach>
            </select>
        </div>

        <div class="mb-3 me-4">
            <label for="monthChart2">Chọn giai đoạn:</label>
            <select name="monthChart2" id="monthChart2" class="py-2 px-4">
                <option  value="0">-- Tất cả tháng --</option>
                <c:forEach begin="1" end="12" var="m">
                    <option  value="${m}" <c:if test="${param.monthChart2 == m}">selected</c:if>>${m}</option>
                </c:forEach>
            </select>
        </div>

        <div class="mb-3">
            <button type="submit" class="btn-action" id="filterChart2">Lọc</button>
        </div>
    </div>
</form:form>

<div class="container">
    <div class="row">
        <div class="col-md-6 col-lg-6 col-12 infos">
            <div class="info-list">
                <table class="table table-hover table-header">  
                    <thead style="background-color: rgba(0,0,0,0.4)">
                        <tr class="text-center">
                            <td>Mã thiết bị</td>
                            <td>Tên thiết bị</td>
                            <td>Ngày sửa chữa</td>
                            <td>Chi phí sửa chữa</td>
                        </tr>
                    </thead>
                    <c:forEach items="${statsRepairHistory}" var="r">
                        <tbody>
                            <tr class="text-center">
                                <td>${r[1]}</td>
                                <td>${r[2]}</td>
                                <td>${r[3]}</td>
                                <td>
                                    <fmt:formatNumber value="${r[4]}" type="currency"  
                                                      currencySymbol="" pattern="#,##0.000 VNĐ" />
                                </td>
                            </tr>
                        </tbody>
                    </c:forEach>
                </table>
            </div>
        </div>
        <div class="col-md-6 col-lg-6 col-12 infos" id="divchart">
            <div class="info-list">
                <canvas id="myChart1"></canvas>
            </div>
        </div>
    </div>
</div>


<script src="https://cdn.jsdelivr.net/npm/chart.js"></script>

<script>

    let chartInstances = {};
    const ctx = document.getElementById('myChart').getContext('2d');
    const ctx1 = document.getElementById('myChart1').getContext('2d');

    let labels = [];
    let datas = [];

    let labelRevenue = [];
    let dataRevenue = [];

    let colors = [];
    let borderColors = [];

    let colorRevenue = [];
    let borderColorRevenue = [];

    <c:forEach items="${stats}" var="s">
    labels.push('${s[2]}');
    datas.push(${s[4]});
    colors.push(`rgba(${Math.random()*255},
        ${Math.random()*255},
        ${Math.random()*255}, 0.8)`);

    borderColors.push(`rgba(${Math.random()*255},
        ${Math.random()*255},
        ${Math.random()*255}, 1)`);
    </c:forEach>

    <c:forEach items="${statsRepairHistory}" var="r">
    labelRevenue.push('${r[2]}');
    dataRevenue.push(${r[4]});
    colorRevenue.push(`rgba(${Math.random()*255},
        ${Math.random()*255},
        ${Math.random()*255}, 0.8)`);

    borderColorRevenue.push(`rgba(${Math.random()*255},
        ${Math.random()*255},
        ${Math.random()*255}, 1)`);
    </c:forEach>


    window.onload = () => {
        drawChartRevenue('myChart', 'bar', labels, datas, colors, borderColors, "Doanh thu thiết bị");
        drawChartRevenue('myChart1', 'bar', labelRevenue, dataRevenue, colorRevenue, borderColorRevenue, "Báo cáo về chi phí sửa chữa cho từng thiết bị");

        document.getElementById('transform').addEventListener('change', (e) => {
            console.log(e.target.value);
            const selectedType = e.target.value;
            drawChartRevenue('myChart', selectedType, labels, datas, colors, borderColors, "Doanh thu thiết bị");
            drawChartRevenue('myChart1', selectedType, labelRevenue, dataRevenue, colorRevenue, borderColorRevenue, "Báo cáo về chi phí sửa chữa cho từng thiết bị");
        });

    };


    function drawChartRevenue(canvasId, type, labels, data, colors, borderColors, title) {

        if (chartInstances[canvasId]) {
            chartInstances[canvasId].destroy();
        }

        const ctx = document.getElementById(canvasId).getContext('2d');

        const dataset = {
            label: title,
            data: data,
            borderWidth: 1,
            backgroundColor: colors,
            borderColor: borderColors
        };

        if (type === "bar") {
            dataset.borderRadius = 50;
        }

        chartInstances[canvasId] = new Chart(ctx, {
            type: type,
            data: {
                labels: labels,
                datasets: [dataset]
            },
            options: {
                scales: {
                    y: {
                        beginAtZero: true
                    }
                }
            }
        });
    }
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

    select {
        -webkit-appearance: none;
        -moz-appearance: none;
        appearance: none;
        border: 1px solid #ccc;
        border-radius: 20px;
        cursor: pointer;
        font-size: 16px;

        &:focus-visible {
            outline: 1px solid #ccc;
        }
    }

    select option {
        line-height: 20px;
    }
</style>