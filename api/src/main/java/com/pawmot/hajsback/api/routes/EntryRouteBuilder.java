package com.pawmot.hajsback.api.routes;

import org.apache.camel.spring.SpringRouteBuilder;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMethod;

@Component
public class EntryRouteBuilder extends SpringRouteBuilder {
    public static final String ENTRY_ROUTE_ENDPOINT = "direct:entry";

    @Override
    public void configure() throws Exception {
        from(ENTRY_ROUTE_ENDPOINT).routeId("entry")
                .process(ex -> {
                    String httpRoute = ex.getIn().getHeader("HttpRoute", String.class);
                    RequestMethod method = ex.getIn().getHeader("HttpMethod", RequestMethod.class);
                    String body = ex.getIn().getBody(String.class);

                    ex.getIn().setBody(String.format("%s %s - %s\n", method, httpRoute, body));
                })
                .to("stream:out");
    }
}
