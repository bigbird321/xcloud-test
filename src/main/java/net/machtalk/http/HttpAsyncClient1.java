package net.machtalk.http;

import org.apache.http.*;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.concurrent.FutureCallback;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.impl.conn.DefaultProxyRoutePlanner;
import org.apache.http.impl.nio.client.CloseableHttpAsyncClient;
import org.apache.http.impl.nio.client.HttpAsyncClientBuilder;
import org.apache.http.impl.nio.client.HttpAsyncClients;
import org.apache.http.impl.nio.conn.PoolingNHttpClientConnectionManager;
import org.apache.http.impl.nio.reactor.DefaultConnectingIOReactor;
import org.apache.http.impl.nio.reactor.IOReactorConfig;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.nio.conn.NoopIOSessionStrategy;
import org.apache.http.nio.conn.SchemeIOSessionStrategy;
import org.apache.http.nio.conn.ssl.SSLIOSessionStrategy;
import org.apache.http.nio.reactor.ConnectingIOReactor;
import org.apache.http.util.EntityUtils;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by zhaop on 2018/3/15
 */
public class HttpAsyncClient1 {

    public static void send(String url, Map<String,String> map, final String encoding) throws KeyManagementException, NoSuchAlgorithmException, ClientProtocolException, IOException {

        //绕过证书验证，处理https请求
        SSLContext sslcontext = createIgnoreVerifySSL();

        // 设置协议http和https对应的处理socket链接工厂的对象
        Registry<SchemeIOSessionStrategy> sessionStrategyRegistry = RegistryBuilder.<SchemeIOSessionStrategy>create()
                .register("http", NoopIOSessionStrategy.INSTANCE)
                .register("https", new SSLIOSessionStrategy(sslcontext))
                .build();
        //配置io线程
        IOReactorConfig ioReactorConfig = IOReactorConfig.custom().setIoThreadCount(Runtime.getRuntime().availableProcessors()).build();
        //设置连接池大小
        ConnectingIOReactor ioReactor;
        ioReactor = new DefaultConnectingIOReactor(ioReactorConfig);
        PoolingNHttpClientConnectionManager connManager = new PoolingNHttpClientConnectionManager(ioReactor, null, sessionStrategyRegistry, null);

        //创建自定义的httpclient对象
        //final CloseableHttpAsyncClient client = proxy("127.0.0.1", 8087).setConnectionManager(connManager).build();
        CloseableHttpAsyncClient client = HttpAsyncClients.createDefault();

        //创建post方式请求对象
        HttpPost httpPost = new HttpPost(url);

        //装填参数
        List<NameValuePair> nvps = new ArrayList<NameValuePair>();
        if(map!=null){
            for (Map.Entry<String, String> entry : map.entrySet()) {
                nvps.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
            }
        }
        //设置参数到请求对象中
        httpPost.setEntity(new UrlEncodedFormEntity(nvps, encoding));

        System.out.println("请求地址："+url);
        System.out.println("请求参数："+nvps.toString());

        //设置header信息
        //指定报文头【Content-type】、【User-Agent】
        httpPost.setHeader("Content-type", "application/x-www-form-urlencoded");
        httpPost.setHeader("User-Agent", "Mozilla/4.0 (compatible; MSIE 5.0; Windows NT; DigExt)");

        // Start the client
        client.start();
        //执行请求操作，并拿到结果（异步）
        client.execute(httpPost, new FutureCallback<HttpResponse>() {

            @Override
            public void failed(Exception ex) {
                //handler.failed(ex);
                System.err.println(Thread.currentThread().getName()+"--失败了--"+ex.getClass().getName()+"--"+ex.getMessage());
                close(client);
            }

            @Override
            public void completed(HttpResponse resp) {
                String body="";
                //这里使用EntityUtils.toString()方式时会大概率报错，原因：未接受完毕，链接已关
                try {
                    HttpEntity entity = resp.getEntity();
                    if (entity != null) {
                        final InputStream instream = entity.getContent();
                        try {
                            final StringBuilder sb = new StringBuilder();
                            final char[] tmp = new char[1024];
                            final Reader reader = new InputStreamReader(instream, encoding);
                            int l;
                            while ((l = reader.read(tmp)) != -1) {
                                sb.append(tmp, 0, l);
                            }
                            body = sb.toString();
                        } finally {
                            instream.close();
                            EntityUtils.consume(entity);
                        }
                    }
                } catch (ParseException | IOException e) {
                    e.printStackTrace();
                }
                //handler.completed(body);
                System.out.println(Thread.currentThread().getName()+"--获取内容："+body);
                close(client);
            }

            @Override
            public void cancelled() {
                //handler.cancelled();
                System.out.println(Thread.currentThread().getName()+"--取消了");
                close(client);
            }
        });
    }

    public static SSLContext createIgnoreVerifySSL() throws NoSuchAlgorithmException, KeyManagementException {
        SSLContext sc = SSLContext.getInstance("SSLv3");

        // 实现一个X509TrustManager接口，用于绕过验证，不用修改里面的方法
        X509TrustManager trustManager = new X509TrustManager() {
            @Override
            public void checkClientTrusted(
                    java.security.cert.X509Certificate[] paramArrayOfX509Certificate,
                    String paramString) throws CertificateException {
            }

            @Override
            public void checkServerTrusted(
                    java.security.cert.X509Certificate[] paramArrayOfX509Certificate,
                    String paramString) throws CertificateException {
            }

            @Override
            public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                return null;
            }
        };
        sc.init(null, new TrustManager[] { trustManager }, null);
        return sc;
    }

    public static HttpAsyncClientBuilder proxy(String hostOrIP, int port){
        // 依次是代理地址，代理端口号，协议类型
        HttpHost proxy = new HttpHost(hostOrIP, port, "http");
        DefaultProxyRoutePlanner routePlanner = new DefaultProxyRoutePlanner(proxy);
        return HttpAsyncClients.custom().setRoutePlanner(routePlanner);
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
            e.printStackTrace();
        }
    }


    public static void main(String[] args) throws KeyManagementException, NoSuchAlgorithmException, ClientProtocolException, IOException {
        //AsyncHandler handler = new AsyncHandler();
        String url = "http://php.weather.sina.com.cn/iframe/index/w_cl.php";
        Map<String, String> map = new HashMap<String, String>();
        map.put("code", "js");
        map.put("day", "0");
        map.put("city", "上海");
        map.put("dfc", "1");
        map.put("charset", "utf-8");
        send(url, map, "utf-8");

        System.out.println("-------------------------------------------------------");

        map.put("city", "北京");
        send(url, map, "utf-8");

        System.out.println("-------------------------------------------------------");

    }


}
