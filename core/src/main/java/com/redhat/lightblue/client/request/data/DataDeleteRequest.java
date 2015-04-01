package com.redhat.lightblue.client.request.data;

import com.redhat.lightblue.client.expression.query.Query;
import com.redhat.lightblue.client.request.AbstractLightblueDataRequest;

public class DataDeleteRequest extends AbstractLightblueDataRequest {
    private Query queryExpression;

    public DataDeleteRequest() {
        super();
    }

    public DataDeleteRequest(String entityName, String entityVersion) {
        super(entityName, entityVersion);
    }

    public void where(Query queryExpression) {
        this.queryExpression = queryExpression;
    }

    @Override
    public Operation getOperation() {
        return Operation.DELETE;
    }

    @Override
    public String getBody() {
        StringBuilder sb = new StringBuilder();
        sb.append("{\"query\":");
        sb.append(queryExpression.toJson());
        sb.append("}");
        return sb.toString();
    }

}
