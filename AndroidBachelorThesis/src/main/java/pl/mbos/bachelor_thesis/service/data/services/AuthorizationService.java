package pl.mbos.bachelor_thesis.service.data.services;

import android.os.AsyncTask;
import android.util.Log;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.SocketTimeoutException;
import java.net.URI;
import java.net.URL;
import java.net.URLConnection;
import java.util.Scanner;

import pl.mbos.bachelor_thesis.dao.User;

/**
 * Service that actually performs user authentication
 */
public class AuthorizationService implements IAuthorizationService {

    private IAuthorizationServiceParent parent;
    private String reason;

    public AuthorizationService(IAuthorizationServiceParent parent) {
        this.parent = parent;
    }

    @Override
    public void authorizeUser(User user) {
        new LoginTask(user).execute();
    }

    public static boolean requestWebService(String serviceUrl) {
        return false;
    }

    private String getResponseText(HttpURLConnection conn) throws IOException {
        InputStream is = conn.getInputStream();
        String body = readTillEnd(is);
        return body;
    }

    private String readTillEnd(InputStream is) throws IOException {
        InputStreamReader isr = new InputStreamReader(is);
        BufferedReader br = new BufferedReader(isr);
        StringBuilder builder = new StringBuilder();
        String line;
        while( (line = br.readLine()) != null){
            builder.append(line);
        }
        return builder.toString();
    }

    class LoginTask extends AsyncTask<Void, Void, Boolean> {
        private User user;

        public LoginTask(User user) {
            this.user = user;
        }

        @Override
        protected Boolean doInBackground(Void... params) {
            HttpURLConnection urlConnection = null;
            try {

                String jsonRequest = "{ " +
                        " \"id\": \"" + user.getId() + "\"," +
                        " \"password\":\"" + user.getFirstName() + "\" " +
                        " }";

                // create connection
                HttpClient client = new DefaultHttpClient();
                HttpPost post = new HttpPost("http://192.168.0.10:8080/Webservice/login");
                post.addHeader("content-type", "application/json");
                StringEntity entity = new StringEntity(jsonRequest, "UTF-8");
                post.setEntity(entity);
                HttpResponse response = client.execute(post);

                int code = response.getStatusLine().getStatusCode();
                HttpEntity responseEntity = response.getEntity();

               // urlConnection = initiateConnection();
               // urlConnection.connect();

               // byte[] data = jsonRequest.getBytes("UTF-8");
              //  OutputStream os = urlConnection.getOutputStream();
              //  os.write(data);
              //  os.close();

                // handle issues
               // int code = urlConnection.getResponseCode();
                if (code != java.net.HttpURLConnection.HTTP_OK) {
//                    InputStream is = urlConnection.getInputStream();
//                    reason = getResponseText(urlConnection);
                    InputStream is = responseEntity.getContent();
                    reason = readTillEnd(is);
                    return false;
                } else {
                    return true;
                }
            } catch (MalformedURLException e) {
                // URL is invalid
                Log.e("DATA", e.getMessage());
                e.printStackTrace();
            } catch (SocketTimeoutException e) {
                // data retrieval or connection timed out
                Log.e("DATA", e.getMessage());
                e.printStackTrace();
            } catch (IOException e) {
                Log.e("DATA", e.getMessage());
                e.printStackTrace();
                // could not read response body
                // (could not create input stream)
            } finally {
                if (urlConnection != null) {
                    urlConnection.disconnect();
                }
            }
            return false;
        }

        private HttpURLConnection initiateConnection() throws MalformedURLException, IOException {
            URL urlToRequest = new URL("http://192.168.0.10:8080/Webservice/login");
            HttpURLConnection urlConnection = (HttpURLConnection) urlToRequest.openConnection();
            urlConnection.setRequestMethod("POST");
            urlConnection.setDoOutput(false);
           // urlConnection.setRequestProperty("Content-Type", "application/json");
           // urlConnection.setRequestProperty("Accept", "application/json");
           // urlConnection.setRequestProperty("Content-Language", "pl-PL");
            return urlConnection;
        }

        @Override
        protected void onPostExecute(Boolean outcome) {
            parent.authorizationOutcome(user, outcome, reason);
        }
    }

}
