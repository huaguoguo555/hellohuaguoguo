package com.huaguoguo.entity;


/**
 * ResultModel : Response ResultModel for RESTful，封装返回JSON格式的数据
 * 
 * @author david.lu
 * @since 20170223
 */

public class ResultModel<T> extends Result {

	private static final long serialVersionUID = 7880907731807860636L;

	/**
	 * 数据
	 */
	private T data;

	public T getData() {
		return data;
	}

	public void setData(T data) {
		this.data = data;
	}

	public ResultModel() {
		super();
	}

	/**
	 * 成功返回状态和数据
	 * 
	 * @param data
	 */
	public ResultModel(T data) {
		super.setStatus(HttpStatus.SC_OK);
		this.data = data;
	}

	/**
	 * 失败返回状态和错误信息
	 * 
	 */
	public ResultModel(long status, String msg) {
		super.setStatus(status);
		super.setMsg(msg);
	}
}