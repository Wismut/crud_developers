package controller;

import constant.Constant;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.entity.ContentType;
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
    public void givenRequestWithProperJsonWithSkillNameWhenRequestIsExecutedThenSkillCreated()
            throws IOException {
        // Given
        String requestBody = "{\"name\":\"dfhrgder\"}";
        HttpPost request = new HttpPost(SKILL_API_URL);

        // When
        HttpResponse response = HttpClientBuilder.create().build().execute(request);

        // Then
        assertEquals(HttpStatus.SC_CREATED, response.getStatusLine().getStatusCode());
    }
}