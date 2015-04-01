package com.redhat.lightblue.client.http.request;

import org.apache.http.client.methods.HttpRequestBase;

import com.redhat.lightblue.client.request.AbstractLightblueDataRequest;

public class LightblueHttpDataRequest extends AbstractLightblueHttpRequest implements LightblueHttpRequest {

    private final AbstractLightblueDataRequest request;

    public LightblueHttpDataRequest(AbstractLightblueDataRequest request) {
        this.request = request;
    }

    @Override
    public HttpRequestBase getRestRequest(String baseServiceURI) {
        switch (request.getOperation()) {
            case DELETE:
                return getHttpPost(request.getRestURI(baseServiceURI), request.getBody());
            case FIND:
                return getHttpPost(request.getRestURI(baseServiceURI), request.getBody());
            case INSERT:
                return getHttpPut(request.getRestURI(baseServiceURI), request.getBody());
            case SAVE:
                return getHttpPost(request.getRestURI(baseServiceURI), request.getBody());
            case UPDATE:
                return getHttpPost(request.getRestURI(baseServiceURI), request.getBody());
            default:
                throw new UnsupportedOperationException("Unknown Operation type: " + request.getOperationPathParam());
        }
    }

}
