package com.verify.efileverifyservice.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 指标定义信息表
 * 
 * @author JinPeng
 * @since 2025/3/19
 */
@Getter
@Setter
@ToString
@TableName("T_METRIC_DEFINITION")
public class MetricDefinition implements Serializable {
    /**
     * 主键
     */
    @ApiModelProperty(name = "主键", notes = "")
    @TableId(type = IdType.ASSIGN_UUID)
    private String id;

    /**
     * 指标分项类型;SCJG:市场结构监管 , SCXW:市场行为监管, SCCY:市场成员监管, SCYX:市场运行监管
     */
    @ApiModelProperty(name = "指标分项类型", notes = "SCJG:市场结构监管 , SCXW:市场行为监管, SCCY:市场成员监管,  SCYX:市场运行监管")
    @NotBlank(message = "指标分项类型不能为空")
    private String subType;

    /**
     * 分类名称
     */
    @ApiModelProperty(name = "分类名称", notes = "")
    @NotBlank(message = "分类名称不能为空")
    private String className;

    /**
     * 指标类型代码
     */
    @ApiModelProperty(name = "指标类型代码", notes = "")
    @NotBlank(message = "指标类型代码不能为空")
    private String metricCode;

    /**
     * 指标类型名称
     */
    @ApiModelProperty(name = "指标类型名称", notes = "")
    @NotBlank(message = "指标类型名称不能为空")
    private String metricName;

    /**
     * 关注等级;A:重点关注指标，B：一般关注指标
     */
    @ApiModelProperty(name = "关注等级", notes = "A:重点关注指标，B：一般关注指标")
    @NotBlank(message = "关注等级不能为空")
    private String focusLevel;

    /**
     * 指标主体
     */
    @ApiModelProperty(name = "指标主体", notes = "")
    @NotBlank(message = "指标主体不能为空")
    private String buissnessSubject;

    /**
     * 责任单位
     */
    @ApiModelProperty(name = "责任单位", notes = "")
    @NotBlank(message = "责任单位不能为空")
    private String leadCompany;

    /**
     * 责任人
     */
    @ApiModelProperty(name = "责任人", notes = "")
    private String leader;

    /**
     * 指标模型
     */
    @ApiModelProperty(name = "指标模型", notes = "")
    @NotBlank(message = "指标模型不能为空")
    @Pattern(regexp = "^([a-zA-Z_][a-zA-Z0-9_]*)?$", message = "表模型格式错误")
    private String relatedModel;

    /**
     * 算子模型
     */
    @ApiModelProperty(name = "算子模型", notes = "")
    @Pattern(regexp = "^([a-zA-Z_][a-zA-Z0-9_]*)?$", message = "表模型格式错误")
    private String operatorModel;

    /**
     * 采集模型
     */
    @ApiModelProperty(name = "采集模型", notes = "")
    private String gatherModel;

    /**
     * 指标含义
     */
    @ApiModelProperty(name = "指标含义", notes = "")
    private String metricDesc;

    /**
     * 指标公式;公式图片文件上传ID
     */
    @ApiModelProperty(name = "指标公式", notes = "")
    private String metricFormula;

    /**
     * 公式释义
     */
    @ApiModelProperty(name = "公式释义", notes = "公式图片文件上传ID")
    private String formulaExplain;


    /**
     * 统计维度;0：按日分时统计，1：按日统计，2：按周统计，3：按月统计，4：按季度统计，5：按年统计
     */
    @ApiModelProperty(name = "统计周期", notes = "0：按日分时统计，1：按日统计，2：按周统计，3：按月统计，4：按季度统计，5：按年统计")
    @NotNull(message = "统计周期不能为空")
    private Integer statsDimension;

    /** 是否自动触发流程处置 N：否， Y：是 */
    @ApiModelProperty(name = "是否自动触发流程处置 N：否， Y：是", notes = "")
    private String autoFlow;

    /** 挂载树节点ID */
    @ApiModelProperty(name = "挂载树节点ID", notes = "")
    @NotBlank(message = "挂载树节点ID不能为空")
    private String treeId;

    /** 指标值 */
    @ApiModelProperty(name = "指标值", notes = "")
    private String metricValue;

    /** 评判结果 */
    @ApiModelProperty(name = "评判结果", notes = "")
    private String evaluationResult;

    /** 归集状态 0：未归集，1：已归集 */
    @ApiModelProperty(name = "归集状态 0：未归集，1：已归集", notes = "")
    private String gatherStatus;

    /** 业务时间 */
    @ApiModelProperty(name = "业务时间", notes = "")
    private String businessTime;

    /** 计划归集时间 */
    @ApiModelProperty(name = "计划归集时间", notes = "")
    private String planGatherTime;

    /** 实际归集时间 */
    @ApiModelProperty(name = "实际归集时间", notes = "")
    private String actualGatherTime;

    /** 数据状况 */
    @ApiModelProperty(name = "数据状况", notes = "")
    private String dataSituation;

    /** 预警等级 0:正常，1：预警，2：告警，3：计算异常 */
    @ApiModelProperty(name = "预警等级 0:正常，1：预警，2：告警，3：计算异常", notes = "")
    private Integer warningLevel;


    /** 数据归集规则描述 */
    @ApiModelProperty(name = "数据归集规则描述", notes = "")
    @NotBlank(message = "数据归集规则描述不能为空")
    private String gatherRule;

    /**
     * 评判标准
     */
    @ApiModelProperty(name = "评判标准", notes = "")
    private String judgingCriteria;

    /**
     * 评判标准格式化数据
     */
    @ApiModelProperty(name = "评判标准格式化数据", notes = "")
    private String judgingFormat;

    /**
     * 预警/告警条件
     */
    @ApiModelProperty(name = "预警/告警条件", notes = "")
    private String alarmCondition;

    /** 计划归集时间规则表达式 */
    @ApiModelProperty(name = "计划归集时间规则表达式", notes = "")
    private String gatherRuleExpress;

    /** 归集预警/告警条件 */
    @ApiModelProperty(name = "归集预警/告警条件", notes = "")
    private String gatherCondition;

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
}
