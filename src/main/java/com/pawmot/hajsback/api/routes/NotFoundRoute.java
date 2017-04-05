package com.pawmot.hajsback.api.routes;

import com.pawmot.hajsback.api.exceptions.HttpStatusException;
import org.apache.camel.spring.SpringRouteBuilder;
import org.springframework.stereotype.Component;

import static org.springframework.http.HttpStatus.NOT_FOUND;

@Component
public class NotFoundRoute extends SpringRouteBuilder {
    public static final String NOT_FOUND_ENDPOINT = "direct:not_found";

    @Override
    public void configure() throws Exception {
        from(NOT_FOUND_ENDPOINT)
                .routeId("not_found")
                .process(ex -> {
                    throw new HttpStatusException(NOT_FOUND);
                });
    }
}
