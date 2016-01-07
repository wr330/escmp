package com.buaa.fly.outlineexecution.manager;

import java.io.File;
import java.util.List;

import com.bstek.bdf2.core.context.ContextHolder;
import com.buaa.fly.domain.Outlineexecution;
import com.buaa.fly.domain.Tasklist;
import com.buaa.fly.view.FileHelper;
import com.common.WriteDocument;

public class ExportOutline {
public static void generateTable(Outlineexecution outlineexecution ,List<Tasklist> tasklist){
	WriteDocument wd = new WriteDocument();
	wd.setVisible(true);
	wd.createNewDocument();
	wd.insertDirectory("试飞大纲执行情况");
	wd.setAlignment(1);
	String[] str=new String[11];
	str[0]="机型";
	str[1]="试飞科目";
	str[2]="飞机构形";
	str[3]="试飞状态";
	str[4]="发动机状态";
	str[5]="试飞方法";
	str[6]="大纲架次";
	str[7]="结合架次";
	str[8]="结合科目";
	str[9]="完成状态";
	str[10]="剩余情况";
	wd.createTable(str.length, 1);
	wd.addFirstTableRow(1,str);
	String[] str1=new String[5];
	str1[0]="任务单号";
	str1[1]="机型";
	str1[2]="试飞科目";
	str1[3]="任务单内容摘要";
	str1[4]="执行情况";
	wd.insertDirectory("包含以上大纲的试飞任务单");
	wd.setAlignment(1);
	if(tasklist.size() == 0)
		wd.createTable(str1.length, tasklist.size()+1);
	else
		wd.createTable(str1.length, tasklist.size());
	wd.addFirstTableRow(2,str1);
	//;
	str[0]=outlineexecution.getAircrafttype();
	str[1]=outlineexecution.getSubject();
	str[2]=outlineexecution.getAircraftStruct();
	str[3]=outlineexecution.getTestStatus();
	str[4]=outlineexecution.getEngineState();
	str[5]=outlineexecution.getTestMethod();
	str[6]=String.valueOf(outlineexecution.getOutlineFlights());
	str[7]=String.valueOf(outlineexecution.getCombineFlights());
	str[8]=outlineexecution.getCombineSubject();
	str[9]=outlineexecution.getCompletestate();
	str[10]=outlineexecution.getRemainstate();
	wd.addContentRow(1, str, 2);
	for(int i=0;i<tasklist.size();i++){
		str1[0]=tasklist.get(i).getTasknumber();
		str1[1]=tasklist.get(i).getAircrafttype();
		str1[2]=tasklist.get(i).getSubject();
		str1[3]=tasklist.get(i).getTaskcontent();
		str1[4]=tasklist.get(i).getTaskexecution();
		wd.addContentRow(2, str1, i+2);
	}
	String path = "/Fly_OutlineExecution/"+outlineexecution.getSubject()+"大纲.doc";
	String filePath = ContextHolder.getRequest().getRealPath("/")
			+ "upload"+path;

	File folder=new File(filePath);
	if(!folder.exists()){
		folder.mkdirs();
	}
	
	//String path = "/Fly_OutlineExecution/"+ outlineexecution.getSubject()+"大纲.doc";
	//String path =FileHelper.createFile("/Fly_OutlineExecution")+"/"+outlineexecution.getSubject()+"大纲.doc";
	//wd.saveAs(filePath);
	//System.out.println(path);
	//wd.closeDocument();
	//wd.closeWord();
}
}
