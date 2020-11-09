package com.tensquare.articlecrawler.utils;

import java.io.*;
import java.net.URL;
import java.net.URLConnection;

/**
 * 下载工具类
 */
public class DownloadUtil {
    /**
     * 下载
     * @param urlStr 下载地址
     * @param filename 保存的文件名
     * @param savePath 保存的路径
     * @throws IOException
     */
    public static void download(String urlStr, String filename, String savePath) throws IOException {
        URL url = new URL(urlStr);
        //打开url连接
        URLConnection connection = url.openConnection();
        //请求超时时间
        connection.setConnectTimeout(5000);
        //输入流
        InputStream in = connection.getInputStream();
        
        //文件
        File file = new File(savePath);
        if (!file.exists()) {
            file.mkdirs();
        }
        OutputStream out = new FileOutputStream(file.getPath() + "\\" + filename);

        //缓冲数据
        byte[] bytes = new byte[1024];
        //数据长度
        int len;
        //先读到bytes中
        while ((len = in.read(bytes)) != -1) {
            //再从bytes中写入文件
            out.write(bytes, 0, len);
        }
        //关闭IO
        out.close();
        in.close();
    }
}