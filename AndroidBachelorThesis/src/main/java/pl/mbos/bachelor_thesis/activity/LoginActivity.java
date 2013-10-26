package pl.mbos.bachelor_thesis.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.InjectView;
import butterknife.OnClick;
import butterknife.Views;
import pl.mbos.bachelor_thesis.R;
import pl.mbos.bachelor_thesis.controller.LoginController;
import pl.mbos.bachelor_thesis.dao.User;
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
    @InjectView(R.id.btn_login)
    Button btn_login;
    LoginController controller;

    /**
     * Method used to build an intent that can start this activity
     *
     * @param parent calling activity
     * @return An {@link Intent}
     */
    public static Intent getIntentToStartThisActivity(Activity parent) {
        Intent intent = new Intent(parent, LoginActivity.class);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Views.inject(this);
        controller = new LoginController(this);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @OnClick(R.id.btn_login)
    public void loginButtonClicked() {
        //TODO implement when UI will be ready
        boolean outcome = controller.performLogin(et_login.getText().toString(), et_password.getText().toString());
        String message;
        if (!outcome) {
            message = controller.getLoginMessage();
            Toast.makeText(this, message, Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void showSpinner() {
        //TODO implement when UI will be ready
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void hideSpinner() {
        //TODO implement when UI will be ready
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void startNextActivity(User user) {
        Intent intent = MainActivity.getIntentToStartThisActivity(this, user);
        startActivity(intent);
        finish();
    }


}
