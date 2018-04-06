package net.machtalk.async;

import com.alibaba.fastjson.JSONObject;

/**
 * Created by zhaop on 2018/3/9
 */
public class RpcService {

    /**
     * 模拟RPC调用
     */
    JSONObject getRpcResult() throws Exception {
        //调用远程方法（远程方法耗时约1s，可以使用Thread.sleep模拟）
        Thread.sleep(1000);

        JSONObject result = new JSONObject();
        result.put("code", 0);

        return result;
    }

}
