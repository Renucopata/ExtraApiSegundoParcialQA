package factoryRequest2;

import factoryRequest2.IRequest;
import factoryRequest2.RequestDELETE;
import factoryRequest2.RequestGET;
import factoryRequest2.RequestPOST;
import factoryRequest2.RequestPUT;

import java.util.HashMap;
import java.util.Map;

public class FactoryRequest {

    public static factoryRequest2.IRequest make(String type){
        Map<String, IRequest> requestMap = new HashMap<>();
        requestMap.put("put",new RequestPUT());
        requestMap.put("post",new RequestPOST());
        requestMap.put("get",new RequestGET());
        requestMap.put("delete",new RequestDELETE());

        return requestMap.containsKey(type.toLowerCase())?
                requestMap.get(type.toLowerCase()):
                requestMap.get("get");
    }
}
