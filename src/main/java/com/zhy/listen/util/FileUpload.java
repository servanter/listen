package com.zhy.listen.util;

import java.io.File;
import java.util.Iterator;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

/**
 * 文件上传
 * 
 * @author zhanghongyan
 * 
 */
@Component
public class FileUpload {

    public static Logger logger = Logger.getLogger(FileUpload.class);

    /**
     * 头像物理路径
     */
    @Resource
    private String headFilePathPre;

    /**
     * web头像路径
     */
    @Resource
    private String headWebUrlPre;

    /**
     * 文件上传
     * 
     * @param request
     * @param response
     * @return 文件名(不包括web)
     * @throws Exception
     */
    private String uploadFile(HttpServletRequest request, HttpServletResponse response, String fileFolderPath, String webProcessUrl) {
        String result = "";
        try {
            String savePath = fileFolderPath;
            File f1 = new File(savePath);
            if (!f1.exists()) {
                f1.mkdirs();
            }
            DiskFileItemFactory fac = new DiskFileItemFactory();
            ServletFileUpload upload = new ServletFileUpload(fac);
            upload.setHeaderEncoding("utf-8");
            List fileList = null;
            try {
                fileList = upload.parseRequest(request);
            } catch (FileUploadException ex) {
            }
            Iterator<FileItem> it = fileList.iterator();
            String name = "";
            String extName = "";
            while (it.hasNext()) {
                FileItem item = it.next();
                if (!item.isFormField()) {
                    name = item.getName();
                    if (name == null || name.trim().equals("")) {
                        continue;
                    }
                    // 扩展名格式：
                    if (name.lastIndexOf(".") >= 0) {
                        extName = name.substring(name.lastIndexOf("."));
                    }
                    File file = null;
                    do {
                        // 生成文件名
                        name = String.valueOf(System.currentTimeMillis());
                        file = new File(savePath + name + extName);
                    } while (file.exists());
                    File saveFile = new File(savePath + name + extName);
                    try {
                        item.write(saveFile);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
            result = name + extName;
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("Upload file error");
        }

        return result;
    }

    /**
     * 上传头像
     * 
     * @param request
     * @param response
     * @return
     */
    public String uploadHead(HttpServletRequest request, HttpServletResponse response) {
        return uploadFile(request, response, headFilePathPre, headWebUrlPre);
    }

    /**
     * 获取当前web头像url
     * 
     * @return
     */
    public String getCurrentHeadWebUrlPre() {
        return headWebUrlPre;
    }
}
