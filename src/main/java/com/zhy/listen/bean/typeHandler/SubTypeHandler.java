package com.zhy.listen.bean.typeHandler;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;

import com.zhy.listen.bean.SubType;

public class SubTypeHandler extends BaseTypeHandler<SubType> {

    @Override
    public SubType getNullableResult(ResultSet rs, String columnName) throws SQLException {
        Integer value = rs.getInt(columnName);
        return null == value ? null : SubType.getSubFromType(value);
    }

    @Override
    public SubType getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        Integer value = rs.getInt(columnIndex);
        return null == value ? null : SubType.getSubFromType(value);
    }

    @Override
    public SubType getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        Integer value = cs.getInt(columnIndex);
        return null == value ? null : SubType.getSubFromType(value);
    }

    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, SubType parameter, JdbcType jdbcType)
            throws SQLException {
        ps.setInt(i, ((SubType) parameter).getType());
    }

}
