<%-- 
    Document   : forumDetail
    Created on : Sep 17, 2024, 2:22:51 PM
    Author     : Acer
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<div class="container">
    <div class="forum">
        <div class="row">
            <div class="col-12 col-sm-12 col-md-8 col-lg-8">
                <div class="forum-header">
                    <h2>${forum.title}</h2>
                </div>

                <div class="forum-content">
                    <p>${forum.content}</p>
                </div>

                <div class="forum-meta">
                    <div class="meta">
                        <span class="post-date" data-date="${forum.createdDate}">${forum.createdDate}</span> 
                        <span>${forum.user.fullName}</span>
                    </div>
                </div>

                <!--                <div class="forum-views">
                                    <h4>Users who viewed this forum:</h4>
                <c:forEach items="${usersViewed}" var="user">
                    <ul class="viewed-users">
                        <li>${user.fullName}</li>
                        <img src="${user.avatar}" alt="${user.fullName}"/>
                    </ul>
                </c:forEach>
            </div>-->

                <div class="forum-add-comment">
                    <h5 class="mb-3">Trả lời bình luận:</h5>
                    <c:url value="/forum/${forum.id}" var="forumUrl"/>
                    <form:form action="${forumUrl}" method="post" modelAttribute="comment" accept-charset="UTF-8">
                        <div class="form-group">
                            <form:textarea path="content" type="text" value="${content}" name="content" class="form-control" rows="3" placeholder="Nhập trả lời của bạn..."/>
                        </div>
                        <button type="submit" class="btn-forum mt-2">Trả lời</button>
                    </form:form>
                </div>

                <div class="forum-comments">
                    <h5 class="mb-3">Trả lời:</h5>
                    <c:forEach items="${comments}" var="comment">
                        <div class="comment-list">
                            <div class="comment-list-item">
                                <div class="comment-img">
                                    <img class="image" src="${comment.user.avatar}" alt="${comment.user.fullName}"/>
                                </div>
                                <div class="comment-info">
                                    <div class="comment-header">
                                        <b>${comment.user.fullName}</b>
                                        <small class="comment-footer post-date" data-date="${comment.createdDate}">${comment.createdDate}</small>
                                    </div>
                                    <div class="comment-body">
                                        ${comment.content}
                                    </div>
                                </div>
                            </div>
                        </div>
                    </c:forEach>
                </div>

                <div class="forum-add">
                    <h5>Thêm diễn đàn mới:</h5>
                    <c:url value="/forum/new" var="forumNewURL"/>
                    <form:form action="${forumNewURL}" method="post"  modelAttribute="forumDTO">
                        <div class="form-group">
                            <form:input path="title" type="text" name="title" class="form-control form-control-new-1" placeholder="Tiêu đề"/>
                        </div>
                        <div class="form-group">
                            <form:textarea path="content" name="content" class="form-control new-content form-control-new-2" rows="5" placeholder="Nội dung diễn đàn"/>
                        </div>
                        <button type="submit" class="btn-forum mt-2">Tạo diễn đàn</button>
                    </form:form>
                </div>
            </div>
            <div class="col-12 col-sm-12 col-md-4 col-lg-4">
                <div class="other-forum-header">
                    <h5>Câu hỏi khác:</h5>
                </div>
                <div class="other-forums">
                    <c:forEach items="${otherForums}" var="otherForum">
                        <div class="list-group-item">
                            <span>
                                <a href="<c:url value="/forum/${otherForum.id}"/>">${otherForum.title}</a>
                            </span>
                            <p>${otherForum.content}</p>
                            <p class="posted">Đăng bởi: ${otherForum.userId.fullName} - 
                                <span class="post-date" data-date="${otherForum.createdDate}">
                                    ${otherForum.createdDate}
                                </span></p>
                        </div>
                    </c:forEach>
                </div>
            </div>
        </div>
    </div>
</div>

<script>
    //Dùng momentjs để chuyển đổi ngày
    document.addEventListener('DOMContentLoaded', function () {
        const postDates = document.querySelectorAll('.post-date');

        postDates.forEach(function (dateElement) {
            const date = dateElement.getAttribute('data-date');
            const fromNow = moment(date).locale('vi').fromNow();
            dateElement.textContent = fromNow;
        });
    });
</script>

<style>
    .container {
        margin-top: 20px;
    }

    .forum {
        text-align: left;
    }

    .forum-header {
        border-bottom: 2px solid rgb(227, 230, 232);
        padding-bottom: 10px;
        margin-bottom: 20px;
    }

    .forum h2 {
        font-size: 28px;
        margin: 0;
        font-weight: 500;
        color: #333;
    }

    .forum-meta {
        text-align: right;
    }

    .meta {
        display: inline-flex;
        flex-direction: column;
        text-align: right;
        color: #777;
        font-size: 12px;
        margin-top: 5px;
        padding: 10px;
        background-color: rgb(237, 245, 253);
        border-radius: 10px;
    }

    .forum-content {
        font-size: 16px;
        line-height: 1.6;
        color: #555;
        margin-bottom: 20px;
        padding: 20px;
        border: 1px solid #999;
        border-radius: 12px;
    }

    .forum-views, .forum-comments {
        margin-top: 30px;
        padding: 10px 0;
        border-bottom: 1px solid #ced4da;
        border-top: 1px solid #ced4da;
    }

    .forum-views h4, .forum-comments h4 {
        font-size: 20px;
        margin-bottom: 10px;
        color: #333;
    }

    .viewed-users, .comment-list {
        list-style: none;
        padding: 0;
    }

    .viewed-users li, .comment-list .comment-list-item {
        background-color: #f9f9f9;
        padding: 10px;
        margin-bottom: 10px;
        border-radius: 5px;
        color: #555;
        transition: background-color 0.3s;
    }

    .comment-list-item {
        display: flex;
        align-items: center;
        border-left: 4px solid #00bfa5;
        padding-left: 15px;
    }

    .comment-list-item:hover {
        background-color: #edeaea;
    }

    .comment-img {
        width: 40px;
        height: 40px;
        margin-right: 20px;
    }

    .image {
        width: 100%;
        height: 100%;
        object-fit: cover;
        border-radius: 100%;
    }

    .comment-header {
        display: flex;
        align-items: center;
        font-weight: 400;
        font-size: 14px;
        margin-bottom: 5px;
        color: #333;
    }

    .comment-header b {
        margin-right: 10px;
    }

    .comment-body {
        font-size: 15px;
        color: #555;
        line-height: 1.5;
    }

    .comment-footer {
        font-size: 12px;
        color: #999;
        text-align: right;
    }
    
    .btn-forum {
        border-radius: 10px;
        border: 1px solid #ccc;
        background-color: #0050ae;
        color: #fff;
        padding: 6px 12px;
        transition: all 1s ease-in-out;
        
        &:hover {
            background-color: #2980b9;
        }
    }
    
    .forum-add {
        margin-top: 30px;
    }
    
    .form-control {
        border-radius: 0;
    }
    
    .forum-add .form-control-new-1 {
        border-top-left-radius: 20px;
        border-top-right-radius: 20px;
        border-bottom-color: #e8e8e8;
    }
    
    .forum-add .form-control-new-2 {
        border-bottom-left-radius: 20px;
        border-bottom-right-radius: 20px;
    }
    
    .new-content {
        border-top-width: 0;
    }
    
    .form-control {
        font-size: 14px;
    }

    .other-forum-header {
        margin: 0 0 20px 10px;
        border-bottom: 2px solid rgb(227, 230, 232);
    }

    .other-forum-header h5 {
        padding-bottom: 12px;
    }

    .other-forums {
        height: 80vh;
        overflow-y: auto;
    }

    .list-group-item,
    .list-group-item:first-child {
        margin-bottom: 10px;
        border-radius: 12px;
    }

    .list-group-item + .list-group-item {
        border-top-width: 1px;
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
</style>