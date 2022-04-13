package org.example.server;

import com.alibaba.nacos.api.NacosFactory;
import com.alibaba.nacos.api.config.ConfigService;
import com.alibaba.nacos.api.exception.NacosException;
import org.example.mapper.UserMapper;
import org.springframework.beans.factory.config.YamlPropertiesFactoryBean;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.io.IOException;
import java.io.StringReader;
import java.util.Properties;

/**
 * Created by huen on 2021/11/23 17:03
 */
//@Service
@RestController
public class UserServiceImpl implements UserService{

    @Resource
    UserMapper userMapper;

    @GetMapping("/get/name/{tenantId}/{cinemaUid}/{id}")
    public Object getName(@PathVariable("tenantId")String tenantId,
                          @PathVariable("cinemaUid") String cinemaUid,
                          @PathVariable("id") long id){
        return userMapper.query(tenantId,cinemaUid,id);

    }

    @GetMapping("/sharding/{code}")
    public Object sharding(@PathVariable("code") String code){
        return userMapper.sharding(code);

    }

    @GetMapping("/sharding/2/{code}")
    public Object sharding2(@PathVariable("code") String code){
        return userMapper.sharding2(code);

    }

    @GetMapping("/get/config")
    public String getConfig() throws NacosException, IOException {
        ConfigService configService = this.createConfigService();
        String config = configService.getConfig("server-dev.properties", "popular", 100);
        System.out.println(config);
        Properties properties = this.nacosConfig(config, "properties");
        properties.stringPropertyNames().forEach(System.out::println);
        return config;
    }


    @GetMapping("/update/config")
    public Boolean updateConfig() throws NacosException{
        ConfigService configService = this.createConfigService();
        String config = configService.getConfig("server-dev.properties", "popular", 100);
        config = config.replace("sharding.datasource.tenantGroup.ds2=","sharding.datasource.tenantGroup.ds2=339158,");
        return configService.publishConfig("server-dev.properties", "popular", config);
    }


    private ConfigService createConfigService() throws NacosException {
        String serverAddr = "192.168.104.124:8848";
        Properties properties = new Properties();
        properties.put("serverAddr", serverAddr);
        properties.put("namespace", "19993610-ad88-4749-9dd7-b1a355146ddd");
        return NacosFactory.createConfigService(properties);
    }

    public Properties nacosConfig(String config,String extension) throws IOException {
        if (extension.equalsIgnoreCase("properties")) {
            Properties prop = new Properties();
            prop.load(new StringReader(config));
            return prop;
        }else if (extension.equalsIgnoreCase("yaml") || extension.equalsIgnoreCase("yml")) {
            YamlPropertiesFactoryBean yamlFactory = new YamlPropertiesFactoryBean();
            yamlFactory.setResources(new ByteArrayResource(config.getBytes()));
            return yamlFactory.getObject();
        }
        return null;
    }
}
