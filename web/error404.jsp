<%@page import="com.bstek.newstree.common.NrsConst"%>
<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
 <% String resourcesPath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
				+ request.getContextPath() + "/"+NrsConst.RESOURCES_ROOT; %>
 <% String contextPath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
				+ request.getContextPath() + "/"; %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<HTML>
<HEAD><TITLE>系统提示</TITLE>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link href="<%=resourcesPath %>resource/style.css" rel="stylesheet" type="text/css" />
<script language="javascript" src="<%=resourcesPath %>resource/js/jquery.min.js"></script>
<script type="text/javascript">
jQuery(document).ready(function(){
	var height =$(window).innerHeight();
    $("#main .error_center").height(height);
});
</script>
</HEAD>
<BODY >
<div id="main" >	
<div class="error_center">
<div class="error_content">
<div>
<img class="error_img" alt="404" src="<%=resourcesPath %>resource/images/404.png">
</div>
<div style="width: 250px">
<p >你访问的页面不存在@@</p>
</div>
<div class="error_bottom" >
<a href="<%=contextPath %>view.home.d">返回首页</a>｜
<a href="javascript:history.back();">返回上一页</a>
</div>
</div>
</div>
</div>
	<!–[if IE 6]>
	<script type="text/javascript" src="<%=resourcesPath %>resource/js/DD_belatedPNG_0.0.8a-min.js"></script>
	<script>
	DD_belatedPNG.fix('.error_img,img');
	</script>
	<![endif]–>
</BODY>
</HTML>
