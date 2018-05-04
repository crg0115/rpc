package com.nk.rpc.net;

import com.nk.rpc.context.RpcContext;
import com.nk.rpc.invoke.ClientInvokeHandler;
import com.nk.rpc.protocol.PackageHandler;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

/**
 * @author Created by niuyang on 2018/1/1.
 */
public class Client {

    public void run() throws Exception {
        String host = "127.0.0.1";
        int port = 8080;
        EventLoopGroup workerGroup = new NioEventLoopGroup();

        final ClientInvokeHandler clientInvokeHandler = new ClientInvokeHandler(new PackageHandler());
        RpcContext.getInstance().setClientInvokeHandler(clientInvokeHandler);

        try {
            Bootstrap b = new Bootstrap();
            b.group(workerGroup);
            b.channel(NioSocketChannel.class);
            b.option(ChannelOption.SO_KEEPALIVE, true);
            b.handler(new ChannelInitializer<SocketChannel>() {
                @Override
                public void initChannel(SocketChannel ch) throws Exception {
                    ch.pipeline().addLast(clientInvokeHandler);
                }
            });

            // Start the client.
            ChannelFuture f = b.connect(host, port).sync();

            // test
//            ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:rpc.xml");
//            TestService testService = (TestService) applicationContext.getBean("testService");
//            int add = testService.add(11, 11);
//            System.out.println(add);

            // Wait until the connection is closed.
            f.channel().closeFuture().sync();
        } finally {
            workerGroup.shutdownGracefully();
        }
    }

}
