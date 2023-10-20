package filaA;


import config2.Configuration2;
import factoryRequest2.FactoryRequest;
import factoryRequest2.RequestInfo;
import io.restassured.response.Response;
import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

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
        auth = Base64.getEncoder().encodeToString((Configuration2.user+":"+ Configuration2.password).getBytes());
    }

    @Test
    public void verifyProjectsTest() {
        for(int i = 0; i<4; i++) {
            requestInfo.setUrl(Configuration2.host + "/api/projects.json").setBody("{\"Content\":\""+projectsContent[i]+"\"}").setHeaders("Authorization", "Basic " + auth);
            response = FactoryRequest.make("post").send(requestInfo);
            response.then().log().all().statusCode(200)
                    .body("Content", equalTo(projectsContent[i]));
        }

        requestInfo.setUrl(Configuration2.host + "/api/projects.json").setHeaders("Authorization", "Basic " + auth);
        response = FactoryRequest.make("get").send(requestInfo);
        response.then().log().all().statusCode(200);

        JSONArray jsonArray = new JSONArray(response.body().print());
        for(int i = 0; i<jsonArray.length(); i++) {
            JSONObject jsonObject = jsonArray.getJSONObject(i);
            int id = jsonObject.getInt("Id");
            requestInfo.setUrl(Configuration2.host + "/api/projects/"+id+".json").setHeaders("Authorization", "Basic " + auth);
            response = FactoryRequest.make("delete").send(requestInfo);
            response.then().log().all().statusCode(200)
                    .body("Id", equalTo(id))
                    .body("Deleted", equalTo(true));
        }

    }


}
