package com.nk.rpc.context;

import com.nk.rpc.invoke.ClientInvokeHandler;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Created by niuyang on 2018/5/4.
 */
public class RpcContext {

    private ClientInvokeHandler clientInvokeHandler;

    private Map<String, Object> handlerMap = new ConcurrentHashMap<>();

    private static RpcContext rpcContext = new RpcContext();

    public static RpcContext getInstance() {
        return rpcContext;
    }

    private RpcContext() {

    }

    public void setClientInvokeHandler(ClientInvokeHandler clientInvokeHandler) {
        this.clientInvokeHandler = clientInvokeHandler;
    }

    public ClientInvokeHandler getClientInvokeHandler() {
        return clientInvokeHandler;
    }

    public Map<String, Object> getHandlerMap() {
        return handlerMap;
    }

    public void setHandlerMap(Map<String, Object> handlerMap) {
        this.handlerMap = handlerMap;
    }
}
