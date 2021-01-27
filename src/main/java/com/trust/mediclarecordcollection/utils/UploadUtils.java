package com.trust.mediclarecordcollection.utils;

import org.apache.http.HttpEntity;
import org.apache.http.HttpStatus;
import org.apache.http.ProtocolVersion;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.Arrays;


/**
 * @author wc
 */
@Component
public class UploadUtils {

    private static final Logger logger = LoggerFactory.getLogger(UploadUtils.class);
    private static final String AUTH_PARTNER_VALUE = "cGFydG5lcjpwYXJ0bmVy";

    //安全密钥
    private static final String AUTH_VALUE = "cGFydG5lcjpwYXJ0bmVy";
    //访问HTTP协议及版本
    private static final ProtocolVersion HTTP_1_0 = new ProtocolVersion("HTTP", 1, 0);
    public  String runGETExample(String getAddress,String docfileid) throws Exception {
        if(getAddress==null||getAddress.length()==0)
            throw new IllegalArgumentException("getAddress can not be null");
        CloseableHttpClient httpclient = HttpClients.createDefault();
        try {
            HttpGet httpGet = new HttpGet(getAddress+"/"+docfileid);
            httpGet.setProtocolVersion(HTTP_1_0);
            // 必须增加的request头部信息
            httpGet.addHeader("authorization", "Basic "+AUTH_VALUE);
            httpGet.addHeader("cache-control", "no-cache");
            httpGet.addHeader("accept", ContentType.APPLICATION_XML.getMimeType());
            httpGet.addHeader("accept-Language","zh-CN,zh;q=0.8,en-US;q=0.5,en;q=0.3");
            System.out.println( httpGet.getRequestLine());
            System.out.println(Arrays.toString(httpGet.getAllHeaders()));
            System.out.println("↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓");
            CloseableHttpResponse response = httpclient.execute(httpGet);
            try {
                //获得响应报文的状态值。
                int statusCode = response.getStatusLine().getStatusCode();
                System.out.println("Response status code: "+ statusCode);

                //读取响应报文的内容
                HttpEntity resEntity = response.getEntity();
                if (resEntity != null) {
                    System.out.println("Response content text: "+ EntityUtils.toString(resEntity,"UTF-8"));
                }
                //如果读取内容一定要记得释放响应报文。该函数就是关闭resEntity.getContent()这个InputStream用的。
                EntityUtils.consume(resEntity);
                return String.valueOf(statusCode);
            } finally {
                response.close();
            }
        }finally{
            httpclient.close();
        }
    }


    public int  runPOSTExample(String postAddress,String docxmlfile,String docfile) throws Exception {
        if(postAddress==null||postAddress.length()==0) {
            throw new IllegalArgumentException("postAddress can not be null");
        }
        //新建HTTPClient对象。这个对象要在finally里面关闭。
            CloseableHttpClient httpclient = HttpClients.createDefault();
            try {
                HttpPost httppost = new HttpPost(postAddress);
                httppost.setProtocolVersion(HTTP_1_0);
                httppost.addHeader("authorization", "Basic " + AUTH_VALUE);
                httppost.addHeader("cache-control", "no-cache");
                httppost.addHeader("accept", ContentType.APPLICATION_XML.getMimeType());
                httppost.addHeader("accept-Language", "zh-CN,zh;q=0.8,en-US;q=0.5,en;q=0.3");

                HttpEntity reqEntity = MultipartEntityBuilder.create()
                        .addPart("medicaldocxml", new FileBody(new File(docxmlfile)))
                        .addPart("medicaldocfile", new FileBody(new File(docfile))).build();
                httppost.setEntity(reqEntity);
                logger.warn("↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓");
                CloseableHttpResponse response = httpclient.execute(httppost);
                try {
                    int statusCode = response.getStatusLine().getStatusCode();
                    logger.warn("Response status code: " + statusCode);
                    HttpEntity resEntity = response.getEntity();
                    if (resEntity != null) {
                        if (statusCode != HttpStatus.SC_ACCEPTED) {
                            String msg = EntityUtils.toString(resEntity, "UTF-8");
                            EntityUtils.consume(resEntity);
                            throw new RuntimeException(msg);
                        }
                    }
                    // 如果读取内容一定要记得释放响应报文。该函数就是关闭resEntity.getContent()这个InputStream用的。
                    EntityUtils.consume(resEntity);

                    return statusCode;
                } finally {
                    response.close();
                }
            } finally {
                httpclient.close();
            }
        }


   /* public static void main(String[] args) {
        UploadUtils ps = new UploadUtils();
        try {
            ps.runPOSTExample("http://172.20.1.126:8081/medicalarchivefiles","E:\\upload\\37fba9b78f4f4f1aaa3f7ea8bd78a91f.xml","E:\\upload\\37fba9b78f4f4f1aaa3f7ea8bd78a91f.pdf");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }*/


}
