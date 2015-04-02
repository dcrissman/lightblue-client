package com.redhat.lightblue.client.request.data;

import com.redhat.lightblue.client.request.AbstractLightblueDataRequest;

public class SimpleDataRequest extends AbstractLightblueDataRequest {

    private final String body;
    private final Operation operation;

    public SimpleDataRequest(String entityName, String entityVersion, Operation operation, String body) {
        super(entityName, entityVersion);
        this.body = body;
        this.operation = operation;
    }

    @Override
    public Operation getOperation() {
        return operation;
    }

    @Override
    public String getBody() {
        return body;
    }

}
