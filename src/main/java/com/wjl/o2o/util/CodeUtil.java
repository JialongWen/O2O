package com.wjl.o2o.util;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.common.BitMatrix;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

public class CodeUtil {
	public static boolean checkVerifyCode(HttpServletRequest request) {
		String verifyCodeExpected = (String) request.getSession().getAttribute(
				com.google.code.kaptcha.Constants.KAPTCHA_SESSION_KEY);
		String verifyCodeActual = HttpServletRequestUtil.getString(request,
				"VerifyCodeActual");
		if (verifyCodeActual == null
				|| !verifyCodeActual.equalsIgnoreCase(verifyCodeExpected)) {
			return false;
		}
		return true;
	}

	/**
	 * 二维码生成的图片流
	 */
	public static BitMatrix generateQRCodeStream(String content, HttpServletResponse response){
		//给响应添加头部信息，主要是告诉浏览器返回的是图片流
		response.setHeader("Cache_Control","no-store");
		response.setHeader("Pragma","no-cache");
		response.setDateHeader("Expires",0);
		response.setContentType("image/png");
		//设置图片的文字编码以及内边框距
		Map<EncodeHintType,Object> hints = new HashMap<EncodeHintType,Object>();
		hints.put(EncodeHintType.CHARACTER_SET,"UTF-8");
		hints.put(EncodeHintType.MARGIN,0);
		BitMatrix bitMatrix;
		try{
			//参数顺序分别为：编码内容，编码类型，生成图片宽度，生成图片高度，设置参数
			bitMatrix = new MultiFormatWriter().encode(content, BarcodeFormat.QR_CODE,300,300,hints);

		}catch (Exception e){
			e.printStackTrace();
			return null;
		}
		return bitMatrix;

	}
}
