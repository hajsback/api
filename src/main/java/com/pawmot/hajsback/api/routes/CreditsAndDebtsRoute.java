package com.pawmot.hajsback.api.routes;

import org.apache.camel.spring.SpringRouteBuilder;
import org.springframework.stereotype.Component;

@Component
public class CreditsAndDebtsRoute extends SpringRouteBuilder {
    public static final String CREDITS_AND_DEBTS_ENDPOINT = "direct:credits_and_debts";

    @Override
    public void configure() throws Exception {
        from(CREDITS_AND_DEBTS_ENDPOINT)
                .routeId("credits_and_debts")
                .process(ex -> {
                    String route = ex.getIn().getHeader("HttpRoute", String.class);
                    String[] components = route.split("/");
                    String email = components[components.length - 1];
                    ex.getIn().setHeader("Email", email);
                    ex.getIn().setBody(null);
                })
                .toD("http://localhost:8081/debtsAndCredits/${header.Email}")
                .convertBodyTo(String.class);
    }
}
