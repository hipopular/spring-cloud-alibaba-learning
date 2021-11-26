package org.example.config;

import java.util.Arrays;
import java.util.Collection;
import java.util.Map;
import org.apache.shardingsphere.sharding.api.sharding.standard.PreciseShardingValue;
import org.apache.shardingsphere.sharding.api.sharding.standard.RangeShardingValue;
import org.apache.shardingsphere.sharding.api.sharding.standard.StandardShardingAlgorithm;

/**
 * Created by huen on 2021/11/18 17:03
 */
public class ShardingDataSourceAlgorithm implements StandardShardingAlgorithm<String> {


    @Override
    public String doSharding(Collection<String> collection, PreciseShardingValue<String> preciseShardingValue) {
        final Map<String, String> tenantGroup = ApplicationContextTools.getBean(ShardingDataSourceConfig.class).getTenantGroup();
        for (String key : tenantGroup.keySet()) {
            String value = tenantGroup.get(key);
            if(Arrays.asList(value.split(",")).contains(preciseShardingValue.getValue())) return key;
        }
        return null;
    }

    @Override
    public Collection<String> doSharding(Collection<String> collection, RangeShardingValue<String> rangeShardingValue) {
        return null;
    }

    @Override
    public void init() {}

    @Override
    public String getType() {
        return "CLASS_BASED";
    }
}
