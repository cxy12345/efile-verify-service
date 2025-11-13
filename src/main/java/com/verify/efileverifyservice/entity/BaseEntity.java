package com.verify.efileverifyservice.entity;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Data
public class BaseEntity {
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
     * 统计范围类别
     * 2
     */
    @ApiModelProperty("统计范围类别2")
    private int type2 = -1;
    /**
     * 业务场景 ID
     */
    @ApiModelProperty("业务场景ID")
    private String marketId = "PCSYN";
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
    /**
     * 加权平均值
     */
    @ApiModelProperty("加权平均值")
    private BigDecimal weightedValue = BigDecimal.valueOf(-1.0);
    /**
     * 最高节点电价
     */
    @ApiModelProperty("最高节点电价")
    private BigDecimal nodePriceHigh = BigDecimal.valueOf(-1.0);
    /**
     * 最低节点电价
     */
    @ApiModelProperty("最低节点电价")
    private BigDecimal nodePriceLow = BigDecimal.valueOf(-1.0);
    /**
     * 最大值
     */
    @ApiModelProperty("最大值")
    private BigDecimal priceHigh = BigDecimal.valueOf(-1.0);
    /**
     * 最小值
     */
    @ApiModelProperty("最小值")
    private BigDecimal priceLow = BigDecimal.valueOf(-1.0);
    /**
     * 平均值
     */
    @ApiModelProperty("平均值")
    private BigDecimal quantityAverage = BigDecimal.valueOf(-1.0);
    /**
     * 地级市编码
     */
    @ApiModelProperty("地级市编码")
    private String regionCode = "";
    /**
     * 地级市名称
     */
    @ApiModelProperty("地级市名称")
    private String regionName = "";
    /**
     * 分钟级数据，一天 96 个点
     * 日分时 00:00 数据开始，必须包含所有 96 点值
     */
    @ApiModelProperty("分钟级数据")
    private List<BigDecimal> minutValue = new ArrayList<>();
    /**
     * 小时级数据，一天 24 个点
     * 日分时 00:00 数据开始，必须包含所有 24 点值
     */
    @ApiModelProperty("小时级数据")
    private List<BigDecimal> hourValue = new ArrayList<>();

    @ApiModelProperty("全省加权平均值")
    private BigDecimal provWeightedValue  = BigDecimal.valueOf(-1.0);

    @ApiModelProperty("日上浮比例")
    private BigDecimal ratio = BigDecimal.valueOf(-1.0);

    @ApiModelProperty("最大值环比增长")
    private BigDecimal maxValueGrowth = BigDecimal.valueOf(-1.0);

    @ApiModelProperty("最小值环比增长")
    private BigDecimal minValueGrowth = BigDecimal.valueOf(-1.0);

    @ApiModelProperty("现货差价")
    private BigDecimal priceDiff = BigDecimal.valueOf(-1.0);

    @ApiModelProperty("最高价出现时间")
    private BigDecimal highOccurTime = BigDecimal.valueOf(-1.0);

    @ApiModelProperty("最低价出现时间")
    private BigDecimal lowOccurTime = BigDecimal.valueOf(-1.0);

    @ApiModelProperty("均价")
    private BigDecimal averagePrice = BigDecimal.valueOf(-1.0);

    @ApiModelProperty("发电企业结构数")
    private int num = -1;

    @Setter
    @Getter
    @ToString
    public static class Minut {
        private String key = "";
        private BigDecimal value = BigDecimal.valueOf(-1.0);
    }

    @Setter
    @Getter
    @ToString
    public static class Hour {
        private BigDecimal value = BigDecimal.valueOf(-1.0);
    }
}
