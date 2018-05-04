package com.nk.rpc.invoke;

import com.google.common.reflect.AbstractInvocationHandler;
import com.nk.rpc.context.RpcContext;
import com.nk.rpc.protocol.ProtocolParser;
import com.nk.rpc.protocol.entity.Payload;
import com.nk.rpc.util.RpcUtil;

import java.lang.reflect.Method;

public class ClientInvokeProxy<T> extends AbstractInvocationHandler {

    @Override
    public Object handleInvocation(Object proxy, Method method, Object[] args) throws Throwable {
        Payload payload = new Payload();
        payload.setService(method.getDeclaringClass().getName());
        payload.setMethod(method.getName());
        payload.setParameterTypes(method.getParameterTypes());
        payload.setParamList(args);
        long id = RpcUtil.getRequestId();
        byte[] request = ProtocolParser.encode(id, payload);

        ClientInvokeHandler handler = RpcContext.getInstance().getClientInvokeHandler();
        RpcCallBack rpcCallBack = handler.sendRequest(id, request);
        Object result = rpcCallBack.start();
        System.out.println(request);
        return result;
    }
}

