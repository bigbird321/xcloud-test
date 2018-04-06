package net.machtalk.http;

import cn.zcyun.xcloud.utils.common.UUIDGen;
import com.alibaba.fastjson.JSONObject;
import org.apache.http.HttpResponse;
import org.apache.http.ParseException;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.concurrent.FutureCallback;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.nio.client.CloseableHttpAsyncClient;
import org.apache.http.impl.nio.client.HttpAsyncClients;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by zhaop on 2018/3/15
 */
public class HttpAsyncClient {
    private final static Logger logger = LoggerFactory.getLogger(HttpAsyncClient.class);

    public static final String CONTENT_TYPE = "application/json;charset=UTF-8";
    public static final String DEFAULT_CHARSET = "UTF-8";


    public static void sendPost(String url, Map<String, String> headers, String params) throws IOException {
        logger.info("Start asyncPost URL:{} headers:{} params:{}", new Object[]{url, headers, params});

        //创建自定义的httpclient对象
        CloseableHttpAsyncClient client = HttpAsyncClients.createDefault();

        //创建post方式请求对象
        HttpPost httpPost = new HttpPost(url);

        //设置header信息
        httpPost.setHeader("Content-type", CONTENT_TYPE);
        if (headers != null) {
            for (Map.Entry<String, String> entry : headers.entrySet()) {
                httpPost.setHeader(entry.getKey(), entry.getValue());
            }
        }

        //构建消息实体，Json格式
        StringEntity entity = new StringEntity(params, DEFAULT_CHARSET);
        entity.setContentType(CONTENT_TYPE);
        httpPost.setEntity(entity);

        //Start the client
        client.start();

        //执行异步请求
        client.execute(httpPost, new FutureCallback<HttpResponse>() {

            @Override
            public void failed(Exception ex) {
                logger.error("End asyncPost URL:{} headers:{} params:{} result:{}, exception:{}", new Object[]{url, headers, params, ex.getMessage(), ex});
                close(client);
            }

            @Override
            public void completed(HttpResponse resp) {
                String body = "";
                try {
                    body = EntityUtils.toString(resp.getEntity(), DEFAULT_CHARSET);
                } catch (ParseException | IOException e) {
                    logger.error("getEntity error:" + e.getMessage(), e);;
                }

                logger.info("End asyncPost URL:{} headers:{} params:{} result:{}", new Object[]{url, headers, params, body});
                close(client);
            }

            @Override
            public void cancelled() {
                logger.info("End asyncPost URL:{} headers:{} params:{} cancelled", new Object[]{url, headers, params});
                close(client);
            }
        });
    }


    /**
     * 关闭client对象
     *
     * @param client
     */
    private static void close(CloseableHttpAsyncClient client) {
        try {
            client.close();
        } catch (IOException e) {
            logger.error("close client error:" + e.getMessage(), e);
        }
    }


    public static void main(String[] args) throws IOException {
        String url2 = "http://php.weather.sina.com.cn/iframe/index/w_cl.php";
        String url1 = "http://php.weather.sina.com.cn";
        Map<String, String> map = new HashMap<String, String>();
        map.put("code", "js");
        map.put("day", "0");
        map.put("city", "上海");
        map.put("dfc", "1");
        map.put("charset", "utf-8");

        JSONObject params = new JSONObject();
        params.put("city", "上海111");
        params.put("requestId", UUIDGen.generate());

        sendPost(url1, map, params.toJSONString());

        System.out.println("-------------------------------------------------------");
        params.put("requestId", UUIDGen.generate());
        map.put("city", "北京");
        for (int i = 0; i < 111; i++){
            sendPost(url2, map, params.toJSONString());

            System.out.println("-------------------------------------------------------");
        }

    }


}
