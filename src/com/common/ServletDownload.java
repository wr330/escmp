package com.common;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ServletDownload extends HttpServlet {  
    private static final long serialVersionUID = 1L;  
         
    /** 
     * @see HttpServlet#HttpServlet() 
     */  
    public ServletDownload() {  
        super();  
        // TODO Auto-generated constructor stub  
    }  
  
    /** 
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response) 
     */  
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {  
        // TODO Auto-generated method stub  
          
        //获得请求文件名  
        String filename = request.getParameter("filename"); 
        if(null==filename)
        	return;
        //System.out.println(filename);  
          
        //设置文件MIME类型  
        response.setContentType(getServletContext().getMimeType(filename));  
        //设置Content-Disposition  
        String name = filename.substring(filename.lastIndexOf('/')+1,filename.length());
        response.setHeader("Content-Disposition", "attachment;filename="+new String(name.getBytes("gb2312"),"ISO8859-1"));  
        //读取目标文件，通过response将目标文件写到客户端  
        //获取目标文件的绝对路径  
        String fullFileName = getServletContext().getRealPath("/upload/" + filename);  
        //System.out.println(fullFileName);  
        //读取文件  
        InputStream in = new FileInputStream(fullFileName);
        //File file=new File(filename);
        //OutputStream out = new FileOutputStream(file);
        OutputStream out = response.getOutputStream();
//        File folder=new File(fullFileName);
//		if(!folder.exists()){
//			folder.mkdirs();
//		}
//		File file=new File(folder,fullFileName);
//		OutputStream out = new FileOutputStream(file);  
//        //写文件  
        int b;  
        while((b=in.read())!= -1)  
        {  
            out.write(b);  
        }  
          
        in.close();  
        out.close();  
    }  
  
    /** 
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response) 
     */  
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {  
        // TODO Auto-generated method stub  
    }  
  
}  