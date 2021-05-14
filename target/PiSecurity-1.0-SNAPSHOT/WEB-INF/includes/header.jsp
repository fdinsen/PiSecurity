<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page import="Models.Role"%>
<nav class="navbar navbar-fixed-top navbar-expand-sm navbar-dark bg-dark ">
    <div class="container-fluid">
        <a class="navbar-brand" href="index.jsp">
            Forum
        </a>

        <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
            <span class="navbar-toggler-icon"></span>
        </button>
        <div class="collapse navbar-collapse" id="navbarSupportedContent">
            <ul class="navbar-nav me-auto mb-2 mb-lg-0">
                <% //In case, if Admin session is not set, redirect to DTO page
                    if ((request.getSession(false).getAttribute("Admin") != null)) {
                %>
                <li class="nav-item">
                    <a class="nav-link" aria-current="page" href="viewCategories.jsp">Categories</a>
                </li>
                <%} %>

            </ul>
            <% //If logged in show dropdown else show login / register
                if ((request.getSession(true).getAttribute("User") == null)) {
            %>
            <div class="d-flex">
                <% if (session.getAttribute("username") == null) { %>
                <a class="nav-link active" aria-current="page" href="login.jsp">Sign in</a>
                <a href="register.jsp"><button class="btn btn-primary">Sign up</button></a>
                <% } else {%>
                <span class="navbar-text" style="color: white">
                    <%=session.getAttribute("username")%>
                </span>
                <% }%>
            </div>
            <%} else {%>
            <ul class="nav pull-right">
                <li class="nav-item dropdown">
                    <a class="nav-link dropdown-toggle" href="#" id="navbarDropdown" role="button" data-bs-toggle="dropdown" aria-expanded="false">
                        Welcome, <%= (request.getSession(true).getAttribute("username"))%>
                    </a>
                    <ul class="dropdown-menu" aria-labelledby="navbarDropdown">
                        <li><a class="dropdown-item" href="#">Action</a></li>
                        <li><a class="dropdown-item" href="#">Another action</a></li>
                        <li><hr class="dropdown-divider"></li>
                        <li><a class="dropdown-item" href="FrontController?target=logout">Logout</a></li>
                    </ul>
                </li>
            </ul>
            <%} %>
        </div>
    </div>
</nav>

<!--Display unverified email box-->
<%  String role = (String) request.getSession(false).getAttribute("role");
    if (role != null && role.equals(Role.unverified.toString())) {
%>
<div class="alert alert-warning" role="alert">
    <form name="resend" action="FrontController" method="post">
        You have not verified your email! 
        <input type="hidden" name="target" value="resend">
        <input type="submit" class="linkbutton" value="Click here to resend">
        <span style="color:green"><%=(request.getAttribute("emailMsg") == null) ? "" : request.getAttribute("emailMsg")%></span>
    </form>
</div>
<%
    }//end if
%>