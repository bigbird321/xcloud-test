//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package net.machtalk.xcloud.utils;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.Collections;
import java.util.LinkedList;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ByteUtils {
    private static final Logger logger = LoggerFactory.getLogger(ByteUtils.class);
    private static final String[] strDigits = new String[]{"0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "a", "b", "c", "d", "e", "f"};

    public ByteUtils() {
    }

    public static byte[] hexStr2Bytes(String src) {
        if(src != null && src.length() != 0) {
            boolean m = false;
            boolean n = false;
            int l = src.length() / 2;
            byte[] ret = new byte[l];

            for(int i = 0; i < l; ++i) {
                int var6 = i * 2 + 1;
                int var7 = var6 + 1;
                ret[i] = uniteBytes(src.substring(i * 2, var6), src.substring(var6, var7));
            }

            return ret;
        } else {
            return null;
        }
    }

    public static String byteToHexStr(byte[] b) {
        if(b == null) {
            return "";
        } else {
            String hs = "";
            String stmp = "";

            for(int n = 0; n < b.length; ++n) {
                stmp = Integer.toHexString(b[n] & 255);
                if(stmp.length() == 1) {
                    hs = hs + "0" + stmp;
                } else {
                    hs = hs + stmp;
                }
            }

            return hs.toUpperCase();
        }
    }

    private static byte uniteBytes(String src0, String src1) {
        byte b0 = Byte.decode("0x" + src0).byteValue();
        b0 = (byte)(b0 << 4);
        byte b1 = Byte.decode("0x" + src1).byteValue();
        byte ret = (byte)(b0 | b1);
        return ret;
    }

    public static String decodeEmpower0(ByteArrayInputStream in) throws IOException {
        byte[] bytes = IOUtils.toByteArray(in);
        LinkedList list = new LinkedList();
        int i = 0;

        for(int len = bytes.length; i < len; ++i) {
            String b = Integer.toHexString(bytes[i] & 255);
            if(1 == b.length()) {
                list.add("0" + b);
            } else {
                list.add(b);
            }
        }

        Collections.reverse(list);
        return StringUtils.join(list, "");
    }

    public static int toUnsignedByte(byte s) {
        return s & 255;
    }

    public static int bytesToInt(byte[] byts) {
        boolean result = false;
        int result1 = byts[3] * 256 * 256 * 256 + byts[2] * 256 * 256 + byts[1] * 256 + byts[0];
        return result1;
    }

    public static byte[] arraycat(byte[] buf1, byte[] buf2) {
        byte[] bufret = null;
        int len1 = 0;
        int len2 = 0;
        if(buf1 != null) {
            len1 = buf1.length;
        }

        if(buf2 != null) {
            len2 = buf2.length;
        }

        if(len1 + len2 > 0) {
            bufret = new byte[len1 + len2];
        }

        if(len1 > 0) {
            System.arraycopy(buf1, 0, bufret, 0, len1);
        }

        if(len2 > 0) {
            System.arraycopy(buf2, 0, bufret, len1, len2);
        }

        return bufret;
    }

    public static byte[] intToByteArray(int i) {
        byte[] result = new byte[]{(byte)(i >> 24 & 255), (byte)(i >> 16 & 255), (byte)(i >> 8 & 255), (byte)(i & 255)};
        return result;
    }

    public static byte[] subBytes(byte[] src, int begin, int count) {
        if(src != null && begin < src.length && count <= src.length) {
            byte[] bs = new byte[count];

            for(int i = begin; i < begin + count; ++i) {
                bs[i - begin] = src[i];
            }

            return bs;
        } else {
            return null;
        }
    }

    public static byte checkSum(byte[] src) {
        int sum = 0;
        if(src != null) {
            for(int i = 0; i < src.length; ++i) {
                sum += toUnsignedByte(src[i]);
                if(sum > 256) {
                    sum -= 256;
                }
            }
        }

        logger.debug("CHECKSUM:" + byteToString(src) + "=" + sum);
        return (byte)sum;
    }

    public static String byteToString(byte[] bByte) {
        if(bByte == null) {
            return "";
        } else {
            StringBuffer sBuffer = new StringBuffer();

            for(int i = 0; i < bByte.length; ++i) {
                sBuffer.append(byteToArrayString(bByte[i]));
            }

            return sBuffer.toString();
        }
    }

    private static String byteToArrayString(byte bByte) {
        int iRet = bByte;
        if(bByte < 0) {
            iRet = bByte + 256;
        }

        int iD1 = iRet / 16;
        int iD2 = iRet % 16;
        return strDigits[iD1] + strDigits[iD2];
    }

    public static int byteArrayToInt(byte[] bByte) {
        String s = byteToString(bByte);
        return Integer.parseInt(s, 16);
    }

    public static String toHexString(String s) {
        String str = "";

        for(int i = 0; i < s.length(); ++i) {
            char ch = s.charAt(i);
            String s4 = Integer.toHexString(ch);
            str = str + s4;
        }

        return str;
    }

}
