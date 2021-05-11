<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:choose>
    <c:when test="${requestScope.categories.size() < 1}">
        <jsp:forward page="getForum"></jsp:forward>
    </c:when>e>
</c:choose>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
        <title>Login</title>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-beta3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-eOJMYsd53ii+scO/bJGFsiCZc+5NDVN2yr8+0RDqr0Ql0h+rP48ckxlpbzKgwra6" crossorigin="anonymous">
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0/dist/js/bootstrap.bundle.min.js" integrity="sha384-p34f1UUtsS3wqzfto5wAAmdvj+osOnFyQFpp4Ua3gs/ZVWx6oOypYoCJhGGScy+8" crossorigin="anonymous"></script>
    </head>
    <body>
        <jsp:include page="WEB-INF/includes/header.jsp"/>
        <h1 class="display-1">Forum</h1>
        <div class="container">
            <div class="row align-items-center">
                <div class="col-sm" style=""></div>
                <div class="col-sm" style="">
                    <div class="card mt-4" style="width: 45rem;">
                        <div class="card-body" align="center">
                            <span style="color:red">
                                <%=(request.getAttribute("errMessage") == null) ? "" : request.getAttribute("errMessage")%>
                            </span>
                            <p>Place forum here</p>
                        </div> 
                    </div>
                </div>
                <div class="col-sm" style=""></div>
            </div>
        </div>
    </body>
</html>
