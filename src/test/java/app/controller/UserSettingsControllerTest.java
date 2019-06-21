package app.controller;

import app.dao.UserSettingsTestData;
import app.model.User;
import app.model.UserSettings;
import app.util.json.JsonTransformer;
import io.restassured.http.ContentType;
import org.eclipse.jetty.http.HttpStatus;
import org.junit.jupiter.api.Test;

import static app.config.WebServer.*;
import static app.dao.UserSettingsTestData.us1;
import static app.dao.UserTestData.*;
import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.when;
import static org.assertj.core.api.Assertions.assertThat;

class UserSettingsControllerTest extends AbstractTest {

    private final String USERS_PATH = API_URL + USERS_URL;
    private final JsonTransformer jsonTransformer = new JsonTransformer();

    @Test
    void getShouldReturnUserSettings() throws Exception {

        UserSettings actual =
        when()
                .get(USERS_PATH + "/" + id1 + SETTINGS_URL)
        .then()
                .statusCode(HttpStatus.OK_200)
                .contentType(ContentType.JSON)
                .extract()
                .body()
                .jsonPath()
                .getObject("", UserSettings.class);

        assertThat(actual).isEqualToComparingFieldByField(us1);
    }

    @Test
    void getShouldReturnNotFoundWhenNonExistentUser() throws Exception {

        when()
                .get(USERS_PATH + "/" + 42 + SETTINGS_URL)
        .then()
                .statusCode(HttpStatus.NOT_FOUND_404);
    }

    @Test
    void deleteUserShouldRemoveAssociatedUserSettings() throws Exception {

        when()
                .delete(USERS_PATH + "/" + id1)
                .then()
                .statusCode(HttpStatus.NO_CONTENT_204);

        when()
                .get(USERS_PATH + "/" + id1 + SETTINGS_URL)
        .then()
                .statusCode(HttpStatus.NOT_FOUND_404);
    }

    @Test
    void putShouldUpdateAssociatedUserSettings() throws Exception {

        UserSettings updated = UserSettingsTestData.updated();

        given()
                .body(jsonTransformer.render(updated))
        .when()
                .put(USERS_PATH + "/" + id1 + SETTINGS_URL)
        .then()
                .statusCode(HttpStatus.NO_CONTENT_204);

        UserSettings actual =
                when()
                        .get(USERS_PATH + "/" + id1 + SETTINGS_URL)
                        .then()
                        .statusCode(HttpStatus.OK_200)
                        .contentType(ContentType.JSON)
                        .extract()
                        .body()
                        .jsonPath()
                        .getObject("", UserSettings.class);

        updated.setId(actual.getId());

        assertThat(actual).isEqualToComparingFieldByField(updated);

    }

    @Test
    void putUserShouldNotChangeAssociatedUserSettings() throws Exception {

        User updatedUser = updated();

        given()
                .body(jsonTransformer.render(updatedUser))
        .when()
                .put(USERS_PATH + "/" + id1)
        .then()
                .statusCode(HttpStatus.NO_CONTENT_204);

        getShouldReturnUserSettings();

    }

    @Test
    void putShouldReturnErrorWhenNoBody() {
        when()
                .put(USERS_PATH + "/" + id1 + SETTINGS_URL)
        .then()
                .statusCode(HttpStatus.BAD_REQUEST_400);
    }

    @Test
    void putShouldReturnErrorWhenEmptyJsonBody() {
        given()
                .body("{}")
        .when()
                .put(USERS_PATH + "/" + id1 + SETTINGS_URL)
        .then()
                .statusCode(HttpStatus.BAD_REQUEST_400);
    }


    @Test
    void putShouldReturnErrorWhenMalformedUrl() {
        given()
                .body("{}")
        .when()
                .put(USERS_PATH + "/q"+ SETTINGS_URL)
        .then()
                .statusCode(HttpStatus.BAD_REQUEST_400);
    }

    @Test
    void postUsersShouldCreateNewUserSettings() throws Exception {

        User user = created();

        Integer id =
        given()
                .body(jsonTransformer.render(user))
        .when()
                .post(USERS_PATH)
        .then()
                .statusCode(HttpStatus.CREATED_201)
                .extract()
                .body()
                .jsonPath()
                .getInt("id");

        UserSettings actual =
        when()
                .get(USERS_PATH + "/" + id + SETTINGS_URL)
        .then()
                .statusCode(HttpStatus.OK_200)
                .contentType(ContentType.JSON)
                .extract()
                .body()
                .jsonPath()
                .getObject("", UserSettings.class);

        UserSettings expected = UserSettings.getDefault();
        expected.setId(actual.getId());

        assertThat(actual).isEqualToComparingFieldByField(expected);

    }

    @Test
    void postShouldReturnMethodNotAllowed() {
        given()
                .body("{}")
        .when()
                .post(USERS_PATH + "/1" + SETTINGS_URL)
        .then()
                .statusCode(HttpStatus.METHOD_NOT_ALLOWED_405)
                .header("Allow", UserSettingsController.ALLOWED_METHODS);
    }

    @Test
    void deleteShouldReturnMethodNotAllowed() {
        when()
                .delete(USERS_PATH + "/1" + SETTINGS_URL)
        .then()
                .statusCode(HttpStatus.METHOD_NOT_ALLOWED_405)
                .header("Allow", UserSettingsController.ALLOWED_METHODS);
    }

}
