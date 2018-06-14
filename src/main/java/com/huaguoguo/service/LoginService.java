package com.huaguoguo.service;

import com.huaguoguo.entity.ResultModel;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

public interface LoginService {

    ResultModel login(Map<String,Object> input, HttpServletResponse response) throws IOException;

    /**
     * 获取在线好友列表
     * @return
     */
    ResultModel getOnlineFriends();
}
