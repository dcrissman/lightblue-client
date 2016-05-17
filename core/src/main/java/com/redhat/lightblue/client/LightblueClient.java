package com.redhat.lightblue.client;

import com.redhat.lightblue.client.request.AbstractDataBulkRequest;
import com.redhat.lightblue.client.request.AbstractLightblueDataRequest;
import com.redhat.lightblue.client.request.AbstractLightblueMetadataRequest;
import com.redhat.lightblue.client.response.LightblueBulkDataResponse;
import com.redhat.lightblue.client.response.LightblueDataResponse;
import com.redhat.lightblue.client.response.LightblueMetadataResponse;

public interface LightblueClient {

	Locking getLocking(String domain);

    LightblueMetadataResponse metadata(AbstractLightblueMetadataRequest lightblueRequest) throws LightblueException;

    LightblueDataResponse data(AbstractLightblueDataRequest lightblueRequest) throws LightblueException;

    LightblueBulkDataResponse bulkData(AbstractDataBulkRequest<AbstractLightblueDataRequest> requests) throws LightblueException;

	<T> T data(AbstractLightblueDataRequest lightblueRequest, Class<T> type) throws LightblueException;

}
