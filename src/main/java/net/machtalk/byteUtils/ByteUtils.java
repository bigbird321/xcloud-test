package net.machtalk.byteUtils;

/**
 * Created by zhaop on 2018/3/5
 */
public class ByteUtils {
    //计算机内的存储都是利用二进制的补码进行存储,&0xff 保持二进制补码的一致性
    //高位在前，低位在后
    public static byte[] int2bytes(int num){
        byte[] result = new byte[4];
        result[0] = (byte)((num >>> 24) & 0xff);//取最高位
        result[1] = (byte)((num >>> 16)& 0xff );
        result[2] = (byte)((num >>> 8) & 0xff );
        result[3] = (byte)((num >>> 0) & 0xff );
        return result;
    }

    //高位在前，低位在后
    public static int bytes2int(byte[] bytes){
        int result = 0;
        if(bytes.length == 4){
            int a = (bytes[0] & 0xff) << 24;//取最高位
            int b = (bytes[1] & 0xff) << 16;
            int c = (bytes[2] & 0xff) << 8;
            int d = (bytes[3] & 0xff);
            result = a | b | c | d;
        }
        return result;
    }

    //高位在前，低位在后
    public static byte[] shortInt2bytes(int num){
        byte[] result = new byte[2];
        result[0] = (byte)((num >>> 8) & 0xff );
        result[1] = (byte)((num >>> 0) & 0xff );
        return result;
    }

    //高位在前，低位在后
    public static int bytes2shortInt(byte[] bytes){
        int result = 0;
        if(bytes.length == 2){
            int c = (bytes[0] & 0xff) << 8;
            int d = (bytes[1] & 0xff);
            result = c | d;
        }
        return result;
    }

    public static void main(String[] args){
        int a = 20001;
        System.out.println(a + "="+Integer.toBinaryString(a));

        byte[] bytes = ByteUtils.shortInt2bytes(a);
        for(int i = 0 ; i<bytes.length ; i++){
            System.out.println(bytes[i]);
        }
        a = ByteUtils.bytes2shortInt(bytes);
        System.out.println(a);

    }

}
