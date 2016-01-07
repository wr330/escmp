package com.bstek.dorado.uploader;

import java.io.File;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import org.springframework.web.context.ServletContextAware;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.util.WebUtils;

import com.bstek.dorado.annotation.Expose;
@Component("defaultUploadProcessor")
public class DefaultUploadProcessor implements UploadProcessor, ServletContextAware {

	private final static Logger logger = LoggerFactory
			.getLogger(DefaultUploadProcessor.class);

	private static String uploadDir;

	private File _uploadFolder;

	private ServletContext servletContext;

	public Object process(MultipartFile file, HttpServletRequest req,
			HttpServletResponse res) {

		String originalName = file.getOriginalFilename();//文件名
		String record = req.getParameter("record");
		String customFilePath = uploadDir + record;
		String parsedPath = parseUploadPath(customFilePath);
		File file1=new File(parsedPath);
		if(!file1.exists()){
			file1.mkdirs();
		}
		String destFilename = generateName(file1,
				originalName);
		File dest = new File(file1, destFilename);
		logger.info("file:{} be saved to {}", originalName,
				dest.getAbsolutePath());
		try {
			FileUtils.copyInputStreamToFile(file.getInputStream(), dest);
		} catch (IOException e) {
			throw new UploaderException(e);
		}
		
		return destFilename;

	}

	private String generateName(File folder, String originalName) {
		File file=new File(folder, originalName);
		if(file.exists()){
			file.delete();//改为替换原名文件
		}
		return originalName;
//		int i = 1, pointIdx = originalName.lastIndexOf('.');
//		String prefix, suffix, newName;
//		if (pointIdx == -1 || pointIdx == originalName.length()) {
//			prefix = originalName;
//			suffix = null;
//		} else {
//			prefix = originalName.substring(0, pointIdx);
//			suffix = originalName.substring(pointIdx + 1);
//		}
//
//		do {
//			if (suffix == null) {
//				newName = String.format("%s_%d", prefix, i++);
//			} else {
//				newName = String.format("%s_%d.%s", prefix, i++, suffix);
//			}
//
//		} while (new File(folder, newName).exists());
//
//		logger.info("file:{} renamed to {}", originalName, newName);
//
//		return newName;
	}
	@Expose
	//删除文件
	public void deleteFile(String record,String filename) {
		if(null == filename || filename.equals(""))
			return;
		String customFilePath = uploadDir + record;
		String parsedPath = parseUploadPath(customFilePath);
		File file=new File(parsedPath,filename);
		if(file.exists()){
			file.delete();
		}
	}
	
	public void afterPropertiesSet() throws Exception {
		Assert.notNull(uploadDir);
		String parsedPath = parseUploadPath(uploadDir);
		logger.info("UploadDir:{}", parsedPath);
		_uploadFolder = new File(parsedPath);
		if (!_uploadFolder.exists()) {
			_uploadFolder.mkdirs();
		}
	}

	public String getUploadDir() {
		return uploadDir;
	}

	/**
	 * 接受参数
	 * 
	 * ${webapp.home}/subdir
	 * 
	 * ${webapp.tmpdir}/subdir
	 * 
	 * ${env:java.io.tmpdir}/subdir
	 * 
	 * @param uploadDir
	 */
	public void setUploadDir(String uploadDir) {
		this.uploadDir = uploadDir;
	}

	private String parseUploadPath(String uploadDir) {
		Pattern p = Pattern.compile("^(\\{(.*)\\})");
		Matcher m = p.matcher(uploadDir);
		if (m.find()) {
			String var = m.group(2);
			StringBuffer dirBuf = new StringBuffer();
			if (var.equals("webapp.home")) {
				m.appendReplacement(dirBuf, this.servletContext
						.getRealPath("/").replaceAll("\\\\", "/"));
			} else if (var.equals("webapp.tmpdir")) {
				m.appendReplacement(dirBuf,
						WebUtils.getTempDir(this.servletContext)
								.getAbsolutePath().replaceAll("\\\\", "/"));
			} else if (var.startsWith("env:")) {
				m.appendReplacement(dirBuf, System
						.getProperty(var.substring(4)).replaceAll("\\\\", "/"));
			} else {
				m.appendReplacement(dirBuf, m.group(0).replaceAll("\\\\", "/"));
			}
			m.appendTail(dirBuf);
			return dirBuf.toString();
		} else {
			return uploadDir;
		}
	}

	public void setServletContext(ServletContext servletContext) {
		this.servletContext = servletContext;
	}

}
