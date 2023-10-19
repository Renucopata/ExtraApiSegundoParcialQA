package filaA;


import config.Configuration;
import factoryRequest.FactoryRequest;
import factoryRequest.RequestInfo;
import io.restassured.response.Response;
import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.Base64;
import java.util.Date;

import static org.hamcrest.Matchers.equalTo;

public class Ejercicio1 {

    RequestInfo requestInfo = new RequestInfo();
    Response response;

    String auth;
    JSONObject projectBody = new JSONObject();
    JSONObject userBody = new JSONObject();

    @BeforeEach
    public void setup() {

        String email = "extraRenuco" + new Date().getTime() + "@gmail.com";
        userBody.put("FullName", "Renuco");
        userBody.put("Email", email);
        userBody.put("Password", "12345");
        projectBody.put("Content", "Renuco ProjectExtra" + new Date().getTime());

    }


    @Test
    public void verifyUserProjectTest() {
        requestInfo.setUrl(Configuration.host + "/api/user.json").setBody(userBody.toString());
        response = FactoryRequest.make("post").send(requestInfo);
        response.then().log().all().statusCode(200)
                .body("Email", equalTo(userBody.get("Email")))
                .body("FullName", equalTo(userBody.get("FullName")));

        auth = Base64.getEncoder().encodeToString((userBody.get("Email")+":"+userBody.get("Password")).getBytes());

        requestInfo.setUrl(Configuration.host + "/api/projects.json").setBody(projectBody.toString()).setHeaders("Authorization", "Basic " + auth);
        response = FactoryRequest.make("post").send(requestInfo);
        response.then().log().all().statusCode(200)
                .body("Content", equalTo(projectBody.get("Content")));



        requestInfo.setUrl(Configuration.host + "api/user/0.json").setHeaders("Authorization", "Basic " + auth);
        response = FactoryRequest.make("delete").send(requestInfo);
        response.then().log().all().statusCode(200)
                .body("Email", equalTo(userBody.get("Email")))
                .body("FullName", equalTo(userBody.get("FullName")));



        requestInfo.setUrl(Configuration.host + "/api/projects.json").setBody(projectBody.toString()).setHeaders("Authorization", "Basic " + auth);
        response = FactoryRequest.make("post").send(requestInfo);
        response.then().log().all().statusCode(200)
                .body("ErrorMessage", equalTo("Account doesn't exist"));


    }



}
