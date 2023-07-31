package io.xrio.gateway.config;

import com.netflix.client.config.IClientConfig;
import com.netflix.loadbalancer.*;
import com.netflix.niws.loadbalancer.NIWSDiscoveryPing;
import org.springframework.context.annotation.Bean;

/**
 * @author : Elattar Saad
 * @version 1.0
 * @since 22/1/2022
 */
public class RibbonEurekaClientConfig {

    @Bean
    public IPing ribbonPing(IClientConfig config) {
        return new NIWSDiscoveryPing();
    }

    @Bean
    public IRule ribbonRule(IClientConfig config) {
        return new AvailabilityFilteringRule();
    }

    /*
    ribbon:
      NIWSServerListClassName: com.netflix.niws.loadbalancer.DiscoveryEnabledNIWSServerList
    */
    @Bean
    public ServerList<Server> getServerList(IClientConfig config) {
        DynamicServerListLoadBalancer<Server> serverDynamicServerListLoadBalancer = new DynamicServerListLoadBalancer<>();
        serverDynamicServerListLoadBalancer.initWithNiwsConfig(config);

        return serverDynamicServerListLoadBalancer.getServerListImpl();
    }

}
