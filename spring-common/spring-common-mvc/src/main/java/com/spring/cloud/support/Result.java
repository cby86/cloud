package com.spring.cloud.support;
import org.springframework.http.HttpStatus;


public class Result {
    private int status;
    private String message;
    private Object data;

    public Result(int value, String message, Object data) {
        this.status = value;
        this.message = message;
        this.data = data;
    }


    public static Result success(Object data) {
        return new Result(HttpStatus.OK.value(), "成功", data);
    }

    public static Result fail(String msg) {
        return new Result(HttpStatus.INTERNAL_SERVER_ERROR.value(), msg, null);
    }


    public static Result fail(String msg, Object data) {
        return new Result(HttpStatus.INTERNAL_SERVER_ERROR.value(), msg, data);
    }


    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
