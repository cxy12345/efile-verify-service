package com.verify.efileverifyservice.enums.metric;

import com.verify.efileverifyservice.enums.FrequencyTypeEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@Getter
public enum MetricEnum {
    METRIC_001("00100101001", Collections.singletonList("METRIC_VALUE"),"1", "","售电公司HHI指数","T_METRIC_SALES_ELECTIC_HHI",FrequencyTypeEnum.DAY,new BigDecimal(1),Collections.singletonList("HHI_s_d")),
    METRIC_001_2("00100101001",Collections.singletonList("METRIC_VALUE"), "2", "","发电企业HHI指数","T_METRIC_PRD_ELEC_HHI_M",FrequencyTypeEnum.DAY,new BigDecimal(1),Collections.singletonList("HHI_f_d")),
    METRIC_002("00100101002", Collections.singletonList("METRIC_VALUE"),"1", "","售电公司Top-m指数","T_METRIC_SALES_ELECTIC_TOP_M",FrequencyTypeEnum.MONTH,new BigDecimal(1),Collections.singletonList("topm_s_m")),
    METRIC_002_2("00100101002",Collections.singletonList("METRIC_VALUE"), "2", "","发电企业Top-m指数","T_METRIC_PRODUCT_ELECTIC_TOP_M",FrequencyTypeEnum.MONTH,new BigDecimal(1),Collections.singletonList("topm_f_m")),
    METRIC_003("00100201003", Arrays.asList("METRIC_VALUE","","COMPANY_NAME","","","",""),"", "","发电集团RSI指数","T_METRIC_PRO_ELEC_RSI",FrequencyTypeEnum.DAY,new BigDecimal(1)),
    METRIC_004("00100202004", Arrays.asList("METRIC_VALUE","UNIT_NAME","","","","",""),"", "","边际机组集中度","T_METRIC_MAR_UNIT_CTN",FrequencyTypeEnum.MONTH,new BigDecimal(1)),
    METRIC_005("00100302005", Collections.singletonList("METRIC_VALUE"),"", "","用户侧主体市场参与度（参与主体数量）","T_METRIC_USE_ELE_M_PART_NUM",FrequencyTypeEnum.MONTH,new BigDecimal(1),Collections.singletonList("uempn_m")),
    METRIC_006("00100302006", Collections.singletonList("METRIC_VALUE"),"", "","用电侧主体市场参与度(电量信息)","T_METRIC_ELEC_ENGAGEMENT",FrequencyTypeEnum.MONTH,new BigDecimal(1),Collections.singletonList("ee_m")),
    METRIC_007("00100401007", Collections.singletonList("METRIC_VALUE"),"", "","市场可用发电容量","T_METRIC_MKT_POWER_GEN_CAP",FrequencyTypeEnum.DAY_MINUTE96,new BigDecimal(1)),
    METRIC_008("00100401008", Collections.singletonList("METRIC_VALUE"),"", "","市场供需比","T_METRIC_SUPPLY_DEMAND_RATIO",FrequencyTypeEnum.DAY,new BigDecimal(1),Collections.singletonList("sdr_d")),
    METRIC_009("00100402009", Arrays.asList("","","","","","","","METRIC_VALUE"),"", "","系统旋转备用容量","T_METRIC_ROT_RESERVE",FrequencyTypeEnum.DAY,new BigDecimal(1),Arrays.asList("rr_zrl_d","rr_frl_d","rm_z_d")),
    METRIC_010("00100401010", Arrays.asList("","","","","","","","METRIC_VALUE"),"", "","备用裕度指标","T_METRIC_RSRV_MARGIN",FrequencyTypeEnum.DAY,new BigDecimal(1),Collections.singletonList("rm_f_d")),
    METRIC_011("00100501011", Collections.singletonList("METRIC_VALUE"),"", "","中长期交易电量占比","T_METRIC_LT_TRAS_ELEC_PROP",FrequencyTypeEnum.MONTH,new BigDecimal(1),Collections.singletonList("ltep_m")),
    METRIC_011_02("00100501011", Collections.singletonList("YEAR_METRIC_VALUE"),"", "","中长期交易电量占比","T_METRIC_LT_TRAS_ELEC_PROP",FrequencyTypeEnum.YEAR,new BigDecimal(1)),
    METRIC_012("00200101001", Collections.singletonList("METRIC_VALUE"),"", "","机组申报容量率","T_METRIC_MOT_UNIT_APPLY_CAP",FrequencyTypeEnum.DAY,new BigDecimal(1),Collections.singletonList("muac_d")),
    METRIC_013("00200102002", Collections.singletonList("METRIC_VALUE"),"", "","机组高价申报率","T_METRIC_MOT_UNIT_HIG_P_RATE",FrequencyTypeEnum.DAY,new BigDecimal(1),Collections.singletonList("muhpr_d")),
    METRIC_014("00200101003", Collections.singletonList("METRIC_VALUE"),"", "","机组报价达上限率","T_METRIC_MOT_UNIT_QUT_RGE_R",FrequencyTypeEnum.DAY,new BigDecimal(1),Collections.singletonList("muuqrr_d")),
    METRIC_015("00200101004", Collections.singletonList("METRIC_VALUE"),"", "","机组报价平均加成指数","T_METRIC_MOT_UNIT_QUO_AVG_PLUS",FrequencyTypeEnum.DAY,new BigDecimal(1),Collections.singletonList("muqap_d")),
    METRIC_016("00200101005", Collections.singletonList("METRIC_VALUE"),"BUSINESS_TYPE", "","机组现货平均报价(分为火电、水电、新能源等)","T_METRIC_MOT_UNIT_AVG_QUOTA",FrequencyTypeEnum.DAY,new BigDecimal(1)),
    METRIC_017("00200101006", Collections.singletonList("METRIC_VALUE"),"BUSINESS_TYPE", "","机组现货报价分布","T_METRIC_MOT_UNIT_DIS",FrequencyTypeEnum.DAY,new BigDecimal(1)),
    METRIC_018("00200102007", Collections.singletonList("METRIC_VALUE"),"", "","机组首段报价达上限台数","T_METRIC_MOT_UNIT_TOP_NUM",FrequencyTypeEnum.DAY,new BigDecimal(1),Collections.singletonList("mutn_d")),
    METRIC_019("00200102008", Collections.singletonList("METRIC_VALUE"),"", "","机组首段报价达上限比例","T_METRIC_MOT_UNIT_TOP_PROP",FrequencyTypeEnum.DAY,new BigDecimal(1),Collections.singletonList("mutp_d")),
    METRIC_020("00200102009", Collections.singletonList("METRIC_VALUE"),"", "","首段申报价格达上限容量","T_METRIC_MOT_UNI_TOP_CAP",FrequencyTypeEnum.DAY,new BigDecimal(1),Collections.singletonList("mutc_d")),
    METRIC_021("00200102010", Arrays.asList("METRIC_VALUE","","COMPANY_NAME","","","",""),"BUSINESS_TYPE", "","集团机组报量分布","T_METRIC_GR_UNIT_QUOTA_DIST",FrequencyTypeEnum.DAY,new BigDecimal(1)),
    METRIC_022("00200101011", Arrays.asList("METRIC_VALUE","UNIT_NAME","","","","UNIT_CODE",""),"", "","机组报价波动系数","T_METRIC_MOT_UNIT_FLU_COEFFI",FrequencyTypeEnum.WEEK,new BigDecimal(1)),
    METRIC_023("00200101012", Collections.singletonList("METRIC_VALUE"),"", "","机组报价一致性","T_METRIC_MOT_UNIT_QUOTA_CONSIS",FrequencyTypeEnum.DAY,new BigDecimal(1),Collections.singletonList("muqc_d")),
    METRIC_024("00200102013", Collections.singletonList("METRIC_VALUE"),"", "","机组申报调频价格达上限率","T_METRIC_MOT_UNIT_FREQ_UP_RATE",FrequencyTypeEnum.DAY,new BigDecimal(1),Collections.singletonList("mufur_d")),
    METRIC_025("00200202014", Collections.singletonList("METRIC_VALUE"),"", "","调频机组中标率","T_METRIC_FREQ_BID_RATE",FrequencyTypeEnum.DAY_MINUTE96,new BigDecimal(1)),
    METRIC_026("00200202015", Collections.singletonList("METRIC_VALUE"),"", "","调频机组出力达调频容量预留上限占比","T_METRIC_FREQ_RSRV_UP_RATIO",FrequencyTypeEnum.DAY_MINUTE96,new BigDecimal(1)),
    METRIC_027("00200202016", Collections.singletonList("METRIC_VALUE"),"", "","调频机组出力达调频容量预留下限占比","T_METRIC_FREQ_RSRV_LOW_RATIO",FrequencyTypeEnum.DAY_MINUTE96,new BigDecimal(1)),
    METRIC_028("00300101001", Arrays.asList("METRIC_VALUE","UNIT_NAME","","","","UNIT_CODE",""),"", "","基准电价偏差率","T_METRIC_ELEC_DEVIATION_RATE",FrequencyTypeEnum.MONTH,new BigDecimal(1)),
    METRIC_029("00300101002", Collections.singletonList("METRIC_VALUE"),"", "","发电计划执行率","T_METRIC_GEN_PLAN_EXEC",FrequencyTypeEnum.DAY,new BigDecimal(1),Collections.singletonList("gpe_d")),
    METRIC_030("00300202003", Arrays.asList("METRIC_VALUE","","","","USER_NAME","","USER_CODE"),"", "","批发用户市场化电价较代理购电价格上浮率","T_METRIC_PURCH_ELE_PRICE_R",FrequencyTypeEnum.MONTH,new BigDecimal(1)),
    METRIC_031("00300202004", Collections.singletonList("METRIC_VALUE"),"", "","用户侧购电执行率","T_METRIC_USR_SID_PURCH_ELEC_RT",FrequencyTypeEnum.MONTH,new BigDecimal(1)),
    METRIC_032("00300302005", Collections.singletonList("METRIC_VALUE"),"", "","合约签约率","T_METRIC_CONTRACT_SIGN",FrequencyTypeEnum.MONTH,new BigDecimal(1)),
    METRIC_033("00300302006", Collections.singletonList("METRIC_VALUE"),"", "","合约延续率","T_METRIC_CONTRACT_CONTINUE",FrequencyTypeEnum.MONTH,new BigDecimal(1)),
    METRIC_034("00300301007", Collections.singletonList("METRIC_VALUE"),"", "","售电电量执行率","T_METRIC_SALE_ELEC_EXEC_RATE",FrequencyTypeEnum.DAY,new BigDecimal(1),Collections.singletonList("seer_d")),
    METRIC_035("00300401008", Collections.singletonList("METRIC_VALUE"),"", "BUSINESS_TYPE","系统负荷预测准确率","T_METRIC_LOAD_FORECAST_ACC",FrequencyTypeEnum.DAY,new BigDecimal(1),Arrays.asList("lfa_d_d","lfa_cd_d")),
    METRIC_036("00300401009", Collections.singletonList("METRIC_VALUE"),"VOLTAGE_LEVEL", "BUSINESS_TYPE","母线负荷预测准确率","T_METRIC_BUSI_ACCURACY",FrequencyTypeEnum.DAY,new BigDecimal(1),Arrays.asList("ba_110_d_d","ba_220_d_d","ba_330_d_d","ba_500_d_d","ba_110_cd_d","ba_220_cd_d","ba_330_cd_d","ba_500_cd_d")),
    METRIC_037("00300401010", Arrays.asList("METRIC_VALUE","","", "BUSINESS_TYPE","","",""),"BUSINESS_TYPE", "","信息披露及时率","T_METRIC_INFO_DIS_TIME_RATE",FrequencyTypeEnum.DAY,new BigDecimal(1),Arrays.asList("idtr_f_d","idtr_sd_d","idtr_dl_d","idtr_x_d","idtr_dw_d","idtr_sc_d")),
    METRIC_038("00400101001", Collections.singletonList("METRIC_VALUE"),"", "","发电侧中长期交易加权平均价格","T_METRIC_TRANS_WTD_AVG_PRICE",FrequencyTypeEnum.MONTH,new BigDecimal(1)),
    METRIC_038_02("00400101001", Collections.singletonList("YEAR_METRIC_VALUE"),"", "","发电侧中长期交易加权平均价格","T_METRIC_TRANS_WTD_AVG_PRICE",FrequencyTypeEnum.YEAR,new BigDecimal(1)),
    METRIC_039("00400101002", Collections.singletonList("METRIC_VALUE"),"METRIC_VAL_NAME", "","现货市场出清加权电价","T_METRIC_WHT_ELEC_PRICE",FrequencyTypeEnum.DAY_MINUTE96,new BigDecimal(1)),
    METRIC_039_02("00400101002", Collections.singletonList("METRIC_VALUE"),"METRIC_VAL_NAME", "","现货市场出清加权电价","T_METRIC_WHT_ELEC_PRICE",FrequencyTypeEnum.MONTH,new BigDecimal(1)),
    METRIC_039_03("00400101002", Collections.singletonList("METRIC_VALUE"),"METRIC_VAL_NAME", "","现货市场出清加权电价","T_METRIC_WHT_ELEC_PRICE",FrequencyTypeEnum.YEAR,new BigDecimal(1)),
    METRIC_040("00400102003", Collections.singletonList("METRIC_VALUE"),"METRIC_VAL_NAME", "","现货市场出清电价较燃煤基准电价上浮率","T_METRIC_PRICE_BM_FLOAT",FrequencyTypeEnum.DAY,new BigDecimal(1),Arrays.asList("pbf_rq_d","pbf_ss_d")),
    METRIC_041("00400101004", Collections.singletonList("METRIC_VALUE"),"", "","中长期交易价格与现货价格偏离度","T_METRIC_LT_PRICE_DEVAT_DEGREE",FrequencyTypeEnum.DAY_MINUTE24,new BigDecimal(1)),
    METRIC_042("00400102005", Collections.singletonList("METRIC_VALUE"),"", "","系统边际价格与平均节点电价偏差分析","T_METRIC_PRICE_NODE_GAP",FrequencyTypeEnum.DAY_MINUTE96,new BigDecimal(1)),
    METRIC_043("00400102006", Collections.singletonList("METRIC_VALUE"),"", "","现货市场出清平均价格加成指数","T_METRIC_MKT_AVG_PRICE_INDEX",FrequencyTypeEnum.DAY,new BigDecimal(1),Collections.singletonList("mapi_d")),
    METRIC_044("00400101007", Collections.singletonList("METRIC_VALUE"),"", "","发电侧现货日前与实时加权平均价格浮动率","T_METRIC_GEN_PRICE_FLOAT",FrequencyTypeEnum.DAY,new BigDecimal(1)),
    METRIC_044_02("00400101007", Collections.singletonList("METRIC_VALUE"),"", "","发电侧现货日前与实时加权平均价格浮动率","T_METRIC_GEN_PRICE_FLOAT",FrequencyTypeEnum.MONTH,new BigDecimal(1)),
    METRIC_045("00400102008", Arrays.asList("METRIC_VALUE","","","BUSINESS_TYPE","","",""),"BUSINESS_TYPE", "","中长期净成交量","T_METRIC_LT_TRADING_VOLUME",FrequencyTypeEnum.MONTH,new BigDecimal(1)),
    METRIC_045_02("00400102008", Arrays.asList("YEAR_METRIC_VALUE","","","BUSINESS_TYPE","","",""),"BUSINESS_TYPE", "","中长期净成交量","T_METRIC_LT_TRADING_VOLUME",FrequencyTypeEnum.YEAR,new BigDecimal(1)),
//中长期交易换手率 统计范围类别只有用电侧
    METRIC_046("00400102009", Collections.singletonList("METRIC_VALUE"),"2", "","中长期交易换手率","T_METRIC_LT_EXCH_HD_RATE",FrequencyTypeEnum.MONTH,new BigDecimal(1)),
    METRIC_046_02("00400102009", Collections.singletonList("YEAR_METRIC_VALUE"),"2", "","中长期交易换手率","T_METRIC_LT_EXCH_HD_RATE",FrequencyTypeEnum.YEAR,new BigDecimal(1)),
    METRIC_047("00400201010", Collections.singletonList("METRIC_VALUE"),"", "","市场用户侧价格浮动率","T_METRIC_MARKET_PRICE_FLOAT",FrequencyTypeEnum.MONTH,new BigDecimal(1)),
    METRIC_047_02("00400201010", Collections.singletonList("YEAR_METRIC_VALUE"),"", "","市场用户侧价格浮动率","T_METRIC_MARKET_PRICE_FLOAT",FrequencyTypeEnum.YEAR,new BigDecimal(1)),
    METRIC_048("00400202011", Collections.singletonList("METRIC_VALUE"),"BUSINESS_TYPE", "","不同类型零售套餐签约量","T_METRIC_DIF_PKG_CONTRACT_NUM",FrequencyTypeEnum.MONTH,new BigDecimal(1)),
    METRIC_049("00400201012", Collections.singletonList("METRIC_VALUE"),"", "","售电公司盈利分析","T_METRIC_PROFIT_ANALYSIS",FrequencyTypeEnum.QUARTER,new BigDecimal(1)),
    METRIC_049_02("00400201012", Collections.singletonList("YEAR_METRIC_VALUE"),"", "","售电公司盈利分析","T_METRIC_PROFIT_ANALYSIS",FrequencyTypeEnum.YEAR,new BigDecimal(1)),
    METRIC_050("00400302013", Collections.singletonList("METRIC_VALUE"),"", "BUSINESS_TYPE","绿电交易情况","T_METRIC_GRE_ELE_TRANS_SITU",FrequencyTypeEnum.YEAR,new BigDecimal(1)),
    METRIC_051("00400301014", Collections.singletonList("METRIC_VALUE"),"", "","清洁能源消纳比例","T_METRIC_CLEAN_EG_ABSORPTION",FrequencyTypeEnum.DAY,new BigDecimal(1)),
    METRIC_051_02("00400301014", Collections.singletonList("METRIC_VALUE"),"", "","清洁能源消纳比例","T_METRIC_CLEAN_EG_ABSORPTION",FrequencyTypeEnum.MONTH,new BigDecimal(1)),
    METRIC_051_03("00400301014", Collections.singletonList("METRIC_VALUE"),"", "","清洁能源消纳比例","T_METRIC_CLEAN_EG_ABSORPTION",FrequencyTypeEnum.YEAR,new BigDecimal(1)),
    UNKNOWN("", new ArrayList<>(),"", "","未知指标","",FrequencyTypeEnum.DAY,new BigDecimal(1));

    private final String metricId;
// 0:指标值，1：机组名称，2：发电集团名称，3：主体类型，4：主体名称，5：机组编号,6:主体编码，7：用户编码，8：正负备用的数值
    private final List<String> metricValue;

    private final String type;

    private final String type2;

    private final String metricName;

    private final String metricModel;

    private final FrequencyTypeEnum frequencyTypeEnum;

    private final BigDecimal unit;

    private List<String>  excelValue;


    public static MetricEnum ofMetricId(String metricId) {
        return Arrays.stream(values()).filter(metricEnum -> metricEnum.metricId.equalsIgnoreCase(metricId)).findFirst()
                .orElse(UNKNOWN);
    }

    public static List<MetricEnum> ofMetricModel(String metricModel) {
        return Arrays.stream(values()).filter(metricEnum -> metricEnum.metricModel.equalsIgnoreCase(metricModel)).collect(Collectors.toList());
    }


    public static List<MetricEnum> getMetricList() {
        return Arrays.stream(values())
                .filter(metricEnum -> metricEnum != UNKNOWN)
                .collect(Collectors.toList());
    }

}
