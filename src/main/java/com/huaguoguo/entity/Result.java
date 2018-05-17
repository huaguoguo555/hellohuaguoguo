package com.huaguoguo.entity;

import java.io.Serializable;

/**
 * Result : 响应的结果对象
 * 
 * @author david.lu
 */
public class Result implements Serializable {
	private static final long serialVersionUID = 6288374846131788743L;

	/**
	 * 状态码
	 */
	private long status;

	/**
	 * 信息
	 */
	private String msg;

	public long getStatus() {
		return status;
	}

	public void setStatus(long status) {
		this.status = status;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public Result() {
	}
}