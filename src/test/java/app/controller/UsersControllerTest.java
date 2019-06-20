package app.controller;

import app.model.User;
import app.util.json.JsonTransformer;
import io.restassured.http.ContentType;
import org.eclipse.jetty.http.HttpStatus;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static app.config.WebServer.API_URL;
import static app.config.WebServer.USERS_URL;
import static app.dao.UserTestData.*;
import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.when;
import static org.assertj.core.api.Assertions.assertThat;

class UsersControllerTest extends AbstractTest {

    private final String USERS_PATH = API_URL + USERS_URL;
    private final JsonTransformer jsonTransformer = new JsonTransformer();

    @Test
    void getShouldReturnUser() throws Exception {

        User actual =
        when()
                .get(USERS_PATH + "/" + id1)
        .then()
                .statusCode(HttpStatus.OK_200)
                .contentType(ContentType.JSON)
                .extract()
                .body()
                .jsonPath()
                .getObject("", User.class);

        assertThat(actual).isEqualToComparingFieldByField(u1);
    }

    @Test
    void getShouldReturnNotFoundWhenNonExistentUser() throws Exception {

        when()
                .get(USERS_PATH + "/" + 42)
        .then()
                .statusCode(HttpStatus.NOT_FOUND_404);
    }

    @Test
    void getAllShouldReturnAllUsers() throws Exception {

        List<User> actual =
                when()
                        .get(USERS_PATH)
                .then()
                        .statusCode(HttpStatus.OK_200)
                        .contentType(ContentType.JSON)
                        .extract()
                        .body()
                        .jsonPath()
                        .getList(".", User.class);

        assertThat(actual).usingFieldByFieldElementComparator().isEqualTo(USERS);
    }

    @Test
    void deleteShouldRemoveAssociatedUser() throws Exception {

        when()
                .delete(USERS_PATH + "/" + id1)
                .then()
                .statusCode(HttpStatus.NO_CONTENT_204);

        List<User> actual =
                when()
                        .get(USERS_PATH)
                .then()
                        .statusCode(HttpStatus.OK_200)
                        .contentType(ContentType.JSON)
                        .extract()
                        .body()
                        .jsonPath()
                        .getList(".", User.class);

        List<User> expected = Arrays.asList(u2, u3, u4);
        assertThat(actual).usingFieldByFieldElementComparator().isEqualTo(expected);
    }

    @Test
    void deleteShouldStillBeValidWithExtraBody() {
        given()
                .body("{\"wrong\":\"body\"}")
        .when()
                .delete(USERS_PATH + "/" + id1)
        .then()
                .statusCode(HttpStatus.NO_CONTENT_204);
    }

    @Test
    void deleteShouldReturnNotFoundWhenNonExistentUser() {
        when()
                .delete(USERS_PATH + "/" + 42)
        .then()
                .statusCode(HttpStatus.NOT_FOUND_404);
    }

    @Test
    void putShouldUpdateAssociatedUser() throws Exception {

        User updatedUser = updated();

        given()
                .body(jsonTransformer.render(updatedUser))
        .when()
                .put(USERS_PATH + "/" + id1)
        .then()
                .statusCode(HttpStatus.NO_CONTENT_204);

        User actual =
        when()
                .get(USERS_PATH + "/" + id1)
        .then()
                .statusCode(HttpStatus.OK_200)
                .contentType(ContentType.JSON)
                .extract()
                .body()
                .jsonPath()
                .getObject("", User.class);

        assertThat(actual).isEqualToComparingFieldByField(updatedUser);

    }

    @Test
    void putShouldReturnBadRequestErrorWhenInconsistentId() throws Exception {

        User updatedUser = updated();
        updatedUser.setId(42);

        given()
                .body(jsonTransformer.render(updatedUser))
        .when()
                .put(USERS_PATH + "/" + id1)
        .then()
                .statusCode(HttpStatus.BAD_REQUEST_400);

    }

    @Test
    void putShouldReturnUserErrorWhenNoBody() {
        when()
                .put(USERS_PATH + "/" + id1)
        .then()
                .statusCode(HttpStatus.BAD_REQUEST_400);
    }

    @Test
    void putShouldReturnUserErrorWhenEmptyJsonBody() {
        given()
                .body("{}")
        .when()
                .put(USERS_PATH + "/" + id1)
        .then()
                .statusCode(HttpStatus.BAD_REQUEST_400);
    }


    @Test
    void putShouldReturnErrorWhenMalformedUrl() {
        given()
                .body("{}")
        .when()
                .put(USERS_PATH + "/q")
        .then()
                .statusCode(HttpStatus.BAD_REQUEST_400);
    }

    @Test
    void postShouldReturnCreatedUser() throws Exception {

        User expected = created();

        User actual =
        given()
                .body(jsonTransformer.render(expected))
        .when()
                .post(USERS_PATH)
        .then()
                .statusCode(HttpStatus.CREATED_201)
                .extract()
                .body()
                .jsonPath()
                .getObject("", User.class);

        assertThat(!actual.isNew());
        expected.setId(actual.getId());

        assertThat(actual).isEqualToComparingFieldByField(expected);
    }

    @Test
    void postShouldCreateNewUser() throws Exception {

        User expected = created();

        Integer id =
        given()
                .body(jsonTransformer.render(expected))
        .when()
                .post(USERS_PATH)
        .then()
                .statusCode(HttpStatus.CREATED_201)
                .extract()
                .body()
                .jsonPath()
                .getInt("id");

        expected.setId(id);

        User actual =
        when()
                .get(USERS_PATH + "/" + id)
        .then()
                .statusCode(HttpStatus.OK_200)
                .contentType(ContentType.JSON)
                .extract()
                .body()
                .jsonPath()
                .getObject("", User.class);

        assertThat(actual).isEqualToComparingFieldByField(expected);

    }

    @Test
    void postShouldReturnBadRequestErrorWhenCreatingUserWithId() throws Exception {

        User user = created();
        user.setId(42);

        given()
                .body(jsonTransformer.render(user))
        .when()
                .post(USERS_PATH)
        .then()
                .statusCode(HttpStatus.BAD_REQUEST_400);
    }

    @Test
    void postShouldReturnUserErrorWhenEmptyJsonBody() {
        given()
                .body("{}")
        .when()
                .post(USERS_PATH)
        .then()
                .statusCode(HttpStatus.BAD_REQUEST_400);
    }

    @Test
    void postShouldReturnUserErrorWhenWrongJsonBody() {
        given()
                .body("{\"wrong\":\"body\"}")
        .when()
                .post(USERS_PATH)
        .then()
                .statusCode(HttpStatus.BAD_REQUEST_400);
    }

}
