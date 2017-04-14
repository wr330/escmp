package com.common;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.hibernate.Session;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.buaa.fly.domain.Sfstatistic;


public class ServletDownload extends HttpServlet {  
    private static final long serialVersionUID = 1L;  
         
    /** 
     * @see HttpServlet#HttpServlet() 
     */  
    public ServletDownload() {  
        super();  
        // TODO Auto-generate constructor stub  
    }  
    
    private HibernateBaseDao hibernateBaseDao;       
    
    /**
      *
      * 在Servlet中注入对象的步骤:
      * 1.取得ServletContext
      * 2.利用Spring的工具类WebApplicationContextUtils得到WebApplicationContext
      * 3.WebApplicationContext就是一个BeanFactory,其中就有一个getBean方法
      *
      */   
    @Override   
    public void init() throws ServletException {           
        super.init();                      
        ServletContext servletContext = this .getServletContext();
        WebApplicationContext ctx = WebApplicationContextUtils.getWebApplicationContext(servletContext);            
        hibernateBaseDao = (HibernateBaseDao)ctx.getBean("hibernateBaseDao");   
     }   
    /** 
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response) 
     */  
    @SuppressWarnings("unchecked")
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {  
        // TODO Auto-generated method stub  
          
        //获得请求文件参数 
        String filename = request.getParameter("filename"); 
        if(null==filename)
        	return;                   
        response.setContentType(getServletContext().getMimeType(filename));  //设置文件MIME类型  
        
        String name = filename.substring(filename.lastIndexOf('/')+1,filename.length());//获取文件名
        String path = "/"+filename.substring(0,filename.lastIndexOf('/')+1);//获取文件路径
        //设置Content-Disposition，读取目标文件，通过response将目标文件写到客户端  
        response.setHeader("Content-Disposition", "attachment;filename="+new String(name.getBytes("gb2312"),"ISO8859-1"));
        if(!FileHelper.existFile(path,name)){
        	
        }
        /*	这个方法暂时没有使用
         * if(!FileHelper.existFile(path,name)){//如果文件不存在，从数据库读取二进制流生成文件
        	String table = filename.substring(0,filename.indexOf('/'));//获取表名
        	String oid = filename.substring(filename.indexOf('/')+1,filename.lastIndexOf('/'));//获取主键Oid
        	if(table.equals("Fly_Sfstatistic")){
        		byte[] datablock = flySpecialDeal(path,oid,name);
			    FileHelper.createFile(path,name,datablock);
        	}
        	else{
        	    String sql = "select datablock from "+table+" where Oid='"+oid+"'";
        	    Session session = hibernateBaseDao.getSessionFactory().openSession();
        	    List<byte[]> result = session.createSQLQuery(sql).list();
        	    byte[] datablock = result.get(0);
			    FileHelper.createFile(path,name,datablock);
        	}
		}*/
        String fullFileName = getServletContext().getRealPath("/upload/" + filename);//获取目标文件的绝对路径    
        InputStream in = new FileInputStream(fullFileName);//读文件
        OutputStream out = response.getOutputStream();//写文件
        int b;  
        while((b=in.read())!= -1)  
        {  
            out.write(b);  
        }            
        in.close();  
        out.close();  
    }  
    //试飞信息模块特殊处理
	private byte[] flySpecialDeal(String path,String oid,String name) throws IOException{
    	String hql = "from "  + Sfstatistic.class.getName() + " where Oid='"+oid+"'";
    	Sfstatistic sf = (Sfstatistic) hibernateBaseDao.query(hql).get(0);
    	if(sf.getFilename1().equals(name))
    		return sf.getDatablock1();
    	else if(sf.getFilename2().equals(name))
    		return sf.getDatablock2();
    	else if(sf.getFilename3().equals(name))
    		return sf.getDatablock3();
    	else if(sf.getFilename4().equals(name))
    		return sf.getDatablock4();
    	return null;
    }
    /** 
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response) 
     */  
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {  
        // TODO Auto-generated method stub  
    }  
  
}  