package filaB;

import config2.Configuration2;
import factoryRequest2.FactoryRequest;
import factoryRequest2.RequestInfo;
import io.restassured.response.Response;
import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


import java.util.Base64;
import java.util.Date;

import static org.hamcrest.Matchers.equalTo;

public class Ejercicio2B {

    RequestInfo requestInfo = new RequestInfo();
    Response response;

    Response allItems;
    JSONObject body = new JSONObject();
    String auth;

    @BeforeEach
    public void setup() {
        auth = Base64.getEncoder().encodeToString((Configuration2.user+":"+Configuration2.user).getBytes());
    }

    @Test
    public void verifyPregunta2() {
        //Verify Create Item

        for(int i=0;i<4;i++){
            body.clear();
            body.put("Content", "ItemExtra"+new Date().getTime());
            requestInfo.setUrl(Configuration2.host+"/api/items.json").setBody(body.toString()).setHeaders("Authorization", "Basic " + auth);
            response = FactoryRequest.make("post").send(requestInfo);
            response.then()
                    .log().all()
                    .statusCode(200)
                    .body("Content", equalTo(body.get("Content")));
        }

        //Get all items

        requestInfo.setUrl(Configuration2.host+"/api/items.json").setHeaders("Authentication","Basic "+auth);
        allItems = FactoryRequest.make("get").send(requestInfo);
        allItems.then()
                .log().all()
                .statusCode(200);


        //Delete all items
        for(int i=0;i<4;i++){
            requestInfo.setUrl(Configuration2.host+"/api/items/"+allItems.then().extract().path("Id["+i+"]")+".json").setHeaders("Authentication","Basic "+auth);
            response = FactoryRequest.make("delete").send(requestInfo);
            response.then()
                    .log().all()
                    .statusCode(200)
                    .body("Id", equalTo(allItems.then().extract().path("Id["+i+"]")));
        }


    }
}
