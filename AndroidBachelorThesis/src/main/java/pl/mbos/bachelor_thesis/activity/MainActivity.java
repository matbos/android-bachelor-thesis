package pl.mbos.bachelor_thesis.activity;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;


import java.util.List;

import javax.inject.Inject;

import butterknife.InjectView;
import butterknife.OnClick;
import butterknife.Views;
import pl.mbos.bachelor_thesis.BaseApplication;
import pl.mbos.bachelor_thesis.R;
import pl.mbos.bachelor_thesis.controller.MainActivityController;
import pl.mbos.bachelor_thesis.dao.Attention;
import pl.mbos.bachelor_thesis.dao.Blink;
import pl.mbos.bachelor_thesis.dao.Meditation;
import pl.mbos.bachelor_thesis.dao.PoorSignal;
import pl.mbos.bachelor_thesis.dao.PowerEEG;
import pl.mbos.bachelor_thesis.dao.User;
import pl.mbos.bachelor_thesis.menu.Menu;
import pl.mbos.bachelor_thesis.service.data.connector.command.CommandServiceClient;
import pl.mbos.bachelor_thesis.service.data.connector.command.ICommandAuthorizationConnectionListener;
import pl.mbos.bachelor_thesis.service.data.connector.data.DataServiceClient;
import pl.mbos.bachelor_thesis.service.data.contract.IDataServiceConnectionListener;
import pl.mbos.bachelor_thesis.view.MainView;

/**
 * Created with IntelliJ IDEA.
 * User: Mateusz Boś
 * Date: 30.09.13
 * Time: 00:50
 */
public class MainActivity extends SlidingMenuActivity implements MainView, IDataServiceConnectionListener, ICommandAuthorizationConnectionListener {

    private static final int REQUEST_ENABLE_BT = 3111990;
    private User user;
    private MainActivityController controller;
    @InjectView(R.id.tv_state)
    public TextView tvState;
    @InjectView(R.id.tv_attention)
    public TextView tvAttention;
    @InjectView(R.id.tv_meditation)
    public TextView tvMeditation;
    @InjectView(R.id.tv_poor_signal)
    public TextView tvPoorSignal;

    private DataServiceClient client;
    private CommandServiceClient cClient;

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
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Views.inject(this);

        user = (User) getIntent().getExtras().getParcelable(User.USER_KEY);
        controller = new MainActivityController(this);
        String userName = (user != null) ? user.getFirstName() + " " + user.getLastName() : "NO USER";
        tvState.setText("NONE");

        client = new DataServiceClient();
        client.registerListener(this);
        cClient = new CommandServiceClient();
        cClient.registerListener(this);

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

    @OnClick(R.id.btn_connect)
    public void connectButtonClicked() {
        controller.buttonConnectClicked();
    }

    @OnClick(R.id.btn_start_stream)
    public void startStreamButtonClicked() {
        controller.startStreamClicked();
    }

    @OnClick(R.id.btn_disconnect)
    public void disconnectButtonClicked() {
        controller.buttonDisconnectClicked();
    }

    @OnClick(R.id.btn_logout)
    public void logoutButtonClicked() {
        controller.buttonLogoutClicked();
    }

    @OnClick(R.id.btn_get_attention)
    public void getAttentionClicked() {
        client.requestAllAttentionData();
    }

    @OnClick(R.id.btn_put_attention)
    public void putAttentionClicked() {
        client.connectToService();
    }

    @OnClick(R.id.btn_put_synchronize)
    public void synchronizeClicked() {
        cClient.setSynchronization(true);
        cClient.synchronize();
    }


    @Override
    public void setState(String state) {
        tvState.setText(state);
    }

    @Override
    public void setPoorSignal(int value) {
        tvPoorSignal.setText("Poor signal " + value);
    }

    @Override
    public void headsetProblem(boolean is) {

    }

    @Override
    public void showHeadsetMessage(String msg, int time) {

    }

    @Override
    public void requestBluetooth() {
        Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
        startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT);
    }

    @Override
    public void showBluetoothRequirementMessage() {
        Toast.makeText(getApplicationContext(), "Połączenie bluetooth jest niezbędne do działania aplikacji", Toast.LENGTH_LONG).show();
    }

    @Override
    public long getUserID() {
        return user.getId();
    }

    @Override
    public void setSynchronizing(boolean synchronizing) {
    }

    @Override
    public void goBackToLoginPage() {

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_ENABLE_BT) {
            if (resultCode == RESULT_OK) {
                controller.notifyBluetoothGranted();
            } else {
                controller.notifyBluetoothDenied();
            }
        }
    }

    @Override
    public void receivedAttentionData(List<Attention> data) {
        for (Attention a : data) {
            Log.i("AtDa", a.getUserId() + " " + a.getValue() + " " + a.getCollectionDate());
        }
    }

    @Override
    public void receivedMeditationData(List<Meditation> data) {

    }

    @Override
    public void receivedBlinkData(List<Blink> data) {

    }

    @Override
    public void receivedPowerData(List<PowerEEG> data) {

    }

    @Override
    public void receivedPoorSignalData(List<PoorSignal> data) {

    }
}

