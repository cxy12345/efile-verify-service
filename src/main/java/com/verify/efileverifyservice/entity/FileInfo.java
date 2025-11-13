package com.verify.efileverifyservice.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * @author loYuan
 * @date 2025/3/10
 */
@Data
@ApiModel("文件信息")
@TableName("FILE_INFO")
public class FileInfo {

    @TableId(value = "id")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    @ApiModelProperty("主键id")
    private Long id;

    @TableField(value = "ORIGINAL_FILENAME")
    @ApiModelProperty("文件的原始名称")
    private String originalFilename;

    @TableField(value = "STORED_FILENAME")
    @ApiModelProperty("存储在系统中的文件名称")
    private String storedFilename;

    @TableField(value = "FILE_EXTENSION")
    @ApiModelProperty("文件的扩展名")
    private String fileExtension;

    @TableField(value = "FILE_PREFIX")
    @ApiModelProperty("文件名无后缀")
    private String filePrefix;

    @TableField(exist = false)
    @ApiModelProperty("文件类型")
    private String fileType;

    @TableField(value = "BUCKET_NAME")
    @ApiModelProperty("存储桶的名称")
    private String bucketName;

    @TableField(value = "FILE_URL")
    @ApiModelProperty("文件的访问 URL")
    private String fileUrl;

    @TableField(value = "FILE_SUFFIX")
    @ApiModelProperty("文件后缀")
    private String fileSuffix;

    @TableField(value = "FILE_SIZE")
    @ApiModelProperty("文件大小")
    private String fileSize;

    @TableField(value = "UPLOAD_TIME")
    @ApiModelProperty("文件上传的时间")
    private Date uploadTime;

    @TableField(value = "CONTENT_TYPE")
    @ApiModelProperty("文件的 MIME 类型")
    private String contentType;

    @TableField(value = "PRIMARY_KEY_RELATIONSHIP")
    @ApiModelProperty("主键关系，与其他业务表之间的连接")
    private String primaryKeyRelationship;

    @TableField(exist = false)
    private String filePath;
}