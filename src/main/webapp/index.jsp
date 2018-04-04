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
			首页 <span>TDM-SIM</span>
		</h1>
		<div class="htmleaf-links">
			<a class="htmleaf-icon icon-htmleaf-home-outline" href="index.jsp"
				></a> 
			<a	class="htmleaf-icon icon-htmleaf-arrow-forward-outline" href="index.jsp"
				target="_blank"></a>
		</div>
		</header>
		<div class="container">
		<center>
		   <div class="jumbotron">
		      <h1>欢迎登陆！</h1>
		      <p>选择你想做的！</p>
		      <a class="btn btn-primary btn-lg" role="button" href="goItemIndex">
			         导出单个刀具</a>
			  <a class="btn btn-primary btn-lg" role="button" href="showCutList">
			    TDM导出刀单</a>
			  <a class="btn btn-primary btn-lg" role="button" href="uploadForword">
			    TDM导入刀单</a>
		     
		   </div>
		   </center>
		</div>


	</div>

	<script src="js/jquery-2.1.1.min.js"></script>
</body>
</html>