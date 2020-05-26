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
		<link rel="stylesheet" type="text/css" href="${APP_PATH}/static/css/bootstrap.min.css">
	</head>

	<body>
		<div class="container">
			<div class="col-md-12" style="height: 20px;"></div>
			<div class="col-md-12">
				<div class="alert alert-danger">
					<table>
						<tr>
							<!-- <td>パスワードの入力内容に誤りがあります。再度入力してください。</td> -->
							<!-- <td>セッション無効、再度ログインしてください。</td> -->
							<td>再度ログインしてください。</td>
							<td><a href="index">戻す</a><td>
						</tr>
					</table>
				</div>
			</div>
		</div>
	</body>
	<script type="text/javascript" src="${APP_PATH}/static/js/jquery-1.7.2.min.js"></script>
	<script type="text/javascript" src="${APP_PATH}/static/js/bootstrap.min.js"></script>
	<script type="text/javascript">




	</script>
</html>
