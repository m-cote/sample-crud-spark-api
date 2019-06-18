package app.controller;

import app.model.User;
import io.restassured.RestAssured.*;
import io.restassured.http.ContentType;
import io.restassured.matcher.RestAssuredMatchers.*;
import org.eclipse.jetty.http.HttpStatus;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.List;

import static app.config.WebConfig.API_URL;
import static app.config.WebConfig.USERS_URL;
import static app.dao.UserTestData.*;
import static org.assertj.core.api.Assertions.assertThat;
import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.when;

public class UsersControllerTest extends AbstractTest {

    private final String USERS_PATH = API_URL + USERS_URL;

    @Nested
    class NormalBehaviour {

        @Test
        void getShouldReturnAllUsers() throws Exception {

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
        void putShouldReturnErrorWhenMalformedUrl() {
            given()
                 .body("{}")
                    .when()
                    .put(USERS_PATH+"/q")
                 .then()
                    .statusCode(HttpStatus.BAD_REQUEST_400);
        }


    }


}
