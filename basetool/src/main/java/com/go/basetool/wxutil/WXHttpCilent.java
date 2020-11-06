package com.go.basetool.wxutil;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.net.URISyntaxException;

public class WXHttpCilent {
    private static PoolingHttpClientConnectionManager pool = null;
    private static String AppSecret = "fda2c2df008f6937b0c06c0761fdf36e";
    private static String AppId = "wx80a823d84c20aa94";

    static {
        pool = new PoolingHttpClientConnectionManager();
        pool.setMaxTotal(100);
    }

    public static String getData(String code) {
        CloseableHttpClient build = HttpClients.custom().setConnectionManager(pool).build();
        URIBuilder uriBuilder = null;
        HttpGet get = null;
        CloseableHttpResponse execute = null;
        String ret = null;
        try {
            uriBuilder = new URIBuilder("https://api.weixin.qq.com/sns/jscode2session");
            uriBuilder.addParameter("appid", AppId);
            uriBuilder.addParameter("secret", AppSecret);
            uriBuilder.addParameter("js_code", code);
            uriBuilder.addParameter("grant_type", "authorization_code");
            get = new HttpGet(uriBuilder.build());
            get.setHeader("Content-Type","application/x-www-form-urlencoded;charset=UTF-8");
            execute = build.execute(get);
            //检测状态码
            if (execute.getStatusLine().getStatusCode() == 200) {
                //将响应转为string
                ret = EntityUtils.toString(execute.getEntity(), "utf8");
                System.out.println(ret);
            }
        } catch (URISyntaxException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (execute != null) {
                try {
                    execute.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return ret;
    }

    public static void main(String[] args) {
        String data = WXHttpCilent.getData("013ntI0w3qohfV2FXu0w3cr8sO1ntI0H");
        System.out.println(data);
    }
}
