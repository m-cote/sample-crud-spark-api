package app.controller;

import app.Application;
import app.config.WebConfig;
import app.model.User;
import io.restassured.RestAssured.*;
import io.restassured.http.ContentType;
import io.restassured.matcher.RestAssuredMatchers.*;
import org.eclipse.jetty.http.HttpStatus;
import org.hamcrest.Matchers.*;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.List;

import static app.dao.UserTestData.*;
import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.when;

//@Sql(scripts = "classpath:db/populateDb.sql", config = @SqlConfig(encoding = "UTF-8"))
public class UsersControllerTest {

    private static final String USERS_PATH = "/api/users";

    @BeforeAll
    static void init() {

        AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext(Application.class);
        new WebConfig(ctx.getBean(UsersController.class));
        ctx.registerShutdownHook();

    }

    @Nested
    class NormalBehaviour {

/*
        @BeforeEach
        void setUp() {

            UsersDAO usersDAO = new MockUsersDAOImpl();
            usersDAO.save(UserAnna);
            usersDAO.save(UserDashawn);

        }
*/



        @Test
        void getShouldReturnUsers() {

            List<User> users =
                    when()
                            .get(USERS_PATH)
                            .then()
                            .statusCode(HttpStatus.OK_200)
                            .contentType(ContentType.JSON)
                            .extract()
                            .body()
                            .jsonPath()
                            .getList(".");

//            org.hamcrest.Matchers.

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
