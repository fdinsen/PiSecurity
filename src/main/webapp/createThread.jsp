<%@ page import="Models.Role" %>
<%@ page import="PresentationLayer.Board.AdminViewBoardsForCategory" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>


<% //If username session is not set, redirect to login
    if((request.getSession(false).getAttribute("username") == null) )
    {
%>
<jsp:forward page="/login.jsp"></jsp:forward>
<%} %>

<c:if test='${requestScope.boardId == null && param.boardId == null}'>
    <jsp:forward page="/index.jsp"></jsp:forward>
</c:if>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <link rel="stylesheet" href="styles.css">
    <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
    <title>Create thread</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-beta3/dist/css/bootstrap.min.css" rel="stylesheet"
          integrity="sha384-eOJMYsd53ii+scO/bJGFsiCZc+5NDVN2yr8+0RDqr0Ql0h+rP48ckxlpbzKgwra6" crossorigin="anonymous">
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0/dist/js/bootstrap.bundle.min.js"
            integrity="sha384-p34f1UUtsS3wqzfto5wAAmdvj+osOnFyQFpp4Ua3gs/ZVWx6oOypYoCJhGGScy+8"
            crossorigin="anonymous"></script>

    <script src="https://cdn.tiny.cloud/1/xgtnv3r5m6hl4a3sdiqbhl1h3r5tz0gukmxr0velbc9e2ogz/tinymce/5/tinymce.min.js" referrerpolicy="origin"></script>

    <script>
        tinymce.init({
            selector: 'textarea#editor',
            skin: 'bootstrap',
            plugins: 'lists, link, image, media',
            toolbar: 'h1 h2 bold italic strikethrough blockquote bullist numlist backcolor | link image media | removeformat help',
            menubar: false
        });
    </script>
</head>
<body>
<jsp:include page="WEB-INF/includes/header.jsp"/>
<div class="container">
    <div class="col-lg text-center" style="">
        <h1 class="display-1">Create new Thread</h1>
    </div>
    <div class="row align-items-center">
        <div class="col-sm" style=""></div>
        <div class="col-sm" style="">
            <div class="card mt-4" style="width: 45rem;">
                <div class="card-body">
                    <form name="newThread" action="FrontController" method="post">
                        <input type="hidden" name="target" value="createThread">
                        <input type="hidden" name="boardId" value="${requestScope.boardId != null ? requestScope.boardId : param.boardId}">
                        <div class="pt-1 mb-4">
                            <label class="form-label">Title</label>
                            <input type="text" name="name" class="form-control"/>
                        </div>
                        <div class="form-group mb-4">
                            <textarea id="editor" name="text"></textarea>
                        </div>
                        <span style="color:red"><%=(request.getAttribute("errMessage") == null) ? "" : request.getAttribute("errMessage")%></span>
                        <span style="color:green"><%=(request.getAttribute("message") == null) ? "" : request.getAttribute("message")%></span>
                        <div class="m-3 text-center">
                            <input class="btn btn-primary"
                                   type="submit" value="Create"></input>
                            <a href="board.jsp?boardId=<c:out value="${requestScope.boardId != null ? requestScope.boardId : param.boardId}"/>">
                                <button type="button" class="btn btn-dark">Cancel</button>
                            </a>
                        </div>
                    </form>
                </div>
            </div>
        </div>
        <div class="col-sm" style=""></div>
    </div>
</div>
</body>
</html>
