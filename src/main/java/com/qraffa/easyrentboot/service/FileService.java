package com.qraffa.easyrentboot.service;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;

@Service
public class FileService {
    // server
    //// 设置文件存放路径
    //private final String SaveFilePath = "/var/www/image";
    //// 设置静态文件服务器的ip:端口
    //private final String Host = "http://115.29.201.93:8091/image";

    // local
    // 设置文件存放路径
    private final String SaveFilePath = "/var/www/image";
    // 设置静态文件服务器的ip:端口
    private final String Host = "http://115.29.201.93:8091/image";

    /**
     * 保存文件
     * @param multipartFile 接收到的文件参数
     * @param fileName 保存到磁盘的文件名
     * @return 文件对外访问的url
     * @throws Exception
     */
    public String saveFile(MultipartFile multipartFile, String fileName) throws Exception{
        // 文件全名
        String fileFullName = String.format("%s/%s",this.SaveFilePath,fileName);

        // 保存文件到磁盘
        File dest = new File(fileFullName);
        multipartFile.transferTo(dest);

        String url = String.format("%s/%s",this.Host,fileName);
        return url;
    }
}
