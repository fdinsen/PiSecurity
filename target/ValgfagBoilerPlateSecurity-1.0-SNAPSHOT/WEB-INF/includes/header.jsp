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
                <!--<li class="nav-item">
                    <a class="nav-link active" aria-current="page" href="index.jsp">Home</a>
                </li>-->
            </ul>
            <div class="d-flex">
                <% if (session.getAttribute("username") == null) { %>
                    <a class="nav-link active" aria-current="page" href="login.jsp">Sign in</a>
                    <a href="register.jsp"><button class="btn btn-primary">Sign up</button></a>
                <% } else { %>
                    <span class="navbar-text" style="color: white">
                        <%=session.getAttribute("username")%>
                    </span>
                <% }%>
            </div>
        </div>
    </div>
</nav>

<!--Display unverified email box-->
<%  String role = (String) request.getSession(false).getAttribute("role");
    if (role != null && role.equals(Role.unverified.toString())) {
%>
    <div class="alert alert-warning" role="alert">
        You have not verified your email! 
        <form>
            <input type="hidden" name="target" value="resend">
            <input type="submit" class="linkbutton" value="Click here to resend">
        </form>
    </div>
<%
}//end if
%>