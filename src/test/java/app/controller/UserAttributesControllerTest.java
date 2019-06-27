package app.controller;

import app.model.UserAttribute;
import app.util.json.JsonTransformer;
import app.util.to.UserAttributesUtil;
import io.restassured.http.ContentType;
import org.eclipse.jetty.http.HttpStatus;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static app.config.WebServer.*;
import static app.repository.UserAttributesTestData.*;
import static app.repository.UserTestData.id1;
import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.when;
import static org.assertj.core.api.Assertions.assertThat;

class UserAttributesControllerTest extends AbstractTest {

    private final String USERS_PATH = API_URL + USERS_URL;
    private final JsonTransformer jsonTransformer = new JsonTransformer();

    @Test
    void getShouldReturnUserAttributes() throws Exception {

        Map<String, String> actual =
        when()
                .get(USERS_PATH + "/" + id1 + ATTRIBUTES_URL)
        .then()
                .statusCode(HttpStatus.OK_200)
                .contentType(ContentType.JSON)
                .extract()
                .body()
                .jsonPath()
                .getMap(".");

        final UserAttributesPayload expected = UserAttributesUtil.asPayload(USER_ATTRIBUTES);
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    void getByKeyShouldReturnUserAttribute() throws Exception {

        Map<String, String> actual =
        when()
                .get(USERS_PATH + "/" + id1 + ATTRIBUTES_URL + "/" + ua1.getKey())
        .then()
                .statusCode(HttpStatus.OK_200)
                .contentType(ContentType.JSON)
                .extract()
                .body()
                .jsonPath()
                .getMap(".");

        final UserAttributesPayload expected = UserAttributesUtil.asPayload(ua1);
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    void getByKeyShouldReturnNotFoundWhenNonExistentKey() throws Exception {

        when()
                .get(USERS_PATH + "/" + id1 + ATTRIBUTES_URL + "/" + "no such key")
        .then()
                .statusCode(HttpStatus.NOT_FOUND_404);
    }

    @Test
    void deleteByKeyShouldRemoveAssociatedUserAttribute() throws Exception {

        when()
                .delete(USERS_PATH + "/" + id1 + ATTRIBUTES_URL + "/" + ua1.getKey())
        .then()
                .statusCode(HttpStatus.NO_CONTENT_204);

        Map<String, String> actual =
                when()
                        .get(USERS_PATH + "/" + id1 + ATTRIBUTES_URL)
                .then()
                        .statusCode(HttpStatus.OK_200)
                        .contentType(ContentType.JSON)
                        .extract()
                        .body()
                        .jsonPath()
                        .getMap(".");

        final UserAttributesPayload expected = UserAttributesUtil.asPayload(Arrays.asList(ua2));
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    void deleteByKeyShouldStillBeValidWithExtraBody() {
        given()
                .body("{\"extra\":\"body\"}")
        .when()
                .delete(USERS_PATH + "/" + id1 + ATTRIBUTES_URL + "/" + ua1.getKey())
        .then()
                .statusCode(HttpStatus.NO_CONTENT_204);
    }

    @Test
    void deleteByKeyShouldReturnNotFoundWhenNonExistentUserAttribute() {
        when()
                .delete(USERS_PATH + "/" + id1 + ATTRIBUTES_URL + "/" + "no such key")
        .then()
                .statusCode(HttpStatus.NOT_FOUND_404);
    }

    @Test
    void postShouldSaveUserAttributes() throws Exception {

        UserAttribute created = created();
        UserAttribute updated = updated();

        List<UserAttribute> savedList = Arrays.asList(created, updated);
        List<UserAttribute> expectedList = Arrays.asList(created, updated, ua2);

        given()
                .body(jsonTransformer.render(UserAttributesUtil.asPayload(savedList)))
        .when()
                .post(USERS_PATH + "/" + id1 + ATTRIBUTES_URL)
        .then()
                .statusCode(HttpStatus.NO_CONTENT_204);

        Map<String, String> actual =
                when()
                        .get(USERS_PATH + "/" + id1 + ATTRIBUTES_URL)
                .then()
                        .statusCode(HttpStatus.OK_200)
                        .contentType(ContentType.JSON)
                        .extract()
                        .body()
                        .jsonPath()
                        .getMap(".");

        assertThat(actual).isEqualTo(UserAttributesUtil.asPayload(expectedList));
    }

    @Test
    void postShouldReturnUserErrorWhenEmptyJsonBody() {
        given()
                .body("{}")
        .when()
                .post(USERS_PATH + "/" + id1 + ATTRIBUTES_URL)
        .then()
                .statusCode(HttpStatus.BAD_REQUEST_400);
    }

    @Test
    void postShouldReturnUserErrorWhenWrongJsonBody() {
        given()
                .body("{\"very\":{\"wrong\":\"body\"}}")
        .when()
                .post(USERS_PATH + "/" + id1 + ATTRIBUTES_URL)
        .then()
                .statusCode(HttpStatus.BAD_REQUEST_400);
    }

}
