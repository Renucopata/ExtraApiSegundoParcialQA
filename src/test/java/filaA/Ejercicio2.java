package filaA;


import config.Configuration;
import factoryRequest.FactoryRequest;
import factoryRequest.RequestInfo;
import io.restassured.response.Response;
import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Base64;
import java.util.Date;

import static org.hamcrest.Matchers.equalTo;

public class Ejercicio2 {

    RequestInfo requestInfo = new RequestInfo();
    Response response;
    String auth;
    String[] projectsContent = {
            "Project Renuco 1 " + new Date().getTime(),
            "Project Renuco 2 " + new Date().getTime(),
            "Project Renuco 3 " + new Date().getTime(),
            "Project Renuco 4 " + new Date().getTime()

    };

    @BeforeEach
    public void setup() {
        auth = Base64.getEncoder().encodeToString((Configuration.user+":"+ Configuration.password).getBytes());
    }

    @Test
    public void verifyMultipleProjectsTest() {
        for(int i = 0; i<4; i++) {
            requestInfo.setUrl(Configuration.host + "/api/projects.json").setBody("{\"Content\":\""+projectsContent[i]+"\"}").setHeaders("Authorization", "Basic " + auth);
            response = FactoryRequest.make("post").send(requestInfo);
            response.then().log().all().statusCode(200)
                    .body("Content", equalTo(projectsContent[i]));
        }

        requestInfo.setUrl(Configuration.host + "/api/projects.json").setHeaders("Authorization", "Basic " + auth);
        response = FactoryRequest.make("get").send(requestInfo);
        response.then().log().all().statusCode(200);

        JSONArray jsonArray = new JSONArray(response.body().print());
        for(int i = 0; i<jsonArray.length(); i++) {
            JSONObject jsonObject = jsonArray.getJSONObject(i);
            int id = jsonObject.getInt("Id");
            requestInfo.setUrl(Configuration.host + "/api/projects/"+id+".json").setHeaders("Authorization", "Basic " + auth);
            response = FactoryRequest.make("delete").send(requestInfo);
            response.then().log().all().statusCode(200)
                    .body("Id", equalTo(id))
                    .body("Deleted", equalTo(true));
        }

    }


}