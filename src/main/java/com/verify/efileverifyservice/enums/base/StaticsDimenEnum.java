package com.verify.efileverifyservice.enums.base;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.annotation.EnumValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;
import java.util.Objects;

/**
 * 统计维度枚举
 * 
 * @author JinPeng
 * @since 2025/3/20
 */
@Getter
@AllArgsConstructor
public enum StaticsDimenEnum {
    /**
     * 0：按日分时统计，1：按日统计，2：按周统计，3：按月统计，4：按季度统计，5：按年统计
     */
    DAY_HOUR("0", "按日分时统计"),

    DAY("1", "按日统计"),

    WEEK("2", "按周统计"),

    MONTH("3", "按月统计"),

    QUARTER("4", "按季度统计"),

    YEAR("5", "按年统计");

    @EnumValue
    private final String value;

    private final String display;

    public static StaticsDimenEnum of(Integer value) {
        if (Objects.isNull(value)) {
            return null;
        }
        return Arrays.stream(values()).filter(item -> item.getValue().equals(value.toString())).findFirst()
            .orElse(StaticsDimenEnum.DAY_HOUR);
    }

    public static StaticsDimenEnum ofDisplay(String display) {
        if (Objects.isNull(display)) {
            return null;
        }
        return Arrays.stream(values()).filter(item -> item.getDisplay().equals(display)).findFirst()
                .orElse(StaticsDimenEnum.DAY_HOUR);
    }

    public static String convert(String display) {
        return Arrays.stream(values()).filter(item -> item.getDisplay().equals(display)).findFirst()
            .map(StaticsDimenEnum::getValue).orElse(StrUtil.EMPTY);
    }

    public static String reverse(Object value) {
        return Arrays.stream(values()).filter(item -> item.getValue().equals(value)).findFirst()
            .map(StaticsDimenEnum::getDisplay).orElse(StrUtil.EMPTY);
    }

    public static String[] excelOptions() {
        return Arrays.stream(values()).map(StaticsDimenEnum::getDisplay).toArray(String[]::new);
    }
}
