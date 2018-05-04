package com.nk.rpc;

import com.nk.rpc.net.Server;
import com.nk.rpc.invoke.ServerInvokeHandler;
import com.nk.rpc.protocol.PackageHandler;

/**
 * @author Created by niuyang on 2018/5/4.
 */
public class Main {

    public static void main(String[] args) throws Exception {
        int port;
        if (args.length > 0) {
            port = Integer.parseInt(args[0]);
        } else {
            port = 8080;
        }
        Server server = new Server(port);
        server.addLastHandler(new ServerInvokeHandler(new PackageHandler()));
        server.run();
    }
}
