package org.example;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.RandomUtil;
import org.example.code.generator.entity.BasisInfo;
import org.example.code.generator.util.EntityInfoUtil;
import org.example.code.generator.util.Generator;
import org.example.code.generator.util.MySqlToJavaUtil;

import java.sql.SQLException;
import java.util.Date;

/**
 * Created by huen on 2021/10/27 11:02
 */
public class MybatisGenerator {

    // 基础信息：项目名、作者、版本
    public static final String PROJECT = "wallet-sign";
    // 数据库连接信息：连接URL、用户名、秘密、数据库名
    public static final String URL = "jdbc:mysql://10.91.231.44:3307/mr_mr_dev?useUnicode=true&characterEncoding=utf8&autoReconnect=true&allowMultiQueries=true&serverTimezone=Asia/Shanghai&useSSL=false";
    public static final String NAME = "mr_rw";
    public static final String PASS = "rB8WIt!TRoJnDj1_Hm";
    public static final String DATABASE = "mr_mr_dev";
    // 类信息：类名、对象名（一般是【类名】的首字母小些）、类说明、时间
    public static final String TABLE = "order_contract_variance_report";

    public static final String TIME = DateUtil.format(new Date(),"hh:mm");
    public static final String DATE = DateUtil.format(new Date(),"yyyy/MM/dd");
    public static final String AGILE = String.valueOf(System.currentTimeMillis()).concat(RandomUtil.randomNumbers(4));
    // 路径信息，分开路径方便聚合工程项目，微服务项目
    public static final String ENTITY_URL = "com.bgy.salesContract.entity";
    public static final String DAO_URL = "com.bgy.salesContract.mapper";
    public static final String SERVICE_URL = "com.bgy.salesContract.service";
    public static final String SERVICE_IMPL_URL = "com.bgy.salesContract.service.impl";
    public static final String CONTROLLER_URL = "com.bgy.salesContract.web";
    //是否是Swagger配置

    public static void main(String[] args) {
        BasisInfo bi = new BasisInfo(PROJECT, DATE , URL, NAME, PASS, DATABASE,TIME, AGILE, ENTITY_URL,
                DAO_URL, DAO_URL, SERVICE_URL, SERVICE_IMPL_URL, CONTROLLER_URL,Boolean.TRUE.toString());
        bi.setTable(TABLE);
        bi.setEntityName(MySqlToJavaUtil.getClassName(TABLE));
        bi.setObjectName(MySqlToJavaUtil.changeToJavaFiled(TABLE));

        try {
            EntityInfoUtil.init(bi);
            String fileUrl = "D:\\Workspace\\spring-cloud-alibaba-learning\\server\\src\\main\\java\\";// 生成文件存放位置
            //开始生成文件
            String aa1 = Generator.createEntity(fileUrl, bi).toString();
            String aa2 = Generator.createDao(fileUrl, bi).toString();
            String aa3 = Generator.createDaoImpl(fileUrl, bi).toString();
            String aa4 = Generator.createService(fileUrl, bi).toString();
            String aa5 = Generator.createServiceImpl(fileUrl, bi).toString();

            System.out.println(aa1);
            System.out.println(aa2);
            System.out.println(aa3);
            System.out.println(aa4);
            System.out.println(aa5);
        } catch (SQLException ignored) {
        }
    }

}