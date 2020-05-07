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
		<link rel="stylesheet" type="text/css" href="${APP_PATH}/static/css/bootstrap.min.css">
	</head>

	<body>
		<button id="button">取得</button>

	</body>
	<script type="text/javascript" src="${APP_PATH}/static/js/jquery-1.7.2.min.js"></script>
	<script type="text/javascript" src="${APP_PATH}/static/js/bootstrap.min.js"></script>
	<script type="text/javascript">
		
		
		
		
	</script>
</html>
