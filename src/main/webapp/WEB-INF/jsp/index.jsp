<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>${ctx }</title>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<script type="text/javascript" src="${ctx }/js/jquery-1.7.2.js"></script>
	<script type="text/javascript">
		$(function(){
			$("#btn").click(function(){
				$.ajax({url:"${ctx}/search/searchResult", type:"POST", data:{"text":$("#t").val()}, success:function (data) {
					$("#result").text(data);
				}});
			});
			$("#near_btn").click(function(){
				$.ajax({url:"${ctx}/path/nearby/", type:"POST", data:$("#path_div").serialize(), success:function (data) {
					$("#result").text(data);
				}});
			});
			$("#btnPath").click(function(){
				$.ajax({url:"${ctx}/path/pathSetting", type:"POST", data:{"id":$("#user_id").val(), "isClean":$("#is_clean").val()}, success:function (data) {
					$("#result").text(data);
				}});
			});
			$("#statusBtn").click(function(){
				$.ajax({url:"${ctx}/status/updateStatus", type:"POST", data:{"userId":$("#status_user_id").val(), "content":$("#status_content").val()}, success:function (data) {
					$("#result").text(data);
				}});
			});
			$("#removeNewsBtn").click(function(){
				$.ajax({url:"${ctx}/news/destory", type:"POST", data:{"id":$("#news_id").val(), "type":$("#sub_type").val()}, success:function (data) {
					$("#result").text(data);
				}});
			});
			
		});
	</script>
  </head>
  
  <body>
  	<input type="text" name="text" id="t" >
  	<input type="button" id="btn" value="搜素音乐"><br>
  	<br><br>地理信息
  	<form id="path_div">
  	用户ID<input name="id" type="text">纬度,经度<input name="loc" type="text">&nbsp;公里数<input name="mile" type="text">&nbsp;省份<input name="discoveryProvince" type="text">&nbsp;城市<input name="discoveryCity" type="text"><input type="button" id="near_btn" value="搜索附近的人"><br>
  	</form>
	用户ID<input type="text" name="text" id="user_id" >
	是否清除<input type="text" name="text" id="is_clean" >
  	<input type="button" id="btnPath" value="设置地理位置是否清除(true清除|false可见)"><br>
  	<br><br>状态<br>
  	用户ID<input type="text" name="text" id="status_user_id" >内容<input id="status_content" name="content" type="text"><input id="statusBtn" type="button" value="发布一条状态"><br>
  	
  	<br><br>新鲜事<br>
  	新鲜事ID<input type="text" id="news_id">
  	类别<input type="text" id="sub_type">
  	<input id="removeNewsBtn" type="button" value="删除一条新鲜事">
  	
  	<div id="result"></div>
  </body>
</html>
