package com.nk.rpc.util;

/**
 * @author Created by niuyang on 2017/12/30.
 */
public class ByteUtil {

    public static int getThreeBytes(byte[] input, int firstByte) {
        return ((input[firstByte] & 0xFF) << 16) + ((input[firstByte + 1] & 0xFF) << 8) +
                (input[firstByte + 2] & 0xFF);
    }

    public static int getFourBytes(byte[] input, int firstByte) {
        return ((input[firstByte] & 0xFF) << 24) + ((input[firstByte + 1] & 0xFF) << 16) +
                ((input[firstByte + 2] & 0xFF) << 8) + (input[firstByte + 3] & 0xFF);
    }

    public static long getEightBytes(byte[] input, int firstByte) {
        long a = 0L;

        for (int i = 0; i < 8; i++) {
            a += (input[firstByte + i] & 0xFF) << (8 * (7 - i));
        }

        return a;
    }

    public static int getTwoBytes(byte[] input, int firstByte) {
        return ((input[firstByte] & 0xFF) << 8) +  (input[firstByte + 1] & 0xFF);
    }

    public static byte[] getSubBytes(byte[] input, int index) {
        int len = input.length - index;
        byte[] b = new byte[len];
        System.arraycopy(input, index, b, 0, len);
        return b;
    }

    public static byte[] longToByte(long a) {
        byte[] b = new byte[8];
        for (int i = 0; i < 8; i++) {
            b[i] = (byte) ((a >> (8 * (7 - i))) & 0xFF);
        }
        return b;
    }

    public static byte[] intToByte(int a) {
        byte[] b = new byte[4];
        for (int i = 0; i < 4; i++) {
            b[i] = (byte) ((a >> (8 * (3 - i))) & 0xFF);
        }
        return b;
    }
}
