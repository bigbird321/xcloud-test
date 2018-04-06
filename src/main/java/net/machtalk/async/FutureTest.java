package net.machtalk.async;

import com.alibaba.fastjson.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.*;


/**
 * Created by zhaop on 2018/3/9
 * 异步Future，半异步调用
 */
public class FutureTest {

    final static ExecutorService executor = Executors.newFixedThreadPool(2);

    public static void main(String[] args) throws Exception {
        RpcService rpcService = new RpcService();
        HttpService httpService = new HttpService();

        Future<JSONObject> future1 = null;
        Future<Integer> future2 = null;

        try {
            long start = System.currentTimeMillis();
            /*future1 = executor.submit(new Callable<JSONObject>() {
                public JSONObject call() throws Exception {
                    return rpcService.getRpcResult();
                }
            });*/
            future1 = executor.submit(() -> rpcService.getRpcResult());
            future2 = executor.submit(() -> httpService.getHttpResult());

            //耗时1s
            JSONObject result1 = future1.get(3000, TimeUnit.MILLISECONDS);
            //耗时2s
            Integer result2 = future2.get(3000, TimeUnit.MILLISECONDS);
            //总耗时2s
            System.out.println("FutureTest cost:" + (System.currentTimeMillis() - start) + " ms");

        } catch (Exception e) {
            if (future1 != null) {
                future1.cancel(true);
            }
            if (future2 != null) {
                future2.cancel(true);
            }
            throw new RuntimeException(e);
        }

    }

}
