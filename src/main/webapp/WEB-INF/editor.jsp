<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
    <head>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-beta3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-eOJMYsd53ii+scO/bJGFsiCZc+5NDVN2yr8+0RDqr0Ql0h+rP48ckxlpbzKgwra6" crossorigin="anonymous">
        <link rel="stylesheet" href="styles.css">
        <title>Editor Page</title>
    </head>
    <% //In case, if Editor session is not set, redirect to DTO page
        if((request.getSession(false).getAttribute("Editor")== null) )
        {
    %>
    <jsp:forward page="/JSP/Login.jsp"></jsp:forward>
    <%} %>
    <body>
        <center><h2>Editor's Home</h2></center>

        Welcome <%=request.getAttribute("userName") %>

        <div style="text-align: right"><a href="<%=request.getContextPath()%>/LogoutServlet">Logout</a></div>

    </body>
</html>