package com.imooc.o2o.util.wechat;

import com.imooc.o2o.entity.AccessToken;
import com.imooc.o2o.util.NetUtil;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;


@Component
public class AccessTokenUtil {

    private static String ACCESS_TOKEN_URL;

    private static AccessToken at;

    @Value("${wechat.accesstoken}")
    public void setAccessTokenUrl(String accessTokenUrl) {
        ACCESS_TOKEN_URL = accessTokenUrl;
    }

    private static void getToken(){
        String token = NetUtil.methodGet(ACCESS_TOKEN_URL);
        JSONObject jsonObject = JSONObject.fromObject(token);
        String access_token = jsonObject.getString("access_token");
        String expiresIn = jsonObject.getString("expires_in");
         at = new AccessToken(access_token,expiresIn);
    }

    public static String getAccessToken(){
        if (at == null || at.isExpire()) {
            getToken();
        }
        return at.getAccessToken();
    }



}
