package ua.wismut.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import constant.Constant;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.test.context.ActiveProfiles;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ActiveProfiles("test")
class SkillControllerIntegrationTest {
    private final String SKILL_API_URL = Constant.URL + "/api/v1/skills/";

    private static final String USER_USERNAME = "user";
    private static final String MODERATOR_USERNAME = "moderator";
    private static final String ADMIN_USERNAME = "admin";
    private static final String PASSWORD = "test";
    private static String userToken;
    private static String moderatorToken;
    private static String adminToken;

    @BeforeAll
    public static void init() throws IOException {
        userToken = receiveToken(USER_USERNAME, PASSWORD);
        moderatorToken = receiveToken(MODERATOR_USERNAME, PASSWORD);
        adminToken = receiveToken(ADMIN_USERNAME, PASSWORD);
    }

    @Test
    public void givenSkillDoesNotExistsWhenSkillInfoIsRetrievedThen404IsReceived()
            throws IOException {
        // Given
        int id = 234545223;
        HttpGet request = new HttpGet(SKILL_API_URL + id);
        request.setHeader("Authorization", "Bearer_" + userToken);

        // When
        HttpResponse httpResponse = HttpClientBuilder.create().build().execute(request);

        // Then
        assertEquals(HttpStatus.SC_NOT_FOUND, httpResponse.getStatusLine().getStatusCode());
    }

    @Test
    public void
    givenRequestWithNoAcceptHeaderWhenRequestIsExecutedThenDefaultResponseContentTypeIsJson()
            throws IOException {
        // Given
        String jsonMimeType = "application/json";
        HttpGet request = new HttpGet(SKILL_API_URL);
        request.setHeader("Accept", "application/json");
        request.setHeader("Content-type", "application/json");
        request.setHeader("Authorization", "Bearer_" + userToken);

        // When
        HttpResponse response = HttpClientBuilder.create().build().execute(request);

        // Then
        String mimeType = ContentType.getOrDefault(response.getEntity()).getMimeType();
        assertEquals(jsonMimeType, mimeType);
        assertEquals(HttpStatus.SC_OK, response.getStatusLine().getStatusCode());
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
        request.setHeader("Authorization", "Bearer_" + userToken);

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
        request.setHeader("Authorization", "Bearer_" + userToken);

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
        request.setHeader("Authorization", "Bearer_" + userToken);

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
        request.setHeader("Authorization", "Bearer_" + userToken);

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
        request.setHeader("Authorization", "Bearer_" + userToken);

        // When
        HttpResponse response = HttpClientBuilder.create().build().execute(request);

        // Then
        assertEquals(HttpStatus.SC_FORBIDDEN, response.getStatusLine().getStatusCode());
    }

    @Test
    public void givenSkillExistsWhenSkillInfoIsRetrievedThen200IsReceived()
            throws IOException {
        // Given
        int id = 1;
        HttpGet request = new HttpGet(SKILL_API_URL + id);
        request.setHeader("Accept", "application/json");
        request.setHeader("Content-type", "application/json");
        request.setHeader("Authorization", "Bearer_" + userToken);

        // When
        HttpResponse httpResponse = HttpClientBuilder.create().build().execute(request);

        // Then
        assertEquals(HttpStatus.SC_OK, httpResponse.getStatusLine().getStatusCode());
    }

    @Test
    public void givenSkillDoesNotExistsWhenModeratorTryToRetrieveThen404IsReceived()
            throws IOException {
        // Given
        int id = 234545223;
        HttpGet request = new HttpGet(SKILL_API_URL + id);
        request.setHeader("Accept", "application/json");
        request.setHeader("Content-type", "application/json");
        request.setHeader("Authorization", "Bearer_" + moderatorToken);

        // When
        HttpResponse httpResponse = HttpClientBuilder.create().build().execute(request);

        // Then
        assertEquals(HttpStatus.SC_NOT_FOUND, httpResponse.getStatusLine().getStatusCode());
    }

    @Test
    public void
    givenRequestWithNoAcceptHeaderWhenModeratorExecuteThenStatusOK()
            throws IOException {
        // Given
        HttpGet request = new HttpGet(SKILL_API_URL);
        request.setHeader("Accept", "application/json");
        request.setHeader("Content-type", "application/json");
        request.setHeader("Authorization", "Bearer_" + moderatorToken);

        // When
        HttpResponse response = HttpClientBuilder.create().build().execute(request);

        // Then
        assertEquals(HttpStatus.SC_OK, response.getStatusLine().getStatusCode());
    }

    @Test
    public void givenRequestWithProperJsonWithSkillNameWhenModeratorExecuteThenStatusForbidden()
            throws IOException {
        // Given
        String requestBody = "{\"name\":\"dfhrgder\"}";
        HttpPost request = new HttpPost(SKILL_API_URL);
        StringEntity entity = new StringEntity(requestBody);
        request.setEntity(entity);
        request.setHeader("Accept", "application/json");
        request.setHeader("Content-type", "application/json");
        request.setHeader("Authorization", "Bearer_" + moderatorToken);

        // When
        HttpResponse response = HttpClientBuilder.create().build().execute(request);

        // Then
        assertEquals(HttpStatus.SC_FORBIDDEN, response.getStatusLine().getStatusCode());
    }

    @Test
    public void givenRequestWithNonProperJsonWhenModeratorExecuteThenStatusForbidden()
            throws IOException {
        // Given
        String requestBody = "{\"namsdfge\":\"dfhrgder\"}";
        HttpPost request = new HttpPost(SKILL_API_URL);
        StringEntity entity = new StringEntity(requestBody);
        request.setEntity(entity);
        request.setHeader("Accept", "application/json");
        request.setHeader("Content-type", "application/json");
        request.setHeader("Authorization", "Bearer_" + moderatorToken);

        // When
        HttpResponse response = HttpClientBuilder.create().build().execute(request);

        // Then
        assertEquals(HttpStatus.SC_FORBIDDEN, response.getStatusLine().getStatusCode());
    }

    @Test
    public void givenRequestWithIdWhenModeratorTryToDeleteAbsentSkillThenStatusForbidden()
            throws IOException {
        // Given
        int id = 32454;
        HttpDelete request = new HttpDelete(SKILL_API_URL + id);
        request.setHeader("Authorization", "Bearer_" + moderatorToken);

        // When
        HttpResponse response = HttpClientBuilder.create().build().execute(request);

        // Then
        assertEquals(HttpStatus.SC_FORBIDDEN, response.getStatusLine().getStatusCode());
    }

    @Test
    public void givenRequestWithoutIdWhenModeratorExecuteThenStatusForbidden()
            throws IOException {
        // Given
        HttpDelete request = new HttpDelete(SKILL_API_URL);
        request.setHeader("Accept", "application/json");
        request.setHeader("Content-type", "application/json");
        request.setHeader("Authorization", "Bearer_" + moderatorToken);

        // When
        HttpResponse response = HttpClientBuilder.create().build().execute(request);

        // Then
        assertEquals(HttpStatus.SC_FORBIDDEN, response.getStatusLine().getStatusCode());
    }

    @Test
    public void givenRequestWithNewFieldsForUpdateWhenModeratorExecuteThenStatusForbidden()
            throws IOException {
        // Given
        int id = 1;
        String requestBody = "{\"name\":\"dfhrgdrh8\"}";
        HttpPut request = new HttpPut(SKILL_API_URL + id);
        StringEntity entity = new StringEntity(requestBody);
        request.setEntity(entity);
        request.setHeader("Accept", "application/json");
        request.setHeader("Content-type", "application/json");
        request.setHeader("Authorization", "Bearer_" + moderatorToken);

        // When
        HttpResponse response = HttpClientBuilder.create().build().execute(request);

        // Then
        assertEquals(HttpStatus.SC_FORBIDDEN, response.getStatusLine().getStatusCode());
    }

    @Test
    public void givenSkillDoesNotExistsWhenAdminTryToReceiveThen404IsReceived()
            throws IOException {
        // Given
        int id = 234545223;
        HttpGet request = new HttpGet(SKILL_API_URL + id);
        request.setHeader("Accept", "application/json");
        request.setHeader("Content-type", "application/json");
        request.setHeader("Authorization", "Bearer_" + adminToken);

        // When
        HttpResponse httpResponse = HttpClientBuilder.create().build().execute(request);

        // Then
        assertEquals(HttpStatus.SC_NOT_FOUND, httpResponse.getStatusLine().getStatusCode());
    }

    @Test
    public void
    givenRequestWithNoAcceptHeaderWhenAdminExecuteThenResponseStatusOK()
            throws IOException {
        // Given
        HttpGet request = new HttpGet(SKILL_API_URL);
        request.setHeader("Accept", "application/json");
        request.setHeader("Content-type", "application/json");
        request.setHeader("Authorization", "Bearer_" + adminToken);

        // When
        HttpResponse response = HttpClientBuilder.create().build().execute(request);

        // Then
        assertEquals(HttpStatus.SC_OK, response.getStatusLine().getStatusCode());
    }

    @Test
    public void givenRequestWithProperJsonWithSkillNameWhenAdminExecuteThenStatusCreated()
            throws IOException {
        // Given
        String requestBody = "{\"name\":\"dfhrgder\"}";
        HttpPost request = new HttpPost(SKILL_API_URL);
        StringEntity entity = new StringEntity(requestBody);
        request.setEntity(entity);
        request.setHeader("Accept", "application/json");
        request.setHeader("Content-type", "application/json");
        request.setHeader("Authorization", "Bearer_" + adminToken);

        // When
        HttpResponse response = HttpClientBuilder.create().build().execute(request);

        // Then
        assertEquals(HttpStatus.SC_OK, response.getStatusLine().getStatusCode());
    }

    @Test
    public void givenRequestWithNonProperJsonWhenAdminExecuteThenStatusBadRequest()
            throws IOException {
        // Given
        String requestBody = "{\"namsdfge\":\"dfhrgder\"}";
        HttpPost request = new HttpPost(SKILL_API_URL);
        StringEntity entity = new StringEntity(requestBody);
        request.setEntity(entity);
        request.setHeader("Accept", "application/json");
        request.setHeader("Content-type", "application/json");
        request.setHeader("Authorization", "Bearer_" + adminToken);

        // When
        HttpResponse response = HttpClientBuilder.create().build().execute(request);

        // Then
        assertEquals(HttpStatus.SC_BAD_REQUEST, response.getStatusLine().getStatusCode());
    }

    @Test
    public void givenRequestWithIdWhenAdminTryToDeleteAbsentSkillThenStatusNotFound()
            throws IOException {
        // Given
        int id = 32454;
        HttpDelete request = new HttpDelete(SKILL_API_URL + id);
        request.setHeader("Authorization", "Bearer_" + adminToken);

        // When
        HttpResponse response = HttpClientBuilder.create().build().execute(request);

        // Then
        assertEquals(HttpStatus.SC_NOT_FOUND, response.getStatusLine().getStatusCode());
    }

    @Test
    public void givenRequestWithoutIdWhenAdminExecuteThenStatusNotAllowed()
            throws IOException {
        // Given
        HttpDelete request = new HttpDelete(SKILL_API_URL);
        request.setHeader("Accept", "application/json");
        request.setHeader("Content-type", "application/json");
        request.setHeader("Authorization", "Bearer_" + adminToken);

        // When
        HttpResponse response = HttpClientBuilder.create().build().execute(request);

        // Then
        assertEquals(HttpStatus.SC_METHOD_NOT_ALLOWED, response.getStatusLine().getStatusCode());
    }

    @Test
    public void givenRequestWithNewFieldsForUpdateWhenAdminExecuteThenStatusOk()
            throws IOException {
        // Given
        int id = 1;
        String requestBody = "{\"name\":\"dfhrgdrh8\"}";
        HttpPut request = new HttpPut(SKILL_API_URL + id);
        StringEntity entity = new StringEntity(requestBody);
        request.setEntity(entity);
        request.setHeader("Accept", "application/json");
        request.setHeader("Content-type", "application/json");
        request.setHeader("Authorization", "Bearer_" + adminToken);

        // When
        HttpResponse response = HttpClientBuilder.create().build().execute(request);

        // Then
        assertEquals(HttpStatus.SC_OK, response.getStatusLine().getStatusCode());
    }

    private static String receiveToken(String username, String password) throws IOException {
        ObjectNode objectNode = new ObjectMapper()
                .createObjectNode()
                .put("username", username)
                .put("password", password);
        HttpPost request = new HttpPost(Constant.URL + "/api/v1/auth/login");
        request.setEntity(new StringEntity(objectNode.toString()));
        request.setHeader("Accept", "application/json");
        request.setHeader("Content-type", "application/json");
        HttpResponse response = HttpClientBuilder.create().build().execute(request);
        JsonNode jsonNode = new ObjectMapper()
                .readTree(response.getEntity().getContent());
        return jsonNode
                .get("token")
                .asText();
    }
}
