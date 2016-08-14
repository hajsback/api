package com.pawmot.hajsback.api.routes;

import org.springframework.stereotype.Component;

@Component
class JmsEndpointFactoryImpl implements JmsEndpointFactory {
    @Override
    public String createRequestResponseEndpoint(String queueName) {
        return String.format("jms:queue:%1$s?replyTo=%1$s.response&replyToType=Exclusive", queueName);
    }
}
