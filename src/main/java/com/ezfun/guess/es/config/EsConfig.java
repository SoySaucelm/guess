/*
package com.ezfun.guess.es.config;

import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.client.Client;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.net.InetAddress;

*/
/**
 * Created by 琦琦酱 on 2018/12/5.
 *//*

@Configuration
@Slf4j
public class EsConfig {

    private String host = "192.168.145.166";

    private String clusterName = "my-application";

    private int port = 9300;

    @Bean
    public Client client() {
        Client client = null;
        try {
            Settings settings = Settings.settingsBuilder().put("cluster.name", clusterName)
                    .put("client.node",false)
                    .put("client.transport.sniff", true)
                    .put("client.transport.ping_timeout","20s").build();
            client = TransportClient.builder().settings(settings).build()
                    .addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName(host), port));
        } catch (Exception e) {
            log.error("create es Client error:{}", e);
        }
        return client;
    }

}
*/
