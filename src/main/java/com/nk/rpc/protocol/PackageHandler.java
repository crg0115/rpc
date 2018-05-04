package com.nk.rpc.protocol;

import com.nk.rpc.protocol.entity.Protocol;
import com.nk.rpc.util.ByteUtil;
import io.netty.buffer.ByteBuf;
import io.netty.channel.Channel;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Created by niuyang on 2018/5/4.
 */
public class PackageHandler {

    private Map<Channel, byte[]> channelBytesMap = new ConcurrentHashMap<>();

    /**
     * 读取字节数据
     *
     * @param buf
     * @param channel
     * @return
     */
    public byte[] readBytes(Channel channel, ByteBuf buf) {
        // 本次channel中的数据长度
        int len_buf = buf.writerIndex();

        // 读取上一次多余的数据
        byte[] b = channelBytesMap.get(channel);
        channelBytesMap.remove(channel);
        int len_b = 0;
        if (b != null) {
            len_b = b.length;
        }

        byte[] bb = new byte[len_buf + len_b];
        if (len_b > 0) {
            System.arraycopy(b, 0, bb, 0, len_b);
        }

        for (int i = 0; i < len_buf; i++) {
            bb[i + len_b] = buf.readByte();
        }

        return bb;
    }

    /**
     * 读取字节数据
     *
     * @param buf
     * @param channel
     * @return
     */
    public List<Protocol> readBytes2(Channel channel, ByteBuf buf) throws IOException {
        // 本次channel中的数据长度
        int len_buf = buf.writerIndex();

        // 读取上一次多余的数据
        byte[] b = channelBytesMap.get(channel);
        channelBytesMap.remove(channel);
        int len_b = 0;
        if (b != null) {
            len_b = b.length;
        }

        byte[] bb = new byte[len_buf + len_b];
        if (len_b > 0) {
            System.arraycopy(b, 0, bb, 0, len_b);
        }

        for (int i = 0; i < len_buf; i++) {
            bb[i + len_b] = buf.readByte();
        }

        // 处理沾包和半包
        List<Protocol> protocolList = new ArrayList<>();
        int len = bb.length;
        int index = 0;
        while (index < len) {
            if (len - index <= ProtocolConstant.HEADER_SIZE) {
                channelBytesMap.put(channel, ByteUtil.getSubBytes(bb, index));
                break;
            }

            int payloadLen = ByteUtil.getFourBytes(bb, index + ProtocolConstant.ID_SIZE);
            if (len < payloadLen + ProtocolConstant.HEADER_SIZE + index) {
                channelBytesMap.put(channel, ByteUtil.getSubBytes(bb, index));
                break;
            }

            Protocol protocol = ProtocolParser.decode(bb, index);
            protocolList.add(protocol);

            index += ProtocolConstant.HEADER_SIZE + payloadLen;
        }

        return protocolList;
    }
}
