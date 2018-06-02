package com.huaguoguo.config.websocket;

import java.io.IOException;
import java.util.concurrent.CopyOnWriteArraySet;

import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;


@ServerEndpoint(value = "/websocket/{Token}")
@Component
public class EzgoWebSocket {


    // 静态变量，用来记录当前在线连接数。应该把它设计成线程安全的。
    private static int onlineCount = 0;

    // concurrent包的线程安全Set，用来存放每个客户端对应的MyWebSocket对象。
    public static CopyOnWriteArraySet<EzgoWebSocket> webSocketSet = new CopyOnWriteArraySet<EzgoWebSocket>();

    // 与某个客户端的连接会话，需要通过它来给客户端发送数据
    private Session session;

    // 登录用户
    private String token;

    private static final Logger logger = LoggerFactory.getLogger(EzgoWebSocket.class);

    /**
     * 连接建立成功调用的方法
     */
    @OnOpen
    public void onOpen(@PathParam("Token") String token, Session session) {
        this.token = token;
        this.session = session;
        webSocketSet.add(this); // 加入set中
        addOnlineCount(); // 在线数加1
        logger.info("有新连接加入！当前在线人数为" + getOnlineCount() + "token-----" + this.token);
    }

    /**
     * 连接关闭调用的方法
     */
    @OnClose
    public void onClose() {
        webSocketSet.remove(this); // 从set中删除
        subOnlineCount(); // 在线数减1
        logger.info("有一连接关闭！当前在线人数为" + getOnlineCount() + "token-----" + this.token);
    }

    /**
     * 收到客户端消息后调用的方法
     *
     * @param message 客户端发送过来的消息
     */
    @OnMessage
    public void onMessage(String message, Session session) {
        try {
            // 群发消息
            for (EzgoWebSocket item : webSocketSet) {
                item.sendMessage("这是[" + this.token + "]发送的消息：" + message);
            }
            this.sendMessage("服务器：我收到你的消息（"+message+"）了"+"-"+this.token);
        } catch (IOException e) {
            e.printStackTrace();
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

    public static synchronized int getOnlineCount() {
        return onlineCount;
    }

    public static synchronized void addOnlineCount() {
        EzgoWebSocket.onlineCount++;
    }

    public static synchronized void subOnlineCount() {
        EzgoWebSocket.onlineCount--;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

}
