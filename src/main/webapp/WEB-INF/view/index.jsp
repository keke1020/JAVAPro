<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
	pageContext.setAttribute("APP_PATH", request.getContextPath());
	
%>
<!DOCTYPE html>
<html lang="ja">
	<head>
		<meta charset="utf-8" />
		<meta name="viewport" content="width=device-width, initial-scale=1.0">
		<meta http-equiv="X-UA-Compatible" content="ie=edge">
		<title>発注管理</title>
		<link rel="shortcut icon" type="image/x-icon" href="${APP_PATH}/static/img/kuma.ico" />
		<link rel="stylesheet" type="text/css" href="${APP_PATH}/static/css/bootstrap-table.min.css">
		<link rel="stylesheet" type="text/css" href="${APP_PATH}/static/css/bootstrap.min.css">
	</head>

	<body>
		<jsp:include page="nav.jsp"></jsp:include>
		<jsp:include page="bbs.jsp"></jsp:include>

	</body>
	<script type="text/javascript" src="${APP_PATH}/static/js/jquery-3.2.1.min.js"></script>
	<script type="text/javascript" src="${APP_PATH}/static/js/popper.min.js"></script>
	<script type="text/javascript" src="${APP_PATH}/static/js/bootstrap.min.js"></script>
	<script type="text/javascript" src="${APP_PATH}/static/js/bootstrap-table.min.js"></script>
	<script type="text/javascript">




	</script>
</html>
