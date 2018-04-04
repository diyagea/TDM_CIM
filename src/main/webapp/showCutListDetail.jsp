<%@ page language="java" import="java.util.*,com.model.Param"
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
			刀单详细页 <span>请选择进给参数</span>
		</h1>
		<div class="htmleaf-links">
			<a class="htmleaf-icon icon-htmleaf-home-outline" href="index.jsp"></a>
			<a class="htmleaf-icon icon-htmleaf-arrow-forward-outline"
				href="index.jsp" target="_blank"></a>
		</div>
		</header>
		<div class="container">
		
			<ul id="myTab" class="nav nav-tabs">
			   <li class="active"><a href="#detail" data-toggle="tab">
			      主数据</a>
			   </li>
			   <li><a href="#cutList" data-toggle="tab">刀具列表</a></li>
			   
			</ul>
			<div id="myTabContent" class="tab-content">
			   <div class="tab-pane fade in active" id="detail">
			      <p><table class="table table-hover">
							<thead>
								<tr>
									 <th>参数</th>
							         <th>值</th>
								</tr>
							</thead>
							<tbody>
								<tr>
									<td>刀单ID</td>
									<td><c:out value="${listDetail.LISTID}"/></td>
								</tr>
								<tr>
									<td>NC程序</td>
									<td><c:out value="${listDetail.NCPROGRAM}"/></td>
								</tr>
								<tr>
									<td>工件说明</td>
									<td><td><c:out value="${listDetail.PRATNAME}"/></td></td>
								</tr>
								<tr>
									<td>工作计划</td>
									<td><c:out value="${listDetail.JOBPLAN}"/></td>
								</tr>
								<tr>
									<td>工作进度</td>
									<td><c:out value="${listDetail.WORKPROCESS}"/></td>
								</tr>
								<tr>
									<td>机床</td>
									<td><c:out value="${listDetail.MACHINENAME}"/></td>
								</tr>
								<tr>
									<td>机床组</td>
									<td><c:out value="${listDetail.MACHINEGROUPNAME}"/></td>
								</tr>
									
							</tbody>
						</table></p>
			   </div>
			   <div class="tab-pane fade" id="cutList">
			      <p><table class="table table-hover">
							<thead>
								<tr>
									 <th>刀具ID</th>
							         <th>说明</th>
							         <th>说明2</th>
							         <th>T号码</th>
								</tr>
							</thead>
							
						</table></p>
						<%  List<Map<String, String>> cutList = (List<Map<String, String>>)session.getAttribute("cutList");
							Map<String, List<Param>> params = (Map<String, List<Param>>)session.getAttribute("params");
						
						%>
						<div class="panel-group" id="accordion">
						<% for(Map<String, String> cut : cutList) {
							List<Param> ps = params.get(cut.get("ID"));
						
						%>
									  <div class="panel panel-default <%=cut.get("ID") %>">
									    <div class="panel-heading ">
									      <h4 class="panel-title <%=cut.get("ID") %>">
										        <a data-toggle="collapse" data-parent="#accordion" 
										          href="#<%=cut.get("ID")%>">
														【<%=cut.get("ID") %>】
													         【<%=cut.get("NAME") %>】
													         【<%=cut.get("NAME2") %>】
													         【<%=cut.get("TOOLNUMBER")%>】
										        </a>
									      </h4>
									    </div>
									    <div id="<%=cut.get("ID")%>" class="panel-collapse collapse">
									      <div class="panel-body">
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
												<%for(Param p : ps){ %>
										      		<tr>
														<td><input type="radio" name="<%=cut.get("ID")%>"
															id="radioNO" value="<%=cut.get("ID")%>|<%=p.getNO() %>"></td>
														<td><%=p.getTECHNOCLASSNAME() %></td>
														<td><%=p.getTECHNOGROUPNAME() %></td>
														<td><%=p.getMATERIALNAME() %></td>
														<td><%=p.getMATERIALGROUPNAME() %>></td>
														<td><%=p.getREVOLUTIONS() %>></td>
														<td><%=p.getCUTSPEED() %></td>
														<td><%=p.getFEEDRATE() %></td>
														<td><%=p.getFEEDPTOOTH() %></td>
														<td><%=p.getFEEDPREV() %></td>
													</tr>
										      	<%} %>
											</tbody>
										</table>
									      	
									      </div>
									    </div>
									  </div>
						<%} %>
						</div>
						<center>
						<button id="saveForExcelBtn" type="submit" class="btn btn-primary" onclick="saveForExcel();">导出</button>
						<a href="showCutList" class="btn btn-default">取消</a>
						</center>
			   </div>
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
		
		function saveForExcel() {
			var i = 0;
			var count = "<%=cutList.size() %>";
			var paramStr = "";
			$('input[type="radio"]:checked').each(function(){ 
				paramStr += this.value + "="
				//$("#"+this.name).addClass("alert alert-success");
				$("."+this.name).css("background-color","#DEEFD8");
				$("."+this.name).removeClass("panel panel-default");
				$("."+this.name).addClass("panel panel-success");
				i++;
			}); 
			if(i<count){
				alert("请选择进给参数");
				return;
			}
			
		 	saveForExcelBtn.html("<i class=\"fa fa-spinner fa-spin mr5\"></i>正在导出数据...");
			saveForExcelBtn.attr("disabled", "disabled");
			$.ajax({
				type : "GET",
				data : {
					"paramStr" : paramStr
				},
				url : "downloadCutLIst",
				dataType : "text",
				success : function(msg) {
					if (msg == "-1") {
						alert("数据导出失败，请重试！");
					} else {
						alert("导出成功！");
						window.location.reload();
						//关闭浏览器
						window.opener = null;
						window.open("", "_self");
						window.close();
					}
					saveForExcelBtn.html("导出");
					saveForExcelBtn.removeAttr("disabled");
					$("input[type='radio']").removeAttr('checked');
				},
				error : function(xml, mess) {
					alert("数据导出失败，请重试！");
					saveForExcelBtn.html("导出");
					saveForExcelBtn.removeAttr("disabled");
				}
			}); 
		}
	</script>
</body>
</html>