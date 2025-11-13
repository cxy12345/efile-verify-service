package com.verify.efileverifyservice.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 归集结果信息表;
 * 
 * @author : jinpeng
 * @since : 2025-6-19
 */
@Data
@ApiModel(value = "归集结果信息表", description = "")
@TableName("T_GATHER_RESULT_INFO")
public class GatherResultInfo implements Serializable, Cloneable {
    /**
     * 主键
     */
    @ApiModelProperty(name = "主键", notes = "")
    @TableId(type = IdType.ASSIGN_UUID)
    private String id;
    /**
     * 模型名称
     */
    @ApiModelProperty(name = "模型名称", notes = "")
    private String modelName;
    /**
     * 模型中文名称
     */
    @ApiModelProperty(name = "模型中文名称", notes = "")
    private String modelChineseName;
    /**
     * 归集数据类型(0:监管指标,1:基础数据)
     */
    @ApiModelProperty(name = "归集数据类型(0:监管指标,1:基础数据)", notes = "")
    private String gatherType;
    /**
     * 责任单位
     */
    @ApiModelProperty(name = "责任单位", notes = "")
    private String leadCompany;
    /**
     * 责任人
     */
    @ApiModelProperty(name = "责任人", notes = "")
    private String leader;
    /**
     * 统计维度;0：按日分时统计，1：按日统计，2：按周统计，3：按月统计，4：按季度统计，5：按年统计
     */
    @ApiModelProperty(name = "统计维度", notes = "0：按日分时统计，1：按日统计，2：按周统计，3：按月统计，4：按季度统计，5：按年统计")
    private Integer statsDimension;
    /**
     * 数据归集规则
     */
    @ApiModelProperty(name = "数据归集规则", notes = "")
    private String gatherRule;
    /**
     * 业务时间
     */
    @ApiModelProperty(name = "业务时间", notes = "")
    private String businessTime;
    /**
     * 计划归集时间
     */
    @ApiModelProperty(name = "计划归集时间", notes = "")
    private String planGatherTime;
    /**
     * 实际归集时间
     */
    @ApiModelProperty(name = "实际归集时间", notes = "")
    private String actualGatherTime;
    /**
     * 归集状态 0：未归集， 1：已归集
     */
    @ApiModelProperty(name = "归集状态 0：未归集， 1：已归集", notes = "")
    private String gatherStatus;
    /**
     * 归集次数
     */
    @ApiModelProperty(name = "归集次数", notes = "")
    private Integer gatherNum;
    /**
     * 未归集原因类型
     */
    @ApiModelProperty(name = "未归集原因类型 0： 未按时归集，1：未到归集时间", notes = "")
    private String gatherCause;
    /**
     * 未归集原因描述
     */
    @ApiModelProperty(name = "未归集原因描述", notes = "")
    private String gatherCauseDesc;

    /** 指标预警等级 0:正常，1：预警，2：告警，3：计算异常 */
    @ApiModelProperty(name = "指标预警等级 0:正常，1：预警，2：告警，3：计算异常", notes = "")
    private Integer warningLevel;

    /** 入库时间 */
    @ApiModelProperty(name = "入库时间", notes = "")
    @TableField(value = "CREATE_TIME", fill = FieldFill.INSERT)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime createTime;

    /** 更新时间 */
    @ApiModelProperty(name = "更新时间", notes = "")
    @TableField(value = "UPDATE_TIME", fill = FieldFill.INSERT_UPDATE)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime updateTime;

    /**
     * 是否置空
     */
    @ApiModelProperty(name = "是否置空", notes = "")
    @TableField(exist = false)
    private String isNull;
}