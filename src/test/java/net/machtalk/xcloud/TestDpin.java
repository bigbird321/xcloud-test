package net.machtalk.xcloud;

import net.machtalk.xcloud.utils.AESCBCEncoder;

/**
 * Created with IntelliJ IDEA.
 * User: zhaop
 * Date: 17-1-11
 * Time: 下午1:39
 * To change this template use File | Settings | File Templates.
 */
public class TestDpin extends junit.framework.TestCase {

    public void test(){
        //key是pin码的后16位，拼接成32位
        String dpin = "0ed50f53cb63d3222dd775b79505b502";
        String pin = "dc0047765e8b9d21ed512b6ad5c104ff";
        String timestamp = "1484111374";
        String subPin = pin.substring(16);
        subPin = subPin + subPin;
        try {
            String random = new String(AESCBCEncoder.desEncrypt(AESCBCEncoder.hexStringToByte(dpin), subPin.getBytes()));
            System.out.println("解密key:" + subPin + ",dpin:" + dpin + ",random:" + random);
            if (!timestamp.equals(random)) {
                //无效的设备码
                System.out.println("dpin error");
                return;
            }
            System.out.println("dpin right");
        } catch (Exception e) {
            System.out.println("解密key:" + subPin + ",dpin:" + dpin + "error:" + e.getMessage());
            //AES解密错误
            return ;
        }
    }


}
