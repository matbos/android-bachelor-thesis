package pl.mbos.bachelor_thesis.service.data.retrofit;

import retrofit.RestAdapter;

/**
 * Created by Mateusz on 01.12.13.
 */
public class RemoteServiceFactory {

    static RestAdapter restAdapter = new RestAdapter.Builder()
                .setServer("http://192.168.0.10:8080/Webservice")
                .build();


    public static DataRemoteService buildDataService() {
        return restAdapter.create(DataRemoteService.class);
    }

    public static AuthorizationRemoteService buildAuthorizationService() {
        return restAdapter.create(AuthorizationRemoteService.class);
    }
}
