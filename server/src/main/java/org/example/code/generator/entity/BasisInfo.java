
package org.example.code.generator.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


@Data
public class BasisInfo implements Serializable {
    private static final long serialVersionUID = 123123L;

    private String project;

    private String date;

    private String author;

    private String version;

    private String dbUrl;

    private String dbName;

    private String dbPassword;

    private String database;

    private String table;

    private String entityName;

    private String objectName;

    private String time;

    private String agile;

    private String entityUrl;

    private String daoUrl;

    private String mapperUrl;

    private String serviceUrl;

    private String serviceImplUrl;

    private String abstractControllerUrl;

    private String controllerUrl;

    private String swaggerConfigUrl;

    private String idType;

    private String idJdbcType;

    private List<PropertyInfo> cis;

    private String isSwagger;

    private Set<String> pkgs = new HashSet<>();

    public BasisInfo(String project,  String date,String dbUrl, String dbName, String dbPassword,
                     String database,String createTime, String agile, String entityUrl, String daoUrl, String mapperUrl,
                     String serviceUrl, String serviceImplUrl, String controllerUrl, String isSwagger) {
        super();
        this.project = project;
        this.date = date;
        this.dbUrl = dbUrl;
        this.dbName = dbName;
        this.dbPassword = dbPassword;
        this.database = database;
        this.time = createTime;
        this.agile = agile;
        this.entityUrl = entityUrl;
        this.daoUrl = daoUrl;
        this.mapperUrl = mapperUrl;
        this.serviceUrl = serviceUrl;
        this.serviceImplUrl = serviceImplUrl;
        this.controllerUrl = controllerUrl;
        this.abstractControllerUrl = controllerUrl.substring(0, controllerUrl.lastIndexOf(".")) + ".aid";
        this.swaggerConfigUrl = controllerUrl.substring(0, controllerUrl.lastIndexOf(".")) + ".config";
        this.isSwagger = isSwagger;
    }
}
