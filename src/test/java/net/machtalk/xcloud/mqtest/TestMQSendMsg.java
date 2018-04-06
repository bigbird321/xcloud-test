package net.machtalk.xcloud.mqtest;

import net.machtalk.xcloud.msgqueue.MQGateWayService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created with IntelliJ IDEA.
 * User: zhaop
 * Date: 15-9-22
 * Time: 下午5:01
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({
        "classpath:applicationContext.xml","/app*.xml"
})
public class TestMQSendMsg {
    @Autowired
    private MQGateWayService mqGateWayService;

    protected void setUp(){

    }

    @Test   //标明是测试方法
    @Transactional   //标明此方法需使用事务
    @Rollback(false)  //标明使用完此方法后事务不回滚,true时为回滚
    public void testMQSendMessage(){
        System.out.println("run TestCase...");
        String message = "{data:{did:\"110000001000000574\",model:\"300SCT007P01\",timestamp:\"1442591984720\",values:[{dvid:1,value:\"1\"},{dvid:3,value:\"225\"}],devList:[]},type:\"post\"}";
        //mqSendMsg.sendMsg();
        mqGateWayService.sendHbaseDataMessage(message);
    }

    protected void tearDown(){

    }
}
