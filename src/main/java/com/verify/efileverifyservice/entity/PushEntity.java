package com.verify.efileverifyservice.entity;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class PushEntity {
    private MultipartFile file;
    private String LogId;
    private String type;
}
