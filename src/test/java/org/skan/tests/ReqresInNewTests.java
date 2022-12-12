package org.skan.tests;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.skan.models.RegisterBodyModel;
import org.skan.models.RegisterResponseModel;


import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;
import static org.skan.specs.RegisterRequestSpecs.registerRequestSpec;
import static org.skan.specs.RegisterResponseSpecs.registerResponseSpec;

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

        assertThat(response.getToken()).isEqualTo("QpwL5tke4Pnpja7X4");
        assertThat(response.getId()).isEqualTo("4");
    }
}
