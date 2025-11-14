package com.verify.efileverifyservice.service.impl;

import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import com.alibaba.druid.util.StringUtils;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.verify.efileverifyservice.entity.*;
import com.verify.efileverifyservice.enums.FrequencyTypeEnum;
import com.verify.efileverifyservice.enums.StatsDimensionEnum;
import com.verify.efileverifyservice.enums.base.BaseCorrespondEnum;
import com.verify.efileverifyservice.enums.base.BaseEnum;
import com.verify.efileverifyservice.enums.base.StaticsDimenEnum;
import com.verify.efileverifyservice.enums.business.BusinessEnum;
import com.verify.efileverifyservice.enums.metric.MetricCorrespondEnum;
import com.verify.efileverifyservice.enums.metric.MetricEnum;
import com.verify.efileverifyservice.mapper.DataMapper;
import com.verify.efileverifyservice.mapper.DataTransferMapper;
import com.verify.efileverifyservice.service.DataTransferService;
import com.verify.efileverifyservice.util.EfileUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.PathMatcher;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.sql.Clob;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;


/**
 * @author wangcx
 * @date 2025/7/11
 */
@Slf4j
@Service
public class DataTransferServiceImpl implements DataTransferService {


    @Value("${efile.interface.name}")
    private String interfaceName;

    @Value("${efile.push-url}")
    private String efileUrl;

    @Value("${efile.cron}")
    private String efileCron;

    @Value("${efile.data-url}")
    private String efileDataUrl;

    @Resource
    private DataTransferMapper dataTransferMapper;

    @Resource
    private DataMapper dataMapper;

    @Resource
    private EfileUtil efileUtil;


    /**
     * 根据日期字符串识别时间类型
     * @param dateStr 日期字符串
     * @return 时间类型枚举
     */
    private StaticsDimenEnum identifyDateType(String dateStr) {
        if (StringUtils.isEmpty(dateStr)) {
            return StaticsDimenEnum.DAY; // 默认按日处理
        }

        if (dateStr.matches("\\d{4}-\\d{2}-\\d{2}")) {
            // 日格式: 2025-11-11
            return StaticsDimenEnum.DAY;
        } else if (dateStr.matches("\\d{4}-\\d{2}")) {
            // 月格式: 2025-11
            return StaticsDimenEnum.MONTH;
        } else if (dateStr.matches("\\d{4}-Q[1-4]")) {
            // 季格式: 2024-Q1
            return StaticsDimenEnum.QUARTER;
        } else if (dateStr.matches("\\d{4}")) {
            // 年格式: 2025
            return StaticsDimenEnum.YEAR;
        } else if (dateStr.matches("\\d{4}-W\\d{2}")) {
            // 周格式: 2025-W04
            return StaticsDimenEnum.WEEK;
        }

        return StaticsDimenEnum.DAY; // 默认按日处理
    }


    @Override
    public void transferData(String supplementDate) {
        List<GatherResultInfo> gatherResultInfos = new ArrayList<>();
        StaticsDimenEnum staticsDimenEnum1 = identifyDateType(supplementDate);
        for (MetricDefinition metricDefinition : dataMapper.queryMetricConfig(staticsDimenEnum1.getValue())) {
            GatherResultInfo gatherResultInfo = new GatherResultInfo();
            gatherResultInfo.setModelName(metricDefinition.getRelatedModel());
            gatherResultInfo.setModelChineseName(metricDefinition.getMetricName());
            gatherResultInfo.setStatsDimension(metricDefinition.getStatsDimension());
            gatherResultInfo.setGatherType("0");
            gatherResultInfo.setBusinessTime(supplementDate);
            gatherResultInfos.add(gatherResultInfo);
        }
        dataMapper.queryBaseDataConfig(staticsDimenEnum1.getDisplay()).forEach(baseDataConfig -> {
            GatherResultInfo gatherResultInfo = new GatherResultInfo();
            gatherResultInfo.setModelName(baseDataConfig.getModelName());
            gatherResultInfo.setModelChineseName(baseDataConfig.getBaseDataName());
            StaticsDimenEnum staticsDimenEnum = StaticsDimenEnum.ofDisplay(baseDataConfig.getStatisticalCycle());
            gatherResultInfo.setStatsDimension(Integer.valueOf(staticsDimenEnum.getValue()));
            gatherResultInfo.setGatherType("1");
            gatherResultInfo.setBusinessTime(supplementDate);
            gatherResultInfos.add(gatherResultInfo);
        });



        List<BaseEntity> baseEntityAll = new ArrayList<>();
        List<MetricEntity> metricEntityAll = new ArrayList<>();
        List<BusinessEntity> businessEntityAll = new ArrayList<>();
        List<GatherResultInfo> baseList = new ArrayList<>();
        List<GatherResultInfo> metricList = new ArrayList<>();
        List<GatherResultInfo> businessList = new ArrayList<>();

        gatherResultInfos.parallelStream().forEach(gatherResultInfo -> {
            if ("1".equals(gatherResultInfo.getGatherType())) {
                try {
                    List<BaseEntity> baseEntities = queryBaseData(gatherResultInfo);
                    synchronized (baseEntityAll) {
                        baseEntityAll.addAll(baseEntities);
                    }
                    synchronized (baseList) {
                        baseList.add(gatherResultInfo);
                    }
                } catch (Exception e) {
                    logError("处理基础数据时发生错误", gatherResultInfo.getModelName(), e);
                }
            } else if ("0".equals(gatherResultInfo.getGatherType())) {
                try {
                    List<MetricEntity> metricEntities = queryMetricData(gatherResultInfo);
                    synchronized (metricEntityAll) {
                        metricEntityAll.addAll(metricEntities);
                    }
                    synchronized (metricList) {
                        metricList.add(gatherResultInfo);
                    }
                    List<BusinessEntity> businessEntityList = queryBusinessData(gatherResultInfo);
                    synchronized (businessEntityAll) {
                        businessEntityAll.addAll(businessEntityList);
                    }
                    synchronized (businessList) {
                        businessList.addAll(businessEntityList.stream().map(businessEntity -> {
                            return businessEntity.getGatherResultInfo();
                        }).collect(Collectors.toList()));
                    }
                } catch (Exception e) {
                    logError("处理指标数据时发生错误", gatherResultInfo.getModelName(), e);
                }
            }
        });
        generateEfile(baseEntityAll, metricEntityAll, baseList, metricList, businessEntityAll, businessList,supplementDate);
    }


    /**
     * 记录错误日志
     */
    private void logError(String message, String modelName, Exception e) {
        log.error(message + "，模型名称: " + modelName + "，错误信息: " + e.getMessage());
        e.printStackTrace();
    }

    /**
     * 生成e文件并上传文件服务器
     */
    private void generateEfile(List<BaseEntity> baseEntityAll, List<MetricEntity> metricEntityAll,
                                           List<GatherResultInfo> baseList, List<GatherResultInfo> metricList,
                                           List<BusinessEntity> businessEntityAll, List<GatherResultInfo> businessList, String supplementDate) {
        final String FILE_NAME_FORMAT = "NanW_%s_%s";
        String date = StringUtils.isEmpty(supplementDate) ? DateUtil.format(DateUtil.date(), DatePattern.PURE_DATE_FORMAT) : supplementDate.replace("-", "");

        try {
            // 处理基础数据文件
            if (!baseEntityAll.isEmpty()) {
//                按照时间戳排序
                baseEntityAll.sort(Comparator.comparing(BaseEntity::getIndexCode));
                String baseFileName = String.format(FILE_NAME_FORMAT, date, "JCSJ");
                efileUtil.fileWrite(baseEntityAll, baseFileName, "基础数据", BaseEntity.class,StringUtils.isEmpty(supplementDate) ? "":supplementDate.replace("-", ""));
            }

            // 处理指标数据文件
            if (!metricEntityAll.isEmpty()) {
                metricEntityAll.sort(Comparator.comparing(MetricEntity::getIndexCode));
                String metricFileName = String.format(FILE_NAME_FORMAT, date, "ZBSJ");
                efileUtil.fileWrite(metricEntityAll, metricFileName, "指标数据", MetricEntity.class,StringUtils.isEmpty(supplementDate) ? "":supplementDate.replace("-", ""));
            }

//            处理算子数据文件
            if (!businessEntityAll.isEmpty()) {
                businessEntityAll.sort(Comparator.comparing(BusinessEntity::getIndexCode));
                String businessFileName = String.format(FILE_NAME_FORMAT, date, "YWSJ");
                efileUtil.fileWrite(businessEntityAll, businessFileName, "业务数据", BusinessEntity.class,StringUtils.isEmpty(supplementDate) ? "":supplementDate.replace("-", ""));
            }

//            return push;
        } catch (Exception e) {
            throw new RuntimeException("生成E文件过程中发生错误: " + e.getMessage(), e);
        }
    }


    // 查询基础数据
    private List<BaseEntity> queryBaseData(GatherResultInfo gatherResultInfo) throws Exception {
//        类型为基础数据的，通过modelName和业务时间去表中查询数据，基础数据表中涉及多字段转换成行数据
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("BUSINESS_TIME", gatherResultInfo.getBusinessTime());
        List<Map<String, Object>> dataMap = dataTransferMapper.getDataMap(
                gatherResultInfo.getModelName(), null, paramMap);
        final BigDecimal DEFAULT_VALUE = BigDecimal.valueOf(-1.0);
        final Integer dataType = Integer.parseInt(statsDimensionConversion(gatherResultInfo.getStatsDimension().toString()));
        final int dataTime = gatherResultInfo.getBusinessTime() == null ? -1 : convertStringToTimestamp(gatherResultInfo.getBusinessTime(), statsDimensionConvertSdf(gatherResultInfo.getStatsDimension().toString()));
        final String businessTime = gatherResultInfo.getBusinessTime();
        List<BaseEnum> baseEnums = BaseEnum.ofBaseModel(gatherResultInfo.getModelName());
        if (baseEnums == null || baseEnums.isEmpty()) {
            return Collections.emptyList();
        }

        if (dataMap == null || dataMap.isEmpty()) {
            gatherResultInfo.setIsNull("1");
            List<BaseEntity> baseEntityList = new ArrayList<>();
            for (BaseEnum baseEnum : baseEnums) {
                BaseEntity baseEntity = new BaseEntity();
                baseEntity.setDataTime(dataTime);
                baseEntity.setDataType(dataType);
                baseEntity.setIndexDate(businessTime);
                baseEntity.setIndexCode(baseEnum.getBaseId());
                baseEntityList.add(baseEntity);
            }
            return baseEntityList;
        }
        List<BaseEntity> baseEntityList = new ArrayList<>();
        if (gatherResultInfo.getModelName().equals("T_BASE_DA_CLR_ELE_PSUM_CMP") || gatherResultInfo.getModelName().equals("T_BASE_RT_CLR_ELE_PSUM_CMP")) {
//            计算累计值
            final BigDecimal[] total = {BigDecimal.ZERO};
            List<BaseEntity> baseTotalList = dataMap.stream()
                    .map(map -> {
                        BaseEntity baseEntity = new BaseEntity();
                        baseEntity.setDataTime(dataTime);
                        baseEntity.setDataType(dataType);
                        baseEntity.setIndexDate(businessTime);
                        baseEntity.setIndexCode(baseEnums.get(0).getBaseId());
                        baseEntity.setType(1);
                        baseEntity.setType2(999);
                        String value = (String) map.get("CLEAR_QTY");
                        total[0] = total[0].add(new BigDecimal(value));
                        baseEntity.setDateValue(total[0].multiply(baseEnums.get(0).getUnit()));
                        return baseEntity;
                    })
                    .collect(Collectors.toList());
            baseEntityList.add(baseTotalList.get(baseTotalList.size() - 1));
        }

        for (Map<String, Object> stringObjectMap : dataMap) {
            String basetype = null;
            String basevalue = "0";
            for (BaseEnum baseEnum : baseEnums) {
                BaseEntity baseEntity = new BaseEntity();
                baseEntity.setDataTime(dataTime);
                baseEntity.setDataType(dataType);
                baseEntity.setIndexDate(businessTime);
                baseEntity.setIndexCode(baseEnum.getBaseId());
                baseType(baseEnum, stringObjectMap, baseEntity);
                FrequencyTypeEnum frequencyTypeEnum = baseEnum.getFrequencyTypeEnum();
                String frequencyTypeCode = frequencyTypeEnum.getCode();
                // 处理特殊频率类型的数据
                switch (frequencyTypeCode) {
                    case "8": // 多地市日分时（96点）
                        String jsonCity8 = clobToString(stringObjectMap.get(baseEnum.getBaseValue().get(0)));
                        List<BaseEntity> parseCity96Data = parseCity96Data(jsonCity8, baseEntity);
                        baseEntityList.addAll(parseCity96Data);
                        continue;
                    case "9": // 多日分时（24点）
                        String jsonCity9 = clobToString(stringObjectMap.get(baseEnum.getBaseValue().get(0)));
                        List<BaseEntity> parseDay24Data = parseDay24Data(jsonCity9, baseEntity);
                        baseEntityList.addAll(parseDay24Data);
                        continue;
                    case "10": // 各月各类型
                        String jsonMonth = clobToString(stringObjectMap.get(baseEnum.getBaseValue().get(0)));
                        List<BaseEntity> parseMonthJsonData = parseMonthJsonData(jsonMonth, baseEntity);
                        baseEntityList.addAll(parseMonthJsonData);
                        continue;
                }

                BigDecimal baseValue = DEFAULT_VALUE;
                if (!isSpecialFrequencyType(frequencyTypeCode)) {
                    Object valueObj = stringObjectMap.get(baseEnum.getBaseValue().get(0));
//                    if (gatherResultInfo.getModelName().equals("T_BASE_MAKET_TYPE")) {
//                        if (baseEnum.getType().equals(basetype)) {
//                            Object data = stringObjectMap.get(baseEnum.getBaseValue().get(0));
//                            if (data == null) {
//                                data = stringObjectMap.get(baseEnum.getBaseValue().get(baseEnum.getBaseValue().size() - 1));
//                            }
//                            valueObj = new BigDecimal(data.toString()).add(new BigDecimal(basevalue));
//                        }
//                        basevalue = valueObj == null ? "0" : valueObj.toString();
//                    }
                    if (valueObj != null) {
                        baseValue = new BigDecimal(valueObj.toString()).multiply(baseEnum.getUnit());
                    }
                }
                if ("11".equals(frequencyTypeCode)) {
                    String type = baseEnum.getType();
                    String typeValue = (String) stringObjectMap.get(type);
                    String name = type + typeValue;
                    List<BaseCorrespondEnum> baseCorrespondEnums = BaseCorrespondEnum.ofDbsTableName(gatherResultInfo.getModelName());
                    if (!baseCorrespondEnums.isEmpty()) {
                        BaseCorrespondEnum metricCorrespondEnum = BaseCorrespondEnum.ofName(name);
                        if (metricCorrespondEnum.getDbsType().equals(typeValue)) {
                            frequencyTypeCode = metricCorrespondEnum.getType();
                        }
                    }
                }

                switch (frequencyTypeCode) {
                    case "1": // 按日统计
                        baseEntity.setDateValue(baseValue);
                        baseValue = DEFAULT_VALUE;
                        break;
                    case "2": // 按月统计
                        baseEntity.setMonthValue(baseValue);
                        baseValue = DEFAULT_VALUE;
                        break;
                    case "3": // 按年统计
                        baseEntity.setYearValue(baseValue);
                        baseValue = DEFAULT_VALUE;
                        break;
                    case "4": // 按季度统计
                        baseEntity.setQuarterValue(baseValue);
                        baseValue = DEFAULT_VALUE;
                        break;
                    case "5": // 按周统计
                        baseEntity.setWeekValue(baseValue);
                        baseValue = DEFAULT_VALUE;
                        break;
                    case "6": // 日分时（24点）
                        String json24 = clobToString(stringObjectMap.get(baseEnum.getBaseValue().get(0)));
                        baseEntity.setHourValue(parse24Data(json24).stream()
                                .map(BaseEntity.Hour::getValue)
                                .collect(Collectors.toList()));
                        break;
                    case "7": // 日分时（96点）
                        String json96 = clobToString(stringObjectMap.get(baseEnum.getBaseValue().get(0)));
                        baseEntity.setMinutValue(parse96Data(json96).stream()
                                .map(BaseEntity.Minut::getValue)
                                .collect(Collectors.toList()));
                        break;
                }
                baseEntity.setPriceHigh(baseValue);
                baseEntity.setPriceLow(baseValue);
                baseEntity.setQuantityAverage(baseValue);
                baseEntity.setWeightedValue(baseValue);
                baseEntity.setNodePriceHigh(baseValue);
                baseEntity.setNodePriceLow(baseValue);
                baseEntity.setMarketId("PCSYN");
//                if (gatherResultInfo.getModelName().equals("T_BASE_MAKET_TYPE")) {
//                    if (baseEnum.getType().equals(basetype)) {
//                        baseEntityList.remove(baseEntityList.size() - 1);
//                    }
//                }

                List<String> baseValues = baseEnum.getBaseValue();
                if (baseValues != null && baseValues.size() > 1) {
                    baseEntity.setRatio(new BigDecimal(getMapValue(stringObjectMap, baseValues, 1).isEmpty() ? "-1" : getMapValue(stringObjectMap, baseValues, 1)));
                    baseEntity.setMaxValueGrowth(new BigDecimal(getMapValue(stringObjectMap, baseValues, 2).isEmpty() ? "-1" : getMapValue(stringObjectMap, baseValues, 2)));
                    baseEntity.setMinValueGrowth(new BigDecimal(getMapValue(stringObjectMap, baseValues, 3).isEmpty() ? "-1" : getMapValue(stringObjectMap, baseValues, 3)));
                    baseEntity.setPriceDiff(new BigDecimal(getMapValue(stringObjectMap, baseValues, 4).isEmpty() ? "-1" : getMapValue(stringObjectMap, baseValues, 4)));
                    baseEntity.setAveragePrice(new BigDecimal(getMapValue(stringObjectMap, baseValues, 5).isEmpty() ? "-1" : getMapValue(stringObjectMap, baseValues, 5)));
                }


                baseEntityList.add(baseEntity);
//                if (gatherResultInfo.getModelName().equals("T_BASE_MAKET_TYPE")) {
//                    basetype = baseEnum.getType();
//                }
            }
        }

        return baseEntityList;
    }

    //    查询算子数据
    private List<BusinessEntity> queryBusinessData(GatherResultInfo gatherResultInfo) throws ParseException {
        //            查询该指标是否包含算子
        List<BusinessEnum> businessEnums = BusinessEnum.ofMetricTable(gatherResultInfo.getModelName());
        List<BusinessEntity> businessEntityList = new ArrayList<>();
        if (businessEnums != null) {
//                查询算子数据
            for (BusinessEnum businessEnum : businessEnums) {
                gatherResultInfo.setModelName(businessEnum.getBusinessModel());
                //        类型为基础数据的，通过modelName和业务时间去表中查询数据，基础数据表中涉及多字段转换成行数据
                Map<String, Object> paramMap = new HashMap<>();
                paramMap.put("BUSINESS_TIME", gatherResultInfo.getBusinessTime());
                List<Map<String, Object>> dataMap = dataTransferMapper.getDataMap(
                        gatherResultInfo.getModelName(), null, paramMap);

                final BigDecimal DEFAULT_VALUE = BigDecimal.valueOf(-1.0);
                final Integer dataType = Integer.parseInt(statsDimensionConversion(gatherResultInfo.getStatsDimension().toString()));
                final int dataTime = gatherResultInfo.getBusinessTime() == null ? -1 : convertStringToTimestamp(gatherResultInfo.getBusinessTime(), statsDimensionConvertSdf(gatherResultInfo.getStatsDimension().toString()));
                final String businessTime = gatherResultInfo.getBusinessTime();
                if (businessEnums == null || businessEnums.isEmpty()) {
                    return Collections.emptyList();
                }
                if (dataMap == null || dataMap.isEmpty()) {
                    gatherResultInfo.setIsNull("1");
                    List<BusinessEntity> businessEntityLists = new ArrayList<>();
                    for (BusinessEnum businessEnumNull : businessEnums) {
                        BusinessEntity businessEntity = new BusinessEntity();
                        BeanUtils.copyProperties(gatherResultInfo, businessEntity.getGatherResultInfo());
                        businessEntity.getGatherResultInfo().setGatherType("2");
                        businessEntity.setDataTime(dataTime);
                        businessEntity.setDataType(dataType);
                        businessEntity.setIndexDate(businessTime);
                        businessEntity.setIndexCode(businessEnumNull.getBusinessId());
                        businessEntityLists.add(businessEntity);
                    }
                    return businessEntityLists;
                }
                for (Map<String, Object> stringObjectMap : dataMap) {
                    BusinessEntity businessEntity = new BusinessEntity();
                    BeanUtils.copyProperties(gatherResultInfo, businessEntity.getGatherResultInfo());
                    businessEntity.getGatherResultInfo().setGatherType("2");
                    businessEntity.setDataTime(dataTime);
                    businessEntity.setDataType(dataType);
                    businessEntity.setIndexDate(businessTime);
                    businessEntity.setIndexCode(businessEnum.getBusinessId());
                    if (gatherResultInfo.getModelName().equals("T_OPERATOR_UNIT_QUO_AVG_PLUS")) {
                        businessEntity.setType(10);
//                        gatherResultInfo.setIsNull("1");
                        BusinessEntity businessEntity1 = new BusinessEntity();
                        BeanUtils.copyProperties(gatherResultInfo, businessEntity1.getGatherResultInfo());
                        businessEntity1.getGatherResultInfo().setGatherType("2");
                        businessEntity1.setType(11);
                        businessEntity1.setDataTime(dataTime);
                        businessEntity1.setDataType(dataType);
                        businessEntity1.setIndexDate(businessTime);
                        businessEntity1.setIndexCode(businessEnum.getBusinessId());
                        businessEntityList.add(businessEntity1);
                    }
//                有问题
//                baseType(businessEnum, stringObjectMap, businessEntity);
                    FrequencyTypeEnum frequencyTypeEnum = businessEnum.getFrequencyTypeEnum();
                    String frequencyTypeCode = frequencyTypeEnum.getCode();
                    BigDecimal baseValue = DEFAULT_VALUE;
                    if (!isSpecialFrequencyType(frequencyTypeCode)) {
                        Object valueObj = stringObjectMap.get(Objects.requireNonNull(businessEnum.getBusinessValue()).get(0));
                        if (valueObj != null) {
                            baseValue = new BigDecimal(valueObj.toString());
                        }
                    }
                    switch (frequencyTypeCode) {
                        case "1": // 按日统计
                            businessEntity.setDateValue(baseValue);
                            baseValue = DEFAULT_VALUE;
                            break;
                        case "2": // 按月统计
                            businessEntity.setMonthValue(baseValue);
                            baseValue = DEFAULT_VALUE;
                            break;
                        case "3": // 按年统计
                            businessEntity.setYearValue(baseValue);
                            baseValue = DEFAULT_VALUE;
                            break;
                        case "4": // 按季度统计
                            businessEntity.setQuarterValue(baseValue);
                            baseValue = DEFAULT_VALUE;
                            break;
                        case "5": // 按周统计
                            businessEntity.setWeekValue(baseValue);
                            baseValue = DEFAULT_VALUE;
                            break;
                    }

//                有问题
                    businessEntity.setUnitId("");
                    businessEntity.setUnitname("");
                    businessEntity.setGroupName("");
                    businessEntity.setGroupId("");
                    businessEntity.setMarketId("PCSYN");
                    List<String> businessValue = businessEnum.getBusinessValue();
                    if (businessValue != null && businessValue.size() > 1) {
                        businessEntity.setGroupName(getMapValue(stringObjectMap, businessValue, 1));
                        businessEntity.setGroupId(getMapValue(stringObjectMap, businessValue, 2));
                        businessEntity.setUnitname(getMapValue(stringObjectMap, businessValue, 3));
                        businessEntity.setUnitId(getMapValue(stringObjectMap, businessValue, 4));
                    }
                    businessEntityList.add(businessEntity);
                }
            }
        }
        return businessEntityList;
    }

    /**
     * 判断是否为特殊频率类型
     */
    private boolean isSpecialFrequencyType(String frequencyTypeCode) {
        return "7".equals(frequencyTypeCode) || "6".equals(frequencyTypeCode) ||
                "8".equals(frequencyTypeCode) || "9".equals(frequencyTypeCode) ||
                "10".equals(frequencyTypeCode);
    }

    // 查询指标数据
    private List<MetricEntity> queryMetricData(GatherResultInfo gatherResultInfo) throws Exception {
        Map<String, Object> paramMap = new HashMap<>();
        List<MetricEntity> metricEntityList = new ArrayList<>();
        paramMap.put("BUSINESS_TIME", gatherResultInfo.getBusinessTime());
        paramMap.put("STATS_DIMENSION", gatherResultInfo.getStatsDimension());
        if (gatherResultInfo.getModelName().equals("T_METRIC_GRE_ELE_TRANS_SITU")) {
            Map<String, String> likeParam = new HashMap<>();
            likeParam.put("METRIC_VAL_NAME", "年");
            paramMap.put("likeParam", likeParam);
        }
        List<MetricEnum> metricEnums = MetricEnum.ofMetricModel(gatherResultInfo.getModelName());
        if (metricEnums != null || !metricEnums.isEmpty()) {
            if (metricEnums.get(0).getType().equals("METRIC_VAL_NAME")) {
                List<MetricCorrespondEnum> metricCorrespondEnums = MetricCorrespondEnum.ofModelName(gatherResultInfo.getModelName());
                for (MetricCorrespondEnum metricCorrespondEnum : metricCorrespondEnums) {
//                    判断是否是一个值多种类型，需要根据类型来判断的
                    paramMap.put("METRIC_VAL_NAME", metricCorrespondEnum.getDbsType1());
                    List<MetricEntity> metric = metric(gatherResultInfo, paramMap, metricEnums);
                    metricEntityList.addAll(metric);
                }
            } else {
                metricEntityList.addAll(metric(gatherResultInfo, paramMap, metricEnums));
            }
        }
        return metricEntityList;
    }

    /**
     * 单独指标查询
     */
    public List<MetricEntity> metric(GatherResultInfo gatherResultInfo, Map<String, Object> paramMap, List<MetricEnum> metricEnums) throws Exception {
        List<Map<String, Object>> dataMap = dataTransferMapper.getDataMap(
                gatherResultInfo.getModelName(), null, paramMap);

        final int dataTime = gatherResultInfo.getBusinessTime() == null ? -1 : convertStringToTimestamp(gatherResultInfo.getBusinessTime(), statsDimensionConvertSdf(gatherResultInfo.getStatsDimension().toString()));
        final Integer dataType = Integer.parseInt(statsDimensionConversion(gatherResultInfo.getStatsDimension().toString()));
        final String businessTime = gatherResultInfo.getBusinessTime();

        if (dataMap == null || dataMap.isEmpty()) {
            gatherResultInfo.setIsNull("1");
            List<MetricEntity> metricEntityList = new ArrayList<>();
            MetricEntity metricEntity = new MetricEntity();
            metricEntity.setDataTime(dataTime);
            metricEntity.setDataType(dataType);
            metricEntity.setIndexDate(businessTime);
            if (metricEnums.size() == 1) {
                metricEntity.setIndexCode(metricEnums.get(0).getMetricId());
            } else {
                for (MetricEnum metricEnum : metricEnums) {
                    metricEntity.setIndexCode(metricEnum.getMetricId());
                }
            }
            metricEntityList.add(metricEntity);
            return metricEntityList;
        }


        List<MetricEntity> metricEntityList = new ArrayList<>();

        // 判断是否是96点数据
        if (dataMap.get(0).get("TIME_PERIOD") != null) {
            metricEntityList.add(metric96(dataMap, gatherResultInfo));
        }
//        判断是否是24点数据
        else if (dataMap.get(0).get("HOUR_PERIOD") != null) {
            metricEntityList.add(metric24(dataMap, gatherResultInfo));
        } else {
            if (metricEnums == null || metricEnums.isEmpty()) {
                return Collections.emptyList();
            }

            for (Map<String, Object> stringObjectMap : dataMap) {
                MetricEntity metricEntity = new MetricEntity();
                metricEntity.setDataTime(dataTime);
                metricEntity.setDataType(dataType);
                metricEntity.setIndexDate(businessTime);

                if (metricEnums.size() == 1) {
                    MetricEnum metricEnum = metricEnums.get(0);
                    metricEntityList.add(metricAssignment(metricEnum, stringObjectMap, metricEntity, null));
                } else {
                    List<MetricEntity> metricEntityLists = new ArrayList<>();
                    List<List<String>> collect = metricEnums.stream().map(MetricEnum::getMetricValue).distinct().collect(Collectors.toList());
                    if (collect.size() == 1) {
                        Integer type = Integer.parseInt(statsDimensionConversion(stringObjectMap.get("STATS_DIMENSION").toString()));
                        metricEntity.setDataType(type);
//                        需要根据不同的周期存入不同的值
                        metricEntityList.add(metricAssignment(metricEnums.get(0), stringObjectMap, metricEntity, stringObjectMap.get("FREQUENCY_TYPE").toString()));
                    } else {
                        for (MetricEnum metricEnum : metricEnums) {
                            metricEntityLists.add(metricAssignment(metricEnum, stringObjectMap, metricEntity, null));
                        }
                        if (metricEntityLists.size() >= 2) {
                            metricEntityLists.get(0).setYearValue(metricEntityLists.get(1).getYearValue());
                        }
                        metricEntityList.add(metricEntityLists.get(0));
                    }
                }
            }
        }
        return metricEntityList;
    }

    /**
     * 统计维度转换
     */
    private String statsDimensionConversion(String statsDimension) {
        StatsDimensionEnum statsDimensionEnum = StatsDimensionEnum.of(statsDimension);
        return statsDimensionEnum.getType();
    }

    private String statsDimensionConvertSdf(String statsDimension) {
        StatsDimensionEnum statsDimensionEnum = StatsDimensionEnum.of(statsDimension);
        return statsDimensionEnum.getSdf();
    }

    /**
     * 指标处理
     */
    public MetricEntity metricAssignment(MetricEnum metricEnum,
                                         Map<String, Object> stringObjectMap,
                                         MetricEntity metricEntity, String code) throws Exception {
        metricEntity.setIndexCode(metricEnum.getMetricId());
        metricType(metricEnum, stringObjectMap, metricEntity);

        FrequencyTypeEnum frequencyTypeEnum = metricEnum.getFrequencyTypeEnum();
        final BigDecimal DEFAULT_VALUE = BigDecimal.valueOf(-1.0);
        BigDecimal metricValue = DEFAULT_VALUE;
        String frequencyCode = frequencyTypeEnum.getCode();
        if (!StringUtils.isEmpty(code)) {
            frequencyCode = code;
        }

        if (isRegularFrequencyType(frequencyCode)) {
            Object valueObj = stringObjectMap.get(metricEnum.getMetricValue().get(0));
            if (valueObj != null) {
                metricValue = new BigDecimal(valueObj.toString());
            }
        }

        switch (frequencyCode) {
            case "1":
                metricEntity.setDateValue(metricValue);
                metricValue = DEFAULT_VALUE;
                break;
            case "2":
                metricEntity.setMonthValue(metricValue);
                metricValue = DEFAULT_VALUE;
                break;
            case "3":
                metricEntity.setYearValue(metricValue);
                metricValue = DEFAULT_VALUE;
                break;
            case "4":
                metricEntity.setQuarterValue(metricValue);
                metricValue = DEFAULT_VALUE;
                break;
            case "5":
                metricEntity.setWeekValue(metricValue);
                metricValue = DEFAULT_VALUE;
                break;
        }
        metricEntity.setLongTermPriceRate(metricValue);
        metricEntity.setSpotPriceRate(metricValue);
        metricEntity.setPriceRate(metricValue);
        metricEntity.setPositiveReserveCapacity(metricValue);
        metricEntity.setNegativeReserveCapacity(metricValue);
        metricEntity.setPositiveReserveDemand(metricValue);
        metricEntity.setNegativeReserveDemand(metricValue);
        metricEntity.setPositiveReserveMargin(metricValue);
        metricEntity.setNegativeReserveMargin(metricValue);

        List<String> metricValues = metricEnum.getMetricValue();
        if (metricValues != null && metricValues.size() > 1) {
            metricEntity.setUnitName(getMapValue(stringObjectMap, metricValues, 1));
            metricEntity.setGroupName(getMapValue(stringObjectMap, metricValues, 2));
            metricEntity.setMemberType(getMapValue(stringObjectMap, metricValues, 3));
            metricEntity.setMemberName(getMapValue(stringObjectMap, metricValues, 4));
            metricEntity.setUnitId(getMapValue(stringObjectMap, metricValues, 5));
            metricEntity.setMemberId(getMapValue(stringObjectMap, metricValues, 6));
            if (isReserveMetric(stringObjectMap)) {
                handleReserveMetrics(stringObjectMap, metricValues, metricEntity);
            }
        }

        return metricEntity;
    }


    /**
     * 判断是否为备用指标
     */
    private boolean isReserveMetric(Map<String, Object> stringObjectMap) {
        Object metricNameObj = stringObjectMap.get("METRIC_NAME");
        if (metricNameObj == null) {
            return false;
        }
        String metricName = metricNameObj.toString();
        return metricName.contains("备用");
    }

    /**
     * 处理备用指标数据
     */
    private void handleReserveMetrics(Map<String, Object> stringObjectMap, List<String> metricValues, MetricEntity metricEntity) {
        Object metricValNameObj = stringObjectMap.get("METRIC_VAL_NAME");
        if (metricValNameObj == null) {
            return;
        }
        String metricValName = metricValNameObj.toString();
        // 判断是正备用还是负备用
        if (metricValName.contains("正")) {
            setPositiveReserveValues(stringObjectMap, metricValues, metricEntity);
        } else if (metricValName.contains("负")) {
            setNegativeReserveValues(stringObjectMap, metricValues, metricEntity);
        }
    }

    /**
     * 设置正备用相关值
     */
    private void setPositiveReserveValues(Map<String, Object> stringObjectMap, List<String> metricValues, MetricEntity metricEntity) {
        Object metricValNameObj = stringObjectMap.get("METRIC_VAL_NAME");
        if (metricValNameObj == null) {
            return;
        }
        String metricValName = metricValNameObj.toString();
        if (metricValName.contains("容量")) {
            metricEntity.setPositiveReserveCapacity(getReserveBigDecimalValue(stringObjectMap, metricValues));
        } else if (metricValName.contains("需求")) {
            metricEntity.setPositiveReserveDemand(getReserveBigDecimalValue(stringObjectMap, metricValues));
        } else if (metricValName.contains("裕度")) {
            metricEntity.setPositiveReserveMargin(getReserveBigDecimalValue(stringObjectMap, metricValues));
        }
    }

    /**
     * 设置负备用相关值
     */
    private void setNegativeReserveValues(Map<String, Object> stringObjectMap, List<String> metricValues, MetricEntity metricEntity) {
        Object metricValNameObj = stringObjectMap.get("METRIC_VAL_NAME");
        if (metricValNameObj == null) {
            return;
        }
        String metricValName = metricValNameObj.toString();
        if (metricValName.contains("容量")) {
            metricEntity.setNegativeReserveCapacity(getReserveBigDecimalValue(stringObjectMap, metricValues));
        } else if (metricValName.contains("需求")) {
            metricEntity.setNegativeReserveDemand(getReserveBigDecimalValue(stringObjectMap, metricValues));
        } else if (metricValName.contains("裕度")) {
            metricEntity.setNegativeReserveMargin(getReserveBigDecimalValue(stringObjectMap, metricValues));
        }
    }

    /**
     * 获取备用指标的BigDecimal值
     */
    private BigDecimal getReserveBigDecimalValue(Map<String, Object> stringObjectMap, List<String> metricValues) {
        String valueStr = getMapValue(stringObjectMap, metricValues, 7);
        // 如果都为空，返回0
        if (valueStr.isEmpty()) {
            valueStr = "-1";
        }
        try {
            return new BigDecimal(valueStr);
        } catch (NumberFormatException e) {
            return BigDecimal.ZERO;
        }
    }


    /**
     * 获取Map中的值
     */
    private String getMapValue(Map<String, Object> map, List<String> keys, int index) {
        if (index >= keys.size() || keys.get(index).isEmpty()) {
            return "";
        }
        Object value = map.get(keys.get(index));
        return value == null ? "" : value.toString();
    }

    /**
     * 判断是否为常规频率类型
     */
    private boolean isRegularFrequencyType(String frequencyCode) {
        return "1".equals(frequencyCode) || "2".equals(frequencyCode) ||
                "3".equals(frequencyCode) || "4".equals(frequencyCode) ||
                "5".equals(frequencyCode) || "7".equals(frequencyCode);
    }

    /**
     * 指标96点处理
     */
    public MetricEntity metric96(List<Map<String, Object>> dataMap, GatherResultInfo gatherResultInfo) throws ParseException {
        MetricEntity metricEntity = new MetricEntity();
        final int dataTime = gatherResultInfo.getBusinessTime() == null ? -1 : convertStringToTimestamp(gatherResultInfo.getBusinessTime(), statsDimensionConvertSdf(gatherResultInfo.getStatsDimension().toString()));
        metricEntity.setDataTime(dataTime);
        metricEntity.setDataType(1);
        metricEntity.setIndexDate(gatherResultInfo.getBusinessTime());

        List<MetricEnum> metricEnums = MetricEnum.ofMetricModel(gatherResultInfo.getModelName());
        if (metricEnums == null || metricEnums.isEmpty()) {
            return metricEntity;
        }

        metricEntity.setIndexCode(metricEnums.get(0).getMetricId());

        final BigDecimal DEFAULT_VALUE = BigDecimal.valueOf(-1.0);
        Map<Integer, BigDecimal> valueMap = new HashMap<>();

        for (Map<String, Object> stringObjectMap : dataMap) {
            Object timePeriodObj = stringObjectMap.get("TIME_PERIOD");
            Object valueObj = stringObjectMap.get("METRIC_VALUE");

            if (timePeriodObj != null && valueObj != null) {
                try {
                    int timePeriod = Integer.parseInt(timePeriodObj.toString());
                    String valueStr = valueObj.toString();
                    if (!valueStr.isEmpty()) {
                        BigDecimal value = new BigDecimal(valueStr);
                        valueMap.put(timePeriod, value);
                    }
                } catch (NumberFormatException e) {
                    // 忽略无法解析的数字格式
                }
            }
        }

        List<BigDecimal> minutValues = new ArrayList<>();
        for (int i = 1; i <= 96; i++) {
            BigDecimal value = valueMap.getOrDefault(i, DEFAULT_VALUE);
            minutValues.add(value);
        }

        metricEntity.setMinutValue(minutValues);

        if (!dataMap.isEmpty()) {
            metricType(metricEnums.get(0), dataMap.get(0), metricEntity);
        }

        return metricEntity;
    }


    /**
     * 指标24点处理
     */
    public MetricEntity metric24(List<Map<String, Object>> dataMap, GatherResultInfo gatherResultInfo) throws ParseException {
        MetricEntity metricEntity = new MetricEntity();
        final int dataTime = gatherResultInfo.getBusinessTime() == null ? -1 : convertStringToTimestamp(gatherResultInfo.getBusinessTime(), statsDimensionConvertSdf(gatherResultInfo.getStatsDimension().toString()));
        metricEntity.setDataTime(dataTime);
        metricEntity.setDataType(7);
        metricEntity.setIndexDate(gatherResultInfo.getBusinessTime());

        List<MetricEnum> metricEnums = MetricEnum.ofMetricModel(gatherResultInfo.getModelName());
        if (metricEnums == null || metricEnums.isEmpty()) {
            return metricEntity;
        }

        metricEntity.setIndexCode(metricEnums.get(0).getMetricId());

        final BigDecimal DEFAULT_VALUE = BigDecimal.valueOf(-1.0);
        Map<String, BigDecimal> valueMap = new HashMap<>();

        for (Map<String, Object> stringObjectMap : dataMap) {
            Object timePeriodObj = stringObjectMap.get("HOUR_PERIOD");
            Object valueObj = stringObjectMap.get("METRIC_VALUE");
            if (timePeriodObj != null && valueObj != null) {
                try {
                    String timePeriod = timePeriodObj.toString();
                    String valueStr = valueObj.toString();
                    if (!valueStr.isEmpty()) {
                        BigDecimal value = new BigDecimal(valueStr);
                        valueMap.put(timePeriod, value);
                    }
                } catch (NumberFormatException e) {
                    // 忽略无法解析的数字格式
                }
            }
        }
        List<BigDecimal> hoursList = new ArrayList<>();
        for (int i = 0; i < 24; i++) {
            String timePeriod = (i == 0) ? "00:00" : String.format("%02d:00", (i) % 24);
            BigDecimal value = valueMap.getOrDefault(timePeriod, DEFAULT_VALUE);
            hoursList.add(value);
        }
        metricEntity.setHourValue(hoursList);
        return metricEntity;
    }

    /**
     * 时间转时间戳
     */
    public static int convertStringToTimestamp(String timeString, String format) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        Date date = sdf.parse(timeString);
        return (int) (date.getTime() / 1000);
    }

    public static int convertDateToTimestamp(Date date) throws ParseException {
        return (int) (date.getTime() / 1000);
    }

    /**
     * 基础数据类别判断
     */

    public void baseType(BaseEnum baseEnum, Map<String, Object> stringObjectMap, BaseEntity baseEntity) {
        String baseEnumType = baseEnum.getType();
        if (isNumericType(baseEnumType)) {
            baseEntity.setType(Integer.parseInt(baseEnumType));
            return;
        }

        String baseModel = baseEnum.getBaseModel();
        if (StrUtil.isNotEmpty(baseEnumType)) {
            handleBaseType1(stringObjectMap, baseEnumType, baseModel, baseEntity);
        }
        if (StrUtil.isNotEmpty(baseEnum.getType2())) {
            handleBaseType2(stringObjectMap, baseEnum.getType2(), baseModel, baseEntity);
        }
    }


    private void handleBaseType1(Map<String, Object> stringObjectMap, String baseEnumType,
                                 String baseModel, BaseEntity baseEntity) {
        Object typeObj = stringObjectMap.get(baseEnumType);
        if (typeObj != null) {
            String type = typeObj.toString();
            BaseCorrespondEnum metricCorrespondEnum = BaseCorrespondEnum.ofModelNameAndType1(baseModel, type);
            if (metricCorrespondEnum != null) {
                try {
                    baseEntity.setType(Integer.parseInt(metricCorrespondEnum.getType()));
                } catch (NumberFormatException e) {
                    // 忽略无法解析的数字格式
                }
            } else {
                List<BaseCorrespondEnum> baseCorrespondEnums = BaseCorrespondEnum.ofDbsTableName(baseModel);
                if (baseCorrespondEnums.isEmpty()) {
                    BaseCorrespondEnum metricCorrespondEnumType = BaseCorrespondEnum.ofDbsType(type);
                    if (metricCorrespondEnumType != null) {
                        try {
                            baseEntity.setType(Integer.parseInt(metricCorrespondEnumType.getType()));
                        } catch (NumberFormatException e) {
                            // 忽略无法解析的数字格式
                        }
                    }
                } else {
                    for (BaseCorrespondEnum baseCorrespondEnum : baseCorrespondEnums) {
                        if (baseCorrespondEnum != null) {
                            try {
                                if (baseCorrespondEnum.getDbsType().equals(type)) {
                                    baseEntity.setType(Integer.parseInt(baseCorrespondEnum.getType()));
                                    return;
                                }
                            } catch (NumberFormatException e) {
                                // 忽略无法解析的数字格式
                            }
                        }
                    }
                }
            }
        }
    }

    private void handleBaseType2(Map<String, Object> stringObjectMap, String baseEnumType,
                                 String baseModel, BaseEntity baseEntity) {
        Object typeObj = stringObjectMap.get(baseEnumType);
        if (typeObj != null) {
            String type = typeObj.toString();
            BaseCorrespondEnum metricCorrespondEnum = BaseCorrespondEnum.ofModelNameAndType2(baseModel, type);
            if (metricCorrespondEnum != null) {
                try {
                    baseEntity.setType2(Integer.parseInt(metricCorrespondEnum.getType2()));
                } catch (NumberFormatException e) {
                    // 忽略无法解析的数字格式
                }
            }
        }
    }

    /**
     * 判断是否为数字类型
     */
    private boolean isNumericType(String type) {
        return type.matches("^-?\\d+$");
//        return "1".equals(type) || "2".equals(type);
    }

    /**
     * 指标类别判断
     */
    public void metricType(MetricEnum metricEnum, Map<String, Object> stringObjectMap, MetricEntity metricEntity) {
        String metricEnumType = metricEnum.getType();
        if (isNumericType(metricEnumType)) {
            metricEntity.setType(Integer.parseInt(metricEnumType));
            return;
        }

        String metricModel = metricEnum.getMetricModel();
        if (StrUtil.isNotEmpty(metricEnumType)) {
            handleMetricType1(stringObjectMap, metricEnumType, metricModel, metricEntity);
        }
        if (StrUtil.isNotEmpty(metricEnum.getType2())) {
            handleMetricType2(stringObjectMap, metricEnum.getType2(), metricModel, metricEntity);
        }
    }

    /**
     * 处理指标类型1
     */
    private void handleMetricType1(Map<String, Object> stringObjectMap, String metricEnumType,
                                   String metricModel, MetricEntity metricEntity) {
        Object typeObj = stringObjectMap.get(metricEnumType);
        if (typeObj != null) {
            String type = typeObj.toString();
            MetricCorrespondEnum metricCorrespondEnum = MetricCorrespondEnum.ofModelNameAndType1(metricModel, type);
            if (metricCorrespondEnum != null) {
                try {
                    metricEntity.setType(Integer.parseInt(metricCorrespondEnum.getType1()));
                } catch (NumberFormatException e) {
                    // 忽略无法解析的数字格式
                }
            }
        }
    }

    /**
     * 处理指标类型2
     */
    private void handleMetricType2(Map<String, Object> stringObjectMap, String metricEnumType2,
                                   String metricModel, MetricEntity metricEntity) {
        Object type2Obj = stringObjectMap.get(metricEnumType2);
        if (type2Obj != null) {
            String type = type2Obj.toString();
            MetricCorrespondEnum metricCorrespondEnum = MetricCorrespondEnum.ofModelNameAndType2(metricModel, type);
            if (metricCorrespondEnum != null) {
                try {
                    metricEntity.setType2(Integer.parseInt(metricCorrespondEnum.getType2()));
                    if (metricCorrespondEnum.getModelName().equals("T_METRIC_GRE_ELE_TRANS_SITU")) {
                        if (!metricCorrespondEnum.getType1().isEmpty()) {
                            metricEntity.setOutOrIn(Integer.parseInt(metricCorrespondEnum.getType1()));
                        }
                    }
                } catch (NumberFormatException e) {
                    // 忽略无法解析的数字格式
                }
            }
        }
    }

    /**
     * 96点转时间
     */
    private static String toPeriod96(Integer timePeriod) {
        if (Objects.isNull(timePeriod) || timePeriod < 1 || timePeriod > 96) {
            throw new RuntimeException("无效的时段值");
        }
        int all = timePeriod * 15;
        int hour = all / 60;
        int minute = all % 60;
        return String.format("%02d:%02d", hour, minute);
    }

    /**
     * 基础数据解析96点数据
     */
    private static List<BaseEntity.Minut> parse96Data(String json) {
        final BigDecimal DEFAULT_VALUE = BigDecimal.valueOf(-1.0);
        JSONArray jsonArray = JSONArray.parseArray(json);

        if (jsonArray == null || jsonArray.isEmpty()) {
            throw new RuntimeException("无效的96点数据: " + json);
        }

        Map<Integer, BigDecimal> valueMap = new HashMap<>();
        for (int i = 0; i < jsonArray.size(); i++) {
            JSONObject jsonObject = jsonArray.getJSONObject(i);
            if (jsonObject != null) {
                try {
                    int timePeriod = jsonObject.getIntValue("timePeriod");
                    String valueStr = jsonObject.getString("value");
                    if (valueStr != null && !valueStr.isEmpty()) {
                        BigDecimal value = new BigDecimal(valueStr);
                        valueMap.put(timePeriod, value);
                    }
                } catch (NumberFormatException e) {
                    // 忽略无法解析的数字格式
                }
            }
        }

        List<BaseEntity.Minut> minutsList = new ArrayList<>();
        for (int i = 1; i <= 96; i++) {
            BigDecimal value = valueMap.getOrDefault(i, DEFAULT_VALUE);
            BaseEntity.Minut minut = new BaseEntity.Minut();
            minut.setKey(toPeriod96(i));
            minut.setValue(value);
            minutsList.add(minut);
        }
        return minutsList;
    }


    /**
     * 基础数据解析多地市96点数据
     */
    private static List<BaseEntity> parseCity96Data(String json, BaseEntity baseEntity) {
        List<BaseEntity> baseEntityList = new ArrayList<>();
        JSONArray jsonArray = JSONArray.parseArray(json);

        if (jsonArray == null || jsonArray.isEmpty()) {
            throw new RuntimeException("无效的96点数据: " + json);
        }

        final BigDecimal DEFAULT_VALUE = BigDecimal.valueOf(-1.0);

        for (int j = 0; j < jsonArray.size(); j++) {
            JSONObject obj = jsonArray.getJSONObject(j);
            if (obj == null || !obj.containsKey("cityName")) {
                continue;
            }

            BaseEntity baseEntityCity = new BaseEntity();
            BeanUtils.copyProperties(baseEntity, baseEntityCity);

            JSONArray jsonArrayCity = obj.getJSONArray("details");
            Map<Integer, BigDecimal> valueMap = new HashMap<>();

            if (jsonArrayCity != null) {
                for (int i = 0; i < jsonArrayCity.size(); i++) {
                    JSONObject jsonObject = jsonArrayCity.getJSONObject(i);
                    if (jsonObject != null) {
                        try {
                            int timePeriod = jsonObject.getIntValue("timePeriod");
                            String valueStr = jsonObject.getString("value");
                            if (valueStr != null && !valueStr.isEmpty()) {
                                BigDecimal value = new BigDecimal(valueStr);
                                valueMap.put(timePeriod, value);
                            }
                        } catch (NumberFormatException e) {
                            // 忽略无法解析的数字格式
                        }
                    }
                }
            }

            List<BigDecimal> valueList = new ArrayList<>();
            for (int i = 1; i <= 96; i++) {
                BigDecimal value = valueMap.getOrDefault(i, DEFAULT_VALUE);
                valueList.add(value);
            }

            baseEntityCity.setMinutValue(valueList);
            baseEntityCity.setRegionName(obj.getString("cityName"));
            baseEntityList.add(baseEntityCity);
        }

        return baseEntityList;
    }


    /**
     * 基础数据解析24点数据
     */
    private static List<BaseEntity.Hour> parse24Data(String json) {
        final BigDecimal DEFAULT_VALUE = BigDecimal.valueOf(-1.0);
        JSONArray jsonArray = JSONArray.parseArray(json);

        if (jsonArray == null || jsonArray.isEmpty()) {
            throw new RuntimeException("无效的24点数据: " + json);
        }

        Map<String, BigDecimal> valueMap = new HashMap<>();
        for (int i = 0; i < jsonArray.size(); i++) {
            JSONObject jsonObject = jsonArray.getJSONObject(i);
            if (jsonObject != null) {
                String timePeriod = jsonObject.getString("timePeriod");
                String valueStr = jsonObject.getString("value");
                if (valueStr != null && !valueStr.isEmpty()) {
                    try {
                        BigDecimal value = new BigDecimal(valueStr);
                        valueMap.put(timePeriod, value);
                    } catch (NumberFormatException e) {
                        // 忽略无法解析的数字格式
                    }
                }
            }
        }

        List<BaseEntity.Hour> hoursList = new ArrayList<>();
        for (int i = 0; i < 24; i++) {
            String timePeriod = (i == 23) ? "23:00-00:00" : String.format("%02d:00-%02d:00", i, (i + 1) % 24);
            BigDecimal value = valueMap.getOrDefault(timePeriod, DEFAULT_VALUE);
            BaseEntity.Hour hour = new BaseEntity.Hour();
            hour.setValue(value);
            hoursList.add(hour);
        }
        return hoursList;
    }


    /**
     * 基础数据解析各日24点数据
     */
    private static List<BaseEntity> parseDay24Data(String json, BaseEntity baseEntity) {
        List<BaseEntity> baseEntityList = new ArrayList<>();
        final BigDecimal DEFAULT_VALUE = BigDecimal.valueOf(-1.0);
        JSONArray jsonArray = JSONArray.parseArray(json);

        if (jsonArray == null || jsonArray.isEmpty()) {
            throw new RuntimeException("无效的24点数据: " + json);
        }

        for (int j = 0; j < jsonArray.size(); j++) {
            JSONObject obj = jsonArray.getJSONObject(j);
            if (obj == null || !obj.containsKey("date")) {
                continue;
            }

            BaseEntity baseEntityDate = new BaseEntity();
            BeanUtils.copyProperties(baseEntity, baseEntityDate);

            JSONArray jsonArrayDate = obj.getJSONArray("details");
            Map<String, BigDecimal> valueMap = new HashMap<>();

            if (jsonArrayDate != null) {
                for (int i = 0; i < jsonArrayDate.size(); i++) {
                    JSONObject jsonObject = jsonArrayDate.getJSONObject(i);
                    if (jsonObject != null) {
                        String timePeriod = jsonObject.getString("timePeriod");
                        String valueStr = jsonObject.getString("value");
                        if (valueStr != null && !valueStr.isEmpty()) {
                            try {
                                BigDecimal value = new BigDecimal(valueStr);
                                valueMap.put(timePeriod, value);
                            } catch (NumberFormatException e) {
                                // 忽略无法解析的数字格式
                            }
                        }
                    }
                }
            }

            List<BigDecimal> valueList = new ArrayList<>();
            for (int i = 0; i < 24; i++) {
                String timePeriod = (i == 23) ? "23:00-00:00" : String.format("%02d:00-%02d:00", i, (i + 1) % 24);
                BigDecimal value = valueMap.getOrDefault(timePeriod, DEFAULT_VALUE);
                valueList.add(value);
            }

            baseEntityDate.setHourValue(valueList);
            baseEntityList.add(baseEntityDate);
        }

        return baseEntityList;
    }


    /**
     * 基础数据解析各月各类型数据
     */
    private static List<BaseEntity> parseMonthJsonData(String json, BaseEntity baseEntity) {
        List<BaseEntity> baseEntityList = new ArrayList<>();
        final BigDecimal DEFAULT_VALUE = BigDecimal.valueOf(-1.0);
        JSONArray jsonArray = JSONArray.parseArray(json);

        if (jsonArray != null) {
            for (int i = 0; i < jsonArray.size(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                if (jsonObject == null) {
                    continue;
                }

                BaseEntity baseEntityDate = new BaseEntity();
                BeanUtils.copyProperties(baseEntity, baseEntityDate);

                String type = jsonObject.getString("type");

                List<String> valueList = new ArrayList<>();
                valueList.add("totalSettle");
                valueList.add("avgSettle");
                valueList.add("sumElec");
                String avgSettle = jsonObject.getString("avgSettle");
                for (String s : valueList) {
                    avgSettle = jsonObject.getString(s);
                    if (avgSettle != null) {
                        break;
                    }
                }

                BaseCorrespondEnum baseCorrespondEnum = BaseCorrespondEnum.ofDbsType(type);
                if (baseCorrespondEnum != null) {
                    try {
                        String typeValue = baseCorrespondEnum.getType();
                        baseEntityDate.setType(Integer.parseInt(typeValue == null ? "-1" : typeValue));
                    } catch (NumberFormatException e) {
                        baseEntityDate.setType(-1);
                    }
                }

                if (avgSettle == null) {
                    baseEntityDate.setMonthValue(DEFAULT_VALUE);
                } else {
                    try {
                        baseEntityDate.setMonthValue(new BigDecimal(avgSettle));
                    } catch (NumberFormatException e) {
                        baseEntityDate.setMonthValue(DEFAULT_VALUE);
                    }
                }

                baseEntityList.add(baseEntityDate);
            }
        }

        return baseEntityList;
    }


    /**
     * 将CLOB类型转换为String
     */
    private static String clobToString(Object clobObj) throws Exception {
        if (clobObj == null) {
            return null;
        }

        if (clobObj instanceof String) {
            return (String) clobObj;
        } else if (clobObj instanceof Clob) {
            Clob clob = (Clob) clobObj;
            return clob.getSubString(1, (int) clob.length());
        } else {
            return clobObj.toString();
        }
    }

}
