<%@ page import="PresentationLayer.Forum.GetForum" %>
<%@ page import="Models.Role" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>

<%
    new GetForum(new Role[]{}).execute(request, response);
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
        <title>DTO</title>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-beta3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-eOJMYsd53ii+scO/bJGFsiCZc+5NDVN2yr8+0RDqr0Ql0h+rP48ckxlpbzKgwra6" crossorigin="anonymous">
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0/dist/js/bootstrap.bundle.min.js" integrity="sha384-p34f1UUtsS3wqzfto5wAAmdvj+osOnFyQFpp4Ua3gs/ZVWx6oOypYoCJhGGScy+8" crossorigin="anonymous"></script>
        <style>
            html,
            body {
                overflow-x: hidden;
                /* Prevent scroll on narrow devices */
            }

            @media (max-width: 767.98px) {
                .offcanvas-collapse {
                    position: fixed;
                    top: 56px;
                    /* Height of navbar */
                    bottom: 0;
                    width: 100%;
                    padding-right: 1rem;
                    padding-left: 1rem;
                    overflow-y: auto;
                    background-color: gray;
                    transition: -webkit-transform .3s ease-in-out;
                    transition: transform .3s ease-in-out;
                    transition: transform .3s ease-in-out, -webkit-transform .3s ease-in-out;
                    -webkit-transform: translateX(100%);
                    transform: translateX(100%);
                }

                .offcanvas-collapse.open {
                    -webkit-transform: translateX(-1rem);
                    transform: translateX(-1rem);
                    /* Account for horizontal padding on navbar */
                }
            }

            .box-shadow {
                box-shadow: 0 .25rem .75rem rgba(0, 0, 0, .05);
            }

            .lh-100 {
                line-height: 1;
            }

            .lh-125 {
                line-height: 1.25;
            }

            .lh-150 {
                line-height: 1.5;
            }

            .text-white-50 {
                color: rgba(255, 255, 255, .5);
            }

            .bg-purple {
                background-color: purple;
            }

            .border-bottom {
                border-bottom: 1px solid #e5e5e5;
            }
            .views-number {
                font-size: 24px;
                line-height: 18px;
                font-weight: 400;
            }

        </style>
    </head>
    <body>
        <jsp:include page="WEB-INF/includes/header.jsp"/>
        <main role="main" class="container bg-dark pb-2 rounded">
            <div class="align-items-center p-3 my-3 text-white-50 bg-purple rounded box-shadow text-center">
                <div class="lh-100">
                    <h6 class="mb-0 text-white lh-100">Forum</h6>
                    <small>Since 2021</small>
                </div>
            </div>
            <div class="row">

            <c:forEach var="category" items="${requestScope.categories}">
                <div class="col-10 my-3 p-3 bg-white rounded box-shadow offset-sm-1">
                    <h6 class="border-bottom border-gray pb-2 mb-0">${category.name}</h6>
                <c:forEach var="board" items="${category.boards}">
                    <div class="media text-muted pt-3">
                        <div class="row border-bottom">
                            <div class="col-10">
                                <p class="media-body pb-3 mb-0 small lh-125 border-gray">
                                    <strong class="d-block text-gray-dark">${board.name}</strong>
                                        ${board.description}
                                </p>
                            </div>
                            <div class="col-md-1 text-center forum-info">
                            <span class="views-number">
                                    ${board.threads.size()}
                            </span>
                                <div>
                                    <small>Threads</small>
                                </div>
                            </div>
                            <div class="col-md-1 text-center forum-info">
                            <span class="views-number">
                                    xxx
                            </span>
                                <div>
                                    <small>Post</small>
                                </div>
                            </div>
                        </div>
                    </div>
                </c:forEach>
                </div>
            </c:forEach>
            </div>
        </main>
    </body>
</html>
