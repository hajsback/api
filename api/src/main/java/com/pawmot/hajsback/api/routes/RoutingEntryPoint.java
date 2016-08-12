package com.pawmot.hajsback.api.routes;

import org.apache.camel.Body;
import org.apache.camel.Header;
import org.springframework.web.bind.annotation.RequestMethod;

public interface RoutingEntryPoint {
    Object routeRequest(@Header("HttpRoute")String httpRoute, @Header("HttpMethod")RequestMethod method, @Body String content);
}
