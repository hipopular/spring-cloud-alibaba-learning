package org.example.config;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.shardingsphere.sharding.api.sharding.complex.ComplexKeysShardingAlgorithm;
import org.apache.shardingsphere.sharding.api.sharding.complex.ComplexKeysShardingValue;

/**
 * Created by huen on 2021/11/18 17:03
 */
public class ShardingFlowTablesAlgorithm implements ComplexKeysShardingAlgorithm<String> {


    @Override
    public Collection<String> doSharding(Collection<String> availableTargetNames,ComplexKeysShardingValue<String> shardingValue) {
        Collection<String> tenantIdCollection = shardingValue.getColumnNameAndShardingValuesMap().get(ShardingDataSourceConfig.UP_TENANT_ID);
        if(CollectionUtils.isEmpty(tenantIdCollection)) {
            tenantIdCollection = shardingValue.getColumnNameAndShardingValuesMap().get(ShardingDataSourceConfig.LO_TENANT_ID);
        }
        Collection<String> cinemaUidCollection = shardingValue.getColumnNameAndShardingValuesMap().get(ShardingDataSourceConfig.UP_CINEMA_ID);
        if(CollectionUtils.isEmpty(cinemaUidCollection)){
            cinemaUidCollection = shardingValue.getColumnNameAndShardingValuesMap().get(ShardingDataSourceConfig.LO_CINEMA_ID);
        }
        String tenantId = tenantIdCollection.stream().findFirst().get();
        String cinemaUid = cinemaUidCollection.stream().findFirst().get();
        return availableTargetNames.stream().map(e -> e.concat("_").concat(tenantId).concat("_").concat(cinemaUid)).collect(Collectors.toList());
    }

    @Override
    public void init() {}

    @Override
    public String getType() {
        return "CLASS_BASED";
    }
}
