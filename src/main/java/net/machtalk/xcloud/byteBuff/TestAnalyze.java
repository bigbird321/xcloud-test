package net.machtalk.xcloud.byteBuff;

import ch.qos.logback.core.encoder.ByteArrayUtil;
import net.machtalk.xcloud.utils.ByteUtils;

import java.nio.ByteBuffer;

/**
 * Created by zhaop on 2017/3/6.
 */
public class TestAnalyze {

    public static void main( String[] args ){
        String hexStr = "161341117";
        byte[] data = ByteUtils.hexStr2Bytes(hexStr);
        byte[] daBytes = new byte[2];
        System.arraycopy(data, 0, daBytes, 0, daBytes.length);
    }

}
