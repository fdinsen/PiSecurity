<%-- 
    Document   : register
    Created on : 03-May-2021, 16:41:48
    Author     : gamma
--%>
<%@page import="PresentationLayer.CaptchaKeyHandler"%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<% CaptchaKeyHandler.setPublicCaptchaKey(request); %>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=windows-1252">
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-beta3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-eOJMYsd53ii+scO/bJGFsiCZc+5NDVN2yr8+0RDqr0Ql0h+rP48ckxlpbzKgwra6" crossorigin="anonymous">
        <link rel="stylesheet" href="styles.css">
        <script src="https://www.google.com/recaptcha/api.js"></script>
        <title>Register</title>
    </head>
    <body>
        <jsp:include page="WEB-INF/includes/header.jsp"/>
        <div class="col-lg text-center" style="">
            <h1 class="display-1">Register</h1>
        </div>
        <div class="container">
            <div class="row align-items-center">
                <div class="col-sm" style=""></div>
                <div class="col-sm" style="">
                    <div class="card mt-4" style="width: 45rem;">
                        <div class="card-body" align="center">
                            <form name="register" action="FrontController" method="post">
                                <input type="hidden" name="target" value="register">

                                <div class="pt-1">
                                    <label class="form-label">Email</label>
                                    <input type="text" name="email" class="form-control"/>
                                </div>
                                <div class="pt-1">
                                    <label class="form-label">Username</label>
                                    <input type="text" name="username" class="form-control"/>
                                </div>
                                <div class="pt-1">
                                    <label class="form-label">Password</label>
                                    <input type="password" name="password" class="form-control"/>
                                </div>
                                <div class="pt-1">
                                    <label class="form-label">Confirm Password</label>
                                    <input type="password" name="password1" class="form-control"/>
                                </div>
                                <span style="color:red"><%=(request.getAttribute("errMessage") == null) ? "" : request.getAttribute("errMessage")%></span>
                                <div class="mt-3 g-recaptcha" data-sitekey=${requestScope.key}></div>
                                <div class="m-3">
                                    <input class="btn btn-primary" type="submit" value="Register"></input>
                                    <input class="btn btn-secondary" type="reset" value="Reset"></input>
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
