package ghtk.masterdev.elasticdemo.Config;

import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.elasticsearch.client.ClientConfiguration;
import org.springframework.data.elasticsearch.client.RestClients;

@Configuration
public class Config {
    @Bean
    public RestHighLevelClient client() {
        //masterdev server
        ClientConfiguration clientConfiguration = ClientConfiguration.builder()
                .connectedTo("172.17.80.26:9200")
                .build();
        return RestClients.create(clientConfiguration).rest();

//        //local
//        ClientConfiguration clientConfiguration = ClientConfiguration.builder()
//                .connectedTo("192.168.56.104:9200")
//                .build();
//        return RestClients.create(clientConfiguration).rest();

    }
}
