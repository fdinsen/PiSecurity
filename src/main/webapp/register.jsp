<%-- 
    Document   : register
    Created on : 03-May-2021, 16:41:48
    Author     : gamma
--%>

<%@page contentType="text/html" pageEncoding="windows-1252"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=windows-1252">
        <title>JSP Page</title>
    </head>
    <body>
        <h1>Register</h1>
        <form name="register" action="FrontController" method="post">
            <input type="hidden" name="target" value="register">
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
                    <td>Password</td>
                    <td><input type="text" name="password1" /></td>
                </tr>
                <tr>
                    <td><span style="color:red"><%=(request.getAttribute("errMessage") == null) ? "" : request.getAttribute("errMessage")%></span></td>
                </tr>
                <tr>
                    <td></td>
                    <td><input type="submit" value="Register"></input><input type="reset" value="Reset"></input></td>
                </tr>
            </table>
        </form>
    </body>
</html>
