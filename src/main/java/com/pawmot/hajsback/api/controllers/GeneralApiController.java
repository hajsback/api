package com.pawmot.hajsback.api.controllers;

import com.auth0.jwt.JWTSigner;
import com.pawmot.hajsback.api.model.users.User;
import com.pawmot.hajsback.api.repositories.users.UserRepository;
import com.pawmot.hajsback.api.routes.EntryRouteBuilder;
import com.pawmot.hajsback.api.routes.RoutingEntryPoint;
import com.pawmot.hajsback.api.security.Security;
import lombok.val;
import org.apache.camel.CamelContext;
import org.apache.camel.Endpoint;
import org.apache.camel.component.bean.ProxyHelper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.HandlerMapping;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
public class GeneralApiController {
    private final CamelContext camelContext;
    private final Security security;
    private final UserRepository userRepository;

    private final String secret;

    public GeneralApiController(CamelContext camelContext, Security security, UserRepository userRepository, @Value("${security.secret}") String secret) {
        this.camelContext = camelContext;
        this.security = security;
        this.userRepository = userRepository;
        this.secret = secret;
    }

    @RequestMapping(path = "v1/**", method = {RequestMethod.GET, RequestMethod.POST }, produces = "application/json")
    public Object handleApiRequest(HttpServletRequest request) throws Exception {
        String route = (String) request.getAttribute(HandlerMapping.PATH_WITHIN_HANDLER_MAPPING_ATTRIBUTE);

        val session = security.getSession(request);
        User user = userRepository.findByEmail(session.getUserEmail());

        final String issuer = "com.pawmot.hajsback.api";
        final String audience = "com.pawmot.hajsback";

        final JWTSigner signer = new JWTSigner(secret);
        final Map<String, Object> claims = new HashMap<>();
        claims.put("iss", issuer);
        claims.put("aud", audience);
        claims.put("com.pawmot.hajsback.user.email", user.getEmail());

        final String jwt = signer.sign(claims);

        RequestMethod httpMethod = RequestMethod.valueOf(request.getMethod());
        String content = readContent(request);

        RoutingEntryPoint routingEntryPoint = createRoutingEntryPoint();

        return routingEntryPoint.routeRequest(route, httpMethod, jwt, content);
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
