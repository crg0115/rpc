package com.nk.rpc.invoke;

import com.nk.rpc.protocol.PackageHandler;
import com.nk.rpc.protocol.entity.Protocol;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Created by niuyang on 2018/1/1.
 */
public class ClientInvokeHandler extends ChannelInboundHandlerAdapter {

    private Map<Long, RpcCallBack> rpcCallBackMap = new ConcurrentHashMap<>();

    private volatile Channel channel;

    private PackageHandler packageHandler;

    public ClientInvokeHandler(PackageHandler packageHandler) {
        this.packageHandler = packageHandler;
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws IOException {
        List<Protocol> protocolList = packageHandler.readBytes2(ctx.channel(), (ByteBuf) msg);
        if (protocolList != null && protocolList.size() > 0) {
            for (Protocol protocol : protocolList) {
                long id = protocol.getId();
                RpcCallBack rpcCallBack = rpcCallBackMap.remove(id);
                if (rpcCallBack != null) {
                    rpcCallBack.over(protocol);
                }
            }
        }
    }

    @Override
    public void channelRegistered(ChannelHandlerContext ctx) throws Exception {
        this.channel = ctx.channel();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }

    public RpcCallBack sendRequest(long id, byte[] request) {
        RpcCallBack callBack = new RpcCallBack(request);
        rpcCallBackMap.put(id, callBack);

        ByteBuf heapBuffer = Unpooled.buffer(request.length);
        heapBuffer.writeBytes(request);
        this.channel.writeAndFlush(heapBuffer);
        return callBack;
    }
}


