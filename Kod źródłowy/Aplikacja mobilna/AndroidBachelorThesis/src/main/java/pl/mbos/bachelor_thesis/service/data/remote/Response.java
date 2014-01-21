package pl.mbos.bachelor_thesis.service.data.remote;

import org.json.JSONObject;

/**
 * Created by Mateusz on 01.12.13.
 */
public class Response {

    private int code;
    private String body;
    private JSONObject jsonBody;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public JSONObject getJsonBody() {
        return jsonBody;
    }

    public void setJsonBody(JSONObject jsonBody) {
        this.jsonBody = jsonBody;
    }

}
