package com.nk.rpc;

import com.nk.rpc.invoke.ServerInvokeHandler;
import com.nk.rpc.net.Server;
import com.nk.rpc.protocol.PackageHandler;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @author Created by niuyang on 2018/5/4.
 */
public class ServerMain {

    public static void main(String[] args) throws Exception {
        new Thread(new Runnable() {
            @Override
            public void run() {
                Server server = new Server(8080);
                server.addLastHandler(new ServerInvokeHandler(new PackageHandler()));
                try {
                    server.run();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();

        Thread.sleep(100L);
//        int port;
//        if (args.length > 0) {
//            port = Integer.parseInt(args[0]);
//        } else {
//            port = 8080;
//        }
//        Server server = new Server(port);
//        server.addLastHandler(new ServerInvokeHandler(new PackageHandler()));
//        server.run();

        ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:rpc.xml");
    }
}
