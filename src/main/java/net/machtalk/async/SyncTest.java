package net.machtalk.async;

import com.alibaba.fastjson.JSONObject;

/**
 * Created by zhaop on 2018/3/9
 * 同步调用
 */
public class SyncTest {

    public static void main(String[] args) throws Exception {
        RpcService rpcService = new RpcService();
        HttpService httpService = new HttpService();

        long start = System.currentTimeMillis();

        //耗时1s
        JSONObject result1 = rpcService.getRpcResult();
        //耗时2s
        Integer result2 = httpService.getHttpResult();
        //总耗时3s

        System.out.println("SyncTest cost:" + (System.currentTimeMillis() - start) + " ms");
    }

}
