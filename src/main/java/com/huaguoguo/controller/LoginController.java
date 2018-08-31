package com.huaguoguo.controller;

import com.huaguoguo.annotation.CheckAuthToken;
import com.huaguoguo.config.websocket.EzgoWebSocket;
import com.huaguoguo.entity.ResultModel;
import com.huaguoguo.service.LoginService;
import com.huaguoguo.util.JedisClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

@RestController
public class LoginController {


    private LoginService loginService;

    @Autowired
    public void setLoginService(LoginService loginService) {
        this.loginService = loginService;
    }

    @RequestMapping(value = "/login",method = RequestMethod.POST)
    public Object login(@RequestBody Map<String,Object> input, HttpServletResponse response) throws IOException {
        ResultModel result = new ResultModel();
        if (StringUtils.isEmpty(input.get("nickName"))){
            result.setStatus(400L);
            result.setMsg("名字都不填，你登录你马呢！！！");
            return result;
        }
        result = loginService.login(input,response);
        return result;
    }

    /**
     * 获取在线好友列表
     * @return
     */
    @CheckAuthToken
    @RequestMapping(value = "/friends/online",method = RequestMethod.GET)
    public Object getOnlineFriends(){
        
        ResultModel result = new ResultModel();
        result = loginService.getOnlineFriends();
        return result;
    }


    public static void main(String[] args) {
        int count = 100000000;
        //bufferTest(count);
        builderTest(count);
    }

    public static void bufferTest(int count){
        StringBuffer sb = new StringBuffer();
        Long start1 = System.currentTimeMillis();
        for (int i = 0; i < count; i++) {
            sb.append(i);
        }
        Long end1 = System.currentTimeMillis();
        System.out.println("buffer:"+ (end1-start1));
    }

    public static void builderTest(int count){
        StringBuilder sb = new StringBuilder();
        Long start1 = System.currentTimeMillis();
        for (int i = 0; i < count; i++) {
            sb.append(i);
        }
        Long end1 = System.currentTimeMillis();
        System.out.println("builder:"+ (end1-start1));
    }
}
