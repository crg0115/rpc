package com.nk.rpc.invoke;

import com.nk.rpc.context.RpcContext;
import com.nk.rpc.protocol.PackageHandler;
import com.nk.rpc.protocol.ProtocolParser;
import com.nk.rpc.protocol.entity.Payload;
import com.nk.rpc.protocol.entity.Protocol;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

/**
 * @author Created by niuyang on 2018/1/1.
 */
public class ServerInvokeHandler extends ChannelInboundHandlerAdapter {

    private PackageHandler packageHandler;

    public ServerInvokeHandler(PackageHandler packageHandler) {
        this.packageHandler = packageHandler;
    }



    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        System.out.println("server channelRead hello======");
        List<Protocol> protocolList = packageHandler.readBytes2(ctx.channel(), (ByteBuf) msg);
        if (protocolList != null && protocolList.size() > 0) {
            for (Protocol protocol : protocolList) {
                process(ctx, protocol);
            }
        }
//        ctx.flush();
    }

    private void process(ChannelHandlerContext ctx, Protocol protocol) throws ClassNotFoundException, NoSuchMethodException, IllegalAccessException, InstantiationException, InvocationTargetException, IOException {
        Payload payload = protocol.getPayload();
        Class<?> aClass = RpcContext.getInstance().getHandlerMap().get(payload.getService()).getClass();
        Method declaredMethod = aClass.getDeclaredMethod(payload.getMethod(), payload.getParameterTypes());

        if (declaredMethod != null) {
            Object invoke = declaredMethod.invoke(aClass.newInstance(), payload.getParamList());
            System.out.println(invoke);

            Payload returnPayload = new Payload();
            returnPayload.setReturnValue(invoke);
            byte[] encode = ProtocolParser.encode(protocol.getId(), returnPayload);

            ByteBuf heapBuffer = Unpooled.buffer(encode.length);
            heapBuffer.writeBytes(encode);
            ctx.writeAndFlush(heapBuffer);
//            ctx.channel().writeAndFlush(heapBuffer);
        }
    }
}
