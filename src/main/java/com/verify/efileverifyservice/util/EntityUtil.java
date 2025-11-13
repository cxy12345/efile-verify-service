package com.verify.efileverifyservice.util;

import com.verify.efileverifyservice.entity.GatherResultInfo;
import io.swagger.annotations.ApiModelProperty;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class EntityUtil {

    /**
     * 获取实体类的字段
     *
     * @param clazz 实体类class对象
     * @return 组装后的字符串列表
     */
    public static String getFieldInfo(Class<?> clazz) {
        Field[] fields = clazz.getDeclaredFields();
        StringBuilder fieldInfo = new StringBuilder("@");
        for (Field field : fields) {
            String fieldName = field.getName();
            ApiModelProperty apiModelProperty = field.getAnnotation(ApiModelProperty.class);
            if (apiModelProperty != null) {
                fieldInfo.append(" ").append(fieldName);
            }
        }
        return fieldInfo.toString();
    }

    /**
     * 获取实体类的ApiModelProperty注解内容
     */
    public static String getApiModelPropertyInfo(Class<?> clazz) {
        Field[] fields = clazz.getDeclaredFields();
        StringBuilder fieldInfo = new StringBuilder("//");
        for (Field field : fields) {
            ApiModelProperty apiModelProperty = field.getAnnotation(ApiModelProperty.class);
            if (apiModelProperty != null) {
                fieldInfo.append(" ").append(apiModelProperty.value());
            }
        }
        return fieldInfo.toString();
    }

    /**
     * list实体类中的数据转换
     */
    public static String convertEntityListToString(List<?> entityList) {
        StringBuilder result = new StringBuilder();

        for (Object entity : entityList) {
            result.append(convertEntityToString(entity)).append("\n");
        }

        return result.toString();
    }

    /**
     * 将单个实体对象转换为指定格式的字符串
     *
     * @param entity 实体对象
     * @return 转换后的字符串
     */
    public static String convertEntityToString(Object entity) {
        if (entity == null) {
            return "";
        }

        StringBuilder result = new StringBuilder("#");
        Field[] fields = entity.getClass().getDeclaredFields();

        try {
            for (Field field : fields) {
                field.setAccessible(true);
                Object value = field.get(entity);
                result.append(" ").append(formatFieldValue(value) == null ?  "" : formatFieldValue(value));
            }
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        return result.toString();
    }

    /**
     * 格式化字段值
     *
     * @param value 字段值
     * @return 格式化后的字符串
     */
    private static String formatFieldValue(Object value) {
        if (value == null) {
            return "-1";
        }
        if (value instanceof GatherResultInfo){
            return null;
        }

        if (value instanceof String) {
            return ((String) value).isEmpty() ? "\"\"" : (String) value;
        }

        if (value instanceof Integer) {
            return String.valueOf(value);
        }

        if (value instanceof BigDecimal) {
            return ((BigDecimal) value).toString();
        }

        if (value instanceof List) {
            if (((List<?>) value).isEmpty()) {
                return "[]";
            } else {
                List<BigDecimal> result = new ArrayList<>();
                for (Object item : (List<?>) value) {
                    result.add(convertToBigDecimal(item));
                }
                return Arrays.toString(result.toArray(new BigDecimal[0])) .replace(", ", ",");
            }
        }

        return value.toString();
    }


    private static BigDecimal convertToBigDecimal(Object value) {
        if (value == null) {
            return null;
        }
        if (value instanceof BigDecimal) {
            return (BigDecimal) value;
        }
        if (value instanceof String) {
            return new BigDecimal((String) value);
        }
        if (value instanceof Number) {
            return BigDecimal.valueOf(((Number) value).doubleValue());
        }
        throw new ClassCastException("Cannot convert " + value.getClass() + " to BigDecimal");
    }
}