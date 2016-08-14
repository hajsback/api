package com.pawmot.hajsback.api.routes;

import org.apache.camel.spring.SpringRouteBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class AddDebtRoute extends SpringRouteBuilder {
    public static final String ADD_DEBT_ENDPOINT = "direct:add_debt";
    private final JmsEndpointFactory jmsEndpointFactory;

    @Autowired
    public AddDebtRoute(JmsEndpointFactory jmsEndpointFactory) {
        this.jmsEndpointFactory = jmsEndpointFactory;
    }

    @Override
    public void configure() throws Exception {
        from(ADD_DEBT_ENDPOINT)
                .routeId("add_debt")
                .to(jmsEndpointFactory.createRequestResponseEndpoint("new_transaction"));
    }
}
