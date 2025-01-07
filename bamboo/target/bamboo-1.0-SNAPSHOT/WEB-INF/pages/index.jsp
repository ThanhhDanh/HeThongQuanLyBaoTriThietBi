<%-- 
    Document   : index
    Created on : Jul 22, 2024, 9:22:15 PM
    Author     : Acer
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<div class="row mt-4 mx-4">
    <div class="col-sm-12 col-md-12 col-lg-8 infos">
        <div class="mp-1 info-list text-start">
            <h4>Tình trạng hôm nay</h4>
            <h6 class="label">Tổng hợp</h6>
            <div class="row g-2 mp-1-list">
                <div class="col-8 col-sm-4 col-md-4 col-lg-4">
                    <div class="mp-1-item">
                        <div class="sup-1-item">
                            <img src="data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAABYAAAAaCAYAAACzdqxAAAAABHNCSVQICAgIfAhkiAAAATNJREFUSIm1lU2SgyAQhV/j3CtkNZUjaHGX6F1ScwVKN+rB6J7FDCk0IKjJ28nP1w3Yr4FC8VCPrm/a0vWUW+D6piXgHo4J0FXfP5tBkmCxRkvl7hDSfytlEqE5DLIV4AUcA5KrOro9pucJSC5+PgVfgNfH3soot5ZiWZbcYS4ALSZWxy6VWKNZsfYcATrivhEAIFbXvcCt7JUfPAJ1fdOKNdp/h9enYhtKJNZoAu6sWMfmD4O9iORyCsxDPfr3KFF5xr5gzoDFGs1DPe4BlYH/iyV88beA36F4xkLzWfBXNBqriRVDBUVDrK7hP0u3xyTWJKv1WdJ7jCclsUaL4hEAFiYkQKdYTUdMaO2OBOzz4bVS7vhZo89lIUKz35RrXZvgVEZhr8s1hY+1/2K5vmn3+McvKxnxJQi7zY4AAAAASUVORK5CYII=" alt="alt"/>
                            <div class="info-item">
                                <p class="number">${equipmentCounter}</p>
                                <p class="label">Số lượng thiết bị</p>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="col-8 col-sm-4 col-md-4 col-lg-4">
                    <div class="mp-1-item">
                        <div class="sup-1-item">
                            <img src="data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAABYAAAAZCAYAAAA14t7uAAAABHNCSVQICAgIfAhkiAAAAYRJREFUSImtVUuygjAQ7I5un1ZpeRddCScDTwau4C5WrALXL/0WMYoUAfHZq3xmOpPJ9IR4A7fqkjiYJMzXh20+5cMpg7a+FoCS/rqE09gBZoy0qWzuSVlSLl3tt5RwAgASmd+fiaayeVtbxZzb+lq0tdWtuiSzIwbi+aR+T2N+UWKSx6lDAUBcZIP+YdB/eRIZwFLSOX74sM36sM0J+Hx6o++AcumyuyDhZOBKABBN4ashnsu+jU+LL80XYgNX/hx2JQC0tQUAhPkQ+ja36gLRJJ4Lz4caI5kDB5MsY5teCBoliNmQPBIA2toKYLnab9J/Bou2vhaSztGIP0UI7iGQsXqdwpCsTUzrc0hFU/R5JnvFu+iqFgBMf+FbMM+BV9y38FFVhIY11FLD3tKrTrNUJy4yQklTWaBz06ayuYiMn0a82m/Str4WpDJhcQSEbnekXMqgunfquNukgOGPlnLpz2FX3iNWQuLFYAhOBgAexCHyQB5IgfsPMkckQ2/RVDbv3+YP/1zf1/752ocAAAAASUVORK5CYII=" alt="alt"/>
                            <div class="info-item">
                                <p class="number">${totalSoldEquipment}</p>
                                <p class="label">Thiết bị đã bán</p>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="col-8 col-sm-4 col-md-4 col-lg-4">
                    <div class="mp-1-item">
                        <div class="sup-1-item">
                            <img src="data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAABQAAAAZCAYAAAAxFw7TAAAABHNCSVQICAgIfAhkiAAAAUdJREFUOI3llLFxg0AQRd8iFSB3YCJLmTsQKsGJNC7DFSAqcBkanLgEnzpQBtm1QAOwDgCDmAMOZx7/6Lhj3/y9213BodRmZ5DYddZJk1O4Ow93pV1cbBa16xXBu6LPAsaJgkiQW0n11u69hjsDIGNuBCmO4dODO4PcAo8u15LaXAVMiSY9h5+KbkCvLiDIXpCipHrpYiRWiNZ1CnptLTcOCmADsncDQdHiPiaLQGrgUKdwG46B5hT8NvAfAFObnVOba11m43I+igsGEguYo6M7FjmsO6iFbQ9z/08CLzaLAuTLFwYTKbcwqAu/f3cVmH5RewEDiLqvYa+rEzYJbEbTj6tuiGgy5q4xMq8+zDUDvRwOYT4lM+twacnMAmtp4guDmZSbyze+MPgL02YtYBSJP2w+Ou59pcC6RJMVgt51xnIJmAo9fAOqTIvxPPU0nAAAAABJRU5ErkJggg==" alt="alt"/>
                            <div class="info-item">
                                <p class="number">${incidentCounter}</p>
                                <p class="label">Thiết bị hư hỏng</p>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <div class="col-sm-12 col-md-12 col-lg-4 infos" id="divchart">
        <div class="info-list w-100 h-100 text-start">
            <h6 class="label mb-2">Mức độ thường xảy ra hư hỏng</h6>
            <p class="label m-0 mb-3"" style="font-size: 12px">
                Mức độ hư hỏng của 1 thiết bị biểu hiện đang ở mức Cao rất nhiều
            </p>
            <canvas id="myChart"></canvas>
        </div>
    </div>
</div>

<div class="row mt-5 mx-4">
    <div class="col-sm-12 col-md-12 col-lg-8 infos">
        <div class="mp-1 info-list text-start">
            <div class="d-flex align-items-center">
                <h4 class="mb-4 me-auto">Thiết bị hoạt động</h4>
                <ul class="pagination justify-content-center">
                    <nav aria-label="Page navigation">
                        <ul class="pagination">
                            <c:set var="currentEquipPage" value="${param.equipPage != null ? param.equipPage : 1}" />
                            <li class="page-item me-2 <c:if test="${currentEquipPage == 1}">disabled</c:if>">
                                <a class="page-link" href="?forumPage=${param.forumPage != null ? param.forumPage : 1}&equipPage=${currentEquipPage - 1}" aria-label="Previous">
                                    <span aria-hidden="true">&laquo;</span>
                                </a>
                            </li>
                            <c:forEach begin="1" end="${Math.ceil(equipmentCounter / 6)}" var="i">
                                <li class="page-item me-2 ${currentEquipPage == i ? 'active' : ''}">
                                    <a class="page-link rounded-circle" href="?equipPage=${i}&forumPage=${param.forumPage != null ? param.forumPage : 1}">${i}</a>
                                </li>
                            </c:forEach>
                            <li class="page-item <c:if test="${currentEquipPage == Math.ceil(equipmentCounter / 6)}">disabled</c:if>">
                                <a class="page-link" href="?forumPage=${param.forumPage != null ? param.forumPage : 1}&equipPage=${currentEquipPage + 1}" aria-label="Next">
                                    <span aria-hidden="true">&raquo;</span>
                                </a>
                            </li>
                        </ul>
                    </nav>
                </ul>
            </div>
            <table class="table table-hover">
                <thead>
                    <tr class="text-center label">
                        <td>Mã thiết bị</td>
                        <td>Tên thiết bị</td>
                        <td>Nhà sản xuất</td>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="equipment" items="${activeEquipments}">
                        <tr class="text-center">
                            <td>${equipment.code}</td>
                            <td>${equipment.name}</td>
                            <td>${equipment.manufacturer}</td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
        </div>
    </div>
    <div class="col-sm-12 col-md-12 col-lg-4 infos" id="divchart">
        <div class="info-list text-start w-100 h-100">
            <div class="d-flex align-items-center">
                <h4 class="mb-4 me-auto">Diễn đàn</h4>
                <!-- Phân trang -->
                <nav aria-label="Page navigation">
                    <ul class="pagination">
                        <c:set var="currentForumPage" value="${param.forumPage != null ? param.forumPage : 1}" />
                        <li class="page-item me-2 <c:if test="${currentForumPage == 1}">disabled</c:if>">
                            <a class="page-link" href="?forumPage=${currentForumPage - 1}&equipPage=${param.equipPage != null ? param.equipPage : 1}" aria-label="Previous">
                                <span aria-hidden="true">&laquo;</span>
                            </a>
                        </li>
                        <c:forEach begin="1" end="${Math.ceil(forumCounter / 3)}" var="i">
                            <li class="page-item me-2 ${currentForumPage == i ? 'active' : ''}">
                                <a class="page-link rounded-circle" href="?forumPage=${i}&equipPage=${param.equipPage != null ? param.equipPage : 1}">${i}</a>
                            </li>
                        </c:forEach>
                        <li class="page-item <c:if test="${currentForumPage == Math.ceil(forumCounter / 3)}">disabled</c:if>">
                            <a class="page-link" href="?forumPage=${currentForumPage + 1}&equipPage=${param.equipPage != null ? param.equipPage : 1}" aria-label="Next">
                                <span aria-hidden="true">&raquo;</span>
                            </a>
                        </li>
                    </ul>
                </nav>
            </div>
            <ul class="list-group">
                <c:forEach items="${forumPosts}" var="post" varStatus="postStatus">
                    <li class="list-group-item">
                        <span>
                            <a href="<c:url value="/forum/${post.id}"/>">${post.title}</a>
                        </span>
                        <p>${post.content}</p>
                        <p class="posted">Đăng bởi: ${post.userId.fullName} - 
                            <span class="post-date" data-date="${post.createdDate}">
                                ${post.createdDate}
                            </span>
                        </p>

                        <div class="viewers">
                            <c:set var="viewers" value="${forumViewers[postStatus.index]}"/>
                            <c:if test="${not empty viewers}">
                                <c:forEach items="${viewers}" var="viewer" varStatus="status">
                                    <!-- Hiển thị chỉ 3 avatar đầu tiên -->
                                    <c:if test="${status.index < 3}">
                                        <img src="${viewer.avatar}" alt="${viewer.fullName}" class="avatar"/>
                                    </c:if>
                                    <!-- Hiển thị "+n người khác" sau 3 avatar -->
                                    <c:if test="${status.index == 3}">
                                        <span class="plus">+ ${fn:length(viewers) - 3}</span>
                                    </c:if>
                                </c:forEach>
                            </c:if>
                        </div>
                    </li>
                </c:forEach>
            </ul>
        </div>
    </div>
</div>

<!-- Modal -->
<input type="hidden" id="hasSchedules" value="${!empty schedules}" />
<div class="modal fade" id="maintenanceModal" tabindex="-1" aria-labelledby="maintenanceModalLabel" aria-hidden="true">
    <div class="modal-dialog" style="z-index: 10000">
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
                    <p class="schedule-item">Không có bảo trì nào cần chú ý.</p>
                </c:if>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn-action" data-bs-dismiss="modal">Đóng</button>
            </div>
        </div>
    </div>
</div>

<script src="https://cdn.jsdelivr.net/npm/chart.js"></script>

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


    //Dùng momentjs để chuyển đổi ngày
    document.addEventListener('DOMContentLoaded', function () {
        const postDates = document.querySelectorAll('.post-date');

        postDates.forEach(function (dateElement) {
            const date = dateElement.getAttribute('data-date');
            const fromNow = moment(date).locale('vi').fromNow(); // Chuyển đổi sang định dạng "X ngày trước"
            dateElement.textContent = fromNow;
        });
    });


    // Chuyển dữ liệu từ server (ở dạng chuỗi JSON) thành đối tượng JavaScript
    const severityData = ${severityData};

    const ctx = document.getElementById('myChart').getContext('2d');
    new Chart(ctx, {
        type: 'line',
        data: {
            labels: Object.keys(severityData),
            datasets: [{
                    label: "Tỉ lệ",
                    data: Object.values(severityData),
                    borderWidth: 1,
                    backgroundColor: ['#FF6384', '#36A2EB', '#FFCE56'],
                }]
        },
        options: {
            scales: {
                y: {
                    beginAtZero: true
                }
            }
        }
    });

</script>

<style>
    .mp-1 {
        color: #333;
        display: flex;
        flex-direction: column;
        height: 100%;
        transition: box-shadow 300ms cubic-bezier(0.4, 0, 0.2, 1);
        border-radius: 10px;
        padding: 32px 0;
    }

    .mp-1-list {
        text-align: left;
        align-items: center;
        justify-content: space-evenly;
        margin-top: 20px;
    }

    .mp-1-item {
        display: flex;
        flex-direction: column;
        gap: 24px;
        padding: 20px;
        border-radius: 16px;
        border: 1px solid #ccc;
        box-shadow: rgba(0, 0, 0, 0.2) 0px 2px 1px -1px, rgba(0, 0, 0, 0.14) 0px 1px 1px 0px, rgba(0, 0, 0, 0.12) 0px 1px 3px 0px;
        height: 100%;
        background-color: transparent;
    }

    .sup-1-item img {
        margin-bottom: 10px;
    }

    .number {
        font-size: 1.5rem;
        font-weight: 600;
    }

    .label {
        font-size: 1rem;
        font-weight: 500;
        color: #999;
    }

    .page-link {
        padding: 4px 11px;
    }

    .list-group-item span {
        font-size: 18px;
        font-weight: 500;
    }

    .list-group-item p {
        color: #666;
        font-size: 14px;
        font-weight: 400;
    }

    .list-group-item .posted,
    .list-group-item .post-date {
        font-size: 12px;
        text-align: right;
        color: #333;
        margin-bottom: 5px;
    }

    .list-group-item .post-date {
        font-weight: 400;
    }

    .viewers {
        display: flex;
        align-items: center;
        justify-content: flex-end;
        margin-top: 10px;
    }

    .viewers img.avatar {
        width: 40px;
        height: 40px;
        border-radius: 50%;
        margin-right: -14px;
        border: 1px solid #ddd; /* Đường viền nhẹ quanh avatar */
        
        &:nth-child(1) {
            z-index: 3;
        }
        &:nth-child(2) {
            z-index: 2;
        }
        &:nth-child(3) {
            z-index: 1;
        }
    }

    .viewers .plus {
        width: 40px;
        height: 40px;
        font-size: 14px;
        color: #fff;
        margin-left: 3px;
        font-style: italic;
        padding: 10px;
        border-radius: 50%;
        background-color: #666;
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


