package factoryRequest2;

import factoryRequest2.RequestInfo;
import io.restassured.response.Response;

public interface IRequest {

    Response send (RequestInfo requestInfo);
}
