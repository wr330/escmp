package com.buaa.fly.view;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import com.bstek.bdf2.core.context.ContextHolder;

public class FileHelper {
	
    public static int bytes;
    public static byte[] datablock;
    //文件转换为二进制数
	public static void fileToData(String path)  {
		String filePath = ContextHolder.getRequest().getRealPath("/")
				+ "upload"+path;
		File file=new File(filePath);
		if(!file.exists())
			return;
		Boolean isReadNotEnd = true;
		int len = -1;
		InputStream in;
		try {
			in = new FileInputStream(file);
			bytes = in.available();
			datablock = new byte[bytes];
			do {
				len = in.read(datablock, 0, datablock.length);
				if (len == -1) 
					isReadNotEnd = false;
			} while (isReadNotEnd);
			in.close();
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	//修改文件路径名
	public static void changeFolderById(String path,String newpath){
		String filePath = ContextHolder.getRequest().getRealPath("/")
				+ "upload"+path;
		String newFilePath = ContextHolder.getRequest().getRealPath("/")
				+ "upload"+newpath;
		File file=new File(filePath);
		if(file.exists())
		    file.renameTo(new File(newFilePath));
	}

	
	//删除文件
	public static void deleteFile(String path){
		String filePath = ContextHolder.getRequest().getRealPath("/")
				+ "upload"+path;
		File file=new File(filePath);
		if(file.exists())
			deleteDir(file);
	}
    //递归删除目录下的所有文件及子目录下所有文件
    private static boolean deleteDir(File dir) {
        if (dir.isDirectory()) {
            String[] children = dir.list();
            for (int i=0; i<children.length; i++) {//递归删除目录中的子目录下
                boolean success = deleteDir(new File(dir, children[i]));
                if (!success) {
                    return false;
                }
            }
        }
        return dir.delete();// 目录此时为空，可以删除
    }
	//文件是否存在
	public static boolean existFile(String path,String fname){
		String filePath = ContextHolder.getRequest().getRealPath("/")
				+ "upload"+path + fname;
		File file=new File(filePath);
		return file.exists();
	}
	//二进制数生成文件
	public static void createFile(String path,String fname,byte[] datablock) throws IOException{
		String filePath = ContextHolder.getRequest().getRealPath("/")
				+ "upload"+path;
		File folder=new File(filePath);
		if(!folder.exists()){
			folder.mkdirs();
		}
		File file=new File(folder,fname);
		OutputStream out=new FileOutputStream(file);
		out.write(datablock);
		out.close();
	}
}
	