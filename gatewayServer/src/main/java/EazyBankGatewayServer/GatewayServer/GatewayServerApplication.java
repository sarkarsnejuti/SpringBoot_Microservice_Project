package EazyBankGatewayServer.GatewayServer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class GatewayServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(GatewayServerApplication.class, args);
	}
	

@Bean
public RouteLocator eazyBankRouteConfig(RouteLocatorBuilder routeLocatorBuilder) {
	
	return routeLocatorBuilder.routes()
			.route(p-> p
					.path("/eazybank/accounts/**")
					.filters( f -> f.rewritePath("/eazybank/accounts/(?<segment>.*)", "/${segment}"))
					.uri("lb://ACCOUNTS"))
			.route(p-> p
					.path("/eazybank/accounts/**")
					.filters( f -> f.rewritePath("/eazybank/accounts/(?<segment>.*)", "/${segment}"))
					.uri("lb://CARDS")).build();
}

}

