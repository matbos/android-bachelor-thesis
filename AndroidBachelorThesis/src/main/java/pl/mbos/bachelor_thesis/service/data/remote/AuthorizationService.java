package pl.mbos.bachelor_thesis.service.data.remote;

import android.content.Context;
import android.content.res.Resources;
import android.os.AsyncTask;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import javax.inject.Inject;

import pl.mbos.bachelor_thesis.BaseApplication;
import pl.mbos.bachelor_thesis.R;
import pl.mbos.bachelor_thesis.dao.User;
import pl.mbos.bachelor_thesis.service.data.services.IAuthorizationService;
import pl.mbos.bachelor_thesis.service.data.services.IAuthorizationServiceParent;

/**
 * Created by Mateusz on 01.12.13.
 */
public class AuthorizationService implements IAuthorizationService {

    @Inject
    BaseService baseService;
    @Inject
    Context ctx;
    private Resources res;
    private IAuthorizationServiceParent parent;

    private final String endpointAddress;

    public AuthorizationService(IAuthorizationServiceParent parent) {
        BaseApplication.getBaseGraph().inject(this);
        this.parent = parent;
        res = ctx.getResources();
        endpointAddress = res.getString(R.string.webservice_base) + res.getString(R.string.webservice_login);
    }

    @Override
    public void authorizeUser(User user) {
        new LoginTask(user).execute();
        String down = "";
    }

    class LoginTask extends AsyncTask<Void, Void, Response> {
        private User user;

        public LoginTask(User user) {
            this.user = user;
        }

        @Override
        protected Response doInBackground(Void... params) {
            String jsonRequest = "{ " +
                    " \"id\": \"" + user.getId() + "\"," +
                    " \"password\":\"" + user.getFirstName() + "\" " +
                    " }";
            return baseService.postJSONRequest(jsonRequest, endpointAddress);
        }

        private HttpURLConnection initiateConnection() throws MalformedURLException, IOException {
            URL urlToRequest = new URL("");
            HttpURLConnection urlConnection = (HttpURLConnection) urlToRequest.openConnection();
            urlConnection.setRequestMethod("POST");
            urlConnection.setDoOutput(false);
            return urlConnection;
        }

        @Override
        protected void onPostExecute(Response response) {
            String reason = response.getBody();
            boolean outcome = (response.getCode() == java.net.HttpURLConnection.HTTP_OK);
            parent.authorizationOutcome(user, outcome, reason);
        }
    }


}
