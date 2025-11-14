package com.verify.efileverifyservice.handler;

import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@Slf4j
public class CustomTypeHandler extends BaseTypeHandler<Object> {
    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, Object parameter, JdbcType jdbcType) throws SQLException {
//        log.info("设置参数: 参数索引: {}, 参数值: {}, JDBC 类型: {}", i, parameter, jdbcType);
        if (parameter instanceof String) {
            ps.setString(i, (String) parameter);
        } else if (parameter instanceof Integer) {
            ps.setInt(i, (Integer) parameter);
        } else if (parameter instanceof Long) {
            ps.setLong(i, (Long) parameter);
        } else if (parameter instanceof Double) {
            ps.setDouble(i, (Double) parameter);
        } else if (parameter instanceof Float) {
            ps.setFloat(i, (Float) parameter);
        } else if (parameter instanceof Boolean) {
            ps.setBoolean(i, (Boolean) parameter);
        } else if (parameter instanceof Byte) {
            ps.setByte(i, (Byte) parameter);
        } else if (parameter instanceof char[]){
            ps.setString(i, new String((char[]) parameter));
        }
    }

    @Override
    public Object getNullableResult(ResultSet resultSet, String s) throws SQLException {
//        log.info("获取结果: 列名: {}", s);
        return null;
    }

    @Override
    public Object getNullableResult(ResultSet resultSet, int i) throws SQLException {
//        log.info("获取结果: 列索引: {}", i);
        return null;
    }

    @Override
    public Object getNullableResult(CallableStatement callableStatement, int i) throws SQLException {
//        log.info("获取结果: 列索引: {}", i);
        return null;
    }
}
