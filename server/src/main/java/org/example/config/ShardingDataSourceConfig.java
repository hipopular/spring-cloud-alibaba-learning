package org.example.config;

import com.alibaba.druid.pool.DruidDataSource;
import java.sql.SQLException;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.stream.Collectors;
import javax.sql.DataSource;
import lombok.Data;
import org.apache.shardingsphere.driver.api.ShardingSphereDataSourceFactory;
import org.apache.shardingsphere.infra.config.algorithm.ShardingSphereAlgorithmConfiguration;
import org.apache.shardingsphere.sharding.algorithm.sharding.classbased.ClassBasedShardingAlgorithmStrategyType;
import org.apache.shardingsphere.sharding.api.config.ShardingRuleConfiguration;
import org.apache.shardingsphere.sharding.api.config.rule.ShardingTableRuleConfiguration;
import org.apache.shardingsphere.sharding.api.config.strategy.sharding.ComplexShardingStrategyConfiguration;
import org.apache.shardingsphere.sharding.api.config.strategy.sharding.StandardShardingStrategyConfiguration;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by huen on 2021/11/19 18:02
 */
@Data
@Configuration
@ConfigurationProperties(prefix = "sharding.datasource")
public class ShardingDataSourceConfig {

    public final static String UP_TENANT_ID = "TENANT_ID";
    public final static String UP_CINEMA_UID = "CINEMA_UID";
    public final static String LO_TENANT_ID = "tenant_id";
    public final static String LO_CINEMA_UID = "cinema_uid";

    private Map<String,String> tenantGroup;

    private List<String> names;

    private List<String> driverClassName;

    private List<String> url;

    private List<String> username;

    private List<String> password;

    private List<String> logicTable;

    @Bean
    @RefreshScope
    public DataSource dataSource() throws SQLException {
        Map<String, DataSource> dataSourceMap = new HashMap<>();

        for (int i = 0; i < names.size(); i++) {
            DruidDataSource druidDataSource = new DruidDataSource();
            druidDataSource.setDriverClassName(driverClassName.get(i));
            druidDataSource.setUrl(url.get(i));
            druidDataSource.setUsername(username.get(i));
            druidDataSource.setPassword(password.get(i));
            dataSourceMap.put(names.get(i), druidDataSource);
        }

        // 配置分片规则
        ShardingRuleConfiguration shardingRuleConfig = new ShardingRuleConfiguration();

        String[] standardLogicTable = logicTable.get(0).split(",");
        for (String logicTable : standardLogicTable) {
            ShardingTableRuleConfiguration  tableRuleConfig = new ShardingTableRuleConfiguration(logicTable, this.getActualDataNodes(names,logicTable));
            // 配置分库策略
            tableRuleConfig.setDatabaseShardingStrategy(new StandardShardingStrategyConfiguration(UP_TENANT_ID, "dbShardingAlgorithm"));
            // 配置分表策略
            tableRuleConfig.setTableShardingStrategy(new ComplexShardingStrategyConfiguration(UP_TENANT_ID, "tableShardingAlgorithm"));
            shardingRuleConfig.getTables().add(tableRuleConfig);
        }

        String[] notStandardLogicTable = logicTable.get(1).split(",");
        for (String logicTable : notStandardLogicTable) {
            ShardingTableRuleConfiguration  flowTableRuleConfig = new ShardingTableRuleConfiguration(logicTable, this.getActualDataNodes(names,logicTable));
            // 配置分库策略
            flowTableRuleConfig.setDatabaseShardingStrategy(new StandardShardingStrategyConfiguration(UP_TENANT_ID, "dbShardingAlgorithm"));
            // 配置分表策略
            flowTableRuleConfig.setTableShardingStrategy(new ComplexShardingStrategyConfiguration(UP_TENANT_ID.concat(",").concat(UP_CINEMA_UID), "flowTableShardingAlgorithm"));

            shardingRuleConfig.getTables().add(flowTableRuleConfig);
        }

        Properties db = new Properties();
        db.setProperty("strategy", ClassBasedShardingAlgorithmStrategyType.STANDARD.name());
        db.setProperty("algorithmClassName", ShardingDataSourceAlgorithm.class.getName());

        Properties table = new Properties();
        table.setProperty("strategy", ClassBasedShardingAlgorithmStrategyType.COMPLEX.name());
        table.setProperty("algorithmClassName", ShardingTablesAlgorithm.class.getName());

        Properties flowTable = new Properties();
        flowTable.setProperty("strategy", ClassBasedShardingAlgorithmStrategyType.COMPLEX.name());
        flowTable.setProperty("algorithmClassName", ShardingFlowTablesAlgorithm.class.getName());

        shardingRuleConfig.getShardingAlgorithms().put("dbShardingAlgorithm",new ShardingSphereAlgorithmConfiguration("CLASS_BASED",db));
        shardingRuleConfig.getShardingAlgorithms().put("tableShardingAlgorithm",new ShardingSphereAlgorithmConfiguration("CLASS_BASED",table));
        shardingRuleConfig.getShardingAlgorithms().put("flowTableShardingAlgorithm",new ShardingSphereAlgorithmConfiguration("CLASS_BASED",flowTable));

        Properties prop = new Properties();
        prop.setProperty("sql-show", "true");

        return ShardingSphereDataSourceFactory .createDataSource(dataSourceMap, Collections.singleton(shardingRuleConfig), prop);
    }

    private String getActualDataNodes(List<String> nodes,String logicTable){
        return nodes.stream().map(e -> e.concat(".").concat(logicTable)).collect(Collectors.joining(","));

    }

}
