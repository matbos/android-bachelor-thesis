package pl.mbos.bachelor_thesis.service.data.remote;

import pl.mbos.bachelor_thesis.dao.User;
import retrofit.http.Body;
import retrofit.http.POST;

/**
 * Created by Mateusz on 12.11.13.
 */
public interface AuthorizationService {

    @POST("/login")
    boolean authorizeUser(@Body User user);

}
