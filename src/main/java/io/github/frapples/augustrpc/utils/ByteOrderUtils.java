package io.github.frapples.augustrpc.utils;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

/**
 * @author Frapples <isfrapples@outlook.com>
 * @date 2018/7/26
 */
public class ByteOrderUtils {
    // Thanks for: https://stackoverflow.com/questions/6374915/java-convert-int-to-byte-array-of-4-bytes

    public static byte[] intToBytesWithBigEndian(int n) {
        return ByteBuffer.allocate(Integer.BYTES).order(ByteOrder.BIG_ENDIAN).putInt(n).array();
    }

    public static int bytesToIntWithBigEndian(byte[] bytes) {
        return ByteBuffer.wrap(bytes).order(ByteOrder.BIG_ENDIAN).getInt();
    }


}
