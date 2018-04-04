<%@ page language="java" import="java.util.*,com.model.Tool"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html lang="zh">
<head>
<meta charset="UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>tree</title>
<link rel="stylesheet" type="text/css" href="css/default.css">
<link href="css/bootstrap.min.css"	rel="stylesheet">
<style type="text/css">
.htmleaf-header {
	margin-bottom: 15px;
	font-family: "Segoe UI", "Lucida Grande", Helvetica, Arial,
		"Microsoft YaHei", FreeSans, Arimo, "Droid Sans",
		"wenquanyi micro hei", "Hiragino Sans GB", "Hiragino Sans GB W3",
		"FontAwesome", sans-serif;
}

.htmleaf-icon {
	color: #fff;
}
</style>
</head>
<body>
	<div class="htmleaf-container">
		<header class="htmleaf-header bgcolor-12">
		<h1>
			导出组合刀具 <span>请选择组和类别</span>
		</h1>
		<div class="htmleaf-links">
			<a class="htmleaf-icon icon-htmleaf-home-outline" href="index.jsp"
				></a> 
			<a	class="htmleaf-icon icon-htmleaf-arrow-forward-outline" href="index.jsp"
				target="_blank"></a>
		</div>
		</header>
		<div class="container">
			<!-- <h1>Item Tree View</h1> -->
			<br>

			<div class="row">

				<div class="col-sm-4">
					<h2>类别和组列表</h2>
					<div id="treeview6" class=""></div>
				</div>

				<c:if test="${choosed != null}">
					<div class="col-sm-8"
						style="box-shadow: inset 1px -1px 1px #422, inset -1px 1px 1px #422;">
						<h3>您的选择如下:</h3>
						<form class="form-horizontal" action="search" role="form">
							<input type="hidden" name="choosed" value="${param.choosed }" />
							<input type="hidden" name="name" value="${param.name }" />
							<input type="hidden" name="type" value="${param.type }" />
							<table class="table">
								<ol class="breadcrumb">
									<li class="active">【${param.choosed }】${param.name }</li>
								</ol>
								<thead>
									<tr>
										<th>参数名称</th>
										<th>参数值</th>
										<th>单位</th>
										<th>参数描述</th>
									</tr>
								</thead>
								<tbody>
									<tr>
										<td>Dc</td>
										<td><div class="col-sm-5">
												<input type="text" name="Dc" class="form-control; width:120px;" placeholder="input " />
											</div></td>
										<td>mm</td>
										<td>Cutting diameter</td>
									</tr>
									<tr>
										<td>Xs</td>
										<td><div class="col-sm-10">
												<input type="text" name="Xs" class="form-control; width:120px;" placeholder="input " />
											</div></td>
										<td>mm</td>
										<td>Gauge length</td>
									</tr>
									 <tr>
									    <td></td>
									    <td><div style=" height:25px; width:80px;"></div></td>
									    <td></td>
									    <td></td>
									  </tr>
								</tbody>
							</table>
							<center>
								<button type="submit" class="btn btn-primary">搜索</button>
								<a href="index.jsp" class="btn btn-default">取消</a>
							</center>
						</form>
					</div>
				</c:if>
			</div>

			<br /> <br />
		</div>


	</div>

	<script src="js/jquery-2.1.1.min.js"></script>
	<script src="js/bootstrap-treeview.js"></script>
	<script type="text/javascript">
		$(function() {

			var treeNodes = ${sessionScope.tree};

			$('#treeview6').treeview({
				levels : 1,
				color : "#428bca",
				enableLinks : true,
				showTags : true,
				data : treeNodes
			});

		});
	</script>
</body>
</html>