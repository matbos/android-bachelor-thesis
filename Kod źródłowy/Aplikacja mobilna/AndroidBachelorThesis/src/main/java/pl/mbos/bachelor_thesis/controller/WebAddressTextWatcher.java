package pl.mbos.bachelor_thesis.controller;

import android.content.res.Resources;
import android.os.AsyncTask;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import javax.inject.Inject;
import javax.net.ssl.HttpsURLConnection;

import pl.mbos.bachelor_thesis.BaseApplication;
import pl.mbos.bachelor_thesis.R;

/**
 * Created by Mateusz on 08.12.13.
 */
public class WebAddressTextWatcher implements TextWatcher {

    private EditText editText;
    private WebAddressListener listener;
    @Inject
    Resources res;

    public WebAddressTextWatcher(EditText editText, WebAddressListener listener) {
        BaseApplication.getBaseGraph().inject(this);
        this.editText = editText;
        this.listener = listener;
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {
        new DataGetTask(editText, s.toString()).execute();
    }

    private class DataGetTask extends AsyncTask<Void, Void, Boolean> {

        private final EditText editText;
        private final String uri;
        private final String checkPart;

        private DataGetTask(EditText editText, String uri) {
            this.editText = editText;
            this.uri = uri;
            checkPart = res.getString(R.string.webservice_check);
        }

        @Override
        protected Boolean doInBackground(Void... p) {
            try {
                URI uriCorrect = new URI(uri+checkPart);
                HttpClient client = new DefaultHttpClient();
                HttpPost post = new HttpPost(uriCorrect);
                HttpResponse response = client.execute(post);
                return response.getStatusLine().getStatusCode() == HttpsURLConnection.HTTP_OK;
            } catch (ClientProtocolException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (URISyntaxException e) {
                e.printStackTrace();
            } catch (IllegalStateException e) {
                e.printStackTrace();
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
            }
            return false;
        }

        @Override
        protected void onPostExecute(Boolean outcome) {
            if (outcome) {
                listener.webServiceAddressChanged(uri.toString());
                editText.setTextColor(res.getColor(R.color.grassAlwaysRegrows));
            } else {
                editText.setTextColor(res.getColor(R.color.alice));
            }
        }
    }
}
