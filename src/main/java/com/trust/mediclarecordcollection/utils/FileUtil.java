package com.trust.mediclarecordcollection.utils;

import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPClientConfig;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPReply;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.*;
import java.net.MalformedURLException;
import java.net.SocketException;
import java.net.URL;
import java.net.URLConnection;


@Component
public class FileUtil {

    private static final Logger logger = LoggerFactory.getLogger(FileUtil.class);


    public  boolean downFromURL(String url, String filePath) {
        logger.debug("down PDF from url");
        boolean isDownload = false;
        BufferedInputStream bis = null;
        BufferedOutputStream bos = null;
        try {
            URL fileURL = new URL(url);
            URLConnection conn =  fileURL.openConnection();
            conn.setConnectTimeout(10000);
            conn.setReadTimeout(10000);
            bis = new BufferedInputStream(fileURL.openStream());
            //logger.debug("filePath:1111111111111111111" + filePath);
            bos = new BufferedOutputStream(new FileOutputStream(new File(filePath)));
            byte[] buff = new byte[1024];
            int len = 0;
            while ((len = bis.read(buff)) != -1) {
                bos.write(buff, 0, len);
            }
            isDownload = true;
        } catch (MalformedURLException e) {
            logger.error(e.toString());
        } catch (IOException e) {
            logger.error(e.toString());
        } finally {
            if (bis != null) {
                try {
                    bis.close();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
            if (bos != null) {
                try {
                    bos.close();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        }
        return isDownload;
    }

    public  boolean downFromFtp2(String ftpIp, int ftpPort, String userName, String password, String remoteDir,
                                       String remoteFileName, String localFileName) {

        logger.debug("down PDF from ftp");
        boolean isDownload = false;
        String encoding = System.getProperty("file.encoding");
        System.out.println("encoding:" + encoding);
        FTPClient f = new FTPClient();
        try {
            f.connect(ftpIp, ftpPort);
            boolean b = f.login(userName, password);
            if (b) {
                logger.debug("login success...");
            } else {
                logger.error("login fail...");
                return isDownload;
            }
            f.setControlEncoding(encoding);
            f.setFileType(FTPClient.BINARY_FILE_TYPE);
            FTPClientConfig conf = new FTPClientConfig(FTPClientConfig.SYST_NT);
            conf.setServerLanguageCode("zh");
            int reply = f.getReplyCode();
            if (!FTPReply.isPositiveCompletion(reply)) {
                f.disconnect();
                logger.error("connect ftp server fail...");
                return isDownload;

            }
            f.changeWorkingDirectory(remoteDir);
            f.enterLocalPassiveMode();
            FTPFile[] files = f.listFiles();
            logger.debug("files.length:" + files.length);
            for (FTPFile ff : files) {
                if (remoteFileName.equalsIgnoreCase(ff.getName())) {
                    File loaclFile = new File(localFileName);
                    OutputStream os = new FileOutputStream(loaclFile);
                    f.retrieveFile(new String(ff.getName().getBytes("GBK"), "ISO-8859-1"), os);
                    os.close();
                    isDownload = true;
                }

            }
            if (!isDownload) {
                logger.error("FTP 目录下pdf文件不存在：" + remoteFileName);
            }
            f.logout();
        } catch (SocketException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (f.isConnected()) {
                try {
                    f.disconnect();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return isDownload;
    }


    public static void main(String[] args) {
//        String url = "http://oss.yizhen.cn/yizhen/hospital/0100651/2019/05/20/CT/11901790/JCBG300195484617806023.pdf";
        //ftp://jhecg:jhecg@192.16.2.75:21/2019/05/17/10346/10346.pdf
        FileUtil ff = new FileUtil();
//        ff.downFromFtp2("192.16.2.75",21,"jhecg","jhecg","2019/05/17/10346/","10346.pdf","本地路径");

//        SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//        String str = "2018-11-20T15:49:55";
//        String str1 = "2018-11-20T15:49:55";
//
//
//
//        String str2 = str.replaceAll("T"," ");
//       // String str3 = str1.replaceAll("T"," ");
//
//        System.out.println(str2);
//        long time = 0;
//        try {
//            time = sf.parse(str2).getTime();
//        } catch (ParseException e) {
//            e.printStackTrace();
//        }
//        System.out.println(time);



    }






}
