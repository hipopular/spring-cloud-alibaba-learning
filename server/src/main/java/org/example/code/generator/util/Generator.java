package org.example.code.generator.util;


import org.example.code.generator.entity.BasisInfo;
import org.example.code.generator.entity.PropertyInfo;
import org.example.code.generator.entity.ResultJson;

import java.util.List;


public class Generator {
    //路径信息
    public static final String ENTITY = "entity";
    public static final String MAPPER = "mapper";
    public static final String DAO_IMPL = "daoImpl";
    public static final String SERVICE = "service";
    public static final String SERVICE_IMPL = "serviceImpl";

    //①创建实体类
    public static ResultJson createEntity(String url, BasisInfo bi) {
        String fileUrl = getGeneratorFileUrl(url, bi.getEntityUrl(), bi.getEntityName(), ENTITY);
        return FreemarkerUtil.createFile(bi, "entity.ftl", fileUrl);
    }

    //②创建DAO
    public static ResultJson createDao(String url, BasisInfo bi) {
        String fileUrl = getGeneratorFileUrl(url, bi.getDaoUrl(), bi.getEntityName(), MAPPER);
        return FreemarkerUtil.createFile(bi, "mapper.ftl", fileUrl);
    }

    //③创建mapper配置文件
    public static ResultJson createDaoImpl(String url, BasisInfo bi) {
        String fileUrl = getGeneratorFileUrl(url, bi.getMapperUrl(), bi.getEntityName(), DAO_IMPL);
        List<PropertyInfo> list = bi.getCis();
        StringBuilder agile = new StringBuilder();
        for (PropertyInfo propertyInfo : list) {
            agile.append(propertyInfo.getColumn()).append(", ");
        }
        agile = new StringBuilder(agile.substring(0, agile.length() - 2));
        bi.setAgile(agile.toString());
        return FreemarkerUtil.createFile(bi, "xml.ftl", fileUrl);
    }

    //④创建SERVICE
    public static ResultJson createService(String url, BasisInfo bi) {
        String fileUrl = getGeneratorFileUrl(url, bi.getServiceUrl(), bi.getEntityName(), SERVICE);
        return FreemarkerUtil.createFile(bi, "service.ftl", fileUrl);
    }

    //⑤创建SERVICE_IMPL
    public static ResultJson createServiceImpl(String url, BasisInfo bi) {
        String fileUrl = getGeneratorFileUrl(url, bi.getServiceImplUrl(), bi.getEntityName(), SERVICE_IMPL);
        return FreemarkerUtil.createFile(bi, "serviceImpl.ftl", fileUrl);
    }

    //生成文件路径和名字
    public static String getGeneratorFileUrl(String url, String packageUrl, String entityName, String type) {
        switch (type) {
            case "entity":
                return url + pageToUrl(packageUrl) + entityName + ".java";
            case "mapper":
                return url + pageToUrl(packageUrl) + entityName + "Mapper.java";
            case "daoImpl":
                return url + pageToUrl(packageUrl) + entityName + "Mapper.xml";
            case "service":
                return url + pageToUrl(packageUrl) + entityName + "Service.java";
            case "serviceImpl":
                return url + pageToUrl(packageUrl) + entityName + "ServiceImpl.java";
            case "controller":
                return url + pageToUrl(packageUrl) + entityName + "Controller.java";
            case "swaggerConfig":
                return url + pageToUrl(packageUrl) + entityName + "Config.java";
        }
        return null;
    }

    public static String pageToUrl(String url) {
        return url.replace(".", "/") + "/";
    }
}
