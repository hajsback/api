package com.pawmot.hajsback.api.controllers;

import com.auth0.jwt.JWTSigner;
import com.pawmot.hajsback.api.exceptions.HttpStatusException;
import com.pawmot.hajsback.api.model.sessions.Session;
import com.pawmot.hajsback.api.repositories.sessions.SessionRepository;
import com.pawmot.hajsback.api.routes.EntryRouteBuilder;
import com.pawmot.hajsback.api.routes.RoutingEntryPoint;
import org.apache.camel.CamelContext;
import org.apache.camel.Endpoint;
import org.apache.camel.component.bean.ProxyHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.HandlerMapping;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@RestController
public class GeneralApiController {
    private final CamelContext camelContext;
    private final SessionRepository sessionRepository;

    @Value("${session.duration}")
    private int sessionDurationSeconds;

    @Value("${security.secret}")
    private String secret;

    @Autowired
    public GeneralApiController(CamelContext camelContext, SessionRepository sessionRepository) {
        this.camelContext = camelContext;
        this.sessionRepository = sessionRepository;
    }

    @RequestMapping(path = "v1/**", method = {RequestMethod.GET, RequestMethod.POST })
    public Object handleApiRequest(HttpServletRequest request) throws Exception {
        String path = (String) request.getAttribute(
                HandlerMapping.PATH_WITHIN_HANDLER_MAPPING_ATTRIBUTE);
        String bestMatchPattern = (String ) request.getAttribute(HandlerMapping.BEST_MATCHING_PATTERN_ATTRIBUTE);

        AntPathMatcher apm = new AntPathMatcher();
        String finalPath = apm.extractPathWithinPattern(bestMatchPattern, path);

        String authHeader = request.getHeader("Authorization");
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            throw new HttpStatusException(HttpStatus.UNAUTHORIZED); // TODO: verify this HTTP status
        }

        UUID accessToken = UUID.fromString(authHeader.substring(7));
        // TODO: Transaction
        Session session = sessionRepository.findByAccessToken(accessToken);

        if (session == null || session.getExpiresBy().isBefore(LocalDateTime.now())) {
            // TODO: remove the session?
            throw new HttpStatusException(HttpStatus.UNAUTHORIZED); // TODO: verify this HTTP status
        } else {
            session.setExpiresBy(LocalDateTime.now().plusSeconds(sessionDurationSeconds));
            sessionRepository.save(session);
        }

        final String issuer = "ApiGateway";

        final JWTSigner signer = new JWTSigner(secret);
        final Map<String, Object> claims = new HashMap<>();
        claims.put("iss", issuer);

        final String jwt = signer.sign(claims);

        RequestMethod httpMethod = RequestMethod.valueOf(request.getMethod());
        String content = readContent(request);

        RoutingEntryPoint routingEntryPoint = createRoutingEntryPoint();

        return routingEntryPoint.routeRequest(finalPath, httpMethod, jwt, content);
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
