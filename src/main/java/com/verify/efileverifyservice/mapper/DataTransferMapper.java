package com.verify.efileverifyservice.mapper;

import com.baomidou.dynamic.datasource.annotation.DS;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * @author wangcx
 * @date 2025/7/11
 */
@DS("datadb")
public interface DataTransferMapper {

    List<Map<String, Object>> getDataMap(@Param("tableName") String tableName,
                                         @Param("columnList") List<String> columnList, @Param("param") Map<String, Object> paramMap);

}
