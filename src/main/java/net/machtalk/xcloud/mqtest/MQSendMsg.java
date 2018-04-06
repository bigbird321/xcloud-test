package net.machtalk.xcloud.mqtest;

import net.machtalk.xcloud.msgqueue.MQGateWayService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created with IntelliJ IDEA.
 * User: zhaop
 * Date: 15-9-22
 * Time: 下午4:54
 */
@Component
public class MQSendMsg {
    @Autowired
    private MQGateWayService mqGateWayService;

    public void sendMsg(){
        String message = "{data:{did:\"110000001000000574\",model:\"300SCT007P01\",timestamp:\"1442591984720\",values:[{dvid:1,value:\"1\"},{dvid:3,value:\"225\"}],devList:[]},type:\"post\"}";
        mqGateWayService.sendHbaseDataMessage(message);
    }

    public static void main(String[] args) {
        MQSendMsg mqSendMsg = new MQSendMsg();
        mqSendMsg.sendMsg();
    }

}
