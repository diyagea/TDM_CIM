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
			刀具参数页 <span>请选择进给参数</span>
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
					<div>
					<h2>详细参数如下</h2>
					<!-- <form class="form-horizontal" role="form" action="download"> -->
						<table class="table table-hover">

							<thead>
								<tr>
							         <th>参数</th>
							         <th>参数值</th>
							         <th>单位</th>
							         <th>参数描述</th>
							         <th>参数位置</th>
								</tr>
							</thead>
							<tbody>
								<c:forEach var="r" items="${results}">
									<tr>
										<td><c:out value="${r.TOOLCLASSFIELDSNAME}"/></td>
										<td><c:out value="${r.VALVALNUM}"/></td>
										<td><c:out value="${r.UNITNAME}"/></td>
										<td><c:out value="${r.TOOLCLASSFIELDSNAME2}"/></td>
										<td><c:out value="${r.TOOLCLASSFIELDSPOS}"/></td>
									</tr>
								</c:forEach>
							</tbody>
						</table>
						<center>
							<button class="btn btn-primary" data-toggle="modal" 
							   data-target="#myModal">
							   选择进给参数
							</button>
							<a href="goItemIndex" class="btn btn-default ">取消</a>
							<!-- 按钮触发模态框 -->
						</center>
						<!-- 模态框（Modal） -->
						<div class="modal fade" id="myModal" tabindex="-1" role="dialog" 
						   aria-labelledby="myModalLabel" aria-hidden="true">
						   <div class="modal-dialog" style="width:1500px;">
						      <div class="modal-content">
						         <div class="modal-header">
						            <button type="button" class="close" 
						               data-dismiss="modal" aria-hidden="true">
						                  &times;
						            </button>
						            <h4 class="modal-title" id="myModalLabel">
						               	选择进给参数
						            </h4>
						         </div>
						         <div class="modal-body">
				            		<table class="table table-hover">
										<thead>
											<tr>
												 <th></th>
										         <th>技术级别</th>
										         <th>技术组</th>
										         <th>材质</th>
										         <th>材质组</th>
										         <th>转速n</th>
										         <th>切削速度vc</th>	
										         <th>进给率vf</th>	
										         <th>每齿进给fz</th>	
										         <th>每一旋转进给fn</th>	
											</tr>
										</thead>
										<tbody>
											<c:forEach var="t" items="${params}">
												<tr>
													<td><input type="radio" name="NO"
														id="radioNO" value="${t.NO}"></td>
													<td><c:out value="${t.TECHNOCLASSNAME}"/></td>
													<td><c:out value="${t.TECHNOGROUPNAME}"/></td>
													<td><c:out value="${t.MATERIALNAME}"/></td>
													<td><c:out value="${t.MATERIALGROUPNAME}"/></td>
													<td><c:out value="${t.REVOLUTIONS}"/></td>
													<td><c:out value="${t.CUTSPEED}"/></td>
													<td><c:out value="${t.FEEDRATE}"/></td>
													<td><c:out value="${t.FEEDPTOOTH}"/></td>
													<td><c:out value="${t.FEEDPREV}"/></td>
												</tr>
											</c:forEach>
										</tbody>
									</table>
						         </div>
						         <div class="modal-footer">
						            <button type="button" class="btn btn-default"  id="close"
						               data-dismiss="modal">关闭
						            </button>
						            <button type="submit" class="btn btn-primary" onclick="saveForExcel();">
						               		确认
						            </button>
						         </div>
						      </div><!-- /.modal-content -->
							</div><!-- /.modal -->
						</div>
					<!-- </form> -->
					</div>
					<center>
							
					</center>
				</div>
			</div>
			<br /> <br />
		</div>


	</div>

	<script src="js/jquery-2.1.1.js"></script>
	<script src="js/jquery-2.1.1.min.js"></script>
	<script src="js/bootstrap-treeview.js"></script>
	<script>
	var saveForExcelBtn = $("#saveForExcelBtn");
	function saveForExcel(){
		var NO = $("input[type='radio']:checked").val();
		if(NO==null){
			alert("请选择进给参数！");
			return;
		}
		$("#close").click();
		saveForExcelBtn.html("<i class=\"fa fa-spinner fa-spin mr5\"></i>正在导出数据...");
		saveForExcelBtn.attr("disabled","disabled");
		$.ajax({
			type: "GET",
			data: {"NO": NO},
			url: "download",
			dataType: "text",
			success: function(msg){
				if(msg == "-1"){
					alert("数据导出失败，请重试！");
				}else{
					//window.location.href = "/downLoadFiles?fileAddress="+msg;
					alert("导出成功！");
					//关闭浏览器
					window.opener=null;
					window.open("","_self");    
					window.close();
				}
				saveForExcelBtn.html("导出");
				saveForExcelBtn.removeAttr("disabled");
				$("input[type='radio']").removeAttr('checked');
			},
			error: function(xml, mess){
				alert("数据导出失败，请重试！");
				saveForExcelBtn.html("导出");
				saveForExcelBtn.removeAttr("disabled");
			}
		});
	}
	
	</script>
</body>
</html>