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
   <script src="js/bootstrap.min.js"></script>
<link
	href="css/bootstrap.min.css"
	rel="stylesheet">
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
			刀单分析结果页 <span>刀单分析结果</span>
		</h1>
		<div class="htmleaf-links">
			<a class="htmleaf-icon icon-htmleaf-home-outline" href="index.jsp"></a>
			<a class="htmleaf-icon icon-htmleaf-arrow-forward-outline"
				href="index.jsp" target="_blank"></a>
		</div>
		</header>
		<div class="container">
			<h3>文件处理的结果为:</h3>
			<c:if test="${flag}">
			<div class="alert alert-success alert-dismissable">
			   <button type="button" class="close" data-dismiss="alert" 
			      aria-hidden="true">
			      &times;
			   </button>
			  上传成功，生成的刀具列表ID为：【${cutListID}】
			</div>
			</c:if>
			<c:if test="${!flag }">
				<div class="alert alert-danger alert-dismissable">
				   <button type="button" class="close" data-dismiss="alert" 
				      aria-hidden="true">
				      &times;
				   </button>
				   上传失败！请检查上传文件是否正确！
				</div>
			</c:if>
			<div class="row">
				<div class="col-sm-12"
					style="box-shadow: inset 1px -1px 10px #444, inset -1px 1px 10px #444;">
					<div>
						<table class="table table-hover">

							<thead>
								<tr>
							         <th>刀具ID</th>
							         <th>刀具名称</th>
							         <th>刀具类型</th>
							         <th>刀具组</th>
							         <th>刀具号码</th>
								</tr>
							</thead>
							<tbody>
								<c:forEach var="r" items="${upResults}">
									<c:if test="${r.isDone == 1}">
										<tr class="success">
											<td><c:out value="${r.TOOLID}"/></td>
											<td><c:out value="${r.TOOLNAME}"/></td>
											<td><c:out value="${r.TOOLCLASSNAME}"/></td>
											<td><c:out value="${r.TOOLGROUPNAME}"/></td>
											<td><c:out value="${r.TOOLNUMBER}"/></td>
										</tr>
									</c:if>
									<c:if test="${r.isDone == 0}">
										<tr class="danger">
											<td><c:out value="${r.TOOLID}"/></td>
											<td><c:out value="${r.TOOLNAME}"/></td>
											<td><c:out value="${r.TOOLCLASSNAME}"/></td>
											<td><c:out value="${r.TOOLGROUPNAME}"/></td>
											<td><c:out value="${r.TOOLNUMBER}"/></td>
										</tr>
									</c:if>
								</c:forEach>
							</tbody>
						</table>
						<center>
							<a href="uploadForword" class="btn btn-primary ">重新上传</a>
							<a href="index.jsp" class="btn btn-default ">返回首页</a>
						</center>
						</div>
					</div>
				</div>
			</div>
			<br /> <br />
		</div>


	<script src="js/jquery-2.1.1.js"></script>
	<script src="js/jquery-2.1.1.min.js"></script>
	<script src="js/bootstrap-treeview.js"></script>
</body>
</html>