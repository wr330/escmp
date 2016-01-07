//package com.bstek.web.controller;
//
//import java.io.File;
//import java.io.IOException;
//import java.io.InputStream;
//import javax.servlet.ServletOutputStream;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import org.springframework.context.ResourceLoaderAware;
//import org.springframework.core.io.Resource;
//import org.springframework.core.io.ResourceLoader;
//import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//import com.bstek.newstree.common.NrsConst;
//@Controller
//public class ResourceController implements ResourceLoaderAware {
//   private ResourceLoader resourceLoader;
//	@Override
//	public void setResourceLoader(ResourceLoader resourceLoader) {
//		this.resourceLoader = resourceLoader;
//	}
//	@RequestMapping("/file")
//	public void handlerFile(@RequestParam(required=false,value="path") String filePath,
//		HttpServletRequest request, HttpServletResponse response)throws Exception {
//		String path = request.getContextPath();
//		String resourcesPath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
//				+ path + "/" + NrsConst.RESOURCES_ROOT;
//		String urlPath = "url:"+resourcesPath+filePath;
//		Resource resource =  resourceLoader.getResource(urlPath);
////		File file = resource.getFile();
//		InputStream inputStream = null;
//		ServletOutputStream outputStream = null;
//		try {
//	    inputStream = resource.getInputStream();
//		outputStream =  response.getOutputStream();
//		byte[] bytes = new byte[1*1024];
//		while(inputStream.read(bytes) != -1){
//		outputStream.write(bytes);
//		outputStream.flush();
//		bytes = new byte[1*1024];
//		}
//		inputStream.close();
//		outputStream.close();
//		} catch (IOException e) {
//			throw e;
//		}finally{
//		
//		}
//	}
//
//}
