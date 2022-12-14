package org.skan.specs;

import io.restassured.specification.RequestSpecification;

import static io.restassured.RestAssured.with;
import static io.restassured.http.ContentType.JSON;
import static org.skan.helpers.CustomApiListener.withCustomTemplates;

public class UserNotFoundRequestSpec {

    public static RequestSpecification userNotFoundRequestSpec = with()
            .filter(withCustomTemplates())
            .baseUri("https://reqres.in")
            .basePath("api/users/23")
            .log().uri();

}
