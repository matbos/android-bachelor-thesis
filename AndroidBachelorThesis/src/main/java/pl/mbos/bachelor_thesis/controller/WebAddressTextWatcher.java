package pl.mbos.bachelor_thesis.controller;

import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

/**
 * Created by Mateusz on 08.12.13.
 */
public class WebAddressTextWatcher implements TextWatcher {

    private EditText editText;

    public WebAddressTextWatcher(EditText editText){
        this.editText = editText;
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {

    }
}
