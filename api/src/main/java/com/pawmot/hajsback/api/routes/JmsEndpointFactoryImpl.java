package com.pawmot.hajsback.api.routes;

import org.apache.camel.component.jms.JmsMessageType;
import org.springframework.stereotype.Component;

@Component
class JmsEndpointFactoryImpl implements JmsEndpointFactory {
    @Override
    public String createRequestResponseEndpoint(String queueName, JmsMessageType messageType) {
        return String.format("jms:queue:%1$s?replyTo=%1$s.response&replyToType=Exclusive&jmsMessageType=%2$s", queueName, messageType.toString());
    }
}
