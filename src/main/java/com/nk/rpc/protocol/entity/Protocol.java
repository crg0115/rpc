package com.nk.rpc.protocol.entity;

/**
 * 协议的定义
 *
 * @author Created by niuyang on 2017/12/30.
 */
public class Protocol {

    /**
     * 请求id 8字节
     */
    private long id;

    /**
     * 荷载大小 4字节
     */
    private int payloadSize;

    private Payload payload;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getPayloadSize() {
        return payloadSize;
    }

    public void setPayloadSize(int payloadSize) {
        this.payloadSize = payloadSize;
    }

    public Payload getPayload() {
        return payload;
    }

    public void setPayload(Payload payload) {
        this.payload = payload;
    }
}
