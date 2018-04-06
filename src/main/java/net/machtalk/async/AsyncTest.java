package net.machtalk.async;

import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;
import org.apache.http.HttpException;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.concurrent.FutureCallback;
import org.apache.http.impl.nio.client.CloseableHttpAsyncClient;
import org.apache.http.impl.nio.client.HttpAsyncClients;
import org.apache.http.nio.IOControl;
import org.apache.http.nio.client.HttpAsyncClient;
import org.apache.http.nio.client.methods.AsyncCharConsumer;
import org.apache.http.nio.client.methods.HttpAsyncMethods;
import org.apache.http.nio.protocol.HttpAsyncRequestProducer;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.nio.CharBuffer;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

/**
 * Created by zhaop on 2018/3/9
 * 全异步调用(CompletableFuture java8)
 */
public class AsyncTest {

    public static CompletableFuture getHttpData(String url) throws IOException, InterruptedException {
        CloseableHttpAsyncClient httpAsyncClient = HttpAsyncClients.createDefault();

        try {
            httpAsyncClient.start();

            CompletableFuture asyncFuture = new CompletableFuture();

            //HttpPost post = new HttpPost(url);
            HttpGet request = new HttpGet(url);

            HttpAsyncRequestProducer producer = HttpAsyncMethods.create(request);

            AsyncCharConsumer<HttpResponse> consumer = new AsyncCharConsumer<HttpResponse>() {
                HttpResponse response;

                @Override
                protected HttpResponse buildResult(final HttpContext context) {
                    return this.response;
                }

                @Override
                protected void onResponseReceived(HttpResponse response) throws HttpException, IOException {
                    this.response = response;
                }

                @Override
                protected void onCharReceived(CharBuffer charBuffer, IOControl ioControl) throws IOException {
                    System.out.println("onCharReceived:" + charBuffer.toString());
                }
            };

            final CountDownLatch latch = new CountDownLatch(1);
            FutureCallback callback = new FutureCallback<HttpResponse>() {
                @Override
                public void completed(HttpResponse response){
                    latch.countDown();
                    System.out.println(request.getRequestLine() + "->" + response.getStatusLine());
                    try {
                        String content = EntityUtils.toString(response.getEntity());
                        asyncFuture.complete(content);
                        System.out.println("response content is " + content);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void failed(Exception e) {
                    latch.countDown();
                    System.out.println(request.getRequestLine() + "->" + e);
                }

                @Override
                public void cancelled() {
                    latch.countDown();
                    System.out.println(request.getRequestLine() + " cancelled");
                }
            };

            httpAsyncClient.execute(producer, consumer, callback);

            latch.await();

            return asyncFuture;

        } finally {
            httpAsyncClient.close();
        }

    }

    public static String test1() throws Exception {
        HttpService httpService = new HttpService();
        /**
         * 场景1 两个以上服务并发异步调用，返回CompletableFuture,不阻塞主线程
         * 并且两个服务也是异步非阻塞调用
         */
        CompletableFuture future1 = httpService.getHttpData("https://www.alipay.com/");
        CompletableFuture future2 = httpService.getHttpData("http://www.apache.org/");
        CompletableFuture future3 = httpService.getHttpData("http://www.apache.org/");

        List<CompletableFuture> futureList = Lists.newArrayList(future1, future2, future3);

        CompletableFuture[] futureArray = futureList.toArray(new CompletableFuture[futureList.size()]);

        CompletableFuture allDoneFuture = CompletableFuture.allOf(futureArray);


        CompletableFuture future4 = allDoneFuture.thenApply(v -> {
            List result = futureList.stream().map(CompletableFuture::join).collect(Collectors.toList());
            //注意顺序
            String result1 = (String)result.get(0);
            String result2 = (String)result.get(1);
            String result3 = (String)result.get(2);
            //处理业务....
            return result1 + result2 + result3;

        }).exceptionally(e -> {
            System.out.println(e);
            return "";
        });

        return future4.get().toString();
    }

    public static void main(String[] args){
        try {
            CompletableFuture future = AsyncTest.getHttpData("http://192.168.0.188:2007/data/stats/kpwjlv/111110001000005714/4/sum/day/2017-10-01");
            System.out.println(future.get());

            long start = System.currentTimeMillis();

            System.out.println(test1());

            System.out.println("cost:" + (System.currentTimeMillis() - start) + " ms");

        } catch (Exception e) {
            e.printStackTrace();
        }


    }
}
