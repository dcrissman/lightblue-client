package com.redhat.lightblue.client.request.data;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.redhat.lightblue.client.Execution;
import com.redhat.lightblue.client.Operation;
import com.redhat.lightblue.client.Query;
import com.redhat.lightblue.client.http.HttpMethod;
import com.redhat.lightblue.client.request.AbstractLightblueDataRequest;

public class DataDeleteRequest extends AbstractLightblueDataRequest implements HasExecution {
    private Query queryExpression;
    private Execution execution;

    public DataDeleteRequest(String entityName, String entityVersion) {
        super(entityName, entityVersion);
    }

    public DataDeleteRequest(String entityName) {
        super(entityName);
    }

    public DataDeleteRequest where(com.redhat.lightblue.client.Query queryExpression) {
        this.queryExpression = queryExpression;

        return this;
    }

    @Override
    public DataDeleteRequest execution(Execution execution) {
        this.execution = execution;

        return this;
    }

    @Override
    public JsonNode getBodyJson() {
        ObjectNode node = JsonNodeFactory.instance.objectNode();
        if (queryExpression != null) {
            node.set("query", queryExpression.toJson());
        }
        if (execution != null) {
            node.set("execution", execution.toJson());
        }
        return node;
    }

    @Override
    public HttpMethod getHttpMethod() {
        return HttpMethod.POST;
    }

    @Override
    public String getOperationPathParam() {
        return "delete";
    }

    @Override
    public Operation getOperation() {
        return Operation.DELETE;
    }

    @Override
    public Execution getExecution() {
        return execution;
    }

}
