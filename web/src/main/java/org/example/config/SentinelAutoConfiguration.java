package org.example.config;

import com.alibaba.csp.sentinel.datasource.ReadableDataSource;
import com.alibaba.csp.sentinel.datasource.nacos.NacosDataSource;
import com.alibaba.csp.sentinel.property.SentinelProperty;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRule;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRuleManager;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.alibaba.nacos.api.PropertyKeyConst;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import javax.annotation.PostConstruct;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

@Configuration
public class SentinelAutoConfiguration {

    @Autowired
    ApplicationContext applicationContext;

    // nacos server ip
    private static final String remoteAddress = "192.168.104.124:8848";
    // nacos group
    private static final String groupId = "popular";
    // nacos dataId
    private static final String dataId = "com.alibaba.csp.sentinel.demo.flow.rule";
    // fill your namespace id,if you want to use namespace. for example: 0f5c7314-4983-4022-ad5a-347de1d1057d,you can get it on nacos's console
    private static final String NACOS_NAMESPACE_ID = "19993610-ad88-4749-9dd7-b1a355146ddd";

    private static final String SYSTEM_DEFAULT_RESOURCE = "/system/default";

    @PostConstruct
    public void init() {

        Properties properties = new Properties();
        properties.put(PropertyKeyConst.SERVER_ADDR, remoteAddress);
        properties.put(PropertyKeyConst.NAMESPACE, NACOS_NAMESPACE_ID);

        ReadableDataSource<String, List<FlowRule>>
                flowRuleDataSource = new NacosDataSource<>(properties, groupId, dataId, this::allConfig);
        FlowRuleManager.register2Property(flowRuleDataSource.getProperty());
    }

    private List<FlowRule> allConfig(String source){
        RequestMappingHandlerMapping mapping = applicationContext.getBean(RequestMappingHandlerMapping.class);
        Map<RequestMappingInfo, HandlerMethod> methodMap = mapping.getHandlerMethods();
        Set<String> urls = methodMap.keySet().stream().map(x -> x.getPatternsCondition().getPatterns()).flatMap(Collection::stream).collect(Collectors.toSet());

        List<FlowRule> ruleList = JSON.parseObject(source, new TypeReference<List<FlowRule>>() {});
        Set<String> ruleResourceUri = ruleList.stream().map(FlowRule::getResource).collect(Collectors.toSet());
        urls.removeAll(ruleResourceUri);
        FlowRule systemDefaultRule = ruleList.stream().filter(e -> e.getResource().equals(SYSTEM_DEFAULT_RESOURCE))
                .collect(Collectors.toList()).get(0);

        List<FlowRule>  defaultRules = urls.stream().map(e -> {
            FlowRule flowRule = new FlowRule();
            flowRule.setRefResource(e);
            flowRule.setCount(systemDefaultRule.getCount());
            flowRule.setLimitApp(systemDefaultRule.getLimitApp());
            flowRule.setGrade(systemDefaultRule.getGrade());
            flowRule.setStrategy(systemDefaultRule.getStrategy());
            flowRule.setControlBehavior(systemDefaultRule.getControlBehavior());
            return flowRule;
        }).collect(Collectors.toList());
        ruleList.addAll(defaultRules);
        return ruleList;
    }
}
