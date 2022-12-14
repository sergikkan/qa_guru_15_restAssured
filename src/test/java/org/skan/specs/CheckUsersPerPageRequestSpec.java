package org.skan.specs;

import io.restassured.specification.RequestSpecification;

import java.net.URI;

import static io.restassured.RestAssured.with;
import static io.restassured.http.ContentType.JSON;
import static org.skan.helpers.CustomApiListener.withCustomTemplates;

public class CheckUsersPerPageRequestSpec {

    public static RequestSpecification checkUsersPerPageRequestSpec = with()
            .filter(withCustomTemplates())
            .baseUri("https://reqres.in")
            .basePath("/api/users?page=1")
            .log().uri();
}
