package controller;

import constant.Constant;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.HttpClientBuilder;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;

class SkillControllerTest {
    private final String SKILL_API_URL = Constant.URL + "/api/v1/skills/";

    @Test
    public void givenUserDoesNotExists_whenUserInfoIsRetrieved_then404IsReceived()
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
    void doGet() {

    }

    @Test
    void doPost() {
    }

    @Test
    void doPut() {
    }

    @Test
    void doDelete() {
    }
}