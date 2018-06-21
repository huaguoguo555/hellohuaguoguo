package com.huaguoguo.config.websocket;

import com.huaguoguo.entity.websocket.WebSocketMessage;
import com.huaguoguo.service.TokenService;
import com.huaguoguo.util.CheckUtil;
import com.huaguoguo.util.JsonUtil;
import com.huaguoguo.util.StringTool;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.*;


@ServerEndpoint(value = "/websocket/{authToken}")
@Component
public class EzgoWebSocket {

    private static TokenService tokenService;
    private static RedisTemplate<String, String> redisTemplate;

    @Autowired
    public EzgoWebSocket(TokenService tokenService, RedisTemplate<String, String> redisTemplate) {
        this.tokenService = tokenService;
        this.redisTemplate = redisTemplate;
    }

    public EzgoWebSocket() {
    }


    // 静态变量，用来记录当前在线连接数。应该把它设计成线程安全的。
    private static int onlineCount = 0;


    public static Map<String, EzgoWebSocket> webSocketMap = new HashMap<>();

    // 与某个客户端的连接会话，需要通过它来给客户端发送数据
    private Session session;

    private String nickName;
    // 登录用户
    private String authToken;

    private static final Logger logger = LoggerFactory.getLogger(EzgoWebSocket.class);

    /**
     * 通过nickName取对应的EzgoWebSocket对象
     *
     * @param nickName
     * @return
     */
    public static EzgoWebSocket get(String nickName) {
        return webSocketMap.get(nickName);
    }

    /**
     * 连接建立成功调用的方法
     */
    @OnOpen
    public void onOpen(@PathParam("authToken") String authToken, Session session) throws IOException {
        this.authToken = authToken;
        this.session = session;
        this.nickName = tokenService.getUserInfoId(authToken);
        if (StringUtils.isEmpty(nickName)) {
            session.close();
            logger.error("无效authToken,连接中断");
            return;
        }
        webSocketMap.put(nickName, this); // 加入map中
        logger.info("有新连接加入！当前在线人数为" + getOnlineCount() + "【nickName-----" + this.nickName + "】");
        //在好友列表显示我上线了
        onlineNotie();
    }

    /**
     * 上线通知
     */
    private void onlineNotie() throws IOException {
        WebSocketMessage message = new WebSocketMessage();
        message.setReceiver("all");
        message.setAvatar("admin");
        message.setTime(new Date());
        message.setContent(nickName);
        message.setType("online");
        massSend(message);
    }

    /**
     * 连接关闭调用的方法
     */
    @OnClose
    public void onClose() throws IOException {
        if (!StringUtils.isEmpty(nickName)) {
            webSocketMap.remove(nickName);
            logger.info("有一连接关闭！当前在线人数为" + getOnlineCount() + "【nickName-----" + this.nickName + "】");
            downlineNotie();
        }
    }

    /**
     * 下线通知
     */
    private void downlineNotie() throws IOException {
        WebSocketMessage message = new WebSocketMessage();
        message.setReceiver("all");
        message.setAvatar("admin");
        message.setTime(new Date());
        message.setContent(nickName);
        message.setType("downline");
        massSend(message);
    }

    /**
     * 收到客户端消息后调用的方法
     *
     * @param message 客户端发送过来的消息
     */
    @OnMessage
    public void onMessage(String message, Session session) {
        logger.info("接收到客户端的消息：{}", message);
        try {
            WebSocketMessage model = JsonUtil.jsonToObj(message, WebSocketMessage.class);
            //检测通过正常发送消息
            WebSocketMessage checkMessageModel = CheckUtil.checkMessageModel(model);
            if (checkMessageModel == null) {
                switch (model.getType()) {
                    case "user":
                        p2pSend(model);
                        break;
                    case "mass":
                        massSend(model);
                        break;
                }
            } else {//发送给发送人提示消息失败
                checkMessageModel.setAvatar(this.nickName);
                checkMessageModel.setReceiver(this.nickName);
                p2pSend(checkMessageModel);
            }
        } catch (IOException e) {
            logger.error("向客户端推送消息异常", e);
        }

    }

    private void p2pSend(WebSocketMessage message) throws IOException {
        String receiver = message.getReceiver();
        EzgoWebSocket ezgoWebSocket = webSocketMap.get(receiver);
        ezgoWebSocket.sendMessage(JsonUtil.toJson(message));
        //保存聊天记录到redis
        String json = JsonUtil.toJson(message);
        logger.info("存入redis的聊天记录{}", json);
        String chatKey = StringTool.genUserChatKey(receiver, message.getAvatar());
        redisTemplate.opsForList().rightPush(chatKey, json);
    }

    /**
     * 群发消息
     *
     * @param message
     * @throws IOException
     */
    private void massSend(WebSocketMessage message) throws IOException {
        // 群发消息
        Set<String> keySet = webSocketMap.keySet();
        Iterator<String> iterator = keySet.iterator();
        while (iterator.hasNext()) {
            EzgoWebSocket item = webSocketMap.get(iterator.next());
            item.sendMessage(JsonUtil.toJson(message));
        }
    }

    /**
     * 发生错误时调用
     */
    @OnError
    public void onError(Session session, Throwable error) {
        error.printStackTrace();
    }

    public void sendMessage(String message) throws IOException {
        this.session.getBasicRemote().sendText(message); // this.session.getAsyncRemote().sendText(message);
    }

    public static void sendInfo(EzgoWebSocket item, String message) throws IOException {
        try {
            item.sendMessage(message);
        } catch (IOException e) {

        }
    }

    public int getOnlineCount() {
        return webSocketMap.size();
    }


    public String getAuthToken() {
        return authToken;
    }

    public void setAuthToken(String token) {
        this.authToken = authToken;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }
}
