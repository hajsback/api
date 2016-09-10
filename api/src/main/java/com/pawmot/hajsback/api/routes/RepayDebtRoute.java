package com.pawmot.hajsback.api.routes;

import com.pawmot.hajsback.common.JmsEndpointFactory;
import com.pawmot.hajsback.internal.api.results.Result;
import org.apache.camel.component.jms.JmsMessageType;
import org.apache.camel.spring.SpringRouteBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import static com.pawmot.hajsback.api.routes.util.Processors.RESULT_PROCESSOR;
import static com.pawmot.hajsback.internal.api.transactions.QueueNames.REPAY_DEBT_QUEUE;
import static org.apache.camel.model.dataformat.JsonLibrary.Gson;

@Component
public class RepayDebtRoute extends SpringRouteBuilder {
    public static final String REPAY_DEBT_ENDPOINT = "direct:repay_debt";
    private final JmsEndpointFactory jmsEndpointFactory;

    @Autowired
    public RepayDebtRoute(JmsEndpointFactory jmsEndpointFactory) {
        this.jmsEndpointFactory = jmsEndpointFactory;
    }

    @Override
    public void configure() throws Exception {
        from(REPAY_DEBT_ENDPOINT)
                .routeId("repay_debt")
                .to(jmsEndpointFactory.createRequestResponseEndpoint(REPAY_DEBT_QUEUE, JmsMessageType.Text))
                .unmarshal().json(Gson, Result.class)
                .process(RESULT_PROCESSOR);
    }
}
