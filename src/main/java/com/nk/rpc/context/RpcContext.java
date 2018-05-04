package com.nk.rpc.context;

import com.nk.rpc.invoke.ClientInvokeHandler;

/**
 * @author Created by niuyang on 2018/5/4.
 */
public class RpcContext {

    private ClientInvokeHandler clientInvokeHandler;

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
}
