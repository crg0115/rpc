package com.nk.rpc;

import com.nk.rpc.net.Client;
import com.nk.rpc.service.TestService;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @author Created by niuyang on 2018/5/4.
 */
public class ClientMain {



    public static void main(String[] args) throws Exception {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    new Client().run();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();

        Thread.sleep(100L);

        ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:rpc.xml");
        int i = 0;
        TestService testService = (TestService) applicationContext.getBean("testService");
        while (i++ < 20) {
            int add = testService.add(11, 11);
            System.out.println(add);
        }
    }
}
