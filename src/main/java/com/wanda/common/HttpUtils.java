package com.wanda.common;

import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Map;

/**
 * Created by 钱斌 on 2016/6/14.
 */
public class HttpUtils {

    private static final String EMPTY_STR = "";
    private static final String UTF_8 = "UTF-8";

    public static String httpGetRequest(String url){
        HttpGet httpGet = new HttpGet(url);
        return getResult(httpGet);
    }

    public static String httpGetRequest(String url, Map<String, Object> params)  {
        HttpGet httpGet = getHttpGetRequest(url,params);
        return getResult(httpGet);
    }

    public static HttpGet getHttpGetRequest(String url, Map<String, Object> params)  {
        URIBuilder ub = new URIBuilder();
        ub.setPath(url);
        ArrayList<NameValuePair> pairs = covertParams2NVPS(params);
        ub.setParameters(pairs);
        HttpGet httpGet = null;
        try {
            httpGet = new HttpGet(ub.build());
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
        return httpGet;
    }


    public static String httpPostRequest(String url){
        HttpPost httpPost = new HttpPost(url);
        return getResult(httpPost);
    }

    public static String httpPostRequest(String url, Map<String, Object> params) throws UnsupportedEncodingException {
        HttpPost httpPost = new HttpPost(url);
        ArrayList<NameValuePair> pairs = covertParams2NVPS(params);
        httpPost.setEntity(new UrlEncodedFormEntity(pairs, UTF_8));
        return getResult(httpPost);
    }

    public static String httpPostRequest(String url, Map<String, Object> headers,
                                         Map<String, Object> params) throws UnsupportedEncodingException{
        HttpPost httpPost = new HttpPost(url);
        for (Map.Entry<String, Object> param: headers.entrySet()) {
            httpPost.addHeader(param.getKey(), param.getValue().toString());
        }
        ArrayList<NameValuePair> pairs = covertParams2NVPS(params);
        httpPost.setEntity(new UrlEncodedFormEntity(pairs, UTF_8));
        return getResult(httpPost);
    }


    private static ArrayList<NameValuePair> covertParams2NVPS(Map<String, Object> params){
        ArrayList<NameValuePair> pairs = new ArrayList<NameValuePair>();
        for (Map.Entry<String, Object> param: params.entrySet()) {
            pairs.add(new BasicNameValuePair(param.getKey(), param.getValue().toString()));
        }
        return pairs;
    }


    /**
     * 处理Http请求
     * @param request
     * @return
     */
    private static String getResult(HttpRequestBase request){
        CloseableHttpClient httpClient = HttpClients.createDefault();
        //用于https请求
        // CloseableHttpClient httpClient = HttpsClientUtils.newHttpsClient();
        try{
            CloseableHttpResponse response = httpClient.execute(request);
            //response.getStatusLine().getStatusCode();
            HttpEntity entity = response.getEntity();
            if(entity!=null){
                //long len = entity.getContentLength();// -1 表示长度未知
                String result = EntityUtils.toString(entity);
                response.close();
                httpClient.close();
                return result;
            }
        } catch (ClientProtocolException e){
            e.printStackTrace();
        } catch (IOException e){
            e.printStackTrace();
        }
        return EMPTY_STR;
    }

}