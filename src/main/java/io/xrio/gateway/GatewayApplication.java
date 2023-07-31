package io.xrio.gateway;

import io.xrio.gateway.config.RibbonEurekaClientConfig;
import io.xrio.gateway.domain.appuser.service.AppUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.ribbon.RibbonClients;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;


@SpringBootApplication
@RequiredArgsConstructor
@EnableZuulProxy
@EnableDiscoveryClient
@RibbonClients(defaultConfiguration = RibbonEurekaClientConfig.class)
public class GatewayApplication implements CommandLineRunner {

    final AppUserService userService;

    public static void main(String... args) {
        SpringApplication.run(GatewayApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        userService.init();
    }
}
