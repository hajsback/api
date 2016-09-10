package com.pawmot.hajsback.common;

import org.apache.camel.component.jms.JmsMessageType;

public interface JmsEndpointFactory {
    String createRequestResponseEndpoint(String queueName, JmsMessageType messageType);

    String createListeningEndpoint(String queueName, JmsMessageType messageType);
}
