package com.nk.rpc.protocol.entity;

import java.io.Serializable;

/**
 * @author Created by niuyang on 2017/12/31.
 */
public class Payload implements Serializable {

    private static final long serialVersionUID = -631690009702018121L;

    /**
     * 服务的全路径名称
     */
    private String service;

    /**
     * 方法名
     */
    private String method;

    /**
     * 返回值类型
     */
    private Class<?> returnType;

    /**
     * 参数类型列表
     */
    private Class<?>[] parameterTypes;

    /**
     * 参数列表
     */
    private Object[] paramList;

    /**
     * 返回结果
     */
    private Object returnValue;

    public String getService() {
        return service;
    }

    public void setService(String service) {
        this.service = service;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public Class<?> getReturnType() {
        return returnType;
    }

    public void setReturnType(Class<?> returnType) {
        this.returnType = returnType;
    }

    public Class<?>[] getParameterTypes() {
        return parameterTypes;
    }

    public void setParameterTypes(Class<?>[] parameterTypes) {
        this.parameterTypes = parameterTypes;
    }

    public Object[] getParamList() {
        return paramList;
    }

    public void setParamList(Object[] paramList) {
        this.paramList = paramList;
    }

    public Object getReturnValue() {
        return returnValue;
    }

    public void setReturnValue(Object returnValue) {
        this.returnValue = returnValue;
    }
}
