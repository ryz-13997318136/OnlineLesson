package com.ryz.cn.model;

import java.io.Serializable;

public class Result implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private Res res;
	private String msg;
	private Object data;
	private int count;
	
	public Result(Res res, String msg) {
		super();
		this.res = res;
		this.msg = msg;
	}
	public Result(Res res, Object data) {
		super();
		this.res = res;
		this.data = data;
	}
	public Result(Res res, Object data,int count) {
		super();
		this.res = res;
		this.data = data;
		this.count = count;
	}
	public Result(Res res, String msg,Object data) {
		super();
		this.res = res;
		this.msg = msg;
		this.data = data;
	}
	public Result(Res res, String msg,Object data,int count) {
		super();
		this.res = res;
		this.msg = msg;
		this.data = data;
		this.count = count;
	}
	public Res getRes() {
		return res;
	}
	public void setRes(Res res) {
		this.res = res;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	
	public Object getData() {
		return data;
	}
	public void setData(Object data) {
		this.data = data;
	}
	
	
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}
	@Override
	public String toString() {
		return "Result [res=" + res + ", msg=" + msg + ", data=" + data + "]";
	}
	
	
	
}

