package org.skan.tests;

import io.qameta.allure.restassured.AllureRestAssured;
import org.junit.jupiter.api.Test;
import org.skan.models.lombok.RegisterBodyLombokModel;
import org.skan.models.lombok.RegisterResponseLombokModel;
import org.skan.models.pojo.RegisterBodyPojoModel;
import org.skan.models.pojo.RegisterResponsePojoModel;

import static io.restassured.RestAssured.given;
import static io.restassured.http.ContentType.JSON;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.is;
import static org.skan.helpers.CustomApiListener.withCustomTemplates;
import static org.skan.specs.RegisterSpecs.registerRequestSpec;
import static org.skan.specs.RegisterSpecs.registerResponseSpec;

public class ReqresInTestsExtended {

    @Test
    void registerTest() {
        //Bad pracrice, move body to model
        String data = "{ \"email\": \"eve.holt@reqres.in\", \"password\": \"pistol\" }";

        given()
                .log().uri()
                .contentType(JSON)
                .body(data)
                .when()
                .post("https://reqres.in/api/register")
                .then()
                .log().status()
                .log().body()
                .statusCode(200)
                .body("token", is("QpwL5tke4Pnpja7X4"))
                .body("id", is(4));
    }

    @Test
    void registerWithPojoModelTest() {
        RegisterBodyPojoModel body = new RegisterBodyPojoModel();
        body.setEmail("eve.holt@reqres.in");
        body.setPassword("pistol");

        RegisterResponsePojoModel response = given()
                .log().all()
                .contentType(JSON)
                .body(body)
                .when()
                .post("https://reqres.in/api/register")
                .then()
                .log().status()
                .log().body()
                .statusCode(200)
                .extract().as(RegisterResponsePojoModel.class);

        assertThat(response.getToken()).isEqualTo("QpwL5tke4Pnpja7X4");
        assertThat(response.getId()).isEqualTo("4");
    }

    @Test
    void registerWithLombokModelTest() {
        RegisterBodyLombokModel body = new RegisterBodyLombokModel();
        body.setEmail("eve.holt@reqres.in");
        body.setPassword("pistol");

        RegisterResponseLombokModel response = given()
                .log().all()
                .contentType(JSON)
                .body(body)
                .when()
                .post("https://reqres.in/api/register")
                .then()
                .log().status()
                .log().body()
                .statusCode(200)
                .extract().as(RegisterResponseLombokModel.class);

        assertThat(response.getToken()).isEqualTo("QpwL5tke4Pnpja7X4");
        assertThat(response.getId()).isEqualTo("4");
    }

    @Test
    void registerWithAllureListenerTest() {
        RegisterBodyLombokModel body = new RegisterBodyLombokModel();
        body.setEmail("eve.holt@reqres.in");
        body.setPassword("pistol");

        RegisterResponseLombokModel response = given()
                .filter(new AllureRestAssured())
                .log().all()
                .contentType(JSON)
                .body(body)
                .when()
                .post("https://reqres.in/api/register")
                .then()
                .log().status()
                .log().body()
                .statusCode(200)
                .extract().as(RegisterResponseLombokModel.class);

        assertThat(response.getToken()).isEqualTo("QpwL5tke4Pnpja7X4");
        assertThat(response.getId()).isEqualTo("4");
    }

    @Test
    void registerWithCustomAllureListenerTest() {
        RegisterBodyLombokModel body = new RegisterBodyLombokModel();
        body.setEmail("eve.holt@reqres.in");
        body.setPassword("pistol");

        RegisterResponseLombokModel response = given()
                .filter(withCustomTemplates())
                .log().all()
                .contentType(JSON)
                .body(body)
                .when()
                .post("https://reqres.in/api/register")
                .then()
                .log().status()
                .log().body()
                .statusCode(200)
                .extract().as(RegisterResponseLombokModel.class);

        assertThat(response.getToken()).isEqualTo("QpwL5tke4Pnpja7X4");
        assertThat(response.getId()).isEqualTo("4");
    }

    @Test
    void registerWithSpecsTest() {
        RegisterBodyLombokModel body = new RegisterBodyLombokModel();
        body.setEmail("eve.holt@reqres.in");
        body.setPassword("pistol");

        RegisterResponseLombokModel response = given()
                .spec(registerRequestSpec)
                .body(body)
                .when()
                .post()
                .then()
                .spec(registerResponseSpec)
                .extract().as(RegisterResponseLombokModel.class);

        assertThat(response.getToken()).isEqualTo("QpwL5tke4Pnpja7X4");
        assertThat(response.getId()).isEqualTo("4");
    }



}
