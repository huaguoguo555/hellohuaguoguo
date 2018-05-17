package com.huaguoguo.entity;

/**
 * 自定义请求状态码
 * 
 * @date 20170227
 */
public enum ResultStatus {
	SUCCESS(100, "成功"), USERNAME_OR_PASSWORD_ERROR(-1001, "用户名或密码错误"), USER_NOT_FOUND(-1002, "用户不存在"), USER_NOT_LOGIN(-1003, "用户未登录");

	/**
	 * 返回码
	 */
	private long status;

	/**
	 * 返回结果描述
	 */
	private String msg;

	ResultStatus(long status, String msg) {
		this.status = status;
		this.msg = msg;
	}
}
