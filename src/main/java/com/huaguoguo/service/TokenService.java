package com.huaguoguo.service;

/**
  * @Author:huaguoguo
  * @Description: 用户token接口
  * @Date: 2018/6/7 10:22
  */
public interface TokenService {

    /**
     * 创建一个token关联上指定用户
     *
     * @param userId
     *            指定用户的id
     * @return 生成的token
     */
    public String createToken(String userId);

    /**
     * 检查token是否有效
     *
     * @param token
     *            token
     * @return 是否有效
     */
    public boolean checkToken(String token);



    /**
     * 从令牌中获取用户ID
     *
     * @param token
     *            加密后的字符串
     * @return
     */
    public String getUserInfoId(String token);

    /**
     * 清除token
     *
     * @param userId
     *            登录用户的id
     */
    public void deleteToken(String userId);

    /**
     * 获取当前线程的 用户信息
     *
     * @return
     */
    public Long getUserId();


}
