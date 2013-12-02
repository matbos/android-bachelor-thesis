package pl.mbos.bachelor_thesis.service.data.retrofit;

import org.json.JSONObject;

import retrofit.client.Response;
import retrofit.http.Body;
import retrofit.http.GET;
import retrofit.http.PUT;

/**
 * Created by Mateusz on 01.12.13.
 */
public interface DataRemoteService {

    @GET("/data/last")
    Response getLastChanges();

    @GET("/data/token")
    Response getToken();

    @PUT("/data")
    Response sendData(@Body JSONObject data);
}
