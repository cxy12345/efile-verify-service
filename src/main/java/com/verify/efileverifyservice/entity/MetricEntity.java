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
public class MetricEntity {
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
     * 日示例，2025-01-01；周示例，2025-W01；月示例，2025-01；季度示例，2025-Q1。
     */
    @ApiModelProperty("期别")
    private String indexDate = "";
    /**
     * 指标编码
     */
    @ApiModelProperty("指标编码")
    private String indexCode = "";
    /**
     * 机组 ID
     */
    @ApiModelProperty("机组ID")
    private String unitId = "";
    /**
     * 机组名称
     */
    @ApiModelProperty("机组名称")
    private String unitName = "";
    /**
     * 统计范围类别
     */
    @ApiModelProperty("统计范围类别")
    private int type = -1;
    /**
     * 统计范围类别2
     */
    @ApiModelProperty("统计范围类别2")
    private int type2 = -1;
    /**
     * 业务场景 ID
     */
    @ApiModelProperty("业务场景ID")
    private String marketId = "PCSYN";

    /**
     * 中长期均价偏差率
     */
    @ApiModelProperty("中长期均价偏差率")
    private BigDecimal longTermPriceRate = BigDecimal.valueOf(-1.0);
    /**
     * 现货均价偏差率
     */
    @ApiModelProperty("现货均价偏差率")
    private BigDecimal spotPriceRate = BigDecimal.valueOf(-1.0);
    /**
     * 整体均价偏差率
     */
    @ApiModelProperty("整体均价偏差率")
    private BigDecimal priceRate = BigDecimal.valueOf(-1.0);
    /**
     * 发电集团 ID 没有
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
    /**
     * 市场主体类型
     */
    @ApiModelProperty("市场主体类型")
    private String memberType = "";
    /**
     * 市场主体 ID
     */
    @ApiModelProperty("市场主体ID")
    private String memberId = "";
    /**
     * 市场主体名称
     */
    @ApiModelProperty("市场主体名称")
    private String memberName = "";
    /**
     * 省间交易（送出/受入方）
     */
    @ApiModelProperty("省间交易(送出/受入方)")
    private int outOrIn = -1;
    /**
     * 正备用容量
     */
    @ApiModelProperty("正备用容量")
    private BigDecimal positiveReserveCapacity = BigDecimal.valueOf(-1.0);
    /**
     * 负备用容量
     */
    @ApiModelProperty("负备用容量")
    private BigDecimal negativeReserveCapacity = BigDecimal.valueOf(-1.0);
    /**
     * 正备用需求
     */
    @ApiModelProperty("正备用需求")
    private BigDecimal positiveReserveDemand = BigDecimal.valueOf(-1.0);
    /**
     * 负备用需求
     */
    @ApiModelProperty("负备用需求")
    private BigDecimal negativeReserveDemand = BigDecimal.valueOf(-1.0);
    /**
     * 正备用裕度
     */
    @ApiModelProperty("正备用裕度")
    private BigDecimal positiveReserveMargin = BigDecimal.valueOf(-1.0);
    /**
     * 负备用裕度
     */
    @ApiModelProperty("负备用裕度")
    private BigDecimal negativeReserveMargin = BigDecimal.valueOf(-1.0);
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
