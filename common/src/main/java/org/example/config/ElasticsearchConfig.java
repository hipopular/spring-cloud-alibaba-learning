package org.example.config;

import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix="elasticsearch")
public class ElasticsearchConfig {

    private String host;
    private String port;

    @Bean(destroyMethod = "close")
    public RestHighLevelClient restHighLevelClient() {
            RestClientBuilder builder = RestClient.builder(new HttpHost(host, Integer.parseInt(port)))
                    .setRequestConfigCallback(requestConfigBuilder -> requestConfigBuilder
                            .setConnectTimeout(3000)
                            .setSocketTimeout(5000)
                            .setConnectionRequestTimeout(500));
            return new RestHighLevelClient(builder);
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
    }

}
