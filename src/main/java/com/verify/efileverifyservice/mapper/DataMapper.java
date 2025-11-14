package com.verify.efileverifyservice.mapper;

import com.verify.efileverifyservice.entity.BaseDataConfig;
import com.verify.efileverifyservice.entity.MetricDefinition;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface DataMapper {

    //    查询基础数据配置表
    @Select("select * from T_BASE_DATA_CONFIG where statistical_cycle = #{statisticalCycle}")
    List<BaseDataConfig> queryBaseDataConfig(@Param("statisticalCycle") String statisticalCycle);

    //    查询指标配置表
    @Select("select * from T_METRIC_DEFINITION where stats_dimension = #{statsDimension}")
    List<MetricDefinition> queryMetricConfig(@Param("statsDimension") String statsDimension);

}
