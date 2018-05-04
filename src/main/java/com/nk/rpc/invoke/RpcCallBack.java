package com.nk.rpc.invoke;

import com.nk.rpc.protocol.entity.Protocol;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class RpcCallBack {

    private byte[] request;
    private Protocol response;

    private Lock lock = new ReentrantLock();
    private Condition finish = lock.newCondition();

    public RpcCallBack(byte[] request) {
        this.request = request;
    }

    public Object start() {
        try {
            lock.lock();
            await();
            if (response != null && response.getPayload() != null) {
                return response.getPayload().getReturnValue();
            }
            return null;
        } finally {
            lock.unlock();
        }
    }

    public void over(Protocol reponse) {
        try {
            lock.lock();
            finish.signal();
            this.response = reponse;
        } finally {
            lock.unlock();
        }
    }

    private void await() {
        boolean timeout = false;
        try {
            timeout = finish.await(3 * 1000L, TimeUnit.MILLISECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        if (!timeout) {
            throw new RuntimeException("rpc timeout");
        }
    }

}
