package net.machtalk.test;

import org.apache.hadoop.hbase.util.Bytes;

/**
 * Created by zhaop on 2017/9/22.
 */
public class TestOpenTSDB {

    public static void main(String[] args) {

        final short flags = 0x8 | 0x3;

        System.out.println(System.currentTimeMillis());
        System.out.println(flags);
        System.out.println(0x8 | 0x7);
        System.out.println(1506071290 | 0xFFFFFFFF00000000L);


    }


    /*public static byte[] buildQualifier(final long timestamp, final short flags) {
        final long base_time;
        if ((timestamp & Const.SECOND_MASK) != 0) {
            // drop the ms timestamp to seconds to calculate the base timestamp
            base_time = ((timestamp / 1000) - ((timestamp / 1000)
                    % Const.MAX_TIMESPAN));
            final int qual = (int) (((timestamp - (base_time * 1000)
                    << (Const.MS_FLAG_BITS)) | flags) | Const.MS_FLAG);
            return Bytes.fromInt(qual);
        } else {
            base_time = (timestamp - (timestamp % Const.MAX_TIMESPAN));
            final short qual = (short) ((timestamp - base_time) << Const.FLAG_BITS
                    | flags);
            return Bytes.fromShort(qual);
        }
    }*/
}
