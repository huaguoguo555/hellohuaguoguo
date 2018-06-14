package com.huaguoguo.controller;

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

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

@RestController
public class LoginController {

    @Autowired
    private LoginService loginService;

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
    @RequestMapping(value = "/friends/online",method = RequestMethod.GET)
    public Object getOnlineFriends(){
        ResultModel result = new ResultModel();
        result = loginService.getOnlineFriends();
        return result;
    }

}
