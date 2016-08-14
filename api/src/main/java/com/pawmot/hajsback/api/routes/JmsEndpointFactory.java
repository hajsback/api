package com.pawmot.hajsback.api.routes;

interface JmsEndpointFactory {
    String createRequestResponseEndpoint(String queueName);
}
