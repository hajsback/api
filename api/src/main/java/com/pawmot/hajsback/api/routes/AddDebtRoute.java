package com.pawmot.hajsback.api.routes;

import com.pawmot.hajsback.internal.api.transactions.AddDebtRequest;
import org.apache.camel.spring.SpringRouteBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import static com.pawmot.hajsback.internal.api.transactions.QueueNames.ADD_DEBT_QUEUE;
import static org.apache.camel.model.dataformat.JsonLibrary.Gson;

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
                .unmarshal().json(Gson, AddDebtRequest.class)
                .to(jmsEndpointFactory.createRequestResponseEndpoint(ADD_DEBT_QUEUE));
    }
}
