<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>


<% //If username session is not set, redirect to login
  if((request.getSession(false).getAttribute("username") == null) )
  {
%>
<jsp:forward page="/login.jsp"></jsp:forward>
<%} %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
  <link rel="stylesheet" href="styles.css">
  <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
  <title>Profile</title>
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
    <h1 class="display-1">Profile</h1>
  </div>
  <div class="row align-items-center">
    <div class="col-sm" style=""></div>
    <div class="col-sm" style="">
      <div class="card mt-4" style="width: 45rem;">
        <div class="card-body text-center">
          <jsp:include page="WEB-INF/includes/profilePicture.jsp"/>
          <form name="uploadProfilePicture" action="FrontController?target=uploadProfilePicture" method="post" enctype="multipart/form-data">
            <input type="file" name="file" size="50" />
            <div class="alert alert-info" role="alert" style="color: #000000">
              Max 10 MB
            <br />
              Supported filetypes: png
            <br />
              Displayed in: 100 x 100
            </div>
            <span style="color:red"><%=(request.getAttribute("errMsg") == null) ? "" : request.getAttribute("errMsg")%></span>
            <span style="color:green"><%=(request.getAttribute("msg") == null) ? "" : request.getAttribute("msg")%></span>
            <br />
            <button class="btn btn-outline-primary mt-1">
              Upload
            </button>
          </form>
        </div>
      </div>
    </div>
    <div class="col-sm" style=""></div>
  </div>
</div>
</body>
</html>
