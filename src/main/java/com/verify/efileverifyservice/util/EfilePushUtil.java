package com.verify.efileverifyservice.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.*;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public class EfilePushUtil {

    private static final Logger log = LoggerFactory.getLogger(EfilePushUtil.class);
    private final RestTemplate restTemplate;

    public EfilePushUtil(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public ResponseEntity<String> pushEfile(String efileUrl, String filetype, MultipartFile file, String pushmode) {
        long startTime = System.currentTimeMillis();
        log.info("开始推送E文件");
        try {
            // 验证文件是否为空
            if (file == null || file.isEmpty()) {
                throw new RuntimeException("文件不能为空");
            }

            // 创建文件资源（使用文件体）
            ByteArrayResource fileResource = new ByteArrayResource(file.getBytes()) {
                @Override
                public String getFilename() {
                    return file.getOriginalFilename();
                }
            };

            // 创建MultiValueMap来存储form-data参数
            MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();

            // 添加必需的参数
            body.add("filetype", filetype);
            body.add("filename", file.getOriginalFilename());
            body.add("file", fileResource);

            // 添加可选的pushmode参数
            if (pushmode != null && !pushmode.trim().isEmpty()) {
                body.add("pushmode", pushmode);
            }

            // 设置请求头
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.MULTIPART_FORM_DATA);

            // 创建HttpEntity
            HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(body, headers);

            // 发送POST请求并设置超时判断
            ResponseEntity<String> response;
            try {
                response = restTemplate.exchange(
                        efileUrl,
                        HttpMethod.POST,
                        requestEntity,
                        String.class
                );
            } catch (ResourceAccessException e) {
                log.error("推送E文件超时或网络异常");
                throw new RuntimeException("推送E文件超时或网络异常: " + e.getMessage(), e);
            }

            // HTTP状态码判断
            if (response.getStatusCode().is5xxServerError()) {
                log.warn("推送E文件服务器内部错误: {}", response.getStatusCode());
                throw new RuntimeException("推送E文件服务器内部错误: " + response.getStatusCode());
            } else if (response.getStatusCode().is4xxClientError()) {
                log.warn("推送E文件客户端错误: {}", response.getStatusCode());
                throw new RuntimeException("推送E文件客户端错误: " + response.getStatusCode());
            }

            return response;

        } catch (IOException e) {
            throw new RuntimeException("读取文件失败: " + e.getMessage(), e);
        } catch (Exception e) {
            throw new RuntimeException("E文件上传失败: " + e.getMessage(), e);
        } finally {
            long duration = System.currentTimeMillis() - startTime;
            log.info("推送E文件结束，耗时: {} ms", duration);
        }
    }
}