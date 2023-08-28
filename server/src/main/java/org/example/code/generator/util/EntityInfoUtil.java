
package org.example.code.generator.util;


import org.example.code.generator.entity.BasisInfo;
import org.example.code.generator.entity.PropertyInfo;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
public class EntityInfoUtil {
	public static BasisInfo init(BasisInfo bi) throws SQLException {
		List<PropertyInfo> columns= new ArrayList<>();
		// 创建连接
        //sql
		String sql="select column_name,data_type,column_comment from information_schema.columns where table_schema='"+bi.getDatabase()+"' and table_name='"+bi.getTable()+"'";
        try (Connection con = DriverManager.getConnection(bi.getDbUrl(), bi.getDbName(), bi.getDbPassword()); PreparedStatement pstemt = con.prepareStatement(sql); ResultSet rs = pstemt.executeQuery()) {
            while (rs.next()) {
                String column = rs.getString(1);
                String jdbcType = rs.getString(2);
                String comment = rs.getString(3);
                PropertyInfo ci = new PropertyInfo();
                ci.setColumn(column);
                if (jdbcType.equalsIgnoreCase("int")) {
                    ci.setJdbcType("Integer");
                } else if (jdbcType.equalsIgnoreCase("datetime")) {
                    ci.setJdbcType("timestamp");
                } else {
                    ci.setJdbcType(jdbcType);
                }
                ci.setComment(comment);
                ci.setProperty(MySqlToJavaUtil.changeToJavaFiled(column));
                ci.setJavaType(MySqlToJavaUtil.jdbcTypeToJavaType(jdbcType));
                //设置注解类型
                if (column.equalsIgnoreCase("id")) {
                    bi.setIdType(ci.getJavaType());
                    bi.setIdJdbcType(ci.getJdbcType());
                }
                columns.add(ci);
                //添加包路径
                Set<String> pkgs = bi.getPkgs();
                pkgs.add(MySqlToJavaUtil.jdbcTypeToJavaTypePck(jdbcType));
                bi.setPkgs(pkgs);
            }
            bi.setCis(columns);
            // 完成后关闭
            rs.close();
            pstemt.close();
            con.close();
            if (columns.isEmpty()) {
                throw new RuntimeException("未能读取到表或表中的字段。请检查链接url，数据库账户，数据库密码，查询的数据名、是否正确。");
            }
            return bi;
        } catch (Exception e) {
            throw new RuntimeException("自动生成实体类错误：" + e.getMessage());
        }
        // 关闭资源
    }
}
