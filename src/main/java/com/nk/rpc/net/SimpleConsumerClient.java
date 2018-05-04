package com.nk.rpc.net;

import com.nk.rpc.protocol.ProtocolParser;
import com.nk.rpc.protocol.entity.Payload;

import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author Created by niuyang on 2018/1/5.
 */
public class SimpleConsumerClient {

    private class ConsumerTask implements Runnable {
        @Override
        public void run() {
            Payload payload = new Payload();
            payload.setService("com.netease.rpc.service.impl.TestServiceImpl");
            payload.setMethod("add");
            payload.setReturnType(int.class);
            payload.setParameterTypes(new Class[]{int.class, int.class});
            payload.setParamList(new Object[]{10, 11});

            Payload payload2 = new Payload();
            payload2.setService("com.netease.rpc.service.impl.TestServiceImpl");
            payload2.setMethod("add");
            payload2.setReturnType(int.class);
            payload2.setParameterTypes(new Class[]{int.class, int.class});
            payload2.setParamList(new Object[]{20, 12});

            try {
                byte[] b1 = ProtocolParser.encode(payload);
                byte[] b2 = ProtocolParser.encode(payload2);

                Socket socket = new Socket("127.0.0.1", 8080);
                OutputStream outputStream = socket.getOutputStream();

                outputStream.write(b1);

                int i = 20;
                while (i-- > 0) {
                    outputStream.write(b2);
                 }
                outputStream.flush();
//                outputStream.close();
//                socket.close();
            } catch (Exception ex) {

            }
        }
    }



    public static void main(String[] args) throws IOException, ClassNotFoundException {
        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(4, 20, 60L,
                TimeUnit.SECONDS,
                new ArrayBlockingQueue<Runnable>(100),
                new ThreadPoolExecutor.AbortPolicy());

        SimpleConsumerClient simpleConsumerClient = new SimpleConsumerClient();
        simpleConsumerClient.new ConsumerTask().run();

//        threadPoolExecutor.execute(simpleConsumerClient.new ConsumerTask());
//        threadPoolExecutor.execute(simpleConsumerClient.new ConsumerTask());
//        threadPoolExecutor.execute(simpleConsumerClient.new ConsumerTask());
//        threadPoolExecutor.execute(simpleConsumerClient.new ConsumerTask());
//        threadPoolExecutor.execute(simpleConsumerClient.new ConsumerTask());
//        threadPoolExecutor.execute(simpleConsumerClient.new ConsumerTask());
//
//        threadPoolExecutor.shutdown();
    }
}
