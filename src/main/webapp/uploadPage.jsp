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
   <script src="js/jquery-2.1.1.min.js"></script>
   <script src="js/bootstrap.min.js"></script>
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
			刀单上传页 <span>请上传刀单文件</span>
		</h1>
		<div class="htmleaf-links">
			<a class="htmleaf-icon icon-htmleaf-home-outline" href="index.jsp"></a>
			<a class="htmleaf-icon icon-htmleaf-arrow-forward-outline"
				href="index.jsp" target="_blank"></a>
		</div>
		</header>
		<div class="container">
			<h3>选择单刀文件:</h3>
				<div class="row">
					<div class="col-sm-3"
						style="box-shadow: inset 1px -1px 10px #444, inset -1px 1px 10px #444;">
						<div>
						<h2> </h2>
						<form role="form" action="uploadCsv" enctype="multipart/form-data" method="post">
							<div class="form-group">
								<input type="file" id="inputfile" name="inputfile" onchange="chooseFile();">
								<p id="error" style="color:red" class="help-block"></p>
							</div>
							<div class="checkbox">
								<label>
								</label>
							</div>
							<center>
								<button type="submit" id="submit" class="btn btn-primary">提交</button>
							</center>
						</form>
						</div>
				</div>
			<br /> <br />
		</div>


	</div>

	<script src="js/jquery-2.1.1.js"></script>
	<script src="js/jquery-2.1.1.min.js"></script>
	<script src="js/bootstrap-treeview.js"></script>
	 <script type="text/javascript">
           function chooseFile() {
                var filepath = $("input[name='inputfile']").val();
                var extStart = filepath.lastIndexOf(".");
                var ext = filepath.substring(extStart, filepath.length).toUpperCase();
                if (ext != ".CSV" && ext != ".csv") {
                    //alert("只能上传Csv文件！");
                    $("#error").text(" 只能上传CSV文件！");
                    $("#submit").attr("disabled","disabled");
                    return false;
                } else { 
                	$("#submit").removeAttr("disabled");
                	$("#error").text("");
                }
                
                return true;
            } 
    </script>
</body>
</html>