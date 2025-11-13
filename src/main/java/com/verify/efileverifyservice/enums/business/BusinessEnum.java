package com.verify.efileverifyservice.enums.business;

import com.verify.efileverifyservice.enums.FrequencyTypeEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@Getter
public enum BusinessEnum {
    BUSINESS_001("0001", Arrays.asList("MID_LONG_CTR_ELEC","GROUP_NAME","GROUP_CODE"),"","","发电企业Top-m指数算子表（仅传排名前15位的发电集团）","T_OPERATOR_PRD_ELEC_TOP_M", FrequencyTypeEnum.MONTH,"中长期交易净合约电量","T_METRIC_PRODUCT_ELECTIC_TOP_M"),
    BUSINESS_002("0002", Arrays.asList("MID_LONG_TOTAL_CTR_ELEC","GROUP_NAME","GROUP_CODE"),"", "","发电企业Top-m指数算子表","T_OPERATOR_PRD_ELEC_TOP_M",FrequencyTypeEnum.MONTH,"当期发电侧总中长期净合约电量","T_METRIC_PRODUCT_ELECTIC_TOP_M"),
    BUSINESS_003("0003", Collections.singletonList("REGISTRATIONS_NUM"),"", "","用电侧主体市场参与度（参与主体数量）算子表","T_OPERATOR_USE_ELE_M_PART_NUM",FrequencyTypeEnum.MONTH,"全省用电侧售电公司和批发用户主体注册总数(Cmax)","T_METRIC_USE_ELE_M_PART_NUM"),
    BUSINESS_004("0004", Collections.singletonList("MARKET_ENTITIES_NUM"),"", "","用电侧主体市场参与度（参与主体数量）算子表","T_OPERATOR_USE_ELE_M_PART_NUM",FrequencyTypeEnum.MONTH,"全省用电侧参与市场的售电公司和批发用户主体个数(Cm)","T_METRIC_USE_ELE_M_PART_NUM"),
    BUSINESS_005("0005", Collections.singletonList("UNITS_ABOVE_COST20"),"", "","机组高价申报率算子表","T_OPERATOR_MOT_UNIT_HIG_P_RATE",FrequencyTypeEnum.DAY,"申报价格高于其发电成本20%的机组个数(N)","T_METRIC_MOT_UNIT_HIG_P_RATE"),
    BUSINESS_006("0006", Collections.singletonList("UNITS_IN_MARKET"),"", "","机组高价申报率算子表","T_OPERATOR_MOT_UNIT_HIG_P_RATE",FrequencyTypeEnum.DAY,"所有参与市场报价的机组个数(M)","T_METRIC_MOT_UNIT_HIG_P_RATE"),
//    有问题
    BUSINESS_007("0007", Collections.singletonList("UNIT_QUOTE"),"", "","机组报价平均加成指数","T_OPERATOR_UNIT_QUO_AVG_PLUS",FrequencyTypeEnum.DAY,"机组报价(P)","T_METRIC_MOT_UNIT_QUO_AVG_PLUS"),
    BUSINESS_008("0008", Collections.singletonList("THER_UNIT_COST"),"", "","机组报价平均加成指数","T_OPERATOR_UNIT_QUO_AVG_PLUS",FrequencyTypeEnum.DAY,"火电机组发电成本(C)","T_METRIC_MOT_UNIT_QUO_AVG_PLUS"),
    BUSINESS_009("0009", Collections.singletonList("COAL_BM_PRICE"),"", "","现货市场出清电价较燃煤基准电价上浮率","T_METRIC_PRICE_BM_FLOAT",FrequencyTypeEnum.DAY,"燃煤基准价","T_METRIC_PRICE_BM_FLOAT"),
    BUSINESS_010("0010", Collections.singletonList("ACT_PRICE_MARKUP_INDEX_SUM"),"", "","现货市场出清平均价格加成指数算子表","T_OPERATOR_MKT_AVG_PRICE_INDEX",FrequencyTypeEnum.DAY,"实际价格加成指数之和(AMIi)","T_METRIC_MKT_AVG_PRICE_INDEX"),
    BUSINESS_011("0011", Collections.singletonList("BIDDING_UNIT_COUNT"),"", "","现货市场出清平均价格加成指数算子表","T_OPERATOR_MKT_AVG_PRICE_INDEX",FrequencyTypeEnum.DAY,"中标机组台数(N)","T_METRIC_MKT_AVG_PRICE_INDEX"),

    BUSINESS_012("0012", Arrays.asList("","GROUP_NAME","GROUP_CODE","UNIT_NAME","UNIT_CODE"),"", "","集团与机组对应关系","T_OPERATOR_GR_UNIT_QUOTA_DIST",FrequencyTypeEnum.DAY,"当期发电侧总中长期净合约电量","T_METRIC_GR_UNIT_QUOTA_DIST"),
    UNKNOWN("", Collections.singletonList(""),"", "","未知指标","",FrequencyTypeEnum.DAY,"","");

    /**
     * 业务的id
     */
    private final String businessId;

    /**
     * 表里的字段
     * 1:指标，2：集团名称，3：集团编码,4：机组名称，5：机组编码
     */
    private final List<String> businessValue;

    private final String type;

    private final String type2;

    private final String businessName;

    private final String businessModel;

    private final FrequencyTypeEnum frequencyTypeEnum;

    private final String columnsName;

    /**
     * 关联的指标表
     */
    private final String metricTable;



    public static BusinessEnum ofBusinessId(String businessId) {
        return Arrays.stream(values()).filter(BusinessEnum -> BusinessEnum.businessId.equalsIgnoreCase(businessId)).findFirst()
                .orElse(UNKNOWN);
    }

    public static List<BusinessEnum> ofBusinessModel(String businessModel) {
        return Arrays.stream(values()).filter(BusinessEnum -> BusinessEnum.businessModel.equalsIgnoreCase(businessModel)).collect(Collectors.toList());
    }

    public static List<BusinessEnum> ofMetricTable(String metricTable) {
        return Arrays.stream(values()).filter(BusinessEnum -> BusinessEnum.metricTable.equalsIgnoreCase(metricTable)).collect(Collectors.toList());
    }


}
