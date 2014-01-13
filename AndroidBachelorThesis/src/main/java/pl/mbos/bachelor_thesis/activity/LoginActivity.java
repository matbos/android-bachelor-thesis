package pl.mbos.bachelor_thesis.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;

import butterknife.InjectView;
import butterknife.OnClick;
import butterknife.Views;
import pl.mbos.bachelor_thesis.R;
import pl.mbos.bachelor_thesis.controller.LoginController;
import pl.mbos.bachelor_thesis.controller.WebAddressTextWatcher;
import pl.mbos.bachelor_thesis.custom.AwesomeText;
import pl.mbos.bachelor_thesis.dao.User;
import pl.mbos.bachelor_thesis.font.Awesome;
import pl.mbos.bachelor_thesis.view.LoginView;

/**
 * Created with IntelliJ IDEA.
 * User: Mateusz Boś
 * Date: 15.09.13
 * Time: 01:14
 */
public class LoginActivity extends SlidingMenuActivity implements LoginView {

    @InjectView(R.id.et_login)
    EditText et_login;
    @InjectView(R.id.et_pass)
    EditText et_password;
    @InjectView(R.id.pb_progressBar)
    ProgressBar bar;
    @InjectView(R.id.at_reason)
    AwesomeText at_reason;
    @InjectView(R.id.et_webAddress)
    EditText et_webserviceAddress;

    LoginController controller;

    /**
     * Method used to build an intent that can start this activity
     *
     * @param parent calling activity
     * @return An {@link Intent}
     */
    public static Intent getIntentToStartThisActivity(Activity parent) {
        Intent intent = new Intent(parent, LoginActivity.class);
        intent.putExtra("clearData", false);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        disableSlidingMenu();
        setContentView(R.layout.activity_login);
        Views.inject(this);
        controller = new LoginController(this);
        et_webserviceAddress.addTextChangedListener(new WebAddressTextWatcher(et_webserviceAddress,controller));
        Typeface font = Typeface.createFromAsset(getAssets(), "fontawesome-webfont.ttf");
        at_reason.setTypeface(font);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        boolean outcome = super.onCreateOptionsMenu(menu);
        if(outcome){
            hideLogoutButton();
        }
        return outcome;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @OnClick(R.id.btn_login)
    public void loginButtonClicked() {
        Long id = -100L;
        String pass = "";
        hideError();
        try {
            id = Long.parseLong(et_login.getText().toString());
        } catch (NumberFormatException ex) {
            showError("Invalid login!");
            return;
        }
        pass = et_password.getText().toString();
        controller.performLogin(id, pass);
    }

    @Override
    public void showSpinner() {
        bar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideSpinner() {
        bar.setVisibility(View.GONE);
    }

    @Override
    public void startNextActivity(User user) {
        Intent intent = SettingsActivity.getIntentToStartThisActivity(this, user);
        startActivity(intent);
        finish();
    }

    @Override
    public void showError(String reason) {
        at_reason.setVisibility(View.VISIBLE);
        at_reason.setText(Awesome.CROSS + " " + reason);
    }

    private void hideError(){
        at_reason.setVisibility(View.GONE);
    }
}
