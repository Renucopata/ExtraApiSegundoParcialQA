package filaA;


import factoryRequest.FactoryRequest;
import config.Configuration;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;

import java.util.Date;
import java.util.Random;


import static org.hamcrest.Matchers.equalTo;


public class Ejercicio1 extends ApiBaseTest {


    @Test
    public void testEjercicio1() {
        createUser();
        createProject();
        deleteUser();
        createProjectWithoutAuth();
    }

    private void createUser() {
        String randomEmail = "renucoExtra" + new Date().getTime() + "@gmail.com";
        String randomPassword = "12345";

        Configuration.user = randomEmail;
        Configuration.password = randomPassword;

        JSONObject body = new JSONObject();
        body.put("Email", randomEmail);
        body.put("Password", randomPassword);
        body.put("FullName", "RenucoExtra");

        requestInfo.setUrl(Configuration.host + "/api/user.json")
                .setBody(body.toString());

        response = FactoryRequest.make(post).send(requestInfo);

        response.then().statusCode(200)
                .body("Email", equalTo(body.get("Email")))
                .body("FullName", equalTo(body.get("FullName")));
    }

    private void createProject() {
        String randomContent = "Project " + new Date().getTime();

        JSONObject body = new JSONObject();
        body.put("Content", randomContent);

        requestInfo.setUrl(Configuration.host + "/api/projects.json")
                .setBody(body.toString())
                .setBasicAuthNeeded(true);

        response = FactoryRequest.make(post).send(requestInfo);
        response.then().statusCode(200).
                body("Content", equalTo(body.get("Content")));
    }

    private void deleteUser() {
        requestInfo.setUrl(Configuration.host + "/api/user/0.json");
        response = FactoryRequest.make(delete).send(requestInfo);
        response.then()
                .statusCode(200)
                .body("Email", equalTo(Configuration.user));
    }

    private void createProjectWithoutAuth() {
        String randomContent = "Project " + new Date().getTime();

        JSONObject body = new JSONObject();
        body.put("Content", randomContent);

        requestInfo.setUrl(Configuration.host + "/api/projects.json")
                .setBody(body.toString());
        response = FactoryRequest.make(post).send(requestInfo);
        response.then().statusCode(200).
                body("ErrorMessage", equalTo("Account doesn't exist"))
                .body("ErrorCode", equalTo(105));
    }
}