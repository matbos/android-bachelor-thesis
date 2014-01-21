package pl.mbos.bachelor_thesis.service.data.remote;

import android.net.Uri;
import android.util.Log;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.SocketTimeoutException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Mateusz on 01.12.13.
 */

public class BaseService {

    public Response postJSONRequest(String request, String endpointAddress) {
        Response finalResponse = new Response();
        try {
            // create connection
            HttpClient client = new DefaultHttpClient();
            HttpPost post = new HttpPost(endpointAddress);
            post.addHeader("content-type", "application/json");
            StringEntity entity = new StringEntity(request, "UTF-8");
            post.setEntity(entity);
            HttpResponse response = client.execute(post);

            parseResponse(finalResponse, response);

        } catch (MalformedURLException e) {
            Log.e("DATA", e.getMessage());
            e.printStackTrace();
        } catch (SocketTimeoutException e) {
            Log.e("DATA", e.getMessage());
            e.printStackTrace();
        } catch (IOException e) {
            Log.e("DATA", e.getMessage());
            e.printStackTrace();
        }
        return finalResponse;
    }

    public Response getJSONRequest(HashMap<String, Object> parameters, String endpointAddress) {
        Response finalResponse = new Response();
        try {
            // create connection
            HttpClient client = new DefaultHttpClient();
            Uri.Builder builder = Uri.parse(endpointAddress).buildUpon();
            for (Map.Entry<String, Object> e : parameters.entrySet()) {
                builder.appendQueryParameter(e.getKey(), e.getValue().toString());
            }
            HttpGet get = new HttpGet(builder.toString());
            HttpResponse response = client.execute(get);

            parseResponse(finalResponse, response);

        } catch (MalformedURLException e) {
            Log.e("DATA", e.getMessage());
            e.printStackTrace();
        } catch (SocketTimeoutException e) {
            Log.e("DATA", e.getMessage());
            e.printStackTrace();
        } catch (IOException e) {
            Log.e("DATA", e.getMessage());
            e.printStackTrace();
        }
        return finalResponse;
    }

    public Response putJSONRequest(JSONObject request, String endpointAddress) {
        Response finalResponse = new Response();
        try {
            // create connection
            HttpClient client = new DefaultHttpClient();
            HttpPut put = new HttpPut(endpointAddress);
            put.addHeader("content-type", "application/json");
            String str = request.toString();
            StringEntity entity = new StringEntity(str, "UTF-8");
            put.setEntity(entity);
            HttpResponse response = client.execute(put);

            parseResponse(finalResponse, response);

        } catch (MalformedURLException e) {
            Log.e("DATA", e.getMessage());
            e.printStackTrace();
        } catch (SocketTimeoutException e) {
            Log.e("DATA", e.getMessage());
            e.printStackTrace();
        } catch (IOException e) {
            Log.e("DATA", e.getMessage());
            e.printStackTrace();
        }
        return finalResponse;
    }

    private String readTillEnd(InputStream is) throws IOException {
        InputStreamReader isr = new InputStreamReader(is);
        BufferedReader br = new BufferedReader(isr);
        StringBuilder builder = new StringBuilder();
        String line;
        while ((line = br.readLine()) != null) {
            builder.append(line);
        }
        return builder.toString();
    }

    private void parseResponse(Response finalResponse, HttpResponse response) throws IOException {
        finalResponse.setCode(response.getStatusLine().getStatusCode());
        handleErrors(finalResponse, response.getEntity());
    }

    private void handleErrors(Response finalResponse, HttpEntity response) throws IOException {
        String body;
        if (finalResponse.getCode() == HttpURLConnection.HTTP_NOT_FOUND) {
            body = "Server could not be found";
        } else {
            InputStream is = response.getContent();
            body = is.toString();
        }
        finalResponse.setBody(body);
    }


}
