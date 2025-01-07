<%-- 
    Document   : equipment
    Created on : Jul 24, 2024, 12:00:30 AM
    Author     : Acer
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html, charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<h1 class="text-center mt-1" style="color:#777d91;">QUẢN TRỊ THIẾT BỊ</h1>

<div class="mh-100 mw-100">
    <c:url value="/equipment" var="action"/>
    <form:form method="post" action="${action}" modelAttribute="equipment" enctype="multipart/form-data">
        <div style="width: 1140px; position: relative; left: 12% ;border: 1px solid #ccc; border-radius: 20px ;padding: 6px 36px">
            <div class="mb-3 mt-3 text-start">
                <label for="name" class="form-label ms-3">Tên thiết bị</label>
                <form:input path="name" type="text" class="form-control" id="name" placeholder="Tên thiết bị" name="name" />
                <form:errors path="name" cssClass="alertWarning" element="div"/>
            </div>
            <div class="mb-3 mt-3 text-start">
                <label for="code" class="form-label ms-3">Mã thiết bị</label>
                <form:input path="code" type="text" class="form-control" id="code" placeholder="Mã thiết bị" name="code" />
                <form:errors path="code" cssClass="alertWarning" element="div"/>
            </div>
            <div class="mb-3 mt-3 text-start">
                <label for="cate" class="form-label ms-3">Danh mục thiết bị</label>
                <form:select path="categoryId" class="form-select" id="cate" onchange="updateType()">
                    <c:forEach items="${cates}" var="c">
                        <c:choose>
                            <c:when test="${c.id == equipment.categoryId.id}">
                                <option value="${c.id}" selected>${c.name}</option>
                            </c:when>
                            <c:otherwise> <%--Mặc định là cái này--%> 
                                <option value="${c.id}">${c.name}</option>
                            </c:otherwise>
                        </c:choose>
                    </c:forEach>
                </form:select>
            </div>
            <div class="mb-3 mt-3 text-start">
                <label for="type" class="form-label ms-3">Loại thiết bị</label>
                <form:input path="type" type="type" readonly="true"  class="form-control" id="type" placeholder="Loại thiết bị" name="type" />
            </div>
            <div class="mb-3 mt-3 text-start">
                <label for="manufacturer" class="form-label ms-3">Nhà cung cấp</label>
                <form:input path="manufacturer" type="text" class="form-control" id="manufacturer" placeholder="Nhà cung cấp" name="manufacturer" />
                <form:errors path="manufacturer" cssClass="alertWarning" element="div"/>    
            </div>
            <div class="mb-3 mt-3 text-start">
                <label for="currentStatus" class="form-label ms-3">Trạng thái hiện tại</label>
                <form:input path="currentStatus" type="text" class="form-control" id="currentStatus" placeholder="Trạng thái hiện tại" name="currentStatus" />
                <form:errors path="currentStatus" cssClass="alertWarning" element="div"/>  
            </div>
            <form:form method="get" action="${action}" modelAttribute="equipLocation">
                <div class="mb-3 mt-3 text-start">
                    <label for="currentLocation" class="form-label ms-3">Vị trí hiện tại</label>
                    <form:input path="currentLocation" type="text" class="form-control" id="currentLocation" placeholder="Vị trí hiện tại" name="currentLocation" />
                    <form:errors path="currentLocation" cssClass="alertWarning" element="div"/>  
                </div>
            </form:form>
            <div class="mb-3 mt-3 text-start">
                <label for="price" class="form-label ms-3">Giá thiết bị (VNĐ)</label>
                <form:input path="price" type="text" class="form-control" id="price" placeholder="Giá thiết bị..." name="price" />
                <form:errors path="price" cssClass="alertWarning" element="div"/>  
            </div>
            <div class="mb-3 mt-3 text-start">
                <label for="description" class="form-label ms-3">Mô tả: </label>
                <form:textarea rows="5" path="description" type="text" class="form-control" id="description" placeholder="Cách dùng" name="description" />
                <form:errors path="description" cssClass="alertWarning" element="div"/>    
            </div>
            <div class="mb-3 mt-3 text-start">
                <label for="file" class="form-label ms-3">Ảnh thiết bị</label>
                <form:input path="file" type="file" class="form-control" id="file" placeholder="Ảnh thiết bị" name="file" />
                <c:if test="${not empty equipImg.image}">
                    <img src="${equipImg.image}" alt="${equipment.name}" class="image"/>
                </c:if>
            </div>
            <div class="mb-3 mt-3 text-start">
                <form:hidden path="id"/>
                <form:form method="get" modelAttribute="equipImg" enctype="multipart/form-data">
                    <form:hidden path="image"/>
                </form:form>
                <form:form method="get" modelAttribute="equipLocation" enctype="multipart/form-data">
                    <form:hidden path="equipmentId"/>
                    <form:hidden path="id"/>
                </form:form>
                <button type="submit" class="btn text-light mt-1" style="background-color: #f983cc">
                    <c:choose>
                        <c:when test="${equipment.id != null}">Cập nhật thiết bị</c:when>
                        <c:otherwise>Thêm thiết bị</c:otherwise>
                    </c:choose>
                </button>
            </div>
        </div>
    </form:form>
</div>

<style>
    .form-control:focus, .form-select:focus {
        outline: none;
        border-color: #ccc;
        box-shadow: none;
    }

    .form-label {
        font-size: 14px;
        font-weight: 500;
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

    .image {
        width: 120px;
        height: 120px;
        object-fit: cover;
        border: 1px solid transparent;
        padding: 8px;
        border-radius: 50%;
        margin-top: 10px;
        cursor: pointer;

        &:hover {
            border: 1px solid #ccc;
            box-shadow: 0 4px 8px 0 rgba(0, 0, 0, 0.2), 0 6px 20px 0 rgba(0, 0, 0, 0.19);
        }
    }
</style>

<script>
    function updateType() {
        var select = document.getElementById("cate");
        var typeInput = document.getElementById("type");
        var selectedOption = select.options[select.selectedIndex].text;
        typeInput.value = selectedOption;
        console.log("Selected type: ", selectedOption);  // Kiểm tra giá trị
    }
</script>
