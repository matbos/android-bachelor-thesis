package pl.mbos.bachelor_thesis.controller;

import android.content.res.Resources;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

import java.net.URI;
import java.net.URISyntaxException;

import javax.inject.Inject;

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
        try {
            URI uri = new URI(s.toString());
            listener.webServiceAddressChanged(s.toString());
            editText.setTextColor(res.getColor(R.color.grassAlwaysRegrows));
        } catch (URISyntaxException e) {
            editText.setTextColor(res.getColor(R.color.alice));
        }
    }
}
