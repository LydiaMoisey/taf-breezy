package by.itacademy.moiseenkolydia.breezy.api;

import io.restassured.http.ContentType;
import org.json.simple.JSONObject;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.when;
import static org.hamcrest.Matchers.equalTo;

public class ApiTest {
    JSONObject data = new JSONObject();

    @Test
    @DisplayName("Request to the site")
    public void testGetRequest() {
        when().
                get(TestData.BASE_URL).
        then().
                assertThat().
                statusCode(200);
    }

    @Test
    @DisplayName("Login with valid credentials")
    public void testLoginWithValidCredentials() {
        data.put("email", TestData.VALID_EMAIL);
        data.put("password", TestData.VALID_PASSWORD);

        given().
                contentType(ContentType.JSON).
                accept(ContentType.JSON).
                body(data.toJSONString()).
        when().
                post(TestData.LOGIN_URL).
        then().
                log().body().
                assertThat().
                body("data.email", equalTo(TestData.VALID_EMAIL)).
                statusCode(200);
    }

    @Test
    @DisplayName("Login with unregistered user credentials")
    public void testLoginWithInvalidEmail() {
        data.put("email", TestData.RANDOM_EMAIL);
        data.put("password", TestData.RANDOM_PASSWORD);

        given().
                contentType(ContentType.JSON).
                accept(ContentType.JSON).
                body(data.toJSONString()).
        when().
                post(TestData.LOGIN_URL).
        then().
                log().body().
                assertThat().
                body("message", equalTo(TestData.ERROR_INVALID_EMAIL_AND_PASSWORD)).
                statusCode(200);
    }

    @Test
    @DisplayName("Login without email")
    public void testLoginWithoutEmail() {
        data.put("password", TestData.RANDOM_PASSWORD);

        given().
                contentType(ContentType.JSON).
                accept(ContentType.JSON).
                body(data.toJSONString()).
        when().
                post(TestData.LOGIN_URL).
        then().
                log().body().
                assertThat().
                body("message", equalTo(TestData.ERROR_WITHOUT_EMAIL)).
                statusCode(200);
    }

    @Test
    @DisplayName("Login with invalid password")
    public void testLoginWithInvalidPassword() {
        data.put("email", TestData.VALID_EMAIL);
        data.put("password", TestData.RANDOM_PASSWORD);

        given().
                contentType(ContentType.JSON).
                accept(ContentType.JSON).
                body(data.toJSONString()).
        when().
                post(TestData.LOGIN_URL).
        then().
                log().body().
                assertThat().
                body("message", equalTo(TestData.ERROR_INVALID_EMAIL_AND_PASSWORD)).
                statusCode(200);
    }

    @Test
    @DisplayName("Login without password")
    public void testLoginWithoutPassword() {

        data.put("email", TestData.VALID_EMAIL);

        given().
                contentType(ContentType.JSON).
                accept(ContentType.JSON).
                body(data.toJSONString()).
        when().
                post(TestData.LOGIN_URL).
        then().
                log().body().
                assertThat().
                body("message", equalTo(TestData.ERROR_WITHOUT_PASSWORD)).
                statusCode(200);
    }

    @Test
    @DisplayName("Login without email and password")
    public void testLoginWithoutEmailAndPassword() {

        given().
                contentType(ContentType.JSON).
                accept(ContentType.JSON).
                body(data.toJSONString()).
        when().
                post(TestData.LOGIN_URL).
        then().
                log().body().
                assertThat().
                body("message", equalTo(TestData.ERROR_WITHOUT_EMAIL_AND_PASSWORD)).
                statusCode(200);
    }
}
