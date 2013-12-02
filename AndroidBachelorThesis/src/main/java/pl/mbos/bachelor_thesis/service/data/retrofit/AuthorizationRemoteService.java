package pl.mbos.bachelor_thesis.service.data.retrofit;

import org.json.JSONObject;

import retrofit.client.Response;
import retrofit.http.Body;
import retrofit.http.POST;

/**
 * Created by Mateusz on 01.12.13.
 */
public interface AuthorizationRemoteService {

    @POST("/login")
    Response authorizeUser(@Body String body);

}
