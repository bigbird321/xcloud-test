package net.machtalk.hbase;

/**
 * Created by zhaop on 2017/12/13
 */
public class Bytes {
    /**
     * Reads a big-endian 4-byte integer from an offset in the given array.
     * @param b The array to read from.
     * @param offset The offset in the array to start reading from.
     * @return An integer.
     * @throws IndexOutOfBoundsException if the byte array is too small.
     */
    public static int getInt(final byte[] b, final int offset) {
        return (b[offset + 0] & 0xFF) << 24
                | (b[offset + 1] & 0xFF) << 16
                | (b[offset + 2] & 0xFF) << 8
                | (b[offset + 3] & 0xFF) << 0;
    }
}
