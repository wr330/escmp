<%@page import="com.bstek.dorado.core.Configure"%>
<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<script language="javascript" type="text/javascript">
var url;
function addFile(fileURL){
	var TANGER_OCX_OBJ;
	TANGER_OCX_OBJ=document.all("TANGER_OCX");
	fileURL=encodeURI(fileURL);
	url=fileURL;
	TANGER_OCX_OBJ.openFromURL(fileURL,false);
	//TANGER_OCX_OBJ.Menubar=false;
	//alert(TANGER_OCX_OBJ.openFromURL);
	TANGER_OCX_OBJ.Toolbars=false;
	//alert(TANGER_OCX_OBJ.name);
	//TANGER_OCX_OBJ.SaveToURL("a.jsp",TANGER_OCX_OBJ.name)
}
function linktourl(){
	var TANGER_OCX_OBJ;
	TANGER_OCX_OBJ=document.all("TANGER_OCX");
	
	TANGER_OCX_OBJ.SaveToURL("b.jsp",url,"fileType=Word.Document",0);
	//alert(1);
}
</script>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>Welcome</title>
</head>
<body leftmargin="0" marginheight="0" marginwidth="0" >
<form method ="post" enctype="mutipart/form-data" action="b.jsp">
 <input type="button" value="提交"  onclick=linktourl()>
		<object  id="TANGER_OCX" classid="clsid:C9BC4DFF-4248-4a3c-8A49-63A7D317F404" codebase="OfficeControl.cab#Version=5,0,0,2" width="100%" height="100%">
         <param name="BorderStyle" value="1">
         <param name="TitlebarColor" value="42768">
         <param name="TitlebarTextColor" value="0">	 
         <param name="Caption" value="欢迎使用！">
         <param name="ProductCaption" value="中航成飞">
		 <param name="ProductKey" value="CC0076043188586D37A970BC2C2385A3DFF8C015">
       </object>
      
       </form>
<script language="javascript" type="text/javascript">
addFile(<%=request.getParameter("url")%>);
</script>
</body>
</html>