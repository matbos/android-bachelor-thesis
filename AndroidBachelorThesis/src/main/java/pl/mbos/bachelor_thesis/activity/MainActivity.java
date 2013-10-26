package pl.mbos.bachelor_thesis.activity;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import pl.mbos.bachelor_thesis.R;
import pl.mbos.bachelor_thesis.controller.MainActivityController;
import pl.mbos.bachelor_thesis.dao.User;
import pl.mbos.bachelor_thesis.view.MainView;

/**
 * Created with IntelliJ IDEA.
 * User: Mateusz Boś
 * Date: 30.09.13
 * Time: 00:50
 */
public class MainActivity extends Activity implements MainView {

    private static final int REQUEST_ENABLE_BT = 3111990;
    private User user;
    private MainActivityController controller;
    public TextView tvState;
    public TextView tvAttention;
    public TextView tvMeditation;
    public TextView tvPoorSignal;
    /**
     * Method used to build an intent that can start this activity
     *
     * @param parent calling activity
     * @param user   object of class {@link User} that will be passed to {@link MainActivity} activity with {@code User.USER_KEY} key
     * @return An {@link Intent}
     */
    public static Intent getIntentToStartThisActivity(Activity parent, User user) {
        Intent intent = new Intent(parent, MainActivity.class);
        intent.putExtra(User.USER_KEY, user);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);    //To change body of overridden methods use File | Settings | File Templates.
        setContentView(R.layout.activity_main);
        controller = new MainActivityController(this);
        user = (User) getIntent().getExtras().getParcelable(User.USER_KEY);
        String userName = (user != null) ? user.getFirstName() + " "+ user.getLastName() : "NO USER";

        Button btn = (Button) findViewById(R.id.btn_connect);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                connectButtonClicked();
            }
        });
        btn = (Button) findViewById(R.id.btn_start_stream);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startStreamButtonClicked();
            }
        });
        btn = (Button) findViewById(R.id.btn_disconnect);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                disconnectButtonClicked();
            }
        });
        btn = (Button) findViewById(R.id.btn_logout);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                logoutButtonClicked();
            }
        });
        tvState = (TextView) findViewById(R.id.tv_state);
        tvAttention = (TextView) findViewById(R.id.tv_attention);
        tvMeditation = (TextView) findViewById(R.id.tv_meditation);
        tvPoorSignal = (TextView) findViewById(R.id.tv_poor_signal);
        tvState.setText("NONE");
    }

    @Override
    protected void onPause() {
        super.onPause();
        controller.close();
    }

    @Override
    protected void onResume() {
        super.onResume();
        controller.resume();
    }

   // @OnClick(R.id.btn_connect)
    public void connectButtonClicked() {
        controller.buttonConnectClicked();
    }

    //@OnClick(R.id.btn_start_stream)
    public void startStreamButtonClicked() {
        controller.startStreamClicked();
    }

    //@OnClick(R.id.btn_disconnect)
    public void disconnectButtonClicked() {
        controller.buttonDisconnectClicked();
    }

    //@OnClick(R.id.btn_logout)
    public void logoutButtonClicked() {
        controller.buttonLogoutClicked();
    }

    @Override
    public void setState(String state) {
        tvState.setText(state);
    }

    @Override
    public void setMeditation(int value) {
        tvMeditation.setText("Meditation "+value);
    }

    @Override
    public void setAttention(int value) {
    tvAttention.setText("Attention "+value);
    }

    @Override
    public void setPoorSignal(int value) {
    tvPoorSignal.setText("Poor signal "+value);
    }

    @Override
    public void requestBluetooth() {
        Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
        startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT);
    }

    @Override
    public void showBluetoothRequirementMessage() {
        Toast.makeText(getApplicationContext(), "Połączenie bluetooth jest niezbędne do działania aplikacji",Toast.LENGTH_LONG).show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == REQUEST_ENABLE_BT){
            if (resultCode == RESULT_OK){
                controller.notifyBluetoothGranted();
            } else {
                controller.notifyBluetoothDenied();
            }
        }
    }
}
