package org.skan.specs;

import io.restassured.specification.RequestSpecification;

import static io.restassured.RestAssured.with;
import static io.restassured.http.ContentType.JSON;
import static org.skan.helpers.CustomApiListener.withCustomTemplates;

public class RegisterRequestSpecs {

    public static RequestSpecification registerRequestSpec = with()
            .filter(withCustomTemplates())
            .baseUri("https://reqres.in")
            .basePath("/api/register")
            .log().all()
            .contentType(JSON);
}
