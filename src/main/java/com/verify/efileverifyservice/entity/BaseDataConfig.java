package com.verify.efileverifyservice.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.List;

/**
 * 基础数据配置
 * @TableName T_BASE_DATA_CONFIG
 */
@TableName(value ="T_BASE_DATA_CONFIG")
@Data
public class BaseDataConfig {
    /**
     * 主键
     */
    @TableId(type = IdType.ASSIGN_UUID)
    private String id;

    /**
     * 基础数据名称
     */
    @NotNull(message = "基础数据名称不能为空")
    private String baseDataName;

    /**
     * 基础数据模型名称
     */
    @NotNull(message = "基础数据模型名称不能为空")
    private String modelName;

    @TableField(exist = false)
    private String modelCnname;

    /**
     * 具体内容
     */
    @TableField(exist = false)
    private String modelColumn;

    /**
     * 关联的数ID
     */
    @NotNull(message = "关联树ID不能为空")
    private String treeId;

    @TableField(exist = false)
    private String treeName;

    /**
     * 责任人
     */
    private String userName;

    @NotNull(message = "责任人S不能为空")
    @TableField(exist = false)
    private String leader;

    /**
     * 责任单位
     */
    @NotNull(message = "责任单位不能为空")
    private String organization;
    /**
     * 统计周期
     */
    @NotNull(message = "统计周期不能为空")
    private String statisticalCycle;

    /**
     * 计划归集时间
     */
    @NotNull(message = "计划归集时间不能为空")
    private String plannedCollectionTime;

    /**
     * 计划归集时间条件
     */
    @TableField(exist = false)
    private String plannedCollectionCondition;

    /**
     * 预警/告警条件
     */
    private String alarmCondition;


    /**
     * 备注
     */
    private String remark;

    /**
     * 入库时间
     */
    @TableField(fill = FieldFill.INSERT)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    @TableField(fill = FieldFill.INSERT_UPDATE)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime updateTime;
}