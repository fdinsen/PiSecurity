<%@ page import="PresentationLayer.Forum.GetForum" %>
<%@ page import="Models.Role" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>

<%
    new GetForum(new Role[]{}).execute(request, response);
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
        <title>Forum</title>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-beta3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-eOJMYsd53ii+scO/bJGFsiCZc+5NDVN2yr8+0RDqr0Ql0h+rP48ckxlpbzKgwra6" crossorigin="anonymous">
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0/dist/js/bootstrap.bundle.min.js" integrity="sha384-p34f1UUtsS3wqzfto5wAAmdvj+osOnFyQFpp4Ua3gs/ZVWx6oOypYoCJhGGScy+8" crossorigin="anonymous"></script>
        <link rel="stylesheet" href="styles.css">
    </head>
    <body>
        <jsp:include page="WEB-INF/includes/header.jsp"/>
        <div class="col-lg text-center" style="">
            <span style="color:green"><%=(request.getAttribute("msg") == null) ? "" : request.getAttribute("msg")%></span>
            <span style="color:red"><%=(request.getAttribute("errMessage") == null) ? "" : request.getAttribute("errMessage")%></span>
        </div>
    <main role="main" class="container bg-dark pb-2 rounded">
        <div class="align-items-center p-3 my-3 text-white-50 bg-purple rounded box-shadow text-center">
            <div class="lh-100">
                <h6 class="mb-0 text-white lh-100">Forum</h6>
                <small>Since 2021</small>
            </div>
        </div>
        <div class="row">

            <c:forEach var="category" items="${requestScope.categories}">
                <div class="col-10 my-3 p-3 bg-white rounded box-shadow offset-sm-1">
                    <h6 class="border-bottom border-gray pb-2 mb-0">${category.name}</h6>
                    <c:forEach var="board" items="${category.boards}">
                        <div class="media text-muted pt-3">
                            <div class="row border-bottom">
                                <div class="col-11">
                                    <p class="media-body pb-3 mb-0 small lh-125 border-gray">
                                        <strong class="d-block text-gray-dark"><a href="board.jsp?boardId=<c:out value="${board.id}"/>">${board.name}</a></strong>
                                        ${board.description}
                                    </p>
                                </div>
                                <div class="col-md-1 text-center forum-info">
                                    <span class="views-number">
                                        ${board.threads.size()}
                                    </span>
                                    <div>
                                        <small>Threads</small>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </c:forEach>
                </div>
            </c:forEach>
        </div>
    </main>
</body>
</html>
