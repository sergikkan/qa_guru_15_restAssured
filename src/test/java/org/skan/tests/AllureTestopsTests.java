package org.skan.tests;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Configuration;
import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Cookie;
import org.skan.api.AuthorizationApi;

import static com.codeborne.selenide.Selectors.byName;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;
import static com.codeborne.selenide.WebDriverRunner.getWebDriver;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.is;

public class AllureTestopsTests {
    public final static String
            USERNAME = "allure8",
            PASSWORD = "allure8",
            USER_TOKEN = "912d688f-f2cb-43c0-9f3a-80a7cae5e020",
            ALLURE_TESTOPS_SESSION = "ALLURE_TESTOPS_SESSION";

    @BeforeAll
    static void beforeAll(){
        Configuration.baseUrl = "https://allure.autotests.cloud";
        RestAssured.baseURI = "https://allure.autotests.cloud";
    }

    @Test
    void loginTest(){
        open("");
        $(byName("username")).setValue(USERNAME);
        $(byName("password")).setValue(PASSWORD).pressEnter();
        $("[aria-label='User menu']").click();
        $(".Menu__item_info").shouldHave(Condition.text(USERNAME));
    }

    @Test
    void loginApiSimpleTest(){

       String xsrfToken = given()
                    .formParam("grant_type", "apitoken")
                    .formParam("scope", "openid")
                    .formParam("token", "912d688f-f2cb-43c0-9f3a-80a7cae5e020")
                    .when()
                    .post("/api/uaa/oauth/token")
                    .then()
                    .statusCode(200)
                    .extract()
                    .path("jti");

       String authorizationCookie = given()
                    .header("X-XSRF-TOKEN", xsrfToken)
                    .header("Cookie", "XSRF-TOKEN=" + xsrfToken)
                    .formParam("username", USERNAME)
                    .formParam("password", PASSWORD)
                    .when()
                    .post("/api/login/system")
                    .then()
                    .statusCode(200).extract().response()
                    .getCookie(ALLURE_TESTOPS_SESSION);

       open("/favicon.ico");
       getWebDriver().manage().addCookie(new Cookie(ALLURE_TESTOPS_SESSION, authorizationCookie));
       open("");

       $("[aria-label='User menu']").click();
       $(".Menu__item_info").shouldHave(Condition.text(USERNAME));
    }

    @Test
    void loginApiTest(){
        String authorizationCookie = new AuthorizationApi().getAuthorizationCookie(USER_TOKEN, USERNAME, PASSWORD);
        open("/favicon.ico");
        getWebDriver().manage().addCookie(new Cookie(ALLURE_TESTOPS_SESSION, authorizationCookie));
        open("");
        $("[aria-label='User menu']").click();
        $(".Menu__item_info").shouldHave(Condition.text(USERNAME));
    }

    @Test
    void viewTestCaseWithApiTest(){
        String authorizationCookie = new AuthorizationApi().getAuthorizationCookie(USER_TOKEN, USERNAME, PASSWORD);

        given()
                .log().all()
                .cookie(ALLURE_TESTOPS_SESSION, authorizationCookie)
                .get("api/rs/testcase/13293/overview")
                .then()
                .log().all()
                .statusCode(200)
                .body("name", is("Добавление найденного товара в корзину"));
    }

    @Test
    void viewTestCaseWithUiTest(){
        String authorizationCookie = new AuthorizationApi().getAuthorizationCookie(USER_TOKEN, USERNAME, PASSWORD);

        open("/favicon.ico");
        getWebDriver().manage().addCookie(new Cookie(ALLURE_TESTOPS_SESSION, authorizationCookie));
        open("/project/1717/test-cases/13293");
        $(".TestCaseLayout__name").shouldHave(Condition.text("Добавление найденного товара в корзину"));
    }


}
