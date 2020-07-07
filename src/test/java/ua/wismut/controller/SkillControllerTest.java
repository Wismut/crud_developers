package ua.wismut.controller;

import constant.Constant;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.*;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;

class SkillControllerTest {
    private final String SKILL_API_URL = Constant.URL + "/api/v1/skills/";

    @Test
    public void givenSkillDoesNotExistsWhenSkillInfoIsRetrievedThen404IsReceived()
            throws IOException {
        // Given
        int id = 234545223;
        HttpUriRequest request = new HttpGet(SKILL_API_URL + id);

        // When
        HttpResponse httpResponse = HttpClientBuilder.create().build().execute(request);

        // Then
        assertEquals(HttpStatus.SC_NOT_FOUND, httpResponse.getStatusLine().getStatusCode());
    }

    @Test
    public void givenSkillExistsWhenSkillInfoIsRetrievedThen200IsReceived()
            throws IOException {
        // Given
        int id = 1;
        HttpUriRequest request = new HttpGet(SKILL_API_URL + id);

        // When
        HttpResponse httpResponse = HttpClientBuilder.create().build().execute(request);

        // Then
        assertEquals(HttpStatus.SC_OK, httpResponse.getStatusLine().getStatusCode());
    }

    @Test
    public void
    givenRequestWithNoAcceptHeaderWhenRequestIsExecutedThenDefaultResponseContentTypeIsJson()
            throws IOException {
        // Given
        String jsonMimeType = "application/json";
        HttpUriRequest request = new HttpGet(SKILL_API_URL);

        // When
        HttpResponse response = HttpClientBuilder.create().build().execute(request);

        // Then
        String mimeType = ContentType.getOrDefault(response.getEntity()).getMimeType();
        assertEquals(jsonMimeType, mimeType);
    }

    @Test
    public void givenRequestWithProperJsonWithSkillNameWhenRequestIsExecutedThenStatusCreated()
            throws IOException {
        // Given
        String requestBody = "{\"name\":\"dfhrgder\"}";
        HttpPost request = new HttpPost(SKILL_API_URL);
        StringEntity entity = new StringEntity(requestBody);
        request.setEntity(entity);
        request.setHeader("Accept", "application/json");
        request.setHeader("Content-type", "application/json");

        // When
        HttpResponse response = HttpClientBuilder.create().build().execute(request);

        // Then
        assertEquals(HttpStatus.SC_CREATED, response.getStatusLine().getStatusCode());
    }

    @Test
    public void givenRequestWithNonProperJsonWhenRequestIsExecutedThenStatusBadRequest()
            throws IOException {
        // Given
        String requestBody = "{\"namsdfge\":\"dfhrgder\"}";
        HttpPost request = new HttpPost(SKILL_API_URL);
        StringEntity entity = new StringEntity(requestBody);
        request.setEntity(entity);
        request.setHeader("Accept", "application/json");
        request.setHeader("Content-type", "application/json");

        // When
        HttpResponse response = HttpClientBuilder.create().build().execute(request);

        // Then
        assertEquals(HttpStatus.SC_BAD_REQUEST, response.getStatusLine().getStatusCode());
    }

    @Test
    public void givenRequestWithIdWhenRequestIsExecutedThenStatusNoContent()
            throws IOException {
        // Given
        int id = 32454;
        HttpDelete request = new HttpDelete(SKILL_API_URL + id);

        // When
        HttpResponse response = HttpClientBuilder.create().build().execute(request);

        // Then
        assertEquals(HttpStatus.SC_NO_CONTENT, response.getStatusLine().getStatusCode());
    }

    @Test
    public void givenRequestWithoutIdWhenRequestIsExecutedThenStatusNoContent()
            throws IOException {
        // Given
        HttpDelete request = new HttpDelete(SKILL_API_URL);

        // When
        HttpResponse response = HttpClientBuilder.create().build().execute(request);

        // Then
        assertEquals(HttpStatus.SC_BAD_REQUEST, response.getStatusLine().getStatusCode());
    }

    @Test
    public void givenRequestWithNewFieldsForUpdateWhenRequestIsExecutedThenStatusOk()
            throws IOException {
        // Given
        int id = 1;
        String requestBody = "{\"name\":\"dfhrgdrh8\"}";
        HttpPut request = new HttpPut(SKILL_API_URL + id);
        StringEntity entity = new StringEntity(requestBody);
        request.setEntity(entity);
        request.setHeader("Accept", "application/json");
        request.setHeader("Content-type", "application/json");

        // When
        HttpResponse response = HttpClientBuilder.create().build().execute(request);

        // Then
        assertEquals(HttpStatus.SC_OK, response.getStatusLine().getStatusCode());
    }
}