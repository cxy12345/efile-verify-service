package com.verify.efileverifyservice.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

@AllArgsConstructor
@Getter
public enum StatsDimensionEnum {

    /**
     * 1:按日统计 2:按月统计 3:按年统计 4:按季度统计 5:按周统计
     */
    DAY("15 分钟","0", "1","yyyy-MM-dd"),

    MONTH("日报","1", "2","yyyy-MM-dd"),

    YEAR("周报","2", "3","yyyy-'W'ww"),

    QUARTER("月报","3", "4","yyyy-MM"),

    DAY_MINUTE24("季报","4", "5","yyyy-'Q'q"),

    DAY_MINUTE96("年报","5", "6","yyyy"),

    WEEK("其它","-1", "7","yyyy-MM-dd HH:mm:ss");

    private final String name;

    private final String dbsType;

    private final String type;

    private final String sdf;

    public static StatsDimensionEnum of(String dbsType) {
        return Arrays.stream(values()).filter(e -> e.dbsType.equals(dbsType)).findFirst().orElse(WEEK);
    }

}
