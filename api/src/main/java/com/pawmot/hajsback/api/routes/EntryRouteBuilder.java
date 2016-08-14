package com.pawmot.hajsback.api.routes;

import lombok.extern.slf4j.Slf4j;
import org.apache.camel.Processor;
import org.apache.camel.builder.PredicateBuilder;
import org.apache.camel.spring.SpringRouteBuilder;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMethod;

@Component
@Slf4j
public class EntryRouteBuilder extends SpringRouteBuilder {
    public static final String ENTRY_ROUTE_ENDPOINT = "direct:entry";
    private static final Processor logProcessor;

    static {
        logProcessor = ex -> {
            String httpRoute = ex.getIn().getHeader("HttpRoute", String.class);
            RequestMethod method = ex.getIn().getHeader("HttpMethod", RequestMethod.class);

            log.info("{} {}", method, httpRoute);
        };
    }

    @Override
    public void configure() throws Exception {
        from(ENTRY_ROUTE_ENDPOINT).routeId("entry")
                .process(logProcessor)
                .choice()
                    .when(PredicateBuilder.and(header("HttpRoute").isEqualToIgnoreCase("/v1/add_debt"), header("HttpMethod").isEqualTo(RequestMethod.POST)))
                            .to(AddDebtRoute.ADD_DEBT_ENDPOINT)
                    .otherwise()
                            .to(NotFoundRoute.NOT_FOUND_ENDPOINT)
                .end();
    }
}
