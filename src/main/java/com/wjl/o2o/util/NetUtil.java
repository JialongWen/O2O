package com.wjl.o2o.util;

import org.springframework.stereotype.Component;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;

@Component
public class NetUtil {

    public static String methodGet(String url){
        try {
            URL urlObj = new URL(url);
            URLConnection connection = urlObj.openConnection();
            InputStream is = connection.getInputStream();
            byte[] b = new byte[1024];
            int len;
            StringBuilder sb = new StringBuilder();
            while ((len = is.read(b))!= -1){
                sb.append(new String(b,0,len));
            }
            return sb.toString();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String methodPost(String url,String data){
        try {
            URL urlObj = new URL(url);
            URLConnection connection = urlObj.openConnection();
            //要发送出去连接就要设置成可发送数据状态
            connection.setDoOutput(true);
            OutputStream os = connection.getOutputStream();
            //写入数据
            os.write(data.getBytes());
            os.close();
            //获取输入流
            InputStream is = connection.getInputStream();
            byte[] b = new byte[1024];
            int len;
            StringBuilder sb = new StringBuilder();
            while ((len = is.read(b))!= -1){
                sb.append(new String(b,0,len));
            }
            return sb.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}
