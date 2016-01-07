package com.bstek.dorado.uploader;
import java.io.File;
import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.h2.store.fs.FileUtils;
import org.springframework.web.multipart.MultipartFile;

import com.bstek.dorado.uploader.UploadProcessor;

public class MyUploadProcessor implements UploadProcessor {

	@Override
	public String process(MultipartFile file, HttpServletRequest req,
			HttpServletResponse res) {
		
		System.out.println(req.getParameter("fileSaveType"));
		System.out.println(req.getParameter("onlineView"));

		// 此处假设上传路径由客户端传过来，也可以适用其他情况，比如本地一个绝对路径。
		String uploadFolder = req.getParameter("uploadFolder");
		if (uploadFolder == null) {
			uploadFolder = "./target/upload";
		}
		File destFile = getDestFile(file.getOriginalFilename(), new File(
				uploadFolder));
		try {
			file.transferTo(destFile);
		} catch (IllegalStateException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return destFile.getName();
	}

	private File getDestFile(String originalFilename, File folder) {

		File destFile;

		// 浏览器的不同，有时获得的不仅仅是文件名而是全路径
		String fileName = null;
		if (originalFilename.indexOf("/") != -1) {
			fileName = originalFilename
					.substring(originalFilename.indexOf("/") + 1);
		} else {
			fileName = originalFilename;
		}

		if (fileName.indexOf("\\") != -1) {
			fileName = fileName.substring(fileName.indexOf("/") + 1);
		}

		// 若文件不存在直接返回
		if (!new File(folder, fileName).exists()) {
			destFile = new File(folder, fileName);
		} else {
			int i = 1, lastDotPos = fileName.lastIndexOf("."), len = fileName
					.length();

			String prefix = null, suffix = null, destFileName;

			// 文件名中不存在"."或者最后一位是"."的时候
			if (lastDotPos == len - 1 || lastDotPos == -1) {
				prefix = fileName;
				suffix = "";
			} else if (lastDotPos != -1) {
				prefix = fileName.substring(0, lastDotPos);
				suffix = fileName.substring(lastDotPos + 1);
			}
			do {
				if (suffix.length() == 0) {
					destFileName = String.format("%s_%d", prefix, i++);
				} else {
					destFileName = String.format("%s_%d.%s", prefix, i++,
							suffix);
				}
				destFile = new File(folder, destFileName);
			} while (destFile.exists());

		}

		FileUtils.createDirectory(destFile.getParent());

		return destFile;
	}

}
