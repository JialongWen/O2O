package com.wjl.o2o.util;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

import javax.imageio.ImageIO;

import org.springframework.web.multipart.commons.CommonsMultipartFile;

import net.coobird.thumbnailator.Thumbnails;
import net.coobird.thumbnailator.geometry.Positions;

public class ImageUtil {
	private static final SimpleDateFormat sDateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
	private static final Random r = new Random();
	private static String basePath = Thread.currentThread().getContextClassLoader().getResource("").getPath();
	
	public static String generateThumbnail(CommonsMultipartFile thumbnail,String targetAddr) {
		String realFileName = getRandomFileName();
		String extension = getFileExtension(thumbnail);
		makeDirPath(targetAddr);
		String relativeAddr = targetAddr + realFileName + extension;
		File dest = new File(PathUtil.getImgBasePath()+relativeAddr);
		File watermark = new File(basePath + "/timg.jpg");
		try {
			Thumbnails.of(thumbnail.getInputStream()).size(200, 200)
			.watermark(Positions.BOTTOM_RIGHT, ImageIO.read(watermark), 0.25f)
			.outputQuality(0.8f).toFile(dest);
		}catch(IOException e) {
			e.printStackTrace();
		}
		return relativeAddr;
	}
	
	/**
	 * 创建目标路径所涉及的目录，即/home/work/o2o/xxx.jpg
	 * 那么home work o2o 这三个文件夹都得自动创建
	 * @param targetAddr
	 * **/
	private static void makeDirPath(String targetAddr) {
		String realFileParentPath = PathUtil.getImgBasePath() + targetAddr;
		File dirPath = new File(realFileParentPath);
		if(!dirPath.exists()) {
			dirPath.mkdirs();
		}
	}

	/**
	 * 获取输入文件流的扩展名
	 * 
	 * **/
	private static String getFileExtension(CommonsMultipartFile thumbnail) {
		String originalFileName = thumbnail.getOriginalFilename();
		
		return originalFileName.substring(originalFileName.lastIndexOf("."));
	}

	/**
	 * 生成随机的文件名，当前年月日小时分钟秒 + 五位随机数
	 * 
	 * **/
	private static String getRandomFileName() {
		//获取五位随机数
		int rannum = r.nextInt(89999) + 10000;
		String nowTimeStr = sDateFormat.format(new Date());	
		return nowTimeStr + rannum;
	}
	
	
	/**
	 * storePath 是文件路径还是目录路径，
	 * 如果storePath 是文件路径则删除该文件,
	 * 如果storePath 是目录路径删除该目录下的所有文件
	 * 
	 * @param storePath
	 */
	public static void deleteFileOrPath(String storePath) {
		File fileOrPath = new File(PathUtil.getImgBasePath() + storePath);
		if(fileOrPath.exists()) {
			if(fileOrPath.isDirectory()) {
				File files[] = fileOrPath.listFiles();
				for(int i = 0; i<files.length; i++) {
					files[i].delete();
				}
			}
			fileOrPath.delete();
		}
	}

	public static void main(String[] args) throws IOException {
		Thumbnails.of(new File("C:\\Users\\Administrator\\Desktop\\TestImg\\Mysignature.PNG")).size(200, 200)
				.watermark(Positions.BOTTOM_RIGHT, ImageIO.read(new File(basePath + "/timg.jpg")), 0.25f)
				.outputQuality(0.8f).toFile("C:\\Users\\Administrator\\Desktop\\TestImg\\qianming2.jpg");
	}
}
