package com.example.tests;

import com.example.BaseClass;
import io.restassured.http.ContentType;

import com.github.javafaker.Faker;

import io.restassured.http.Method;
import io.restassured.response.Response;
import org.apache.http.HttpStatus;

import org.testng.annotations.Test;

import static io.restassured.RestAssured.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

import org.json.JSONArray;
import org.json.JSONObject;

public class GetApi extends BaseClass {

    @Test
    public void getDataResponse() {
        Response response = getResponse();

        assertThat(response.getStatusCode(), is(HttpStatus.SC_OK));
        JSONArray jsonArray = new JSONArray(response.asString());

        JSONObject jsonObject = jsonArray.getJSONObject(0);

        System.out.println("Get Response => " + jsonObject.toString());

        int id = jsonObject.getInt("id");
        String name = jsonObject.getString("name");
        String gender = jsonObject.getString("gender");
        String email = jsonObject.getString("email");
        String status = jsonObject.getString("status");

        assertThat(jsonObject.getInt("id"), is(id));
        assertThat(jsonObject.getString("name"), is(name));
        assertThat(jsonObject.getString("gender"), is(gender));
        assertThat(jsonObject.getString("status"), is(status));
        assertThat(jsonObject.getString("email"), is(email));
    }

    @Test
    public void postDataResponse() {
        Faker faker = new Faker();

        String name = faker.name().name();
        String email = faker.internet().emailAddress();
        String gender = "male";
        String status = "active";

        String body = "{\n " +
                "\"name\":\"" + name + "\",\n" +
                "\"email\":\"" + email + "\",\n" +
                "\"gender\":\"" + gender + "\",\n" +
                "\"status\":\"" + status + "\"\n" +
                "}";

        Response response = getPostResponse(body);
        assertThat(response.getStatusCode(), is(HttpStatus.SC_CREATED));

        System.out.println("Post Response => " + response.asString());

        JSONObject jsonObject = new JSONObject(response.asString());
        int id = jsonObject.getInt("id");

        assertThat(jsonObject.getInt("id"), is(id));
        assertThat(jsonObject.getString("name"), is(name));
        assertThat(jsonObject.getString("gender"), is(gender));
        assertThat(jsonObject.getString("status"), is(status));
        assertThat(jsonObject.getString("email"), is(email));
    }

    @Test
    public void putDataResponse() {
        Faker faker = new Faker();

        String name = faker.name().name();
        String email = faker.internet().emailAddress();
        String gender = "male";
        String status = "active";

        String body = "{\n " +
                "\"name\":\"" + name + "\",\n" +
                "\"email\":\"" + email + "\",\n" +
                "\"gender\":\"" + gender + "\",\n" +
                "\"status\":\"" + status + "\"\n" +
                "}";

        Response response = getPostResponse(body);

        System.out.println("Post Response => " + response.asString());

        assertThat(response.getStatusCode(), is(HttpStatus.SC_CREATED));

        JSONObject jsonObject = new JSONObject(response.asString());

        int id = jsonObject.getInt("id");

        assertThat(jsonObject.getInt("id"), is(id));
        assertThat(jsonObject.getString("name"), is(name));
        assertThat(jsonObject.getString("gender"), is(gender));
        assertThat(jsonObject.getString("status"), is(status));
        assertThat(jsonObject.getString("email"), is(email));

        String putName = faker.name().name();
        String putEmail = faker.internet().emailAddress();
        String putGender = "female";
        String putStatus = "inactive";

        String putBody = "{\n " +
                "\"name\":\"" + putName + "\",\n" +
                "\"email\":\"" + putEmail + "\",\n" +
                "\"gender\":\"" + putGender + "\",\n" +
                "\"status\":\"" + putStatus + "\"\n" +
                "}";

        Response putResponse = getPutResponse(id, putBody);
        assertThat(putResponse.getStatusCode(), is(HttpStatus.SC_OK));

        System.out.println("Put Response => " + putResponse.asString());

        int putId = jsonObject.getInt("id");

        JSONObject putJsonObject = new JSONObject(putResponse.asString());

        assertThat(putJsonObject.getInt("id"), is(putId));
        assertThat(putJsonObject.getString("name"), is(putName));
        assertThat(putJsonObject.getString("gender"), is(putGender));
        assertThat(putJsonObject.getString("status"), is(putStatus));
        assertThat(putJsonObject.getString("email"), is(putEmail));
    }

    @Test
    public void deleteDataResponse() {
        Faker faker = new Faker();

        String name = faker.name().name();
        String email = faker.internet().emailAddress();
        String gender = "male";
        String status = "active";

        String body = "{\n " +
                "\"name\":\"" + name + "\",\n" +
                "\"email\":\"" + email + "\",\n" +
                "\"gender\":\"" + gender + "\",\n" +
                "\"status\":\"" + status + "\"\n" +
                "}";

        Response response = getPostResponse(body);
        System.out.println("Post Response => " + response.asString());

        JSONObject jsonObject = new JSONObject(response.asString());

        int id = jsonObject.getInt("id");

        assertThat(jsonObject.getInt("id"), is(id));
        assertThat(jsonObject.getString("name"), is(name));
        assertThat(jsonObject.getString("gender"), is(gender));
        assertThat(jsonObject.getString("status"), is(status));
        assertThat(jsonObject.getString("email"), is(email));

        Response deleteResponse = getDeleteResponse(id);
        assertThat(deleteResponse.getStatusCode(), is(HttpStatus.SC_NO_CONTENT));

        System.out.println("Delete Response => " + deleteResponse.asString());
    }

    public Response getResponse() {
        Response response = given()
                .request(Method.GET, "/users");
        return response;
    }

    public Response getPostResponse(String body) {
        Response response = given()
                .header("Authorization", tokens)
                .contentType(ContentType.JSON)
                .body(body)
                .request(Method.POST, "/users");
        return response;
    }

    public Response getPutResponse(int id, String putBody) {
        return given()
                .header("Authorization", tokens)
                .contentType(ContentType.JSON)
                .body(putBody)
                .request(Method.PUT, "/users/" + id);
    }

    public Response getDeleteResponse(int id) {
        return given()
                .header("Authorization", tokens)
                .request(Method.DELETE, "/users/" + id);
    }

}