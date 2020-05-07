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
		<style type="text/css">
			body {
				padding: 10px;
			}

			.c_white {
				color: #fff !important;
			}
		</style>
	</head>

	<body>
		<nav class="navbar navbar-expand-lg navbar-dark bg-primary">
			<a class="navbar-brand" href="javascript:void(0)" id="index" style="font-size:28px;">Import List</a>
			<div class="collapse navbar-collapse">
				<ul class="navbar-nav mr-auto">
					<li class="nav-item dropdown">
						<a class="nav-link dropdown-toggle c_white" href="javascript:void(0)" role="button" data-toggle="dropdown" aria-haspopup="true"
						 aria-expanded="false">発注リスト</a>
						<div class="dropdown-menu" aria-labelledby="navbarDropdown">
							<a class="dropdown-item" href="javascript:void(0)">フルデータリスト</a>
							<a class="dropdown-item" href="javascript:void(0)">印刷用画面</a>
							<a class="dropdown-item" href="javascript:void(0)">画像管理</a>
							<div class="dropdown-divider"></div>
							<a class="dropdown-item" href="javascript:void(0)">発注管理設定</a>
						</div>
					</li>
					<li class="nav-item dropdown">
						<a class="nav-link dropdown-toggle c_white" href="javascript:void(0)" role="button" data-toggle="dropdown" aria-haspopup="true"
						 aria-expanded="false">倉庫管理</a>
						<div class="dropdown-menu" aria-labelledby="navbarDropdown">
							<a class="dropdown-item" href="javascript:void(0)">フルモード（メイン）</a>
							<a class="dropdown-item" href="javascript:void(0)">棚登録</a>
							<a class="dropdown-item" href="javascript:void(0)">新宮在庫</a>
							<a class="dropdown-item" href="javascript:void(0)">在庫変更</a>
							<div class="dropdown-divider"></div>
							<a class="dropdown-item" href="javascript:void(0)">特殊（管理用）</a>
						</div>
					</li>
					<li class="nav-item dropdown">
						<a class="nav-link dropdown-toggle c_white" href="javascript:void(0)" role="button" data-toggle="dropdown" aria-haspopup="true"
						 aria-expanded="false">商品管理</a>
						<div class="dropdown-menu" aria-labelledby="navbarDropdown">
							<a class="dropdown-item" href="javascript:void(0)">ファイルサーバー</a>
							<div class="dropdown-divider"></div>
							<a class="dropdown-item" href="javascript:void(0)">商品用TODO</a>
							<a class="dropdown-item" href="javascript:void(0)">価格管理表</a>
							<a class="dropdown-item" href="javascript:void(0)">在庫増減表</a>
							<div class="dropdown-divider"></div>
							<a class="dropdown-item" href="javascript:void(0)">FTPタイマー</a>
							<a class="dropdown-item" href="javascript:void(0)">各種マニュアル</a>
						</div>
					</li>
					<li class="nav-item dropdown">
						<a class="nav-link dropdown-toggle c_white" href="javascript:void(0)" role="button" data-toggle="dropdown" aria-haspopup="true"
						 aria-expanded="false">受注管理</a>
						<div class="dropdown-menu" aria-labelledby="navbarDropdown">
							<a class="dropdown-item" href="javascript:void(0)">物流倉庫</a>
							<div class="dropdown-divider"></div>
							<a class="dropdown-item" href="javascript:void(0)">問題対応LIST</a>
							<div class="dropdown-divider"></div>
							<a class="dropdown-item" href="javascript:void(0)">各種マニュアル</a>
						</div>
					</li>
					<li class="nav-item dropdown">
						<a class="nav-link dropdown-toggle c_white" href="javascript:void(0)" role="button" data-toggle="dropdown" aria-haspopup="true"
						 aria-expanded="false">その他</a>
						<div class="dropdown-menu" aria-labelledby="navbarDropdown">
							<a class="dropdown-item" href="javascript:void(0)" id="test">TEST</a>
						</div>
					</li>
				</ul>
				<input class="form-control form-control-sm mr-sm-2" type="search" value="${usr}" readonly="readonly" style="width: auto;">
				<a　href="javascript:void(0)" class="navbar-brand" id="logout" style="font-size: 1rem; cursor: pointer;">ログアウト</a>
			</div>
		</nav>
	</body>
	<script type="text/javascript" src="${APP_PATH}/static/js/jquery-3.2.1.min.js"></script>
	<script type="text/javascript">
		$(function() {
			$("#logout").click(function() {
				$.ajax({
					url: "${APP_PATH}/logout",
					dataType: "json",
					async: false,
					data: {},
					type: "POST",
					beforeSend: function() {
						//请求前的处理
					},
					success: function(res) {
						console.log(res);
						window.location.href = "${APP_PATH}/";
					},
					complete: function() {
			
					},
					error: function(err) {
			
					}
				});
			})
			
			$("#index").click(function() {
				toPage(0);
			})
			
			$("#test").click(function() {
				toPage(999);
			})
			
			function toPage(page_index){
				$.ajax({
					url: "${APP_PATH}/toPage",
					dataType: "json",
					async: false,
					data: {
						//999: test
						"page_index": page_index
					},
					type: "POST",
					beforeSend: function() {
						//请求前的处理
					},
					success: function(res) {
						console.log(res);
						if(res.toPage == "index"){
							window.location.href = "${APP_PATH}/";
							return;
						} else if(res.toPage == "test"){
							window.location.href = "${APP_PATH}/test";
							return;
						}
						
						
						
					},
					complete: function() {
							
					},
					error: function(err) {
							
					}
				});
			}
		})
	</script>
</html>
