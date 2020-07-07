package com.ezfun.guess.spring.v2.event.sun;

/**
 * @author SoySauce
 * @date 2020/6/19
 */
public class TypeHandler{
        /*extends BaseTypeHandler<String> {
    @Override
    public void setNonNullParameter(PreparedStatement preparedStatement, int i, String s, JdbcType jdbcType) throws SQLException {
//        preparedStatement.setString(i, s);
    }

    @Override
    public String getNullableResult(ResultSet resultSet, String s) throws SQLException {
        return parseStr(resultSet.getString(s));
    }

    @Override
    public String getNullableResult(ResultSet resultSet, int i) throws SQLException {
        return resultSet.getString(i);
    }

    @Override
    public String getNullableResult(CallableStatement callableStatement, int i) throws SQLException {
        return callableStatement.getString(i);
    }

    private String parseStr(String str) {
        if (null == str) {
            return null;
        }
        try {
            return new String(str.getBytes("iso-8859-1"), "utf-8");
        } catch (UnsupportedEncodingException e) {
        }
        return null;
    }*/
}
