package com.pawmot.hajsback.api.routes;

import org.apache.camel.component.jms.JmsMessageType;

interface JmsEndpointFactory {
    String createRequestResponseEndpoint(String queueName, JmsMessageType text);
}
