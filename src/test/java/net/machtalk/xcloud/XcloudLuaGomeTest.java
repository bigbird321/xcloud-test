/*
package net.machtalk.xcloud;

import junit.framework.TestCase;
import net.machtalk.xcloud.thrift.service.cs.LuaParseService;
import org.apache.thrift.TException;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransport;
import org.apache.thrift.transport.TTransportException;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

*/
/**
 * Created by zhaop on 2017/4/12.
 *//*


public class XcloudLuaGomeTest extends TestCase {
    */
/** 服务的IP *//*

    private String serviceIP = "192.168.0.115";
    */
/** 服务的端口 *//*

    private int servicePort = 10010;
    */
/** 超时设置 *//*

    private int timeOut = 3000;


    public void test() throws TException {
        System.out.println("run TestCase...");
        TTransport transport = new TSocket(this.serviceIP,
                this.servicePort, this.timeOut);
        transport.open();
        LuaParseService.Client luaParseService = new LuaParseService.Client(new TBinaryProtocol(transport));
        System.out.println(luaParseService.binaryToJson("1","AABBCCDD"));
        System.out.println(luaParseService.jsonToBinary("2","{\"as\":\"\"}"));

    }

}
*/
