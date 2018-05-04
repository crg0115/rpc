package com.nk.rpc.util;

import java.util.concurrent.atomic.AtomicLong;

/**
 * @author Created by niuyang on 2018/2/2.
 */
public class RpcUtil {

    private static final AtomicLong INVOKE_ID = new AtomicLong(0L);

    /**
     * 获取请求的id
     *
     * @return
     */
    public static long getRequestId() {
        return INVOKE_ID.getAndIncrement();
    }
}
