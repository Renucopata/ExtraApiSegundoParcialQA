package factory;

import java.util.HashMap;
import java.util.Map;

public class RequestInfo {

    private boolean basicAuthNeeded = false;
    private String url;
    private String body;
    private Map<String,String> headers = new HashMap<>();
    private Map<String,String> params =  new HashMap<>();

    public boolean isBasicAuthNeeded() {
        return basicAuthNeeded;
    }

    public RequestInfo setBasicAuthNeeded(boolean basicAuthNeeded) {
        this.basicAuthNeeded = basicAuthNeeded;
        return this;
    }

    public String getUrl() {
        return url;
    }

    public RequestInfo setUrl(String url) {
        this.url = url;
        return this;
    }

    public String getBody() {
        return body;
    }

    public RequestInfo setBody(String body) {
        this.body = body;
        return this;
    }

    public Map<String, String> getHeaders() {
        return headers;
    }

    public RequestInfo addHeader(String key, String value) {
        this.headers.put(key, value);
        return this;
    }

    public Map<String, String> getParams() {
        return params;
    }

    public RequestInfo addParam(String key, String value) {
        this.params.put(key, value);
        return this;
    }
}
