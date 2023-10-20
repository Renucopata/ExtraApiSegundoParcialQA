package factoryRequest2;

import config2.Configuration2;
import factoryRequest2.IRequest;
import factoryRequest2.RequestInfo;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class RequestGET implements IRequest {
    @Override
    public Response send(RequestInfo requestInfo) {
        Response response = given()
                .auth()
                .preemptive()
                .basic(Configuration2.user, Configuration2.password)
                .log()
                .all()
                .when()
                .get(requestInfo.getUrl());
        response.then().log().all();
        return response;
    }
}
