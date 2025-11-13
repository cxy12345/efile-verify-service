package com.verify.efileverifyservice.entity;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class BusinessEntity {
    /**
     * 时间戳
     * 数据统计时间，10 位时间戳
     */
    @ApiModelProperty("时间戳")
    private int dataTime = -1;
    /**
     * 数据类型
     * 1：15 分钟
     * 2：日报
     * 3：周报
     * 4：月报
     * 5：季报
     * 6：年报
     * 7：其它
     */
    @ApiModelProperty("数据类型")
    private int dataType = -1;
    /**
     * 期别
     * 日示例，2025-01-01；
     * 周示例，2025-W01；
     * 月示例，2025-01；
     * 季度示例，2025-Q1。
     */
    @ApiModelProperty("期别")
    private String indexDate = "";
    /**
     * 指标编码
     * 编码请查询4.2基础数据列表中指标编码列
     */
    @ApiModelProperty("指标编码")
    private String indexCode = "";
    /**
     * 统计范围类别
     *
     */
    @ApiModelProperty("统计范围类别")
    private int type = -1;
    /**
     * 业务场景 ID
     */
    @ApiModelProperty("业务场景ID")
    private String marketId = "PCSYN";
    /**
     * 机组ID
     */
    @ApiModelProperty("机组ID")
    private String unitId = "";
    /**
     * 机组名称
     */
    @ApiModelProperty("机组名称")
    private String unitname = "";
    /**
     * 发电集团id
     */
    @ApiModelProperty("发电集团ID")
    private String groupId = "";
    /**
     * 发电集团名称
     */
    @ApiModelProperty("发电集团名称")
    private String groupName = "";
    /**
     * 本日指标值
     */
    @ApiModelProperty("本日指标值")
    private BigDecimal dateValue = BigDecimal.valueOf(-1.0);
    /**
     * 本周指标值
     */
    @ApiModelProperty("本周指标值")
    private BigDecimal weekValue = BigDecimal.valueOf(-1.0);
    /**
     * 本月指标值
     */
    @ApiModelProperty("本月指标值")
    private BigDecimal monthValue = BigDecimal.valueOf(-1.0);
    /**
     * 本季度指标值
     */
    @ApiModelProperty("本季度指标值")
    private BigDecimal quarterValue = BigDecimal.valueOf(-1.0);
    /**
     * 年累计指标值
     */
    @ApiModelProperty("年累计指标值")
    private BigDecimal yearValue = BigDecimal.valueOf(-1.0);

    private GatherResultInfo gatherResultInfo = new GatherResultInfo();
}
