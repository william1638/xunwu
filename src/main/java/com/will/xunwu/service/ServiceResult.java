package com.will.xunwu.service;

/**
 * 服务接口通用结构
 * @author William
 * @date 2018/3/24
 */
public class ServiceResult<T> {

    private boolean success ;
    private String message ;
    private T result ;

    public ServiceResult(boolean success, String message, T result) {
        this.success = success;
        this.message = message;
        this.result = result;
    }

    public ServiceResult(boolean success, String message) {

        this.success = success;
        this.message = message;
    }

    public ServiceResult(boolean success) {

        this.success = success;
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

    public T getResult() {
        return result;
    }

    public void setResult(T result) {
        this.result = result;
    }
}
