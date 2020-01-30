package com.imooc.o2o.util.baidu;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

import com.imooc.o2o.entity.LongUrl;
import com.imooc.o2o.util.wechat.AccessTokenUtil;
import net.sf.json.JSON;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class ShortNetAddress {
	private static Logger log = LoggerFactory.getLogger(ShortNetAddress.class);

	public static int TIMEOUT = 30 * 1000;
	public static String ENCODING = "UTF-8";


	private static String SHOT_URL;

	@Value("${wechat.shoturl}")
	public void setShotUrl(String shotUrl) {
		this.SHOT_URL = shotUrl;
	}
	/**
	 * JSON get value by key
	 * 
	 * @param replyText
	 * @param key
	 * @return
	 */
	private static String getValueByKey_JSON(String replyText, String key) {
		ObjectMapper mapper = new ObjectMapper();
		JsonNode node;
		String tinyUrl = null;
		try {
			//吧调用的返回的消息串转成JSON对象
			node = mapper.readTree(replyText);
			System.out.println("mode is :"+node);
			System.out.println("replyTest is :"+replyText.toString());
			//依据key从json对象里获取对应的值
			tinyUrl = node.get(key).asText();
		} catch (JsonProcessingException e) {
			log.error("getValueByKey_JSON error:" + e.toString());
			e.printStackTrace();
		} catch (IOException e) {
			log.error("getValueByKey_JSON error:" + e.toString());
		}

		return tinyUrl;
	}

	/**
	 * 通过HttpConnection 获取返回的字符串
	 * 
	 * @param connection
	 * @return
	 * @throws IOException
	 */
	private static String getResponseStr(HttpURLConnection connection)
			throws IOException {
		StringBuffer result = new StringBuffer();
		//从连接中获取http状态码
		int responseCode = connection.getResponseCode();
		if (responseCode == HttpURLConnection.HTTP_OK) {
			//如果返回状态码是ok的，那么取出连接的输入流
			InputStream in = connection.getInputStream();
			BufferedReader reader = new BufferedReader(new InputStreamReader(
					in, ENCODING));
			String inputLine = "";
			while ((inputLine = reader.readLine()) != null) {
				//将消息读入结果中
				result.append(inputLine);
			}
		}
		//将结果转换成string返回
		return String.valueOf(result);
	}

	/**
	 * 根据传入的url,通过访问百度短连接的接口，将其转换成短的url
	 * @param
	 * @return
	 */
	public static String getShortURL(LongUrl longUrl) {
			String at = AccessTokenUtil.getAccessToken();
			String requesturl = SHOT_URL + at;
			JSONObject jsonObject = JSONObject.fromObject(longUrl);

			System.out.println(jsonObject.toString());
			try {
				URL urlObj = new URL(requesturl);
				URLConnection connection = urlObj.openConnection();
				//要发送出去连接就要设置成可发送数据状态
				connection.setDoOutput(true);
				OutputStream os = connection.getOutputStream();
				//写入数据
				os.write(jsonObject.toString().getBytes());
				os.close();
				//获取输入流
				InputStream is = connection.getInputStream();
				byte[] b = new byte[1024];
				int len;
				StringBuilder sb = new StringBuilder();
				while ((len = is.read(b))!= -1){
					sb.append(new String(b,0,len));
				}
				JSONObject shotUrl = JSONObject.fromObject(sb.toString());
				return shotUrl.getString("short_url");
			} catch (Exception e) {
				e.printStackTrace();
			}
			return null;
		}


	public static JSON createShortUrl(String url)throws IOException{
		StringBuilder long_url = new StringBuilder("http://www.mynb8.com/api3/sina.html?appkey=8bdc11f24be3e6d65406fd8d741a6334&long_url=");
		long_url.append(url);
		URL u = new URL(String.valueOf(long_url));
		InputStream in = u.openStream();
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		try {
			byte buf[] = new byte[1024];
			int read = 0;
			while ((read = in .read(buf)) > 0) {
				out.write(buf, 0, read);
			}
		} finally {
			if ( in != null) {
				in .close();
			}
		}
		byte b[] = out.toByteArray();
		return null;
	}

}
