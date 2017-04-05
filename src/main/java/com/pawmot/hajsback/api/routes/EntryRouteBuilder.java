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
        final String httpRouteHeader = "HttpRoute";
        final String httpMethodHeader = "HttpMethod";
        from(ENTRY_ROUTE_ENDPOINT).routeId("entry")
                .process(logProcessor)
                .choice()
                    .when(PredicateBuilder.and(header(httpRouteHeader).isEqualToIgnoreCase("/v1/add_debt"),
                            header(httpMethodHeader).isEqualTo(RequestMethod.POST)))
                            .to(AddDebtRoute.ADD_DEBT_ENDPOINT)
                    .when(PredicateBuilder.and(header(httpRouteHeader).isEqualToIgnoreCase("/v1/repay_debt"),
                            header(httpMethodHeader).isEqualTo(RequestMethod.POST)))
                            .to(RepayDebtRoute.REPAY_DEBT_ENDPOINT)
                    .when(PredicateBuilder.and(header(httpRouteHeader).startsWith("/v1/credits_and_debts/"),
                            header(httpMethodHeader).isEqualTo(RequestMethod.GET)))
                            .to(CreditsAndDebtsRoute.CREDITS_AND_DEBTS_ENDPOINT)
                    .otherwise()
                            .to(NotFoundRoute.NOT_FOUND_ENDPOINT)
                .end();
    }
}
