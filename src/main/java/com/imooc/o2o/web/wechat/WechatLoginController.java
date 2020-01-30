package com.imooc.o2o.web.wechat;

import java.io.IOException;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.imooc.o2o.dto.WechatAuthExecution;
import com.imooc.o2o.entity.WechatAuth;
import com.imooc.o2o.enums.WechatAuthStateEnum;
import com.imooc.o2o.service.WechatAuthService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.imooc.o2o.dao.PersonInfoDao;
import com.imooc.o2o.dto.PersonInfoExecution;
import com.imooc.o2o.dto.UserAccessToken;
import com.imooc.o2o.dto.WechatUser;
import com.imooc.o2o.entity.PersonInfo;
import com.imooc.o2o.enums.PersonInfoStateEnum;
import com.imooc.o2o.service.PersonInfoService;
import com.imooc.o2o.util.wechat.WechatUtil;

/**
 * 获取关注公众号之后的微信用户信息的接口，如果在微信浏览器里访问
 * https://open.weixin.qq.com/connect/oauth2/authorize?appid=您的appId&redirect_uri=http://wenfeiqin.top/myo2o/wechatlogin/logincheck&role_type=1&response_type=code&scope=snsapi_userinfo&state=1#wechat_redirect
 * 则这里将会获取到code,之后再可以通过code获取到access_token 进而获取到用户信息
 * 
 *
 */

@Controller
@RequestMapping("wechatlogin")
public class WechatLoginController {
	
	@Autowired
	private PersonInfoService personInfoService;

	@Autowired
    private WechatAuthService wechatAuthService;
	
	private static Logger log = LoggerFactory.getLogger(WechatLoginController.class);

    @RequestMapping(value = "/logincheck", method = { RequestMethod.GET })
    public String doGet(HttpServletRequest request, HttpServletResponse response) {
        log.debug("weixin login get...");
        // 获取微信公众号传输过来的code,通过code可获取access_token,进而获取用户信息
        String code = request.getParameter("code");
        // 这个state可以用来传我们自定义的信息，方便程序调用，这里也可以不用
        // String roleType = request.getParameter("state");
        log.debug("weixin login code:" + code);
        WechatUser user = null;
        String openId = null;
        if (null != code) {
            UserAccessToken token;
            try {
                // 通过code获取access_token
                token = WechatUtil.getUserAccessToken(code);
                log.debug("weixin login token:" + token.toString());
                // 通过token获取accessToken
                String accessToken = token.getAccessToken();
                // 通过token获取openId
                openId = token.getOpenId();
                // 通过access_token和openId获取用户昵称等信息
                user = WechatUtil.getUserInfo(accessToken, openId);
                log.debug("weixin login user:" + user.toString());
                request.getSession().setAttribute("openId", openId);
            } catch (IOException e) {
                log.error("error in getUserAccessToken or getUserInfo or findByOpenId: " + e.toString());
                e.printStackTrace();
            }
        }
        // ======todo begin======
        // 前面咱们获取到openId后，可以通过它去数据库判断该微信帐号是否在我们网站里有对应的帐号了，
        if(openId != null && user != null) {
        	//先查询数据库中的personInfo表确认该opendId是否存在用户
        	PersonInfo existingUser = personInfoService.queryPersonInfoByOpenId(openId);
            WechatAuth wechatAuth = wechatAuthService.getWechatAuthByOpenId(openId);
            //如果不存在则存入一个personInfo并且存入session中
        	if(existingUser != null && existingUser.getUserId() != null && wechatAuth != null && wechatAuth.getWechatAuthId() != null) {
                //如果存在的话直接取出存入session中
                request.getSession().setAttribute("user", existingUser);
        	}else {
                if (existingUser == null) {
                    PersonInfo personInfo = new PersonInfo();
                    personInfo.setName(user.getNickName());
                    personInfo.setProfileImg(user.getHeadimgurl());
                    personInfo.setGender(String.valueOf(user.getSex()));
                    personInfo.setOpenId(openId);
                    personInfo.setUserType(1);
                    personInfo.setEnableStatus(1);
                    PersonInfoExecution pe = personInfoService.addPersonInfo(personInfo);
                    if (pe.getState() == PersonInfoStateEnum.SUCCESS.getState()) {
                        //如果成功了那么就往session里面存一个user
                        PersonInfo nowUser = personInfoService.queryPersonInfoByOpenId(openId);
                        if (nowUser != null && nowUser.getUserId() != null) {
                            //之后再存一个wechatAuth
                            WechatAuth newWechatAuth = new WechatAuth(openId, nowUser, new Date());
                            WechatAuthExecution we = wechatAuthService.register(newWechatAuth, null);
                            //如果存成功了就往session里存一个user
                            if (we.getState() == WechatAuthStateEnum.SUCCESS.getState()){
                                request.getSession().setAttribute("user", nowUser);
                            }else {
                                return "shop/operationfail";
                            }
                        }else {
                            return "shop/operationfail";
                        }
                    }
                }
                if(wechatAuth == null && existingUser != null){
                    PersonInfo nowUser = personInfoService.queryPersonInfoByOpenId(openId);
                    if (nowUser != null && nowUser.getUserId() != null) {
                        //之后再存一个wechatAuth
                        WechatAuth newWechatAuth = new WechatAuth(openId, nowUser, new Date());
                        WechatAuthExecution we = wechatAuthService.register(newWechatAuth, null);
                        //如果存成功了就往session里存一个user
                        if (we.getState() == WechatAuthStateEnum.SUCCESS.getState()){
                            request.getSession().setAttribute("user", nowUser);
                        }else {
                            return "shop/operationfail";
                        }
                    }else {
                        return "shop/operationfail";
                    }
                }

            }
        }
        // 没有的话这里可以自动创建上，直接实现微信与咱们网站的无缝对接。
        // ======todo end======
        if (user != null) {
            // 获取到微信验证的信息后返回到指定的路由（需要自己设定）
            return "frontend/index";
        } else {
            return "shop/operationfail";
        }
    }
}
