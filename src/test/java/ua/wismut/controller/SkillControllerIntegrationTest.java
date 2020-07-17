package ua.wismut.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import constant.Constant;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.*;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.test.context.ActiveProfiles;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ActiveProfiles("test")
class SkillControllerIntegrationTest {
    private final String SKILL_API_URL = Constant.URL + "/api/v1/skills/";

    private static String token;

    @BeforeAll
    public static void init() throws IOException {
        String requestBody = "{\"username\":\"user\",\"password\":\"test\"}";
        HttpPost request = new HttpPost(Constant.URL + "/api/v1/auth/login");
        StringEntity stringEntity = new StringEntity(requestBody);
        request.setEntity(stringEntity);
        request.setHeader("Accept", "application/json");
        request.setHeader("Content-type", "application/json");
        HttpResponse response = HttpClientBuilder.create().build().execute(request);
        JsonNode jsonNode = new ObjectMapper()
                .readTree(
                        new BufferedReader(
                                new InputStreamReader(response.getEntity().getContent())).lines().collect(Collectors.joining("\n")
                        )
                );
        token = jsonNode
                .get("token")
                .toPrettyString()
                .replaceAll("\"", "");
    }

    @Test
    public void givenSkillDoesNotExistsWhenSkillInfoIsRetrievedThen404IsReceived()
            throws IOException {
        // Given
        int id = 234545223;
        HttpUriRequest request = new HttpGet(SKILL_API_URL + id);
        request.setHeader("Authorization", "Bearer_" + token);

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
        request.setHeader("Accept", "application/json");
        request.setHeader("Content-type", "application/json");
        request.setHeader("Authorization", "Bearer_" + token);

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
        request.setHeader("Accept", "application/json");
        request.setHeader("Content-type", "application/json");
        request.setHeader("Authorization", "Bearer_" + token);

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
        request.setHeader("Authorization", "Bearer_" + token);

        // When
        HttpResponse response = HttpClientBuilder.create().build().execute(request);

        // Then
        assertEquals(HttpStatus.SC_FORBIDDEN, response.getStatusLine().getStatusCode());
    }

    @Test
    public void givenRequestWithNonProperJsonWhenRequestIsExecutedThenStatusForbidden()
            throws IOException {
        // Given
        String requestBody = "{\"namsdfge\":\"dfhrgder\"}";
        HttpPost request = new HttpPost(SKILL_API_URL);
        StringEntity entity = new StringEntity(requestBody);
        request.setEntity(entity);
        request.setHeader("Accept", "application/json");
        request.setHeader("Content-type", "application/json");
        request.setHeader("Authorization", "Bearer_" + token);

        // When
        HttpResponse response = HttpClientBuilder.create().build().execute(request);

        // Then
        assertEquals(HttpStatus.SC_FORBIDDEN, response.getStatusLine().getStatusCode());
    }

    @Test
    public void givenRequestWithIdWhenTryToDeleteAbsentSkillThenStatusNotFound()
            throws IOException {
        // Given
        int id = 32454;
        HttpDelete request = new HttpDelete(SKILL_API_URL + id);
        request.setHeader("Authorization", "Bearer_" + token);

        // When
        HttpResponse response = HttpClientBuilder.create().build().execute(request);

        // Then
        assertEquals(HttpStatus.SC_FORBIDDEN, response.getStatusLine().getStatusCode());
    }

    @Test
    public void givenRequestWithoutIdWhenRequestIsExecutedThenStatusNoContent()
            throws IOException {
        // Given
        HttpDelete request = new HttpDelete(SKILL_API_URL);
        request.setHeader("Accept", "application/json");
        request.setHeader("Content-type", "application/json");
        request.setHeader("Authorization", "Bearer_" + token);

        // When
        HttpResponse response = HttpClientBuilder.create().build().execute(request);

        // Then
        assertEquals(HttpStatus.SC_FORBIDDEN, response.getStatusLine().getStatusCode());
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
        request.setHeader("Authorization", "Bearer_" + token);

        // When
        HttpResponse response = HttpClientBuilder.create().build().execute(request);

        // Then
        assertEquals(HttpStatus.SC_FORBIDDEN, response.getStatusLine().getStatusCode());
    }
}
