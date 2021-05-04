<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
        <title>Login</title>
    </head>
    <body>
        <form name="login" action="FrontController" method="post">
            <input type="hidden" name="target" value="login">
            <table align="center">
                <tr>
                    <td>Username</td>
                    <td><input type="text" name="username" /></td>
                </tr>
                <tr>
                    <td>Password</td>
                    <td><input type="text" name="password" /></td>
                </tr>
                <tr>
                    <td><span style="color:red"><%=(request.getAttribute("errMessage") == null) ? "" : request.getAttribute("errMessage")%></span></td>
                </tr>
                <tr>
                    <td></td>
                    <td><input type="submit" value="Login"></input><input type="reset" value="Reset"></input></td>
                </tr>
            </table>
            <a href="register.jsp">Create User</a>
        </form>
    </body>
</html>
