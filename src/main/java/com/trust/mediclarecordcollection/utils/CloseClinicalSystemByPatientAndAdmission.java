package com.trust.mediclarecordcollection.utils;

import org.apache.http.HttpEntity;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Arrays;

import static org.apache.http.HttpVersion.HTTP_1_0;


/**
 * @author wc
 */
@Component
public class CloseClinicalSystemByPatientAndAdmission {

    private static final Logger logger = LoggerFactory.getLogger(CloseClinicalSystemByPatientAndAdmission.class);
    private static final String AUTH_VALUE = "c3lzdGVtOnN5c3RlbQ==";
    private static final String AUTH_PARTNER_VALUE = "cGFydG5lcjpwYXJ0bmVy";

    public  int closeClinicalSystemByPatientIdAndAdmissionAndClinicalSysCode(String patientId, String admission,
                                                                                   String clinicalSys) throws Exception {
        logger.debug("patientId---:" + patientId);
        logger.debug("admission---:" + admission);
        logger.debug("clinicalSys---:" + clinicalSys);
        CloseableHttpClient httpclient = HttpClients.createDefault();
        HttpDelete httpDelete = new HttpDelete("http://10.20.13.79:8081/hospitals/12320200466286020Y/patients/" + patientId
                + "/admissions/" + admission + "/clinicalsystems/" + clinicalSys);
        httpDelete.setProtocolVersion(HTTP_1_0);
        httpDelete.addHeader("authorization", "Basic " + AUTH_PARTNER_VALUE);
        httpDelete.addHeader("cache-control", "no-cache");
        httpDelete.addHeader("accept-Language", "zh-CN,zh;q=0.8,en-US;q=0.5,en;q=0.3");
        httpDelete.addHeader("Content-Type", "application/json");
        httpDelete.addHeader("Accept", "application/xml");
        CloseableHttpResponse response = null;
        try {
            logger.debug(httpDelete.getRequestLine().toString());
            logger.debug(Arrays.toString(httpDelete.getAllHeaders()));
            logger.warn("↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓");
            response = httpclient.execute(httpDelete);
            logger.debug(response.getStatusLine().toString());
            int statusCode = response.getStatusLine().getStatusCode();
            logger.warn("Response status code: " + statusCode);
            HttpEntity resEntity = response.getEntity();
            if (resEntity != null) {
                logger.debug("Response content text: " + EntityUtils.toString(resEntity, "UTF-8"));
                if (statusCode != HttpStatus.SC_NO_CONTENT) {
                    String msg = EntityUtils.toString(resEntity, "UTF-8");
                    EntityUtils.consume(resEntity);
                    throw new RuntimeException(msg);
                }
            }
            EntityUtils.consume(resEntity);
            return statusCode;
        } finally {
            try {
                if (response != null) {
                    response.close();
                }
                if (httpclient != null) {
                    httpclient.close();
                }
            } catch (IOException e) {
                logger.error(e.toString());
            }
        }
    }
}
