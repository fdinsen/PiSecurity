<%-- 
    Document   : thread
    Created on : 18-May-2021, 15:36:16
    Author     : gamma
--%>

<%@page import="PresentationLayer.Thread.GetThread"%>
<%@page import="Models.Role"%>
<%@page contentType="text/html" pageEncoding="windows-1252"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>



<%
    new GetThread(new Role[]{}).execute(request, response);
%>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
        <title>Thread</title>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-beta3/dist/css/bootstrap.min.css" rel="stylesheet"
              integrity="sha384-eOJMYsd53ii+scO/bJGFsiCZc+5NDVN2yr8+0RDqr0Ql0h+rP48ckxlpbzKgwra6" crossorigin="anonymous">
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0/dist/js/bootstrap.bundle.min.js"
                integrity="sha384-p34f1UUtsS3wqzfto5wAAmdvj+osOnFyQFpp4Ua3gs/ZVWx6oOypYoCJhGGScy+8"
        crossorigin="anonymous"></script>
    </head>
    <body>
        <jsp:include page="WEB-INF/includes/header.jsp"/>
        <div class="container">
            <div class="col-lg text-center" style="">
                <h1 class="display-1">Thread</h1>
            </div>
            <div class="row align-items-center">
                <div class="col-xs-12" style="">
                    <div class="card mt-4">
                        <div class="card-body" align="center">
                            <div class="container">

                                <div id="title" class="row card mt-4" align="left">
                                    <div class="card-body">
                                        <h5 class="display-5" align="left">${requestScope.thread.name}</h5>
                                    </div>
                                </div> <!--TITLE END-->

                                <div class="row" align="left"> <!--POST START-->
                                    <div class="col-2 mt-4">
                                        <p>${requestScope.thread.createdBy.username}</p>
                                        <jsp:include page="WEB-INF/includes/profilePicture.jsp"/>
                                    </div>
                                    <div class="col-10">
                                        <div class="card mt-4">
                                            <div class="card-body">
                                                <p>${requestScope.thread.text}</p>
                                            </div>
                                        </div>
                                    </div>
                                </div> 
                                <hr> <!--POST END-->

                                <c:forEach var="post" items="${requestScope.thread.posts}">
                                    <div class="row" align="left"> <!--POST START-->
                                        <div class="col-2 mt-10">
                                            <p>${post.createdBy.username}</p>
                                            <img src="https://www.mico.dk/wp-content/uploads/2020/05/blank-profile-picture-973460_1280.png" style="width: 8rem;">
                                        </div>
                                        <div class="col-8">
                                            <div class="card mt-4">
                                                <div class="card-body">
                                                    <c:out value="${post.text}" escapeXml="false" />
                                                </div>
                                            </div>
                                        </div>
                                    </div> 
                                    <hr> <!--POST END-->
                                </c:forEach>
                            </div>
                            <div class="text-center mb-5">
                                <a href="createPost.jsp?threadId=<c:out value="${requestScope.threadId != null ? requestScope.threadId : param.threadId}"/>">
                                    <button type="button" class="btn btn-primary">Create new Post</button>
                                </a>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </body>
</html>
