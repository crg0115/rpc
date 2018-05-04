package com.nk.rpc.protocol;

import com.nk.rpc.protocol.entity.Protocol;
import com.nk.rpc.protocol.entity.Payload;
import com.nk.rpc.util.ByteUtil;
import com.nk.rpc.util.HessianUtil;
import com.nk.rpc.util.RpcUtil;

import java.io.IOException;

/**
 * 协议的定义
 *
 * @author Created by niuyang on 2017/12/30.
 */
public class ProtocolParser {

    /**
     * 构造协议头
     *
     * @return
     */
    private static byte[] buildProtocolHeader(byte[] body) {
        return buildProtocolHeader(RpcUtil.getRequestId(), body);
    }

    private static byte[] buildProtocolHeader(long id, byte[] body) {
        // 构造id
        long mid = id;
        byte[] b_mid = ByteUtil.longToByte(mid);

        // 构造荷载长度
        int len = body.length;
        byte[] b_len = ByteUtil.intToByte(len);

        byte[] b = new byte[ProtocolConstant.HEADER_SIZE];
        System.arraycopy(b_mid, 0, b, 0, b_mid.length);
        System.arraycopy(b_len, 0, b, b_mid.length, b_len.length);

        return b;
    }

    public static byte[] encode(long id, Payload payload) throws IOException {
        byte[] body = HessianUtil.serialize(payload);
        byte[] header = buildProtocolHeader(id, body);

        byte[] b = new byte[header.length + body.length];
        System.arraycopy(header, 0, b, 0, header.length);
        System.arraycopy(body, 0, b, header.length, body.length);

        return b;
    }

    public static byte[] encode(Payload payload) throws IOException {
        return encode(RpcUtil.getRequestId(), payload);
    }

    public static byte[] encode(long id, byte[] result) throws IOException {
        byte[] header = buildProtocolHeader(id, result);

        byte[] b = new byte[header.length + result.length];
        System.arraycopy(header, 0, b, 0, header.length);
        System.arraycopy(result, 0, b, header.length, result.length);

        return b;
    }

    public static Protocol decode(byte[] encodeBytes, int index) throws IOException {
        long id = ByteUtil.getEightBytes(encodeBytes, index);
        int len = ByteUtil.getFourBytes(encodeBytes, index + ProtocolConstant.ID_SIZE);
        byte[] b = new byte[len];
        System.arraycopy(encodeBytes, index + ProtocolConstant.HEADER_SIZE, b, 0, len);

        Payload payload = (Payload) HessianUtil.deserialize(b);
        Protocol protocol = new Protocol();
        protocol.setId(id);
        protocol.setPayloadSize(len);
        protocol.setPayload(payload);
        return protocol;
    }
}
