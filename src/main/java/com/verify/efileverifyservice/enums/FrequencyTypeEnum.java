package com.verify.efileverifyservice.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

/**
 * 统计频率类型枚举
 * 
 * @author JinPeng
 * @since 2025/5/28
 */
@AllArgsConstructor
@Getter
public enum FrequencyTypeEnum {
    /**
     * 1:按日统计 2:按月统计 3:按年统计 4:按季度统计 5:按周统计
     */
    DAY("1", "按日统计"),

    MONTH("2", "按月统计"),

    YEAR("3", "按年统计"),

    QUARTER("4", "按季度统计"),

    DAY_MINUTE24("6", "日分时（24点）"),

    DAY_MINUTE96("7", "日分时（96点）"),

    CITY_MINUTE96("8", "多地市日分时（96点）"),

    MORE_MINUTE24("9", "多日分时（24点）"),

    MONTH_JSON("10", "各月各类型"),

    WEEK("5", "按周统计"),

    DAY_AND_MONTH("11", "按日和按月"),

    OTHER("0", "其他");

    private final String code;

    private final String desc;

    public static FrequencyTypeEnum of(String code) {
        return Arrays.stream(values()).filter(e -> e.code.equals(code)).findFirst().orElse(null);
    }
}
