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
<link href="css/bootstrap.min.css" rel="stylesheet">
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
			选择刀具页 <span>请选择刀具</span>
		</h1>
		<div class="htmleaf-links">
			<a class="htmleaf-icon icon-htmleaf-home-outline" href="index.jsp"></a>
			<a class="htmleaf-icon icon-htmleaf-arrow-forward-outline"
				href="index.jsp" target="_blank"></a>
		</div>
		</header>
		<div class="container">
			<h3>您的选择类型和组如下:</h3>
			<ol class="breadcrumb">
				<li class="active">【${param.choosed }】${param.name }</li>
			</ol>
			<div class="row">
				<div class="col-sm-12"
					style="box-shadow: inset 1px -1px 10px #444, inset -1px 1px 10px #444;">
					<h2>已找到的刀具</h2>
					<form id="choosed" action="findResult" class="form-horizontal" role="form">
						<input type="hidden" name="choosed" value="${param.choosed }" />
						<input type="hidden" name="name" value="${param.name }" />
						<table class="table table-hover">
							<thead>
								<tr>
									 <th></th>
							         <th>整套刀具 ID</th>
							         <th>刀具说明</th>
							         <th>刀具类别</th>
							         <th>刀具组</th>
							         <th>重量</th>
							         <th>Dc</th>	
							         <th>Xs</th>	
								</tr>
							</thead>
							<tbody>
								<c:forEach var="t" items="${items}">
									<tr>
										<td><input type="radio" name="item"
											id="optionsRadios1" value="${t.TOOLID}|${t.TOOLCLASSID}|${t.TOOLGROUPID}|${t.COUNTCUTS}"></td>
										<td><c:out value="${t.TOOLID}"/></td>
										<td><c:out value="${t.NAME}"/></td>
										<td><c:out value="${t.TOOLCLASSNAME}"/></td>
										<td><c:out value="${t.TOOLGROUPNAME}"/></td>
										<td><c:out value="${t.WEIGHT}"/></td>
										<td><c:out value="${t.VALVALNUM3}"/></td>
										<td><c:out value="${t.VALVALNUM1}"/></td>
									</tr>
								</c:forEach>
							</tbody>
						</table>
					</form>
						<center>
							<button class="btn btn-primary" onclick="choose();">查看</button>
							<a href="goItemIndex" class="btn btn-default">取消</a>
						</center>
				</div>
			</div>
			<br /> <br />
		</div>


	</div>

	<script src="js/jquery-2.1.1.js"></script>
	<script src="js/jquery-2.1.1.min.js"></script>
	<script src="js/bootstrap-treeview.js"></script>
	<script type="text/javascript">
		function choose() {
			//$(#optionsRadios1).attr("checked",'checked');
			var choose = $("input[type='radio']:checked").val();
			if(choose == null){
				alert('您还没有选择！');
			}else{
				$("#choosed").submit();
			}
		}
	</script>
</body>
</html>