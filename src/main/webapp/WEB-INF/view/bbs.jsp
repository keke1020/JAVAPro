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
			::-webkit-scrollbar {

				display: none;

			}

			table {
				table-layout: fixed;
			}

			th,
			td {
				padding: .25rem !important;
				word-break: break-all;
			}

			.fixed-table-container thead th .th-inner {
				white-space: normal;
			}

			#bbsTable thead {
				display: none !important;
			}

			.fixed-table-toolbar {
				display: none;
			}

			.bbs-box .inner {
				height: 1.6rem;
				line-height: 1.6rem;
				margin-top: 0.1rem;
			}

			.dropdown-toggle.btn {
				/* padding: .375rem .75rem;
				font-size: 1rem; */
				padding: 0 .30rem;
				font-size: 0.1rem;
			}

			.bootstrap-table .fixed-table-pagination>.pagination-detail,
			.bootstrap-table .fixed-table-pagination>.pagination {
				margin-top: 0.1rem;
				margin-bottom: 0.1rem;
			}
		</style>
	</head>

	<body>
		<div style="width: 80%; font-size: 12px;">
			<div class="table-responsive bbs-box">
				<div style="border: 1px solid #dee2e6; height: 2rem;" class="my-1">
					<!-- <div style="width: 10%; float: left;">BBS：</div> -->
					<div class="row" style="line-height: 2rem;">
						<div class="col-md-1" style="text-align: center; line-height: 1.8rem; padding-right: 0;">BBS：</div>
						<div class="col-md-11">
							<input type="text" class="form-control inner" id="message" maxlength="40" style="float: left; width: 300px; font-size: 12px;">
							<input type="button" class="btn btn-light inner mx-1 sent" style="float: left; font-size: 12px;padding: 0; width: 60px;"
							 value="送信"></input>
							<div style="line-height: 1.8rem;"> ※40文字まで</div>
						</div>
					</div>
				</div>
				<div id="toolbar"></div>
				<table id="bbsTable" class="table table-hover"></table>
			</div>
		</div>

	</body>
	<script type="text/javascript" src="${APP_PATH}/static/js/jquery-3.2.1.min.js"></script>
	<script type="text/javascript">
		$(function() {
			// $.ajax({
			// 	type: "POST",
			// 	url: "${APP_PATH}/getBBS",
			// 	contentType: "application/json;charset=utf-8",
			// 	dataType: "json",
			// 	async: false,
			// 	data: '',
			// 	jsonp: 'callback',
			// 	beforeSend: function() {
			// 		//请求前的处理
			// 	},
			// 	success: function(res) {
			// 		console.log(res);
			// 		var container = document.getElementById('bbsTable')
			// 		var headerArray = ['ユーザー', 'メッセージ'];
			// 	},
			// 	complete: function() {
			// 	},
			// 	error: function(err) {
			// 		console.log(err);
			// 	}
			// });

			function addFunctionAlty(value, row, index) {
				return [
					row.state + value + "<a class='del_' href='#'>[del]</a>"
				].join('');
			}

			window.operateEvents = {
				'click .del_': function(e, value, row, index) {
					$.ajax({
						url: "${APP_PATH}/deleteBBS",
						dataType: "text",
						async: false,
						data: {
							"ID": row.id
						},
						type: "POST",
						beforeSend: function() {
						},
						success: function(data) {
							console.log(data);
							var opt = {
								url: "${APP_PATH}/getBBS",
								silent: true,
								query: {}
							};
							$("#bbsTable").bootstrapTable('refresh', opt);
						},
						complete: function() {
						},
						error: function(XMLHttpResponse, textStatus, errorThrown) {
							console.log("1 异步调用返回失败,XMLHttpResponse.readyState:" + XMLHttpResponse.readyState);
							console.log("2 异步调用返回失败,XMLHttpResponse.status:" + XMLHttpResponse.status);
							console.log("3 异步调用返回失败,textStatus:" + textStatus);
							console.log("4 异步调用返回失败,errorThrown:" + errorThrown);
						}
					});
				}
			};

			$('#bbsTable').bootstrapTable('destroy').bootstrapTable({
				url: "${APP_PATH}/getBBS",
				/*data : data,*/
				toolbar: '#toolbar', //工具按钮用哪个容器
				method: 'post', //请求方式
				striped: false, //是否显示行间隔色
				cache: false, //是否使用缓存
				toolbarAlign: "left", //工具栏对齐方式
				sidePagination: "client", //分页方式：client客户端分页，server服务端分页
				uniqueId: "ＩＤ",
				pageNumber: 1, //初始化加载第一页
				pageSize: 5, //每页的记录行数
				pageList: [0, 5, 10, 15, 20], //可供选择的每页的行数
				pagination: true, // 是否分页
				showRefresh: false, //是否显示刷新按钮
				clickToSelect: false, //是否启用点击选中行
				//            height: 500,                        //行高
				cardView: false, //是否显示详细视图
				detailView: false, //是否显示父子表
				queryParamsType: '', //设置请求参数格式
				queryParams: function queryParams(params) { //设自定义查询参数
					/*请求远程数据时，您可以通过修改queryParams来发送其他参数。
					如果queryParamsType = 'limit'，params对象包含：limit，offset，search，sort，order。
					否则，它包含：pageSize，pageNumber，searchText，sortName，sortOrder。
					返回false停止请求。
					默认： function(params) { return params }*/
					return params;
				},
				columns: [{
						field: "username",
						title: "ユーザー",
						align: "left",
						valign: "middle",
						width: "5%"
					},
					{
						field: 'update',
						title: '更新時間',
						align: "left",
						valign: 'middle',
						width: "20%"  
					},
					{
						field: 'message',
						title: 'メッセージ',
						align: 'left',
						valign: 'middle',
						width: "75%",
						events: operateEvents, //给按钮注册事件
						formatter: addFunctionAlty //表格中增加按钮 
					}
				]
			});

			$(".sent").click(function() {
				$.ajax({
					url: "${APP_PATH}/insertBBS",
					dataType: "text",
					async: false,
					data: {
						// "username": username,
						"message": $("#message").val()
					},
					type: "POST",
					beforeSend: function() {
						//请求前的处理
					},
					success: function(data) {
						console.log(data);
						var opt = {
							url: "${APP_PATH}/getBBS",
							silent: true,
							query: {}
						};
						$("#bbsTable").bootstrapTable('refresh', opt);
					},
					complete: function() {

					},
					error: function(XMLHttpResponse, textStatus, errorThrown) {
						console.log("1 异步调用返回失败,XMLHttpResponse.readyState:" + XMLHttpResponse.readyState);
						console.log("2 异步调用返回失败,XMLHttpResponse.status:" + XMLHttpResponse.status);
						console.log("3 异步调用返回失败,textStatus:" + textStatus);
						console.log("4 异步调用返回失败,errorThrown:" + errorThrown);
					}
				});
			})

		});
	</script>
</html>
