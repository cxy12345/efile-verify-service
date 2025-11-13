package com.verify.efileverifyservice.enums.metric;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@Getter
public enum MetricCorrespondEnum {
    //    母线负荷预测准确率
    METRIC1("110kV", "1", "1", "", "", "T_METRIC_BUSI_ACCURACY"),
    METRIC2("220kV", "2", "2", "", "", "T_METRIC_BUSI_ACCURACY"),
    METRIC3("330kV", "3", "3", "", "", "T_METRIC_BUSI_ACCURACY"),
    METRIC4("500kV", "4", "4", "", "", "T_METRIC_BUSI_ACCURACY"),
    METRIC5("短期", "", "", "1", "1", "T_METRIC_BUSI_ACCURACY"),
    METRIC6("超短期", "", "", "2", "2", "T_METRIC_BUSI_ACCURACY"),
    //    不同类型零售套餐签约量
    METRIC7("固定价格", "1", "1", "", "", "T_METRIC_DIF_PKG_CONTRACT_NUM"),
    METRIC8("联动价格", "2", "2", "", "", "T_METRIC_DIF_PKG_CONTRACT_NUM"),
    //    绿电交易情况
    METRIC9("电量", "年累计交易电量", "1", "", "", "T_METRIC_GRE_ELE_TRANS_SITU"),
    METRIC10("电价", "年累计成交均价", "2", "", "", "T_METRIC_GRE_ELE_TRANS_SITU"),
    METRIC11("电价（环境价值）", "", "", "", "", "T_METRIC_GRE_ELE_TRANS_SITU"),
    METRIC12("省内绿电", "", "", "1", "1", "T_METRIC_GRE_ELE_TRANS_SITU"),
    METRIC13("省间绿电", "", "1", "2", "2", "T_METRIC_GRE_ELE_TRANS_SITU"),
    METRIC53("省间绿电", "", "2", "3", "2", "T_METRIC_GRE_ELE_TRANS_SITU"),
    //    集团机组报量分布
    METRIC14("100以下", "1", "2", "", "", "T_METRIC_GR_UNIT_QUOTA_DIST"),
    METRIC15("100(含）-200", "2", "3", "", "", "T_METRIC_GR_UNIT_QUOTA_DIST"),
    METRIC16("200(含）-300", "3", "4", "", "", "T_METRIC_GR_UNIT_QUOTA_DIST"),
    METRIC17("300(含）-400", "4", "5", "", "", "T_METRIC_GR_UNIT_QUOTA_DIST"),
    METRIC18("400(含）-500", "5", "6", "", "", "T_METRIC_GR_UNIT_QUOTA_DIST"),
    METRIC19("500(含）-600", "6", "7", "", "", "T_METRIC_GR_UNIT_QUOTA_DIST"),
    METRIC20("600(含）-700", "7", "8", "", "", "T_METRIC_GR_UNIT_QUOTA_DIST"),
    METRIC21("700(含）以上", "8", "9", "", "", "T_METRIC_GR_UNIT_QUOTA_DIST"),
    METRIC22("0元以下", "0", "1", "", "", "T_METRIC_GR_UNIT_QUOTA_DIST"),
    //    信息披露及时率
    METRIC23("发电企业", "1", "1", "", "", "T_METRIC_INFO_DIS_TIME_RATE"),
    METRIC24("售电公司", "2", "2", "", "", "T_METRIC_INFO_DIS_TIME_RATE"),
    METRIC25("电力用户", "3", "3", "", "", "T_METRIC_INFO_DIS_TIME_RATE"),
    METRIC26("新型主体", "4", "4", "", "", "T_METRIC_INFO_DIS_TIME_RATE"),
    METRIC27("电网企业", "5", "5", "", "", "T_METRIC_INFO_DIS_TIME_RATE"),
    METRIC28("市场运营机构", "6", "6", "", "", "T_METRIC_INFO_DIS_TIME_RATE"),
    //    系统负荷预测准确率
    METRIC29("短期", "", "", "1", "1", "T_METRIC_LOAD_FORECAST_ACC"),
    METRIC30("超短期", "", "", "2", "2", "T_METRIC_LOAD_FORECAST_ACC"),
    //    中长期净成交量
    METRIC31("用电侧", "1", "2", "", "", "T_METRIC_LT_TRADING_VOLUME"),
    METRIC32("发电侧", "2", "1", "", "", "T_METRIC_LT_TRADING_VOLUME"),
    //    机组现货平均报价(分为火电、水电、新能源等)
    METRIC33("水电", "1", "3", "", "", "T_METRIC_MOT_UNIT_AVG_QUOTA"),
    METRIC34("火电", "2", "1", "", "", "T_METRIC_MOT_UNIT_AVG_QUOTA"),
//    METRIC35("风电", "3", "4", "", "", "T_METRIC_MOT_UNIT_AVG_QUOTA"),
//    METRIC36("光伏", "4", "5", "", "", "T_METRIC_MOT_UNIT_AVG_QUOTA"),
//    METRIC37("新能源", "5", "9", "", "", "T_METRIC_MOT_UNIT_AVG_QUOTA"),
    METRIC38("其他", "6", "5", "", "", "T_METRIC_MOT_UNIT_AVG_QUOTA"),
    METRIC39("储能", "7", "6", "", "", "T_METRIC_MOT_UNIT_AVG_QUOTA"),
    //    机组现货报价分布
    METRIC40("100以下", "1", "2", "", "", "T_METRIC_MOT_UNIT_DIS"),
    METRIC41("100(含）-200", "2", "3", "", "", "T_METRIC_MOT_UNIT_DIS"),
    METRIC42("200(含）-300", "3", "4", "", "", "T_METRIC_MOT_UNIT_DIS"),
    METRIC43("300(含）-400", "4", "5", "", "", "T_METRIC_MOT_UNIT_DIS"),
    METRIC44("400(含）-500", "5", "6", "", "", "T_METRIC_MOT_UNIT_DIS"),
    METRIC45("500(含）-600", "6", "7", "", "", "T_METRIC_MOT_UNIT_DIS"),
    METRIC46("600(含）-700", "7", "8", "", "", "T_METRIC_MOT_UNIT_DIS"),
    METRIC47("700(含）以上", "8", "9", "", "", "T_METRIC_MOT_UNIT_DIS"),
    METRIC48("0元以下", "0", "1", "", "", "T_METRIC_MOT_UNIT_DIS"),
    //    现货市场出清加权电价
    METRIC49("实时均价", "实时均价", "2", "", "", "T_METRIC_WHT_ELEC_PRICE"),
    METRIC50("日前均价", "日前均价", "1", "", "", "T_METRIC_WHT_ELEC_PRICE"),
    //    现货市场出清电价较燃煤基准电价上浮率
    METRIC51("实时基准电价上浮率", "实时基准电价上浮率", "2", "", "", "T_METRIC_PRICE_BM_FLOAT"),
    METRIC52("日前基准电价上浮率", "日前基准电价上浮率", "1", "", "", "T_METRIC_PRICE_BM_FLOAT") ;

    private final String name;

    private final String dbsType1;

    private final String type1;

    private final String dbsType2;

    private final String type2;

    private final String modelName;


    public static MetricCorrespondEnum ofName(String name) {
        return Arrays.stream(values()).filter(metricEnum -> metricEnum.name.equalsIgnoreCase(name)).findFirst()
                .orElse(null);
    }

    public static List<MetricCorrespondEnum> ofModelName(String modelName) {
        return Arrays.stream(values()).filter(metricEnum -> metricEnum.modelName.equalsIgnoreCase(modelName)).collect(Collectors.toList());
    }

    public static MetricCorrespondEnum ofModelNameAndType1(String modelName, String type1) {
        return Arrays.stream(values()).filter(metricEnum -> metricEnum.modelName.equalsIgnoreCase(modelName) && metricEnum.dbsType1.equalsIgnoreCase(type1)).findFirst()
                .orElse(null);
    }

    public static MetricCorrespondEnum ofModelNameAndType2(String modelName, String type2) {
        return Arrays.stream(values()).filter(metricEnum -> metricEnum.modelName.equalsIgnoreCase(modelName) && metricEnum.dbsType2.equalsIgnoreCase(type2)).findFirst()
                .orElse(null);
    }
}
