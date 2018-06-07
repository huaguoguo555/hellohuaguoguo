package com.huaguoguo.util;

import com.huaguoguo.entity.websocket.WebSocketMessage;
import org.springframework.util.StringUtils;

/**
 * @Author:huaguoguo
 * @Description: 各种检验的工具类
 * @Date: 2018/6/7 15:58
 */
public class CheckUtil {

    /**
     * 校验接收的消息
     *
     * @param message
     * @return
     */
    public static WebSocketMessage checkMessageModel(WebSocketMessage message) {
        WebSocketMessage checkMode = new WebSocketMessage();
        checkMode.setType("error");
        if (message == null){
            checkMode.setContent("消息对象为空，消息发送失败");
            return checkMode;
        }
        if (StringUtils.isEmpty(message.getAvatar())) {
            checkMode.setContent("发送人为空，消息发送失败");
            return checkMode;
        }
        if (StringUtils.isEmpty(message.getContent())) {
            checkMode.setContent("发送消息为空，消息发送失败");
            return checkMode;
        }
        if (StringUtils.isEmpty(message.getType())) {
            checkMode.setContent("消息类型，消息发送失败");
            return checkMode;
        }
        if (StringUtils.isEmpty(message.getReceiver())) {
            checkMode.setContent("接收人为空，消息发送失败");
            return checkMode;
        }
        if (message.getTime() == null) {
            checkMode.setContent("发送时间为空，消息发送失败");
            return checkMode;
        }
        return null;
    }
}
