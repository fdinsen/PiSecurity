<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <title>Create Category</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-beta3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-eOJMYsd53ii+scO/bJGFsiCZc+5NDVN2yr8+0RDqr0Ql0h+rP48ckxlpbzKgwra6" crossorigin="anonymous">
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0/dist/js/bootstrap.bundle.min.js" integrity="sha384-p34f1UUtsS3wqzfto5wAAmdvj+osOnFyQFpp4Ua3gs/ZVWx6oOypYoCJhGGScy+8" crossorigin="anonymous"></script>
</head>
<% //In case, if Admin session is not set, redirect to Login page
    if((request.getSession(false).getAttribute("Admin")== null) )
    {
%>
<jsp:forward page="/login.jsp"></jsp:forward>
<%} %>
<body>
<jsp:include page="WEB-INF/includes/header.jsp"/>
<div class="container">
    <div class="col-lg text-center" style="">
        <h1 class="display-1">Create Category</h1>
    </div>
    <div class="row align-items-center">
        <div class="col-sm" style=""></div>
        <div class="col-sm" style="">
            <div class="card mt-4" style="width: 45rem;">
                <div class="card-body" align="center">
                    <form name="createCategory" action="FrontController" method="post">
                        <input type="hidden" name="target" value="createCategory">

                        <div class="pt-1">
                            <label class="form-label">Category Name</label>
                            <input type="text" name="name" class="form-control"/>
                        </div>
                        <span style="color:red"><%=(request.getAttribute("errMsg") == null) ? "" : request.getAttribute("errMsg")%></span>
                        <span style="color:green"><%=(request.getAttribute("message") == null) ? "" : request.getAttribute("message")%></span>
                        <div class="m-3">
                            <input class="btn btn-primary" type="submit" value="Create"></input>
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