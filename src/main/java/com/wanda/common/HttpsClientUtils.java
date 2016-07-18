package com.wanda.common;

import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;

/**
 * this class is used to get https connection
 */
public class HttpsClientUtils {

    public static CloseableHttpClient newHttpsClient() {
        try {
            /*目前没有信任证书,如果有证书添加该段代码并在SSLContexts中加载
            KeyStore trustStore = KeyStore.getInstance(KeyStore.getDefaultType());
            FileInputStream instream = new FileInputStream(new File("d:\\tomcat.keystore"));
            try {
                // 加载keyStore
                trustStore.load(instream, "123456".toCharArray());
            } catch (CertificateException e) {
                e.printStackTrace();
            } finally {
                try {
                    instream.close();
                } catch (Exception ignore) {
                }
            }*/
           //使用自定义的SSLContext
            SSLContext sslcontext = createIgnoreVerifySSL();
            // 使用默认的SSLContexts相信自己的CA和所有自签名的证书
            // SSLContexts.custom()/*.loadTrustMaterial(trustStore, new TrustSelfSignedStrategy())*/.build();
            // Allow TLSv1 protocol only
            SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(
                    sslcontext,
                    new String[] { "TLSv1" },
                    null,
                    SSLConnectionSocketFactory.getDefaultHostnameVerifier());
            return  HttpClients.custom().setSSLSocketFactory(sslsf).build();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 绕过验证
     *
     * @return
     * @throws NoSuchAlgorithmException
     * @throws KeyManagementException
     */
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
}


