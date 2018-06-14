package com.huaguoguo.service;

import com.huaguoguo.entity.ResultModel;

import java.util.Map;

public interface ChatService {

    /**
     * 查询聊天记录
     * @param message
     * @return
     */
    ResultModel getChatRecord(Map<String, String> message);
}
