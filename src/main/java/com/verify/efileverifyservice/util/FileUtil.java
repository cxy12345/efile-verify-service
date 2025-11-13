package com.verify.efileverifyservice.util;

import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.DateUtil;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

/**
 * @author loYuan
 * @date 2024/10/31
 */
public class FileUtil {


    /**
     * 文件内容加密为MD5值
     *
     * @param plainText 文件内容字符串
     * @return MD5值字符串
     */
    public static String stringToMD5(String plainText) {
        byte[] secretBytes = null;
        try {
            secretBytes = MessageDigest.getInstance("md5").digest(
                    plainText.getBytes(StandardCharsets.UTF_8));
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("md5算法异常！");
        }
        String md5code = new BigInteger(1, secretBytes).toString(16);
        for (int i = 0; i < 32 - md5code.length(); i++) {
            md5code = "0" + md5code;
        }
        return md5code;
    }

    /**
     * 文件二进制数组加密为Base64字符串
     *
     * @param content 文件二进制数组
     * @return Base64字符串
     */
    public static String fileContentEncode(byte[] content) {
        byte[] encodeBytes = Base64.getEncoder().encode(content);
        return new String(encodeBytes, StandardCharsets.UTF_8);
    }


    /**
     * 文件内容Base64解码为二进制数组
     *
     * @param fileContent 文件内容Base64字符串
     * @return 文件二进制数组
     */
    public static byte[] fileContentDecode(String fileContent) {
        return Base64.getDecoder().decode(fileContent.getBytes(StandardCharsets.UTF_8));
    }

    /**
     * 从文件路径截取文件名
     *
     * @param filePath 文件路径
     * @return 文件名
     */
    public static String filePathToFileName(String filePath) {
        return filePath.substring(filePath.lastIndexOf("/") + 1);
    }

    /**
     * 去除文件后缀名
     *
     * @param fileName 文件名
     * @return 去除后缀名的文件名
     */
    public static String removeFileExtension(String fileName) {
        int index = fileName.lastIndexOf(".");
        return fileName.substring(0, index);
    }

    /**
     * 获取文件扩展名
     *
     * @param fileName 文件名
     * @return 文件扩展名
     */
    public static String getFileExtension(String fileName) {
        int index = fileName.lastIndexOf(".");
        return fileName.substring(index + 1);
    }

    /**
     * 路径去除文件名
     *
     * @param filePath 文件路径
     */
    public static String removeFileName(String filePath) {
        //filePath =  "/a/b/f.txt" 得到 /a/b/
        // 找到最后一个斜杠的位置
        int lastSlashIndex = filePath.lastIndexOf('/');

        // 如果找到了斜杠，则返回去除文件名后的路径
        if (lastSlashIndex != -1) {
            return filePath.substring(0, lastSlashIndex + 1);
        }

        // 如果没有斜杠，则返回原始路径
        return filePath;
    }

    /**
     * 判断文件路径有几层目录
     * filePath: /a/b/f.txt 目前最多只有两层目录
     */
    public static int getFilePathDepth(String filePath) {
        if (filePath.startsWith("/")) {
            filePath = filePath.substring(1);
        }
        String[] split = filePath.split("/");
        return split.length - 1;
    }

    /**
     * 获取文件大小
     *
     * @param size 文件大小
     * @return 文件大小字符串
     */
    public static String getFileSize(Long size) {
        if (size < 1024) {
            return String.format("%d B", size);
        } else if (size < 1024 * 1024) {
            return String.format("%.2f KB", (double) size / 1024);
        } else if (size < 1024 * 1024 * 1024) {
            return String.format("%.2f MB", (double) size / (1024 * 1024));
        }
        return "";
    }

    public static String getFileFormat(String fileSuffix) {
        // 将输入的扩展名转换为小写以实现不区分大小写
        String lowerCaseExtension = fileSuffix.toLowerCase();

        // 图片文件扩展名列表
        String[] imageExtensions = {"jpg", "jpeg", "png", "gif", "bmp", "tiff"};
        // 文档扩展名列表
        String[] documentExtensions = {"doc", "docx", "xls", "xlsx", "ppt", "pptx", "pdf", "txt"};

        // 检查是否为图片文件
        for (String imgExt : imageExtensions) {
            if (lowerCaseExtension.equals(imgExt)) {
                return "I"; // Picture
            }
        }

        // 检查是否为文档文件
        for (String docExt : documentExtensions) {
            if (lowerCaseExtension.equals(docExt)) {
                return "D"; // Document
            }
        }

        // 如果不是图片也不是文档，则认为是其他文件
        return "F"; // File
    }


    /**
     * 根据文件后缀获取MIME类型
     */
    public static String getMimeTypeByExtension(String extension) {
        if (extension == null) {
            return "application/octet-stream";
        }

        extension = extension.toLowerCase();

        // 常见MIME类型映射
        Map<String, String> mimeTypes = new HashMap<>();
        // 图片
        mimeTypes.put("jpg", "image/jpeg");
        mimeTypes.put("jpeg", "image/jpeg");
        mimeTypes.put("png", "image/png");
        mimeTypes.put("gif", "image/gif");
        mimeTypes.put("bmp", "image/bmp");
        mimeTypes.put("svg", "image/svg+xml");
        mimeTypes.put("webp", "image/webp");

        // 文档
        mimeTypes.put("pdf", "application/pdf");
        mimeTypes.put("txt", "text/plain");
        mimeTypes.put("html", "text/html");
        mimeTypes.put("htm", "text/html");
        mimeTypes.put("css", "text/css");
        mimeTypes.put("js", "application/javascript");
        mimeTypes.put("json", "application/json");
        mimeTypes.put("xml", "application/xml");
        mimeTypes.put("md", "text/markdown");

        return mimeTypes.getOrDefault(extension, "application/octet-stream");
    }

    /**
     * 将byte数组转换为文件
     *
     * @param bytes    文件的byte数组
     * @param filePath 文件保存路径
     * @return 生成的文件
     * @throws IOException IO异常
     */
    public static File bytesToFile(byte[] bytes, String filePath) throws IOException {
        String date = DateUtil.format(DateUtil.date(), DatePattern.NORM_DATE_FORMAT);
        File file = new File(filePath + "/" + date);

        // 检查目标路径是否是一个已存在的目录
        if (file.exists() && file.isDirectory()) {
            throw new IOException("目标路径是一个已存在的目录: " + filePath);
        }

        // 确保父目录存在
        File parentDir = file.getParentFile();
        if (parentDir != null && !parentDir.exists()) {
            parentDir.mkdirs();
        }

        try (FileOutputStream fos = new FileOutputStream(file);
             BufferedOutputStream bos = new BufferedOutputStream(fos)) {
            bos.write(bytes);
            bos.flush();
        }
        return file;
    }

}
