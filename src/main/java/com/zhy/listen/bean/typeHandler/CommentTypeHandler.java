package com.zhy.listen.bean.typeHandler;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;

import com.zhy.listen.bean.CommentType;

public class CommentTypeHandler extends BaseTypeHandler<CommentType> {

    @Override
    public CommentType getNullableResult(ResultSet rs, String columnName) throws SQLException {
        Integer value = rs.getInt(columnName);
        return null == value ? null : CommentType.type2CommonType(value);
    }

    @Override
    public CommentType getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        Integer value = rs.getInt(columnIndex);
        return null == value ? null : CommentType.type2CommonType(value);
    }

    @Override
    public CommentType getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        Integer value = cs.getInt(columnIndex);
        return null == value ? null : CommentType.type2CommonType(value);
    }

    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, CommentType parameter, JdbcType jdbcType)
            throws SQLException {
        ps.setInt(i, ((CommentType) parameter).getType());
    }

}
