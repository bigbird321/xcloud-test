package net.machtalk.async;

import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang.math.RandomUtils;

import java.util.Random;
import java.util.concurrent.CompletableFuture;

/**
 * Created by zhaop on 2018/3/9
 */
public class HttpService {

    /**
     * 模拟http调用
     */
    Integer getHttpResult() throws Exception {
        //调用远程方法（远程方法耗时约2s，可以使用Thread.sleep模拟）
        Thread.sleep(2000);
        return RandomUtils.nextInt(10);
    }

    public CompletableFuture<String> getHttpData(String url){
        CompletableFuture<String> future = CompletableFuture.supplyAsync(() -> {
            try {
                Thread.sleep(2000);
                JSONObject result = new JSONObject();
                result.put("code", 0);

                return result.toJSONString();
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        });
        return future;
    }

}
