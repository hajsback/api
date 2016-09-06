package com.pawmot.hajsback.api.routes;

import com.pawmot.hajsback.api.exceptions.HttpStatusException;
import com.pawmot.hajsback.internal.api.results.Result;
import com.pawmot.hajsback.internal.api.results.ResultKind;
import org.apache.camel.component.jms.JmsMessageType;
import org.apache.camel.spring.SpringRouteBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
                .to(jmsEndpointFactory.createRequestResponseEndpoint(ADD_DEBT_QUEUE, JmsMessageType.Text))
                .unmarshal().json(Gson, Result.class)
                .process(ex -> {
                    Result result = ex.getIn().getBody(Result.class);

                    if (result.getResultKind() == ResultKind.ValidationError) {
                        throw new HttpStatusException(HttpStatus.BAD_REQUEST);
                    }

                    ex.getIn().setBody(null);
                });
    }
}
