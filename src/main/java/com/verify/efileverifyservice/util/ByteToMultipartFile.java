package com.verify.efileverifyservice.util;

import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.file.Files;

/**
 * @author loYuan
 * @date 2024/10/30
 */
public class ByteToMultipartFile implements MultipartFile, Serializable {

    private final byte[] content; // 存储字节数组的内容
    private final String name; // MultipartFile 的名称
    private final String originalFilename; // 原始文件名
    private final String contentType; // 文件的内容类型

    /**
     * 构造函数，初始化 MultipartFile 对象。
     *
     * @param content          字节数组内容
     * @param name             MultipartFile 的名称
     * @param originalFilename 原始文件名
     * @param contentType      文件的内容类型
     */
    public ByteToMultipartFile(byte[] content, String name, String originalFilename, String contentType) {
        this.content = content;
        this.name = name;
        this.originalFilename = originalFilename;
        this.contentType = contentType;
    }

    /**
     * 获取 MultipartFile 的名称。
     *
     * @return MultipartFile 的名称
     */
    @Override
    public String getName() {
        return name;
    }

    /**
     * 获取原始文件名。
     *
     * @return 原始文件名
     */
    @Override
    public String getOriginalFilename() {
        return originalFilename;
    }

    /**
     * 获取文件的内容类型。
     *
     * @return 文件的内容类型
     */
    @Override
    public String getContentType() {
        return contentType;
    }

    /**
     * 判断 MultipartFile 是否为空。
     *
     * @return 如果字节数组长度为 0，则返回 true；否则返回 false
     */
    @Override
    public boolean isEmpty() {
        return this.content.length == 0;
    }

    /**
     * 获取文件的大小（字节数）。
     *
     * @return 文件的大小
     */
    @Override
    public long getSize() {
        return this.content.length;
    }

    /**
     * 获取字节数组内容。
     *
     * @return 字节数组内容
     * @throws IOException 如果发生 I/O 错误
     */
    @Override
    public byte[] getBytes() throws IOException {
        return this.content;
    }

    /**
     * 获取输入流。
     *
     * @return 输入流
     * @throws IOException 如果发生 I/O 错误
     */
    @Override
    public InputStream getInputStream() throws IOException {
        return new ByteArrayInputStream(this.content);
    }

    /**
     * 将文件内容保存到指定位置。
     *
     * @param dest 目标文件路径
     * @throws IOException           如果发生 I/O 错误
     * @throws IllegalStateException 如果文件状态不正确
     */
    @Override
    public void transferTo(File dest) throws IOException, IllegalStateException {
        // 实现文件的保存操作，可根据具体需求自行实现
        // 例如：Files.write(dest.toPath(), content);
        Files.write(dest.toPath(), content);
    }

}