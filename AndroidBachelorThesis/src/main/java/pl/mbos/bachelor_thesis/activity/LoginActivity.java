package pl.mbos.bachelor_thesis.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import butterknife.InjectView;
import butterknife.OnClick;
import butterknife.Views;
import pl.mbos.bachelor_thesis.R;
import pl.mbos.bachelor_thesis.controller.LoginController;
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
public class LoginActivity extends Activity implements LoginView {

    @InjectView(R.id.et_login)
    TextView et_login;
    @InjectView(R.id.et_pass)
    TextView et_password;
    @InjectView(R.id.pb_progressBar)
    ProgressBar bar;
    @InjectView(R.id.at_reason)
    AwesomeText at_reason;
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
        setContentView(R.layout.activity_login);
        Views.inject(this);
        controller = new LoginController(this);
        Typeface font = Typeface.createFromAsset(getAssets(), "fontawesome-webfont.ttf");
        at_reason.setTypeface(font);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @OnClick(R.id.btn_login)
    public void loginButtonClicked() {
        Long id = -100L;
        String pass = "";
        try {
            id = Long.parseLong(et_login.getText().toString());
        } catch (NumberFormatException ex) {
        }
        pass = et_password.getText().toString();
        controller.performLogin(id, pass);
    }

    @Override
    public void showSpinner() {
        //TODO_UI implement when UI will be ready
        bar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideSpinner() {
        //TODO_UI implement when UI will be ready
        bar.setVisibility(View.GONE);
    }

    @Override
    public void startNextActivity(User user) {
        Intent intent = MainActivity.getIntentToStartThisActivity(this, user);
        startActivity(intent);
        finish();
    }

    @Override
    public void showError(String reason) {
        at_reason.setVisibility(View.VISIBLE);
        at_reason.setText(Awesome.CROSS + " " + reason);
    }
}
