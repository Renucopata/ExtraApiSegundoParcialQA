package factory;

import config.Configuration;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import static io.restassured.RestAssured.given;

public class RequestPUT implements IRequest {
    @Override
    public Response send(RequestInfo requestInfo) {
        RequestSpecification req = given()
                .headers(requestInfo.getHeaders());

        if (requestInfo.isBasicAuthNeeded()){
            req.auth()
                    .preemptive()
                    .basic(Configuration.user, Configuration.password);
        }

        Response response = req
                .body(requestInfo.getBody())
                .log()
                .all().
                when()
                .put(requestInfo.getUrl());
        response.then().log().all();
        return response;
    }
}