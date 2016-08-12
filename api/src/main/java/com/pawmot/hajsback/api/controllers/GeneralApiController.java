package com.pawmot.hajsback.api.controllers;

import com.pawmot.hajsback.api.routes.EntryRouteBuilder;
import com.pawmot.hajsback.api.routes.RoutingEntryPoint;
import org.apache.camel.CamelContext;
import org.apache.camel.Endpoint;
import org.apache.camel.component.bean.ProxyHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.HandlerMapping;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.IOException;

@RestController
public class GeneralApiController {
    private final CamelContext camelContext;

    @Autowired
    public GeneralApiController(CamelContext camelContext) {
        this.camelContext = camelContext;
    }

    @RequestMapping(path = "v1/**", method = {RequestMethod.GET, RequestMethod.POST })
    public Object handleApiRequest(HttpServletRequest request) throws Exception {
        String path = (String) request.getAttribute(
                HandlerMapping.PATH_WITHIN_HANDLER_MAPPING_ATTRIBUTE);
        String bestMatchPattern = (String ) request.getAttribute(HandlerMapping.BEST_MATCHING_PATTERN_ATTRIBUTE);

        AntPathMatcher apm = new AntPathMatcher();
        String finalPath = apm.extractPathWithinPattern(bestMatchPattern, path);

        // authenticate & create JWT here?
        // prolong session?

        RequestMethod httpMethod = RequestMethod.valueOf(request.getMethod());
        String content = readContent(request);

        RoutingEntryPoint routingEntryPoint = createRoutingEntryPoint();

        return routingEntryPoint.routeRequest(finalPath, httpMethod, content);
    }

    private RoutingEntryPoint createRoutingEntryPoint() throws Exception {
        Endpoint endpoint = camelContext.getEndpoint(EntryRouteBuilder.ENTRY_ROUTE_ENDPOINT);
        return ProxyHelper.createProxy(endpoint, RoutingEntryPoint.class);
    }

    private static String readContent(HttpServletRequest request) throws IOException {
        StringBuilder sb = new StringBuilder();
        BufferedReader reader = request.getReader();

        String line;
        while ((line = reader.readLine()) != null) {
            sb.append(line);
        }

        return sb.toString();
    }
}
