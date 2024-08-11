package com.demo.entity;

import java.io.Serializable;

/**
 * 用于向页面传递信息
 * 将页面所做的结果和提示信息作为该类的属性
 * 当Controller需要向页面传递消息时，将内容封装在该类中
 * @param <T>代表任意类型
 */
public class Result<T> implements Serializable {
   private boolean success;//标识操作是否成功
    private String message;
    private T data;

    public Result(boolean success, String message) {
        super();
        this.success=success;
        this.message=message;
    }

    @Override
    public String toString() {
        return "Result{" +
                "success=" + success +
                ", message='" + message + '\'' +
                ", data=" + data +
                '}';
    }

    public Result() {
        super();
    }

    public Result(boolean success, String message, T data) {
        this.success = success;
        this.message = message;
        this.data = data;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
