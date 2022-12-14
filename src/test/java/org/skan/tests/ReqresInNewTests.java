package org.skan.tests;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.skan.models.CheckUsersPerPageResponseModel;
import org.skan.models.RegisterBodyModel;
import org.skan.models.RegisterResponseModel;


import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;
import static org.skan.specs.CheckUsersPerPageRequestSpec.checkUsersPerPageRequestSpec;
import static org.skan.specs.CheckUsersPerPageResponseSpec.checkUsersPerPageResponseSpec;
import static org.skan.specs.RegisterRequestSpecs.registerRequestSpec;
import static org.skan.specs.RegisterResponseSpecs.*;
import static org.skan.specs.UserNotFoundRequestSpec.userNotFoundRequestSpec;
import static org.skan.specs.UserNotFoundResponseSpec.userNotFoundResponseSpec;

public class ReqresInNewTests {

    @DisplayName("Успешная регистрация")
    @Test
    void registerWithSpecsTest() {
        RegisterBodyModel body = new RegisterBodyModel();
        body.setEmail("eve.holt@reqres.in");
        body.setPassword("pistol");

        RegisterResponseModel response = given()
                .spec(registerRequestSpec)
                .body(body)
                .when()
                .post()
                .then()
                .spec(registerResponseSpec)
                .extract().as(RegisterResponseModel.class);

        assertThat(response.getToken()).isNotNull();
        assertThat(response.getId()).isNotNull();
    }

    @DisplayName("Вводим пустой пароль при регистрации")
    @Test
    void registerWithoutPassTest() {
        RegisterBodyModel body = new RegisterBodyModel();
        body.setEmail("eve.holt@reqres.in");

        RegisterResponseModel response = given()
                .spec(registerRequestSpec)
                .body(body)
                .when()
                .post()
                .then()
                .spec(registerWithoutPassResponseSpec)
                .extract().as(RegisterResponseModel.class);

        assertThat(response.getError()).isEqualTo("Missing password");
    }

    @DisplayName("Вводим пустой email при регистрации")
    @Test
    void registerWithoutEmailTest() {
        RegisterBodyModel body = new RegisterBodyModel();
        body.setPassword("pistol");

        RegisterResponseModel response = given()
                .spec(registerRequestSpec)
                .body(body)
                .when()
                .post()
                .then()
                .spec(registerWithoutEmailResponseSpec)
                .extract().as(RegisterResponseModel.class);

        assertThat(response.getError()).isEqualTo("Missing email or username");
    }

    @DisplayName("Количество пользователей на странице")
    @Test
    void CheckUsersPerPage() {

        CheckUsersPerPageResponseModel response = given()
                .spec(checkUsersPerPageRequestSpec)
                .when()
                .get()
                .then()
                .spec(checkUsersPerPageResponseSpec)
                .extract().as(CheckUsersPerPageResponseModel.class);

        assertThat(response.getPer_page()).isEqualTo(6);
    }

    @DisplayName("Пользователь не найден")
    @Test
    void userNotFound() {

        given()
                .spec(userNotFoundRequestSpec)
                .when()
                .get()
                .then()
                .spec(userNotFoundResponseSpec);
    }
}
