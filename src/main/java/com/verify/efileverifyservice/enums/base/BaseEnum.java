package com.verify.efileverifyservice.enums.base;

import com.verify.efileverifyservice.enums.FrequencyTypeEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@Getter
public enum BaseEnum {
    BASE_001("00001", Collections.singletonList("COAL_INST_CAPACITY"),"2","","电源结构","T_BASE_POWER_STRUCTURE", FrequencyTypeEnum.MONTH,"煤电装机容量",new BigDecimal(1),FrequencyTypeEnum.DAY),
    BASE_001_02("00001", Collections.singletonList("WATER_INST_CAPACITY"),"1","","电源结构","T_BASE_POWER_STRUCTURE",FrequencyTypeEnum.MONTH,"水电装机容量",new BigDecimal(1),FrequencyTypeEnum.DAY),
    BASE_001_03("00001", Collections.singletonList("GAS_INST_CAPACITY"),"3","","电源结构","T_BASE_POWER_STRUCTURE",FrequencyTypeEnum.MONTH,"气电装机容量",new BigDecimal(1),FrequencyTypeEnum.DAY),
    BASE_001_04("00001", Collections.singletonList("WIND_INST_CAPACITY"),"4","","电源结构","T_BASE_POWER_STRUCTURE",FrequencyTypeEnum.MONTH,"风电装机容量",new BigDecimal(1),FrequencyTypeEnum.DAY),
    BASE_001_05("00001", Collections.singletonList("PUMP_INST_CAPACITY"),"7","","电源结构","T_BASE_POWER_STRUCTURE",FrequencyTypeEnum.MONTH,"抽蓄装机容量",new BigDecimal(1),FrequencyTypeEnum.DAY),
    BASE_001_06("00001", Collections.singletonList("PV_INST_CAPACITY"),"5","","电源结构","T_BASE_POWER_STRUCTURE",FrequencyTypeEnum.MONTH,"光伏装机容量",new BigDecimal(1),FrequencyTypeEnum.DAY),
    BASE_001_07("00001", Collections.singletonList("NEW_INST_CAPACITY"),"8","","电源结构","T_BASE_POWER_STRUCTURE",FrequencyTypeEnum.MONTH,"新型储能装机容量",new BigDecimal(1),FrequencyTypeEnum.DAY),
    BASE_001_08("00001", Collections.singletonList("NP_INST_CAPACITY"),"6","","电源结构","T_BASE_POWER_STRUCTURE",FrequencyTypeEnum.MONTH,"核电",new BigDecimal(1),FrequencyTypeEnum.DAY),
    BASE_001_09("00001", Collections.singletonList("OTHER_INST_CAPACITY"),"9","","电源结构","T_BASE_POWER_STRUCTURE",FrequencyTypeEnum.MONTH,"其他",new BigDecimal(1),FrequencyTypeEnum.DAY),
    BASE_002("00002", Collections.singletonList("PRICE_DETAILS"),"1","","日前现货出清价格","T_BASE_SPOT_RECENT_PRICE",FrequencyTypeEnum.DAY_MINUTE96,"出清价格明细",new BigDecimal(1),FrequencyTypeEnum.DAY),
    BASE_003("00003", Collections.singletonList("MAX_PRICE"),"1", "","日前现货出清价格","T_BASE_SPOT_RECENT_PRICE",FrequencyTypeEnum.DAY,"最大值",new BigDecimal(1),FrequencyTypeEnum.DAY),
    BASE_004("00004", Collections.singletonList("MIN_PRICE"),"1", "","日前现货出清价格","T_BASE_SPOT_RECENT_PRICE",FrequencyTypeEnum.DAY,"最小值",new BigDecimal(1),FrequencyTypeEnum.DAY),
    BASE_005("00005", Collections.singletonList("AVG_PRICE_DETAILS"),"1", "","日前现货出清价格","T_BASE_SPOT_RECENT_PRICE",FrequencyTypeEnum.DAY_MINUTE96,"加权平均值",new BigDecimal(1),FrequencyTypeEnum.DAY),
    BASE_006("00006", Collections.singletonList("HIGHEST_PRICE"),"1", "","日前现货出清价格","T_BASE_SPOT_RECENT_PRICE",FrequencyTypeEnum.DAY,"最高节点电价",new BigDecimal(1),FrequencyTypeEnum.DAY),
    BASE_007("00007", Collections.singletonList("LOWEST_PRICE"),"1", "","日前现货出清价格","T_BASE_SPOT_RECENT_PRICE",FrequencyTypeEnum.DAY,"最低节点电价",new BigDecimal(1),FrequencyTypeEnum.DAY),
    BASE_008("00008", Collections.singletonList("CITY_DETAILS"),"", "","日前现货出清价格","T_BASE_SPOT_RECENT_PRICE",FrequencyTypeEnum.CITY_MINUTE96,"各地市加权平均价",new BigDecimal(1),FrequencyTypeEnum.DAY),
    BASE_009("00009", Collections.singletonList("PRICE_DETAILS"),"2", "","实时现货出清价格","T_BASE_SPOT_REAL_PRICE",FrequencyTypeEnum.DAY_MINUTE96,"出清价格明细",new BigDecimal(1),FrequencyTypeEnum.DAY),
    BASE_010("00010", Collections.singletonList("MAX_PRICE"),"2", "","实时现货出清价格","T_BASE_SPOT_REAL_PRICE",FrequencyTypeEnum.DAY,"最大值",new BigDecimal(1),FrequencyTypeEnum.DAY),
    BASE_011("00011", Collections.singletonList("MIN_PRICE"),"2", "","实时现货出清价格","T_BASE_SPOT_REAL_PRICE",FrequencyTypeEnum.DAY,"最小值",new BigDecimal(1),FrequencyTypeEnum.DAY),
    BASE_012("00012", Collections.singletonList("AVG_PRICE_DETAILS"),"2", "","实时现货出清价格","T_BASE_SPOT_REAL_PRICE",FrequencyTypeEnum.DAY_MINUTE96,"加权平均值",new BigDecimal(1),FrequencyTypeEnum.DAY),
    BASE_013("00013", Collections.singletonList("HIGHEST_PRICE"),"2", "","实时现货出清价格","T_BASE_SPOT_REAL_PRICE",FrequencyTypeEnum.DAY,"最高节点电价",new BigDecimal(1),FrequencyTypeEnum.DAY),
    BASE_014("00014", Collections.singletonList("LOWEST_PRICE"),"2", "","实时现货出清价格","T_BASE_SPOT_REAL_PRICE",FrequencyTypeEnum.DAY,"最低节点电价",new BigDecimal(1),FrequencyTypeEnum.DAY),
    BASE_015("00015", Collections.singletonList("CITY_DETAILS"),"", "","实时现货出清价格","T_BASE_SPOT_REAL_PRICE",FrequencyTypeEnum.CITY_MINUTE96,"各地市加权平均价",new BigDecimal(1),FrequencyTypeEnum.DAY),
    BASE_016("00016", Collections.singletonList("ELEC_DETAILS"),"1", "","日前现货出清电量","T_BASE_SPOT_RECENT_ELEC",FrequencyTypeEnum.DAY_MINUTE96,"出清电量明细",new BigDecimal(1),FrequencyTypeEnum.DAY),
    BASE_017("00017", Collections.singletonList("MAX_ELEC"),"1", "","日前现货出清电量","T_BASE_SPOT_RECENT_ELEC",FrequencyTypeEnum.DAY,"最大值",new BigDecimal(1),FrequencyTypeEnum.DAY),
    BASE_018("00018", Collections.singletonList("MIN_ELEC"),"1", "","日前现货出清电量","T_BASE_SPOT_RECENT_ELEC",FrequencyTypeEnum.DAY,"最小值",new BigDecimal(1),FrequencyTypeEnum.DAY),
    BASE_019("00019", Collections.singletonList("AVG_ELEC_DETAILS"),"1", "","日前现货出清电量","T_BASE_SPOT_RECENT_ELEC",FrequencyTypeEnum.DAY_MINUTE96,"平均值",new BigDecimal(1),FrequencyTypeEnum.DAY),
    BASE_020("00020", Collections.singletonList("COMP_DETAILS"),"", "","日前现货出清电量","T_BASE_SPOT_RECENT_ELEC",FrequencyTypeEnum.MONTH_JSON,"当日累计值及构成",new BigDecimal(1),FrequencyTypeEnum.DAY),
    BASE_021("00021", Collections.singletonList("CITY_DETAILS"),"", "","日前现货出清电量","T_BASE_SPOT_RECENT_ELEC",FrequencyTypeEnum.CITY_MINUTE96,"各地市出清电量",new BigDecimal(1),FrequencyTypeEnum.DAY),
    BASE_022("00022", Collections.singletonList("ELEC_DETAILS"),"2", "","实时现货出清电量","T_BASE_SPOT_REAL_ELEC",FrequencyTypeEnum.DAY_MINUTE96,"出清电量明细",new BigDecimal(1),FrequencyTypeEnum.DAY),
    BASE_023("00023", Collections.singletonList("MAX_ELEC"),"2", "","实时现货出清电量","T_BASE_SPOT_REAL_ELEC",FrequencyTypeEnum.DAY,"最大值",new BigDecimal(1),FrequencyTypeEnum.DAY),
    BASE_024("00024", Collections.singletonList("MIN_ELEC"),"2", "","实时现货出清电量","T_BASE_SPOT_REAL_ELEC",FrequencyTypeEnum.DAY,"最小值",new BigDecimal(1),FrequencyTypeEnum.DAY),
    BASE_025("00025", Collections.singletonList("AVG_ELEC_DETAILS"),"2", "","实时现货出清电量","T_BASE_SPOT_REAL_ELEC",FrequencyTypeEnum.DAY_MINUTE96,"平均值",new BigDecimal(1),FrequencyTypeEnum.DAY),
    BASE_026("00026", Collections.singletonList("COMP_DETAILS"),"", "","实时现货出清电量","T_BASE_SPOT_REAL_ELEC",FrequencyTypeEnum.MONTH_JSON,"出清电量构成",new BigDecimal(1),FrequencyTypeEnum.DAY),
    BASE_027("00027", Collections.singletonList("CITY_DETAILS"),"", "","实时现货出清电量","T_BASE_SPOT_REAL_ELEC",FrequencyTypeEnum.CITY_MINUTE96,"各地市加权出清电量",new BigDecimal(1),FrequencyTypeEnum.DAY),
    BASE_028("00028", Collections.singletonList("YEAR_DETAILS"),"", "","中长期电量","T_BASE_MID_TERM_ELEC_QUANTITY",FrequencyTypeEnum.MORE_MINUTE24,"年度分解到日",new BigDecimal(1),FrequencyTypeEnum.MONTH),
    BASE_029("00029", Collections.singletonList("MONTH_DETAILS"),"", "","中长期电量","T_BASE_MID_TERM_ELEC_QUANTITY",FrequencyTypeEnum.MORE_MINUTE24,"月度分解到日",new BigDecimal(1),FrequencyTypeEnum.MONTH),
    BASE_030("00030", Collections.singletonList("INNER_MONTH_DETAILS"),"", "","中长期电量","T_BASE_MID_TERM_ELEC_QUANTITY",FrequencyTypeEnum.MORE_MINUTE24,"月内分解到日",new BigDecimal(1),FrequencyTypeEnum.MONTH),
    BASE_031("00031", Collections.singletonList("SUM_DETAILS"),"", "","中长期电量","T_BASE_MID_TERM_ELEC_QUANTITY",FrequencyTypeEnum.MORE_MINUTE24,"各日中长期总和",new BigDecimal(1),FrequencyTypeEnum.MONTH),
    BASE_032("00032", Collections.singletonList("YEAR_SETTLE"),"", "","中长期电量","T_BASE_MID_TERM_ELEC_QUANTITY",FrequencyTypeEnum.MONTH,"年度交易月结算量",new BigDecimal(1),FrequencyTypeEnum.MONTH),
    BASE_033("00033", Collections.singletonList("MONTH_SETTLE"),"", "","中长期电量","T_BASE_MID_TERM_ELEC_QUANTITY",FrequencyTypeEnum.MONTH,"月度交易月结算量",new BigDecimal(1),FrequencyTypeEnum.MONTH),
    BASE_034("00034", Collections.singletonList("INNER_MONTH_SETTLE"),"", "","中长期电量","T_BASE_MID_TERM_ELEC_QUANTITY",FrequencyTypeEnum.MONTH,"月内交易月结算量",new BigDecimal(1),FrequencyTypeEnum.MONTH),
    BASE_035("00035", Collections.singletonList("COMP_DETAILS"),"", "","中长期电量","T_BASE_MID_TERM_ELEC_QUANTITY",FrequencyTypeEnum.MONTH_JSON,"各类型中长期结算量",new BigDecimal(1),FrequencyTypeEnum.MONTH),
    BASE_036("00036", Collections.singletonList("YEAR_DETAILS"),"", "","中长期电价","T_BASE_MID_TERM_ELEC_PRICE",FrequencyTypeEnum.MORE_MINUTE24,"年度",new BigDecimal(1),FrequencyTypeEnum.MONTH),
    BASE_037("00037", Collections.singletonList("MONTH_DETAILS"),"", "","中长期电价","T_BASE_MID_TERM_ELEC_PRICE",FrequencyTypeEnum.MORE_MINUTE24,"月度",new BigDecimal(1),FrequencyTypeEnum.MONTH),
    BASE_038("00038", Collections.singletonList("INNER_MONTH_DETAILS"),"", "","中长期电价","T_BASE_MID_TERM_ELEC_PRICE",FrequencyTypeEnum.MORE_MINUTE24,"月内",new BigDecimal(1),FrequencyTypeEnum.MONTH),
    BASE_039("00039", Collections.singletonList("SUM_DETAILS"),"", "","中长期电价","T_BASE_MID_TERM_ELEC_PRICE",FrequencyTypeEnum.MORE_MINUTE24,"各日中长期加权平均价",new BigDecimal(1),FrequencyTypeEnum.MONTH),
    BASE_040("00040", Collections.singletonList("YEAR_SETTLE"),"", "","中长期电价","T_BASE_MID_TERM_ELEC_PRICE",FrequencyTypeEnum.MONTH,"年度交易月结算均价",new BigDecimal(1),FrequencyTypeEnum.MONTH),
    BASE_041("00041", Collections.singletonList("MONTH_SETTLE"),"", "","中长期电价","T_BASE_MID_TERM_ELEC_PRICE",FrequencyTypeEnum.MONTH,"月度交易月结算均价",new BigDecimal(1),FrequencyTypeEnum.MONTH),
    BASE_042("00042", Collections.singletonList("INNER_MONTH_SETTLE"),"", "","中长期电价","T_BASE_MID_TERM_ELEC_PRICE",FrequencyTypeEnum.MONTH,"月内交易月结算均价",new BigDecimal(1),FrequencyTypeEnum.MONTH),
    BASE_043("00043", Collections.singletonList("COMP_DETAILS"),"", "","中长期电价","T_BASE_MID_TERM_ELEC_PRICE",FrequencyTypeEnum.MONTH_JSON,"各类型中长期结算均价",new BigDecimal(1),FrequencyTypeEnum.MONTH),
    BASE_044("00044", Collections.singletonList("AVG_PRICE"),"FREQUENCY_TYPE", "","辅助服务","T_BASE_ASSIST_SERVICE",FrequencyTypeEnum.DAY_AND_MONTH,"调频机组中标均价",new BigDecimal(1),FrequencyTypeEnum.DAY),
    BASE_045("00045", Collections.singletonList("TOTAL_COST"),"FREQUENCY_TYPE", "","辅助服务","T_BASE_ASSIST_SERVICE",FrequencyTypeEnum.DAY_AND_MONTH,"当日调频总费用",new BigDecimal(1),FrequencyTypeEnum.DAY),
    BASE_046("00046", Collections.singletonList("CAPACITY_AVG_PRICE"),"", "","容量补偿","T_BASE_CAPACITY_COMPENSATION",FrequencyTypeEnum.MONTH,"平均容量电价",new BigDecimal(1),FrequencyTypeEnum.MONTH),
    BASE_047("00047", Collections.singletonList("CAPACITY_COST"),"", "","容量补偿","T_BASE_CAPACITY_COMPENSATION",FrequencyTypeEnum.MONTH,"容量费用",new BigDecimal(1),FrequencyTypeEnum.MONTH),
    BASE_048("00048", Collections.singletonList("ELEC_AVG_PRICE"),"", "","容量补偿","T_BASE_CAPACITY_COMPENSATION",FrequencyTypeEnum.MONTH,"度电平均费用",new BigDecimal(1000),FrequencyTypeEnum.MONTH),
    BASE_049("00049", Collections.singletonList("TRADING_POWER"),"STAT_PERIOD", "","绿电交易-结算口径","T_BASE_GREEN_ELEC_SETTLE",FrequencyTypeEnum.MONTH,"省内交易电量",new BigDecimal(1),FrequencyTypeEnum.MONTH),
    BASE_049_03("00049", Collections.singletonList("INTER_PROV_GREE_EXP_ENE"),"STAT_PERIOD", "","绿电交易-结算口径","T_BASE_GREEN_ELEC_SETTLE",FrequencyTypeEnum.MONTH,"省间绿电-送出-电量",new BigDecimal(1),FrequencyTypeEnum.MONTH),
    BASE_049_04("00049", Collections.singletonList("INTER_PROV_GREE_IMP_ENE"),"STAT_PERIOD", "","绿电交易-结算口径","T_BASE_GREEN_ELEC_SETTLE",FrequencyTypeEnum.MONTH,"省间绿电-送入-电量",new BigDecimal(1),FrequencyTypeEnum.MONTH),
    BASE_050("00050", Collections.singletonList("TRADING_PRICE"),"STAT_PERIOD", "","绿电交易-结算口径","T_BASE_GREEN_ELEC_SETTLE",FrequencyTypeEnum.MONTH,"省内交易价格",new BigDecimal(1000),FrequencyTypeEnum.MONTH),
    BASE_050_03("00050", Collections.singletonList("INTER_PROV_GREE_EXP_PRI"),"STAT_PERIOD", "","绿电交易-结算口径","T_BASE_GREEN_ELEC_SETTLE",FrequencyTypeEnum.MONTH,"省间绿电-送出-电价",new BigDecimal(1000),FrequencyTypeEnum.MONTH),
    BASE_050_04("00050", Collections.singletonList("INTER_PROV_GREE_IMP_PRI"),"STAT_PERIOD", "","绿电交易-结算口径","T_BASE_GREEN_ELEC_SETTLE",FrequencyTypeEnum.MONTH,"省间绿电-送入-电价",new BigDecimal(1000),FrequencyTypeEnum.MONTH),

    BASE_051("00051", Arrays.asList("","UP_RATE"),"2", "","实时现货出清价格-日上浮比例与最值环比","T_BASE_RT_CLR_INCMAX_MON",FrequencyTypeEnum.DAY,"日上浮比例",new BigDecimal(1),FrequencyTypeEnum.DAY),
    BASE_051_01("00051",Arrays.asList("", "UP_RATE"),"1", "","日前现货出清价格-日上浮比例与最值环比","T_BASE_DA_CLR_INCMAX_MON",FrequencyTypeEnum.DAY,"日上浮比例",new BigDecimal(1),FrequencyTypeEnum.DAY),
    BASE_052("00052", Arrays.asList("","","MAX_UP_RATE"),"2", "","实时现货出清价格-日上浮比例与最值环比","T_BASE_RT_CLR_INCMAX_MON",FrequencyTypeEnum.DAY,"最大值上浮比例",new BigDecimal(1),FrequencyTypeEnum.DAY),
    BASE_052_01("00052", Arrays.asList("","","MAX_UP_RATE"),"1", "","日前现货出清价格-日上浮比例与最值环比","T_BASE_DA_CLR_INCMAX_MON",FrequencyTypeEnum.DAY,"最大值上浮比例",new BigDecimal(1),FrequencyTypeEnum.DAY),
    BASE_053("00053", Arrays.asList("","","","MIN_UP_RATE"),"2", "","实时现货出清价格-日上浮比例与最值环比","T_BASE_RT_CLR_INCMAX_MON",FrequencyTypeEnum.DAY,"最小值上浮比例",new BigDecimal(1),FrequencyTypeEnum.DAY),
    BASE_053_01("00053", Arrays.asList("","","","MIN_UP_RATE"),"1", "","日前现货出清价格-日上浮比例与最值环比","T_BASE_DA_CLR_INCMAX_MON",FrequencyTypeEnum.DAY,"最小值上浮比例",new BigDecimal(1),FrequencyTypeEnum.DAY),
    BASE_054("00054", Arrays.asList("","","","","DA_RT_DIFF"),"1", "","出清价格-现货差价","T_BASE_CLR_SPOT_DIFF",FrequencyTypeEnum.DAY,"日前-实时差价",new BigDecimal(1),FrequencyTypeEnum.DAY),
    BASE_054_01("00054", Arrays.asList("","","","","DA_RT_DIFF"),"2", "","出清价格-现货差价","T_BASE_CLR_SPOT_DIFF",FrequencyTypeEnum.DAY,"日前-实时差价",new BigDecimal(1),FrequencyTypeEnum.DAY),
    BASE_055("00055", Collections.singletonList("CLEAR_QTY"),"USER_TYPE", "USER_TYPE","日前现货出清电量-全省当日累计值及构成","T_BASE_DA_CLR_ELE_PSUM_CMP",FrequencyTypeEnum.DAY,"出清电量",new BigDecimal(100000),FrequencyTypeEnum.DAY),
    BASE_056("00056", Collections.singletonList("CLEAR_QTY"),"USER_TYPE", "USER_TYPE","实时现货出清电量-全省当日累计值及构成","T_BASE_RT_CLR_ELE_PSUM_CMP",FrequencyTypeEnum.DAY,"出清电量",new BigDecimal(100000),FrequencyTypeEnum.DAY),
    BASE_057("00057", Collections.singletonList("MON_GRID_AGT_SETT_QTY"),"", "","中长期-电量-月度电网企业代理购电结算电量","T_BASE_MID_LONG_TERM_ELEC",FrequencyTypeEnum.MONTH,"月度电网企业代理购电结算电量",new BigDecimal(1),FrequencyTypeEnum.MONTH),
    BASE_058("00058", Collections.singletonList("TOTAL_SOCIAL_ELEC"),"", "","中长期-电量-全社会用电量","T_BASE_MID_LONG_TERM_ELEC_ALL",FrequencyTypeEnum.MONTH,"全社会用电量 单位：亿千瓦时",new BigDecimal(1),FrequencyTypeEnum.MONTH),
    BASE_059("00059", Collections.singletonList("CUR_TRADE_AVG"),"", "","中长期电价-环比-日","T_BASE_MTL_YOY_DAY",FrequencyTypeEnum.DAY,"当前交易日均价 单位：元/兆瓦时",new BigDecimal(1),FrequencyTypeEnum.DAY),
    BASE_060("00060", Collections.singletonList("GROWTH_RATE"),"", "","中长期电价-环比-日","T_BASE_MTL_YOY_DAY",FrequencyTypeEnum.DAY,"环比增长率",new BigDecimal(1),FrequencyTypeEnum.DAY),
    BASE_061("00061", Collections.singletonList("YOY_GROWTH_RATE"),"", "","中长期电价-同比-月","T_BASE_MTL_YOY_MON",FrequencyTypeEnum.MONTH,"同比增长率",new BigDecimal(1),FrequencyTypeEnum.MONTH),
    BASE_062("00062", Collections.singletonList("YOY_GROWTH_RATE"),"", "","中长期电价-同比-年","T_BASE_MTL_YOY_YEAR",FrequencyTypeEnum.YEAR,"同比增长率",new BigDecimal(1),FrequencyTypeEnum.YEAR),
    BASE_063("00063", Collections.singletonList("ALLOC_REBATE_AMT"),"", "","发电侧费用-市场化费用-月","T_BASE_GEN_MKT_MON",FrequencyTypeEnum.MONTH,"分摊返还总费用",new BigDecimal(1),FrequencyTypeEnum.MONTH),
    BASE_063_01("00063", Collections.singletonList("ALLOC_REBATE_AMT"),"", "","发电侧费用-市场化费用-年","T_BASE_GEN_MKT_YEAR",FrequencyTypeEnum.YEAR,"分摊返还总费用",new BigDecimal(1),FrequencyTypeEnum.YEAR),
    BASE_064("00064", Collections.singletonList("GEN_NON_MARKET_AMT"),"", "","发电侧费用-非市场化费用-月","T_BASE_GEN_NONMKT_MON",FrequencyTypeEnum.MONTH,"发电侧非市场化总费用",new BigDecimal(1),FrequencyTypeEnum.MONTH),
    BASE_064_01("00064", Collections.singletonList("GEN_NON_MARKET_AMT"),"", "","发电侧费用-非市场化费用-年","T_BASE_GEN_NONMKT_YEAR",FrequencyTypeEnum.YEAR,"发电侧非市场化总费用",new BigDecimal(1),FrequencyTypeEnum.YEAR),
    BASE_065("00065", Collections.singletonList("AUX_FEE"),"999", "","发电侧-辅助服务费用-月","T_BASE_ASVC_GEN_MON",FrequencyTypeEnum.MONTH,"辅助服务费用",new BigDecimal(1),FrequencyTypeEnum.MONTH),
    BASE_065_01("00065", Collections.singletonList("PEAK_AUX_FEE"),"1", "","发电侧-辅助服务费用-月","T_BASE_ASVC_GEN_MON",FrequencyTypeEnum.MONTH,"调峰辅助服务费用",new BigDecimal(1),FrequencyTypeEnum.MONTH),
    BASE_065_02("00065", Collections.singletonList("REG_AUX_FEE"),"2", "","发电侧-辅助服务费用-月","T_BASE_ASVC_GEN_MON",FrequencyTypeEnum.MONTH,"调频辅助服务费用",new BigDecimal(1),FrequencyTypeEnum.MONTH),
    BASE_065_03("00065", Collections.singletonList("BACKUP_AUX_FEE"),"3", "","发电侧-辅助服务费用-月","T_BASE_ASVC_GEN_MON",FrequencyTypeEnum.MONTH,"备用辅助服务费用",new BigDecimal(1),FrequencyTypeEnum.MONTH),
    BASE_065_04("00065", Collections.singletonList("RAMP_AUX_FEE"),"4", "","发电侧-辅助服务费用-月","T_BASE_ASVC_GEN_MON",FrequencyTypeEnum.MONTH,"爬坡辅助服务费用",new BigDecimal(1),FrequencyTypeEnum.MONTH),
    BASE_065_05("00065", Collections.singletonList("OTH_AUX_FEE"),"5", "","发电侧-辅助服务费用-月","T_BASE_ASVC_GEN_MON",FrequencyTypeEnum.MONTH,"其他辅助费用",new BigDecimal(1),FrequencyTypeEnum.MONTH),
    BASE_065_06("00065", Collections.singletonList("AUX_FEE"),"999", "","发电侧-辅助服务费用-年","T_BASE_ASVC_GEN_YEAR",FrequencyTypeEnum.YEAR,"辅助服务费用",new BigDecimal(1),FrequencyTypeEnum.YEAR),
    BASE_065_07("00065", Collections.singletonList("PEAK_AUX_FEE"),"1", "","发电侧-辅助服务费用-年","T_BASE_ASVC_GEN_YEAR",FrequencyTypeEnum.YEAR,"调峰辅助服务费用",new BigDecimal(1),FrequencyTypeEnum.YEAR),
    BASE_065_08("00065", Collections.singletonList("REG_AUX_FEE"),"2", "","发电侧-辅助服务费用-年","T_BASE_ASVC_GEN_YEAR",FrequencyTypeEnum.YEAR,"调频辅助服务费用",new BigDecimal(1),FrequencyTypeEnum.YEAR),
    BASE_065_09("00065", Collections.singletonList("BACKUP_AUX_FEE"),"3", "","发电侧-辅助服务费用-年","T_BASE_ASVC_GEN_YEAR",FrequencyTypeEnum.YEAR,"备用辅助服务费用",new BigDecimal(1),FrequencyTypeEnum.YEAR),
    BASE_065_10("00065", Collections.singletonList("RAMP_AUX_FEE"),"4", "","发电侧-辅助服务费用-年","T_BASE_ASVC_GEN_YEAR",FrequencyTypeEnum.YEAR,"爬坡辅助服务费用",new BigDecimal(1),FrequencyTypeEnum.YEAR),
    BASE_065_11("00065", Collections.singletonList("OTH_AUX_FEE"),"5", "","发电侧-辅助服务费用-年","T_BASE_ASVC_GEN_YEAR",FrequencyTypeEnum.YEAR,"其他辅助费用",new BigDecimal(1),FrequencyTypeEnum.YEAR),
    BASE_066("00066", Collections.singletonList("ALLOC_REBATE_PRICE"),"", "","发电侧费用-市场化费用-月","T_BASE_GEN_MKT_MON",FrequencyTypeEnum.MONTH,"度电分摊返还均价",new BigDecimal(1),FrequencyTypeEnum.MONTH),
    BASE_066_01("00066", Collections.singletonList("ALLOC_REBATE_PRICE"),"", "","发电侧费用-市场化费用-年","T_BASE_GEN_MKT_YEAR",FrequencyTypeEnum.YEAR,"度电分摊返还均价",new BigDecimal(1),FrequencyTypeEnum.YEAR),
    BASE_067("00067", Collections.singletonList("GEN_NON_RUN_AVG_PRICE"),"", "","发电侧费用-非市场化费用-月","T_BASE_GEN_NONMKT_MON",FrequencyTypeEnum.MONTH,"发电侧非市场运行费用度电分摊返还均价",new BigDecimal(1),FrequencyTypeEnum.MONTH),
    BASE_067_01("00067", Collections.singletonList("GEN_NON_RUN_AVG_PRICE"),"", "","发电侧费用-非市场化费用-年","T_BASE_GEN_NONMKT_YEAR",FrequencyTypeEnum.YEAR,"发电侧非市场运行费用度电分摊返还均价",new BigDecimal(1),FrequencyTypeEnum.YEAR),
    BASE_068("00068", Collections.singletonList("AUX_FEE_PER_QTY"),"999", "","发电侧-辅助服务费用-月","T_BASE_ASVC_GEN_MON",FrequencyTypeEnum.MONTH,"辅助服务费用度电分摊",new BigDecimal(1),FrequencyTypeEnum.MONTH),
    BASE_068_01("00068", Collections.singletonList("PEAK_AUX_PER_QTY"),"1", "","发电侧-辅助服务费用-月","T_BASE_ASVC_GEN_MON",FrequencyTypeEnum.MONTH,"调峰辅助服务费用度电分摊",new BigDecimal(1),FrequencyTypeEnum.MONTH),
    BASE_068_02("00068", Collections.singletonList("REG_AUX_PER_QTY"),"2", "","发电侧-辅助服务费用-月","T_BASE_ASVC_GEN_MON",FrequencyTypeEnum.MONTH,"调频辅助服务费用度电分摊",new BigDecimal(1),FrequencyTypeEnum.MONTH),
    BASE_068_03("00068", Collections.singletonList("BACKUP_AUX_PER_QTY"),"3", "","发电侧-辅助服务费用-月","T_BASE_ASVC_GEN_MON",FrequencyTypeEnum.MONTH,"备用辅助服务费用度电分摊",new BigDecimal(1),FrequencyTypeEnum.MONTH),
    BASE_068_04("00068", Collections.singletonList("RAMP_AUX_PER_QTY"),"4", "","发电侧-辅助服务费用-月","T_BASE_ASVC_GEN_MON",FrequencyTypeEnum.MONTH,"爬坡辅助服务费用度电分摊",new BigDecimal(1),FrequencyTypeEnum.MONTH),
    BASE_068_05("00068", Collections.singletonList("OTH_AUX_PER_QTY"),"5", "","发电侧-辅助服务费用-月","T_BASE_ASVC_GEN_MON",FrequencyTypeEnum.MONTH,"其他辅助费用度电分摊",new BigDecimal(1),FrequencyTypeEnum.MONTH),
    BASE_068_06("00068", Collections.singletonList("AUX_FEE_PER_QTY"),"999", "","发电侧-辅助服务费用-年","T_BASE_ASVC_GEN_YEAR",FrequencyTypeEnum.YEAR,"辅助服务费用度电分摊",new BigDecimal(1),FrequencyTypeEnum.YEAR),
    BASE_068_07("00068", Collections.singletonList("PEAK_AUX_PER_QTY"),"1", "","发电侧-辅助服务费用-年","T_BASE_ASVC_GEN_YEAR",FrequencyTypeEnum.YEAR,"调峰辅助服务费用度电分摊",new BigDecimal(1),FrequencyTypeEnum.YEAR),
    BASE_068_08("00068", Collections.singletonList("REG_AUX_PER_QTY"),"2", "","发电侧-辅助服务费用-年","T_BASE_ASVC_GEN_YEAR",FrequencyTypeEnum.YEAR,"调频辅助服务费用度电分摊",new BigDecimal(1),FrequencyTypeEnum.YEAR),
    BASE_068_09("00068", Collections.singletonList("BACKUP_AUX_PER_QTY"),"3", "","发电侧-辅助服务费用-年","T_BASE_ASVC_GEN_YEAR",FrequencyTypeEnum.YEAR,"备用辅助服务费用度电分摊",new BigDecimal(1),FrequencyTypeEnum.YEAR),
    BASE_068_10("00068", Collections.singletonList("RAMP_AUX_PER_QTY"),"4", "","发电侧-辅助服务费用-年","T_BASE_ASVC_GEN_YEAR",FrequencyTypeEnum.YEAR,"爬坡辅助服务费用度电分摊",new BigDecimal(1),FrequencyTypeEnum.YEAR),
    BASE_068_11("00068", Collections.singletonList("OTH_AUX_PER_QTY"),"5", "","发电侧-辅助服务费用-年","T_BASE_ASVC_GEN_YEAR",FrequencyTypeEnum.YEAR,"其他辅助费用度电分摊",new BigDecimal(1),FrequencyTypeEnum.YEAR),
    BASE_069("00069", Collections.singletonList("ALLOC_REBATE_AMT"),"", "","用户侧费用-市场化费用-月","T_BASE_USR_MKT_MON",FrequencyTypeEnum.MONTH,"分摊返还总费用",new BigDecimal(1),FrequencyTypeEnum.MONTH),
    BASE_069_01("00069", Collections.singletonList("ALLOC_REBATE_AMT"),"", "","用户侧费用-市场化费用-年","T_BASE_USER_MKT_YEAR",FrequencyTypeEnum.YEAR,"分摊返还总费用",new BigDecimal(1),FrequencyTypeEnum.YEAR),
    BASE_070("00070", Collections.singletonList("USER_NON_MARKET_AMT"),"", "","用户侧费用-非市场化费用-月","T_BASE_USER_NONMKT_MON",FrequencyTypeEnum.MONTH,"用户侧非市场化总费用",new BigDecimal(1),FrequencyTypeEnum.MONTH),
    BASE_070_01("00070", Collections.singletonList("USER_NON_MARKET_AMT"),"", "","用户侧费用-非市场化费用-年","T_BASE_USER_NONMKT_YEAR",FrequencyTypeEnum.YEAR,"用户侧非市场化总费用",new BigDecimal(1),FrequencyTypeEnum.YEAR),
    BASE_071("00071", Collections.singletonList("AUX_FEE"),"999", "","用户侧-辅助服务费用-月","T_BASE_ASVC_USER_MON",FrequencyTypeEnum.MONTH,"辅助服务费用",new BigDecimal(1),FrequencyTypeEnum.MONTH),
    BASE_071_01("00071", Collections.singletonList("PEAK_AUX_FEE"),"1", "","用户侧-辅助服务费用-月","T_BASE_ASVC_USER_MON",FrequencyTypeEnum.MONTH,"调峰辅助服务费用",new BigDecimal(1),FrequencyTypeEnum.MONTH),
    BASE_071_02("00071", Collections.singletonList("REG_AUX_FEE"),"2", "","用户侧-辅助服务费用-月","T_BASE_ASVC_USER_MON",FrequencyTypeEnum.MONTH,"调频辅助服务费用",new BigDecimal(1),FrequencyTypeEnum.MONTH),
    BASE_071_03("00071", Collections.singletonList("BACKUP_AUX_FEE"),"3", "","用户侧-辅助服务费用-月","T_BASE_ASVC_USER_MON",FrequencyTypeEnum.MONTH,"备用辅助服务费用",new BigDecimal(1),FrequencyTypeEnum.MONTH),
    BASE_071_04("00071", Collections.singletonList("RAMP_AUX_FEE"),"4", "","用户侧-辅助服务费用-月","T_BASE_ASVC_USER_MON",FrequencyTypeEnum.MONTH,"爬坡辅助服务费用",new BigDecimal(1),FrequencyTypeEnum.MONTH),
    BASE_071_05("00071", Collections.singletonList("OTH_AUX_FEE"),"5", "","用户侧-辅助服务费用-月","T_BASE_ASVC_USER_MON",FrequencyTypeEnum.MONTH,"其他辅助费用",new BigDecimal(1),FrequencyTypeEnum.MONTH),
    BASE_071_06("00071", Collections.singletonList("AUX_FEE"),"999", "","用户侧-辅助服务费用-年","T_BASE_ASVC_USER_YEAR",FrequencyTypeEnum.YEAR,"辅助服务费用",new BigDecimal(1),FrequencyTypeEnum.YEAR),
    BASE_071_07("00071", Collections.singletonList("PEAK_AUX_FEE"),"1", "","用户侧-辅助服务费用-年","T_BASE_ASVC_USER_YEAR",FrequencyTypeEnum.YEAR,"调峰辅助服务费用",new BigDecimal(1),FrequencyTypeEnum.YEAR),
    BASE_071_08("00071", Collections.singletonList("REG_AUX_FEE"),"2", "","用户侧-辅助服务费用-年","T_BASE_ASVC_USER_YEAR",FrequencyTypeEnum.YEAR,"调频辅助服务费用",new BigDecimal(1),FrequencyTypeEnum.YEAR),
    BASE_071_09("00071", Collections.singletonList("BACKUP_AUX_FEE"),"3", "","用户侧-辅助服务费用-年","T_BASE_ASVC_USER_YEAR",FrequencyTypeEnum.YEAR,"备用辅助服务费用",new BigDecimal(1),FrequencyTypeEnum.YEAR),
    BASE_071_10("00071", Collections.singletonList("RAMP_AUX_FEE"),"4", "","用户侧-辅助服务费用-年","T_BASE_ASVC_USER_YEAR",FrequencyTypeEnum.YEAR,"爬坡辅助服务费用",new BigDecimal(1),FrequencyTypeEnum.YEAR),
    BASE_071_11("00071", Collections.singletonList("OTH_AUX_FEE"),"5", "","用户侧-辅助服务费用-年","T_BASE_ASVC_USER_YEAR",FrequencyTypeEnum.YEAR,"其他辅助费用",new BigDecimal(1),FrequencyTypeEnum.YEAR),
    BASE_072("00072", Collections.singletonList("ALLOC_REBATE_PRICE"),"", "","用户侧费用-市场化费用-月","T_BASE_USR_MKT_MON",FrequencyTypeEnum.MONTH,"度电分摊返还均价",new BigDecimal(1),FrequencyTypeEnum.MONTH),
    BASE_072_01("00072", Collections.singletonList("ALLOC_REBATE_PRICE"),"", "","用户侧费用-市场化费用-年","T_BASE_USER_MKT_YEAR",FrequencyTypeEnum.YEAR,"度电分摊返还均价",new BigDecimal(1),FrequencyTypeEnum.YEAR),
    BASE_073("00073", Collections.singletonList("USER_NON_AVG_PRIC"),"", "","用户侧费用-非市场化费用-月","T_BASE_USER_NONMKT_MON",FrequencyTypeEnum.MONTH,"用户侧非市场运行费用度电分摊返还均价",new BigDecimal(1),FrequencyTypeEnum.MONTH),
    BASE_073_01("00073", Collections.singletonList("USER_NON_AVG_PRIC"),"", "","用户侧费用-非市场化费用-年","T_BASE_USER_NONMKT_YEAR",FrequencyTypeEnum.YEAR,"用户侧非市场运行费用度电分摊返还均价",new BigDecimal(1),FrequencyTypeEnum.YEAR),
    BASE_074("00074", Collections.singletonList("AUX_FEE_PER_QTY"),"999", "","用户侧-辅助服务费用-月","T_BASE_ASVC_USER_MON",FrequencyTypeEnum.MONTH,"辅助服务费用度电分摊",new BigDecimal(1),FrequencyTypeEnum.MONTH),
    BASE_074_01("00074", Collections.singletonList("PEAK_AUX_PER_QTY"),"1", "","用户侧-辅助服务费用-月","T_BASE_ASVC_USER_MON",FrequencyTypeEnum.MONTH,"调峰辅助服务费用度电分摊",new BigDecimal(1),FrequencyTypeEnum.MONTH),
    BASE_074_02("00074", Collections.singletonList("REG_AUX_PER_QTY"),"2", "","用户侧-辅助服务费用-月","T_BASE_ASVC_USER_MON",FrequencyTypeEnum.MONTH,"调频辅助服务费用度电分摊",new BigDecimal(1),FrequencyTypeEnum.MONTH),
    BASE_074_03("00074", Collections.singletonList("BACKUP_AUX_PER_QTY"),"3", "","用户侧-辅助服务费用-月","T_BASE_ASVC_USER_MON",FrequencyTypeEnum.MONTH,"备用辅助服务费用度电分摊",new BigDecimal(1),FrequencyTypeEnum.MONTH),
    BASE_074_04("00074", Collections.singletonList("RAMP_AUX_PER_QTY"),"4", "","用户侧-辅助服务费用-月","T_BASE_ASVC_USER_MON",FrequencyTypeEnum.MONTH,"爬坡辅助服务费用度电分摊",new BigDecimal(1),FrequencyTypeEnum.MONTH),
    BASE_074_05("00074", Collections.singletonList("OTH_AUX_PER_QTY"),"5", "","用户侧-辅助服务费用-月","T_BASE_ASVC_USER_MON",FrequencyTypeEnum.MONTH,"其他辅助费用度电分摊",new BigDecimal(1),FrequencyTypeEnum.MONTH),
    BASE_074_06("00074", Collections.singletonList("AUX_FEE_PER_QTY"),"999", "","用户侧-辅助服务费用-年","T_BASE_ASVC_USER_YEAR",FrequencyTypeEnum.YEAR,"辅助服务费用度电分摊",new BigDecimal(1),FrequencyTypeEnum.YEAR),
    BASE_074_07("00074", Collections.singletonList("PEAK_AUX_PER_QTY"),"1", "","用户侧-辅助服务费用-年","T_BASE_ASVC_USER_YEAR",FrequencyTypeEnum.YEAR,"调峰辅助服务费用度电分摊",new BigDecimal(1),FrequencyTypeEnum.YEAR),
    BASE_074_08("00074", Collections.singletonList("REG_AUX_PER_QTY"),"2", "","用户侧-辅助服务费用-年","T_BASE_ASVC_USER_YEAR",FrequencyTypeEnum.YEAR,"调频辅助服务费用度电分摊",new BigDecimal(1),FrequencyTypeEnum.YEAR),
    BASE_074_09("00074", Collections.singletonList("BACKUP_AUX_PER_QTY"),"3", "","用户侧-辅助服务费用-年","T_BASE_ASVC_USER_YEAR",FrequencyTypeEnum.YEAR,"备用辅助服务费用度电分摊",new BigDecimal(1),FrequencyTypeEnum.YEAR),
    BASE_074_10("00074", Collections.singletonList("RAMP_AUX_PER_QTY"),"4", "","用户侧-辅助服务费用-年","T_BASE_ASVC_USER_YEAR",FrequencyTypeEnum.YEAR,"爬坡辅助服务费用度电分摊",new BigDecimal(1),FrequencyTypeEnum.YEAR),
    BASE_074_11("00074", Collections.singletonList("OTH_AUX_PER_QTY"),"5", "","用户侧-辅助服务费用-年","T_BASE_ASVC_USER_YEAR",FrequencyTypeEnum.YEAR,"其他辅助费用度电分摊",new BigDecimal(1),FrequencyTypeEnum.YEAR),
    BASE_075("00075", Collections.singletonList("SETTLE_QTY"),"TYPE", "","交易结算-发电侧-月","T_BASE_SETTLE_GEN_MON",FrequencyTypeEnum.MONTH,"结算电量",new BigDecimal(1),FrequencyTypeEnum.MONTH),
    BASE_075_01("00075", Collections.singletonList("SETTLE_QTY"),"TYPE", "","交易结算-发电侧-年","T_BASE_SETTLE_GEN_YEAR",FrequencyTypeEnum.YEAR,"结算电量",new BigDecimal(1),FrequencyTypeEnum.YEAR),
    BASE_076("00076", Arrays.asList("","","","","","SETTLE_AVG_PRICE"),"TYPE", "","交易结算-发电侧-月","T_BASE_SETTLE_GEN_MON",FrequencyTypeEnum.MONTH,"结算均价",new BigDecimal(1),FrequencyTypeEnum.MONTH),
    BASE_076_01("00076", Arrays.asList("","","","","","SETTLE_AVG_PRICE"),"TYPE", "","交易结算-发电侧-年","T_BASE_SETTLE_GEN_YEAR",FrequencyTypeEnum.YEAR,"结算均价",new BigDecimal(1),FrequencyTypeEnum.YEAR),
    BASE_077("00077", Collections.singletonList("MAX_PRICE"),"TYPE", "","交易结算-发电侧-月","T_BASE_SETTLE_GEN_MON",FrequencyTypeEnum.MONTH,"最高价",new BigDecimal(1),FrequencyTypeEnum.MONTH),
    BASE_077_01("00077", Collections.singletonList("MAX_PRICE"),"TYPE", "","交易结算-发电侧-年","T_BASE_SETTLE_GEN_YEAR",FrequencyTypeEnum.YEAR,"最高价",new BigDecimal(1),FrequencyTypeEnum.YEAR),
    BASE_079("00079", Collections.singletonList("MIN_PRICE"),"TYPE", "","交易结算-发电侧-月","T_BASE_SETTLE_GEN_MON",FrequencyTypeEnum.MONTH,"最低价",new BigDecimal(1),FrequencyTypeEnum.MONTH),
    BASE_079_01("00079", Collections.singletonList("MIN_PRICE"),"TYPE", "","交易结算-发电侧-年","T_BASE_SETTLE_GEN_YEAR",FrequencyTypeEnum.YEAR,"最低价",new BigDecimal(1),FrequencyTypeEnum.YEAR),
    BASE_081("00081", Collections.singletonList("SETTLE_QTY"),"TYPE", "","交易结算-用户侧-月","T_BASE_SETTLE_USER_MON",FrequencyTypeEnum.MONTH,"结算电量",new BigDecimal(1),FrequencyTypeEnum.MONTH),
    BASE_081_01("00081", Collections.singletonList("SETTLE_QTY"),"TYPE", "","交易结算-用户侧-年","T_BASE_SETTLE_USER_YEAR",FrequencyTypeEnum.YEAR,"结算电量",new BigDecimal(1),FrequencyTypeEnum.YEAR),
    BASE_082("00082", Arrays.asList("","","","","","SETTLE_AVG_PRICE"),"TYPE", "","交易结算-用户侧-月","T_BASE_SETTLE_USER_MON",FrequencyTypeEnum.MONTH,"结算均价",new BigDecimal(1),FrequencyTypeEnum.MONTH),
    BASE_082_01("00082", Arrays.asList("","","","","","SETTLE_AVG_PRICE"),"TYPE", "","交易结算-用户侧-年","T_BASE_SETTLE_USER_YEAR",FrequencyTypeEnum.YEAR,"结算均价",new BigDecimal(1),FrequencyTypeEnum.YEAR),
    BASE_083("00083", Collections.singletonList("MAX_PRICE"),"TYPE", "","交易结算-用户侧-月","T_BASE_SETTLE_USER_MON",FrequencyTypeEnum.MONTH,"最高价",new BigDecimal(1),FrequencyTypeEnum.MONTH),
    BASE_083_01("00083", Collections.singletonList("MAX_PRICE"),"TYPE", "","交易结算-用户侧-年","T_BASE_SETTLE_USER_YEAR",FrequencyTypeEnum.YEAR,"最高价",new BigDecimal(1),FrequencyTypeEnum.YEAR),
    BASE_085("00085",Collections.singletonList( "MIN_PRICE"),"TYPE", "","交易结算-用户侧-月","T_BASE_SETTLE_USER_MON",FrequencyTypeEnum.MONTH,"最低价",new BigDecimal(1),FrequencyTypeEnum.MONTH),
    BASE_085_01("00085", Collections.singletonList("MIN_PRICE"),"TYPE", "","交易结算-用户侧-年","T_BASE_SETTLE_USER_YEAR",FrequencyTypeEnum.YEAR,"最低价",new BigDecimal(1),FrequencyTypeEnum.YEAR),
    BASE_087("00087", Collections.singletonList("GEN_ENT"),"1", "","市场成员类型","T_BASE_MAKET_TYPE",FrequencyTypeEnum.MONTH,"发电企业",new BigDecimal(1),FrequencyTypeEnum.MONTH),
    BASE_087_01("00087", Collections.singletonList("ELEC_CO"),"2", "","市场成员类型","T_BASE_MAKET_TYPE",FrequencyTypeEnum.MONTH,"售电公司",new BigDecimal(1),FrequencyTypeEnum.MONTH),
    BASE_087_02("00087", Collections.singletonList("WHOLESALE_USER"),"3", "","市场成员类型","T_BASE_MAKET_TYPE",FrequencyTypeEnum.MONTH,"批发用户 所属：电力用户",new BigDecimal(1),FrequencyTypeEnum.MONTH),
    BASE_087_02_01("00087", Collections.singletonList("RETAIL_USER"),"3", "","市场成员类型","T_BASE_MAKET_TYPE",FrequencyTypeEnum.MONTH,"零售用户 所属：电力用户",new BigDecimal(1),FrequencyTypeEnum.MONTH),
    BASE_087_03("00087", Collections.singletonList("NEW_ENTITY"),"4", "","市场成员类型","T_BASE_MAKET_TYPE",FrequencyTypeEnum.MONTH,"新型主体",new BigDecimal(1),FrequencyTypeEnum.MONTH),
    BASE_087_04("00087", Collections.singletonList("OP_ORG"),"5", "","市场成员类型","T_BASE_MAKET_TYPE",FrequencyTypeEnum.MONTH,"运营机构",new BigDecimal(1),FrequencyTypeEnum.MONTH),
    BASE_087_05("00087", Collections.singletonList("TOTAL_NUM"),"999", "","市场成员类型","T_BASE_MAKET_TYPE",FrequencyTypeEnum.MONTH,"累计值",new BigDecimal(1),FrequencyTypeEnum.MONTH),
    BASE_088("00088", Collections.singletonList("NUM"),"TYPE", "","发电企业构成","T_BASE_GENERATION_COMPOSITION",FrequencyTypeEnum.MONTH,"数量 单位：家",new BigDecimal(1),FrequencyTypeEnum.MONTH),
    BASE_089("00089", Collections.singletonList("INST_CAP"),"TYPE", "","发电企业构成","T_BASE_GENERATION_COMPOSITION",FrequencyTypeEnum.MONTH,"装机容量 单位：万千瓦",new BigDecimal(1),FrequencyTypeEnum.MONTH),
    BASE_090("00090", Collections.singletonList("NUM"),"TYPE", "","新型主体构成","T_BASE_NEW_ENTITY_COMPOSITION",FrequencyTypeEnum.MONTH,"数量",new BigDecimal(1),FrequencyTypeEnum.MONTH),
    BASE_091("00091", Collections.singletonList("INST_CAP"),"TYPE", "","新型主体构成","T_BASE_NEW_ENTITY_COMPOSITION",FrequencyTypeEnum.MONTH,"装机容量",new BigDecimal(1),FrequencyTypeEnum.MONTH),
    UNKNOWN("", Collections.singletonList(""),"", "","未知指标","",FrequencyTypeEnum.DAY,"",new BigDecimal(1),FrequencyTypeEnum.MONTH);

    private final String baseId;
//1:指标值,2:日上浮比例，3：最大值环比增长，4：最小值环比增长,5:现货差价，6：均价，7：最高价出现时间（没这个字段），8：最低价出现时间（没这个字段）
    private final List<String> baseValue;

    private final String type;

    private final String type2;

    private final String baseName;

    private final String baseModel;

    private final FrequencyTypeEnum frequencyTypeEnum;

    private final String columnsName;

    private final BigDecimal unit;

    /**
     * 实际归集频率
     * @param baseId
     * @return
     */
    private final FrequencyTypeEnum gatherEnum;


    public static BaseEnum ofBaseId(String baseId) {
        return Arrays.stream(values()).filter(BASEEnum -> BASEEnum.baseId.equalsIgnoreCase(baseId)).findFirst()
                .orElse(UNKNOWN);
    }

    public static List<BaseEnum> ofBaseModel(String baseModel) {
        return Arrays.stream(values()).filter(BASEEnum -> BASEEnum.baseModel.equalsIgnoreCase(baseModel)).collect(Collectors.toList());
    }

    public static List<BaseEnum> getBaseList() {
        return Arrays.stream(values())
                .filter(baseEnum -> baseEnum != UNKNOWN)
                .collect(Collectors.toList());
    }



}
