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
		<title>Login</title>
		<link rel="shortcut icon" type="image/x-icon" href="${APP_PATH}/static/img/kuma.ico" />
		<link rel="stylesheet" type="text/css" href="${APP_PATH}/static/css/bootstrap.min.css">
	</head>

	<body>
		<form action="login" method="post">
			<div class="container">
				<div class="col-md-12" style="height: 180px;"></div>
				<div class="col-md-12" style="text-align: center;">
					<div class="h1">
						システム
					</div>
				</div>
			</div>
			<div class="container">
				<div class="col-md-12">
					<div class="col-xs-12">
						<div class="alert-success h2">
							ようこそ
						</div>
					</div>
				</div>
				<div class="col-md-12" style="height: 20px;"></div>
				<div class="col-md-12">
					<div class="form-group col-md-12">
						<div>
							<label class="control-label">パスワード</label>
						</div>
						<div>
							<input type="password" class="form-control" name="password" placeholder="パスワードを入力してください。" autocomplete="off"
							 οnkeydοwn='if(event.keyCode==13){gosubmit();}' />
						</div>
						<div class="col-md-12" style="height: 33px;"></div>
						<div class="col-md-12" style="text-align: center;">
							<button class="btn btn-primary" type="submit">ログイン</button>
						</div>
					</div>
				</div>
				<input type="hidden"name="formToken"value="${formToken}" />
			</div>
		</form>

	</body>
	<script type="text/javascript">
		// $(document).keydown(function(event) {
		// 	if (event.keyCode == 13) {
		// 		login();
		// 	}
		// });

		// function login() {
		// var username = $("#username").val();
		// var password = $("#password").val();
		// if(password){

		// } else {
		// 	$.ajax({
		// 		url: "${APP_PATH}/login",
		// 		dataType: "json",
		// 		async: false,
		// 		data: {
		// 			// "username": username,
		// 			"password": password
		// 		},
		// 		type: "POST",
		// 		beforeSend: function() {
		// 			//请求前的处理
		// 		},
		// 		success: function(res) {
		// 			$("#err_msg").hide();
		// 		},
		// 		complete: function() {

		// 		},
		// 		error: function(err) {
		// 			$("#err_msg").show();
		// 		}
		// 	});
		// }
		// }
	</script>
</html>
