package com.huaguoguo.util;

/**
  * @Author:huaguoguo
  * @Description: 字符串处理工具类
  * @Date: 2018/6/13 15:00
  */
public class StringTool {

    /**
     * 生成两个用户聊天记录key
     * @param user1
     * @param user2
     * @return
     */
    public static String genUserChatKey(String user1,String user2){

        StringBuffer key = new StringBuffer();
        if (user1.compareTo(user2)>0){
            key.append(user1).append('-').append(user2);
        }else {
            key.append(user2).append('-').append(user1);
        }
        return key.toString();
    }
}
