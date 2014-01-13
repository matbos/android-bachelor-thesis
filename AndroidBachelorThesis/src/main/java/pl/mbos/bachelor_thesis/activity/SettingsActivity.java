package pl.mbos.bachelor_thesis.activity;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import javax.inject.Inject;

import butterknife.InjectView;
import butterknife.OnClick;
import butterknife.Views;
import pl.mbos.bachelor_thesis.R;
import pl.mbos.bachelor_thesis.controller.SettingsController;
import pl.mbos.bachelor_thesis.controller.WebAddressTextWatcher;
import pl.mbos.bachelor_thesis.dao.User;
import pl.mbos.bachelor_thesis.dao.helper.UserHelper;
import pl.mbos.bachelor_thesis.view.SettingsView;

public class SettingsActivity extends SlidingMenuActivity implements SettingsView {

    private static final int REQUEST_ENABLE_BT = 3111990;

    @InjectView(R.id.tv_connectionState)
    TextView tv_state;
    @InjectView(R.id.et_webAddress)
    EditText et_webAddress;
    @InjectView(R.id.tv_synchronize)
    TextView tv_synchronize;
    @InjectView(R.id.tv_synchronizationState)
    TextView tv_synchronizationAllowed;
    @InjectView(R.id.btn_connect)
    TextView btn_connect;
    @Inject
    Resources res;

    private SettingsController controller;
    private String stateBeginning;
    private String headsetConnected;
    private String headsetDisconnected;
    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        Views.inject(this);
        user = UserHelper.retrieveUserFromSO();
        controller = new SettingsController(this);
        et_webAddress.addTextChangedListener(new WebAddressTextWatcher(et_webAddress, controller));
        initStrings();
        controller.initializeFields();
        configureBaseController(controller);
    }

    @Override
    protected void onResume() {
        controller.start();
        super.onResume();
    }

    @Override
    protected void onPause() {
        controller.close();
        super.onPause();
    }

    private void initStrings() {
        Resources res = getResources();
        stateBeginning = res.getString(R.string.headsetBeginning);
        headsetConnected = res.getString(R.string.connected);
        headsetDisconnected = res.getString(R.string.disconnected);
    }

    @OnClick(R.id.btn_connect)
    public void connectButtonClicked() {
        controller.buttonConnectClicked();
    }

    @OnClick(R.id.btn_streamState)
    public void startStreamButtonClicked() {
        controller.startStreamClicked();
    }

    @OnClick(R.id.btn_synchronizeOn)
    public void buttonSyncMediumClicked() {
        controller.setSyncMediumClicked();
    }

    @OnClick(R.id.btn_synchronization)
    public void buttonSetSynchronizationClicked() {
        controller.toggleSyncAllowanceClicked();
    }

    @OnClick(R.id.btn_synchronizeNow)
    public void synchronizeClicked() {
        controller.forceSynchronization();
    }


    @Override
    public void setState(String state) {
        if (state.equalsIgnoreCase(headsetDisconnected)) {
            btn_connect.setText(res.getString(R.string.connect));
        } else {
            btn_connect.setText(res.getString(R.string.disconnect));
        }
        tv_state.setText(stateBeginning + " " + state);
    }

    @Override
    public void setPoorSignal(int value) {

    }

    @Override
    public void headsetProblem(boolean is) {
        if (is) {
            headsetItem.setVisible(true);
            headsetItem.setIcon(R.drawable.headset_off);
        } else {
            headsetItem.setIcon(R.drawable.headset);
        }
    }

    @Override
    public void showHeadsetMessage(String msg, int time) {
        Toast.makeText(this, msg, time).show();
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
        tv_synchronizationAllowed.setText(res.getText(R.string.synchronization) + " " + res.getText((synchronizing) ? R.string.allowed : R.string.forbidden));
    }

    @Override
    public void goBackToLoginPage() {
        startActivity(LoginActivity.getIntentToStartThisActivity(this));
        this.finish();
    }

    @Override
    public void setSyncRepresentation(boolean state) {
        tv_synchronizationAllowed.setText("Synchronization is " + ((state) ? "on" : "off"));
    }

    @Override
    public void setSyncAllowance(boolean allowance) {
        tv_synchronize.setText("Synchronize on "+((allowance)?"wifi":"both"));
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

    /**
     * Method used to build an intent that can start this activity
     *
     * @param parent calling activity
     * @return An {@link Intent}
     */
    public static Intent getIntentToStartThisActivity(Activity parent, User user) {
        Intent intent = new Intent(parent, SettingsActivity.class);
        return intent;
    }
}
