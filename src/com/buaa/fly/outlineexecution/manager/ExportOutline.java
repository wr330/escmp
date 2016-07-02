package com.buaa.fly.outlineexecution.manager;



import java.io.File;
import java.util.Collection;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import com.bstek.bdf2.core.context.ContextHolder;

import com.buaa.fly.combineVehicle.dao.CombineVehicleDao;
import com.buaa.fly.domain.Outlineexecution;
import com.buaa.fly.domain.Tasklist;
import com.buaa.fly.domain.CombineVehicle;
import com.common.FileHelper;
import com.common.WriteDocument;
@Repository("exportOutline")
public class ExportOutline {
	@Resource
	private CombineVehicleDao combineVehicleDao;
	public void generateTable(Outlineexecution outlineexecution ,List<Tasklist> tasklist){
		WriteDocument wd = new WriteDocument();
		wd.setVisible(false);
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
public void generateOutlineexecution(Collection<Outlineexecution> Outlineexecution) throws Exception{
	List<Outlineexecution> tasklist = (List<Outlineexecution>)Outlineexecution;
	WriteDocument wd = new WriteDocument();
	wd.setVisible(false);
	wd.createNewDocument();
	wd.insertDirectory("试飞大纲执行情况");
	wd.setAlignment(1);
	String[] str=new String[11];
	str[0]="机型";
	str[1]="科目序号";
	str[2]="试飞科目";
	str[3]="飞机构形";
	str[4]="试飞状态";
	str[5]="发动机状态";
	str[6]="试飞方法";
	str[7]="大纲架次";
	str[8]="结合架次";
	str[9]="结合科目";
	str[10]="完成状态";
	wd.createTable(str.length, 1);
	wd.addFirstTableRow(1,str);
	wd.setAlignment(1);
	int num = 1;
	for(int i=0;i<tasklist.size();i++){
		Collection<CombineVehicle> c = combineVehicleDao.queryCVbyOutline(tasklist.get(i).getOid());
		List<CombineVehicle> cv = (List<CombineVehicle>)c;
		if(null != cv && cv.size() > 0){
			str[0]=tasklist.get(i).getAircrafttype();
			str[1]=tasklist.get(i).getProject().getSubjectno();
			str[2]=tasklist.get(i).getProject().getName();
			str[3]=tasklist.get(i).getAircraftStruct();
			str[4]=tasklist.get(i).getTestStatus();
			str[5]=tasklist.get(i).getEngineState();
			str[6]=tasklist.get(i).getTestMethod();
			str[7]=String.valueOf(tasklist.get(i).getOutlineFlights());
			str[8]=String.valueOf(cv.get(0).getCombineNumber());
			str[9]=cv.get(0).getCombineSubject();
			str[10]=tasklist.get(i).getCompletestate();
			wd.addRow(1);
			num++;
			wd.addContentRow(1, str, num);
			for(int j = 1; j <cv.size(); j++){
				str[0]=null;
				str[1]=null;
				str[2]=null;
				str[3]=null;
				str[4]=null;
				str[5]=null;
				str[6]=null;
				str[7]=null;
				str[8]=String.valueOf(cv.get(j).getCombineNumber());
				str[9]=cv.get(j).getCombineSubject();
				str[10]=null;
				wd.addRow(1);
				num++;
				wd.addContentRow(1, str, num);
				}
			if(c.size()>1){
			wd.mergeCell(1, num, 1, num-c.size()+1, 1);
			wd.mergeCell(1, num, 2, num-c.size()+1, 2);
			wd.mergeCell(1, num, 3, num-c.size()+1, 3);
			wd.mergeCell(1, num, 4, num-c.size()+1, 4);
			wd.mergeCell(1, num, 5, num-c.size()+1, 5);
			wd.mergeCell(1, num, 6, num-c.size()+1, 6);
			wd.mergeCell(1, num, 7, num-c.size()+1, 7);
			wd.mergeCell(1, num, 8, num-c.size()+1, 8);
			wd.mergeCell(1, num, 11, num-c.size()+1, 11);
			}
		}else{
			str[0]=tasklist.get(i).getAircrafttype();
			str[1]=tasklist.get(i).getProject().getSubjectno();
			str[2]=tasklist.get(i).getProject().getName();
			str[3]=tasklist.get(i).getAircraftStruct();
			str[4]=tasklist.get(i).getTestStatus();
			str[5]=tasklist.get(i).getEngineState();
			str[6]=tasklist.get(i).getTestMethod();
			str[7]=String.valueOf(tasklist.get(i).getOutlineFlights());
			str[8]=null;
			str[9]=null;
			str[10]=tasklist.get(i).getCompletestate();
			wd.addRow(1);
			num++;
			wd.addContentRow(1, str, i+2);
		}
		
	}
	String path = "\\Fly_Outlineexecution\\"+tasklist.get(0).getOid();
	String filePath = ContextHolder.getRequest().getRealPath("/")
			+ "upload"+path;
	String fileName = "试飞大纲";
	File folder=new File(filePath);
	if(!folder.exists()){
		folder.mkdirs();
	}
	/*wd.saveAs(filePath + "//" + fileName);
	wd.closeWord();*/
	System.out.println("生成的试飞大纲路径在：" + filePath);
	wd.saveWordFile(filePath + "//" + fileName);
}
}
