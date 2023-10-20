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
    String email;
    String projectName;

    @BeforeEach
    public void setup() {

         email = "extraRenuco" + new Date().getTime() + "@gmail.com";
         projectName = "Renuco ProjectExtra" + new Date().getTime();
        userBody.put("FullName", "Renuco");
        userBody.put("Email", email);
        userBody.put("Password", "12345");
        projectBody.put("Content", projectName);

    }


    @Test
    public void verifyUserProjectTest() {

        //Create user
        requestInfo.setUrl(Configuration.host + "/api/user.json").setBody(userBody.toString());
        response = FactoryRequest.make("post").send(requestInfo);
        response.then().log().all().statusCode(200)
                .body("Email", equalTo(userBody.get("Email")))
                .body("FullName", equalTo(userBody.get("FullName")));

        auth = Base64.getEncoder().encodeToString((userBody.get("Email")+":"+userBody.get("Password")).getBytes());

        int idUser = response.then().extract().path("Id");

        System.out.println("pasooo el id: " + idUser);

        //Create project
        requestInfo.setUrl(Configuration.host + "/api/projects.json").setBody(projectBody.toString()).setHeaders("Authorization", "Basic " + auth);
        response = FactoryRequest.make("post").send(requestInfo);
        response.then().log().all().statusCode(200)
                .body("Content", equalTo(projectBody.get("Content")));



        requestInfo.setUrl(Configuration.host + "/api/user/"+idUser+".json").setHeaders("Authorization", "Basic " + auth);
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
