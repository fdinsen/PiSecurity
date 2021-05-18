<%@ page import="Models.Role" %>
<%@ page import="PresentationLayer.Board.AdminViewBoardsForCategory" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:if test='${requestScope.boardId == null && param.boardId == null}'>
    <jsp:forward page="/index.jsp"></jsp:forward>
</c:if>

<c:if test="${requestScope.board == null}">
    <jsp:forward
            page="/FrontController?target=getBoard&boardId=${requestScope.boardId != null ? requestScope.boardId : param.boardId}"></jsp:forward>
</c:if>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
    <title>${requestScope.board.name}</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-beta3/dist/css/bootstrap.min.css" rel="stylesheet"
          integrity="sha384-eOJMYsd53ii+scO/bJGFsiCZc+5NDVN2yr8+0RDqr0Ql0h+rP48ckxlpbzKgwra6" crossorigin="anonymous">
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0/dist/js/bootstrap.bundle.min.js"
            integrity="sha384-p34f1UUtsS3wqzfto5wAAmdvj+osOnFyQFpp4Ua3gs/ZVWx6oOypYoCJhGGScy+8"
            crossorigin="anonymous"></script>
    <style type="text/css">
        .panel-heading {
            font-weight: 700;
            font-style: normal;
            line-height: 40px;
            height: 40px;
            padding: 0 0 0 10px;
            text-transform: uppercase;
            color: #fff;
            border-color: transparent;
            border-radius: 3px 3px 0 0;
            background-color: #26bcb5;
        }

        .table {
            margin-bottom: 0;
            border-top: 0;
            background: #fff;
        }

        .footable {
            width: 100%;
        }

        .table>thead>tr>th {
            font-size: inherit;
            color: #fff;
            border: 0;
            border-bottom: 7px solid #ddd;
            padding: 4px 10px;
            border-width: 0;
            background: #8f929a;
        }

        .table>tbody>tr:first-child>td,
        .table>tfoot>tr:first-child>td,
        .table>thead>tr:first-child>td {
            border-top: 0 none;
        }

        .table-primary tbody tr:nth-child(2n+1) td,
        .table-primary tbody tr:nth-child(2n+1) th {
            background: 0 0 #fff;
        }

        .table>tbody>tr>td,
        .table>tfoot>tr>td,
        .table>thead>tr>td {
            padding: 10px 8px;
            vertical-align: inherit;
            border-top: 4px solid #e4e9eb;
        }

        .table>tbody>tr>td,
        .table>tbody>tr>th,
        .table>tfoot>tr>td,
        .table>tfoot>tr>th,
        .table>thead>tr>td {
            padding: 8px;
            line-height: 1.42857143;
            vertical-align: top;
            border-top: 1px solid #ddd;
        }

        .table-primary tbody td {
            color: #94a0a0;
            border-width: 0;
            background: 0 0 #fafafa;
        }

        .table>tbody>tr>td,
        .table>tfoot>tr>td,
        .table>thead>tr>td {
            padding: 10px 8px;
            vertical-align: inherit;
            border-top: 4px solid #e4e9eb;
        }

        .table>tbody>tr>td,
        .table>tbody>tr>th,
        .table>tfoot>tr>td,
        .table>tfoot>tr>th,
        .table>thead>tr>td {
            padding: 8px;
            line-height: 1.42857143;
            vertical-align: top;
            border-top: 1px solid #ddd;
        }

        .table-primary tbody td {
            color: #94a0a0;
            border-width: 0;
            background: 0 0 #fafafa;
        }

        table .stats-col {
            font-family: 'Asap Condensed', 'Arial', 'Helvetica', sans-serif;
            font-size: 13px;
            background-color: #e4e9eb !important;
            letter-spacing: .4px;
        }
    </style>
</head>
<body>
<jsp:include page="WEB-INF/includes/header.jsp"/>
<div class="container">
    <main role="main" class="container bg-dark pb-2 rounded">
        <div class="align-items-center p-3 my-3 text-white-50 bg-purple rounded box-shadow text-center">
            <div class="lh-100">
                <h1 class="mb-0 text-white lh-100"><c:out value="${requestScope.board.name}"/></h1>
            </div>
        </div>
        <div class="container">
            <div class="text-center mb-5">
                <a href="createThread.jsp?boardId=<c:out value="${requestScope.boardId != null ? requestScope.boardId : param.boardId}"/>">
                    <button type="button" class="btn btn-primary">Create new Thread</button>
                </a>
            </div>
            <div class="panel panel-forum">
                <div class="panel-heading">
                    Topics
                </div>
                <div class="panel-inner">
                    <table class="footable table table-primary">
                        <thead>
                        <tr>
                            <th>Title</th>
                            <th>Statistics</th>
                            <th>Last post</th>
                        </tr>
                        </thead>
                        <tbody>
                        <c:forEach var="thread" items="${requestScope.board.threads}">
                            <tr>
                                <td>
                                    <div class="desc-wrapper">
                                        <a href="thread.jsp?tId=<c:out value="${thread.id}"/>"><c:out
                                                value="${thread.name}"/></a> <br>
                                        by&nbsp;<a href="" style="color: #AA0000;"><c:out
                                            value="${thread.createdBy.username}"/></a>
                                        <small>&nbsp;-&nbsp; <fmt:formatDate value="${thread.createdAt}"
                                                                             pattern="dd-MM-yyyy HH:mm"/>
                                        </small>

                                    </div>
                                </td>
                                <td class="stats-col footable-visible">
                                        <span class="stats-wrapper">
                                            <c:out value="${thread.posts.size()}"/>&nbsp;Replies&nbsp;<br><c:out value="${thread.views}"/>&nbsp;Views
                                        </span>
                                </td>
                                <td class="footable-visible footable-last-column">
                                        <span class="last-wrapper text-overflow">
                                            by&nbsp;<a href="#LINK"
                                                       style="color: #AA0000;" class="username-coloured"
                                                       data-original-title="" title=""><c:out
                                                value="${thread.createdBy.username}"/></a>
                                            <a href="#LINK"
                                               data-original-title="View the latest post"><i
                                                    class="mobile-post fa fa-sign-out"></i></a> <br>
                                            <small><fmt:formatDate value="${thread.createdAt}"
                                                                   pattern="dd-MM-yyyy HH:mm"/></small>
                                        </span>
                                </td>
                            </tr>
                        </c:forEach>
                        </tbody>
                    </table>
                </div>
            </div>
        </div>
    </main>
</div>
</body>
</html>
