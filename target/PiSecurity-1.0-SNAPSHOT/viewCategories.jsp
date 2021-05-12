<%@ page import="PresentationLayer.ViewCategories" %>
<%@ page import="Models.Role" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<% //In case, if Admin session is not set, redirect to Login page
    if((request.getSession(false).getAttribute("Admin")== null) )
    {
%>
<jsp:forward page="/login.jsp"></jsp:forward>
<%} %>

<%
    new ViewCategories(new Role[]{}).execute(request, response);
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
    <title>Categories</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-beta3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-eOJMYsd53ii+scO/bJGFsiCZc+5NDVN2yr8+0RDqr0Ql0h+rP48ckxlpbzKgwra6" crossorigin="anonymous">
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0/dist/js/bootstrap.bundle.min.js" integrity="sha384-p34f1UUtsS3wqzfto5wAAmdvj+osOnFyQFpp4Ua3gs/ZVWx6oOypYoCJhGGScy+8" crossorigin="anonymous"></script>
</head>
<body>
<jsp:include page="WEB-INF/includes/header.jsp"/>
<div class="container">
    <div class="col-lg text-center" style="">
        <h1 class="display-1">Categories</h1>
    </div>
    <div class="row align-items-center">
        <div class="col-xs-12" style="">
            <div class="card mt-4">
                <div class="card-body" align="center">
                    <table class="table table-responsive">
                            <a href="createCategory.jsp"><button type="button" class="btn btn-dark">Create New</button></a>
                        <div class="col">
                            <span style="color:green"><%=(request.getAttribute("message") == null) ? "" : request.getAttribute("message")%></span>
                        </div>
                        <div class="col">
                            <span style="color:red"><%=(request.getAttribute("errMessage") == null) ? "" : request.getAttribute("errMessage")%></span>
                        </div>
                        <thead>
                        <tr>
                            <th scope="col">ID</th>
                            <th scope="col">Name</th>
                            <th scope="col">Created By</th>
                            <th scope="col">Updated By</th>
                            <th scope="col">Created At</th>
                            <th scope="col">Updated At</th>
                            <th scope="col">Actions</th>
                        </tr>
                        </thead>
                        <tbody>
                        <c:forEach var="category" items="${requestScope.categories}">
                            <tr>

                        <c:choose>
                            <c:when test="${requestScope.editing == true && requestScope.editCatId == category.id}">
                            <form action="FrontController" method="POST">
                                <input type="hidden" name="target" value="editCategory">
                                <input type="hidden" name="catId" value="${category.id}">
                                <th scope="row">${category.id}</th>
                                <td>
                                    <div class="input-group has-validation">
                                    <input aria-describedby="validation" type="text" name="name" value="${requestScope.categoryName}" class="form-control <%=(request.getAttribute("errMsg") == null) ? "" : " is-invalid" %>"/>
                                    <div id="validation" class="invalid-feedback">
                                        <%=(request.getAttribute("errMsg") == null) ? "" : request.getAttribute("errMsg")%>
                                    </div>
                                    </div>
                                </td>
                                <td>${category.createdBy.username}</td>
                                <td>${category.updatedBy.username}</td>
                                <td><fmt:formatDate value="${category.createdAt}" pattern="dd-MM-yyyy HH:mm" /></td>
                                <td><fmt:formatDate value="${category.updatedAt}" pattern="dd-MM-yyyy HH:mm" /></td>
                                <td class="text-center">
                                    <button type="submit" class="btn btn-outline-primary btn">Save</button>
                                    <a href="viewCategories.jsp"><button type="button" class="btn btn-outline-warning btn">Cancel</button></a>
                                </td>
                            </form>
                            </c:when>
                            <c:otherwise>
                                <th scope="row">${category.id}</th>
                                <td>${category.name}</td>
                                <td>${category.createdBy.username}</td>
                                <td>${category.updatedBy.username}</td>
                                <td><fmt:formatDate value="${category.createdAt}" pattern="dd-MM-yyyy HH:mm" /></td>
                                <td><fmt:formatDate value="${category.updatedAt}" pattern="dd-MM-yyyy HH:mm" /></td>
                                <td class="text-center">
                                    <form action="FrontController" method="POST">
                                    <input type="hidden" name="target" value="editCategory">
                                    <input type="hidden" name="catId" value="${category.id}">
                                    <button type="submit" class="btn btn-outline-primary btn">Edit</button>
                                    <input type="hidden" name="name" value="${category.name}">
                                    <input type="hidden" name="beginEdit" value="1">
                                    </form>
                                    <form action="FrontController" method="POST">
                                        <input type="hidden" name="target" value="deleteCategory">
                                        <input type="hidden" name="catId" value="${category.id}">
                                        <button type="submit" class="btn btn-outline-danger btn">Delete</button>
                                    </form>
                                </td>
                            </c:otherwise>
                        </c:choose>


                        </tr>
                        </c:forEach>
                        </tbody>
                    </table>
                </div>
            </div>
        </div>
    </div>
</div>
</body>
</html>
