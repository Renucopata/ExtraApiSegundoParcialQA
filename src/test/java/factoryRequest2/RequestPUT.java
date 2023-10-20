package factoryRequest2;

import config.Configuration;
import factoryRequest2.IRequest;
import factoryRequest2.RequestInfo;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class RequestPUT implements IRequest {
    @Override
    public Response send(RequestInfo requestInfo) {
        Response response = given()
                .auth()
                .preemptive()
                .basic(Configuration.user, Configuration.password)
                .body(requestInfo.getBody())
                .log()
                .all()
                .when()
                .put(requestInfo.getUrl());
        response.then().log().all();
        return response;
    }
}
