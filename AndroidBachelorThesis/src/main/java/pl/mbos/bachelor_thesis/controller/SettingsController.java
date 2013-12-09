package pl.mbos.bachelor_thesis.controller;

import android.content.res.Resources;

import javax.inject.Inject;

import pl.mbos.bachelor_thesis.BaseApplication;
import pl.mbos.bachelor_thesis.R;
import pl.mbos.bachelor_thesis.service.connection.contract.IEEGAcquisitionServiceConnection;
import pl.mbos.bachelor_thesis.service.connection.contract.IEEGAcquisitionServiceConnectionListener;
import pl.mbos.bachelor_thesis.service.data.connector.command.ICommandAuthorizationConnectionListener;
import pl.mbos.bachelor_thesis.service.data.contract.ICommandServiceConnection;
import pl.mbos.bachelor_thesis.service.data.contract.ICommandServiceConnectionListener;
import pl.mbos.bachelor_thesis.view.MainView;

/**
 * Created by Mateusz on 08.12.13.
 */
public class SettingsController extends SlidingMenuBaseController implements IEEGAcquisitionServiceConnectionListener, ICommandServiceConnectionListener, ICommandAuthorizationConnectionListener, WebAddressListener {

    private MainView view;
    @Inject
    public IEEGAcquisitionServiceConnection acquisitionService;
    @Inject
    public ICommandServiceConnection commandService;
    @Inject
    Resources res;
    private String state = "";

    public SettingsController(MainView view) {
        this.view = view;
        BaseApplication.getBaseGraph().inject(this);
        acquisitionService.connectToService(view.getUserID());
        commandService.connectToService();
    }

    public void initializeFields() {
        commandService.isSynchronizationPermitted();
    }

    /**
     * Method that does cleaning concerning {@link #acquisitionService} object
     */
    public void close() {
        acquisitionService.unregisterListener(this);
        commandService.unregisterListener(this);
    }

    /**
     * Method that has to be called from activity's onStart method to allow proper functioning.
     */
    public void start() {
        acquisitionService.registerListener(this);
        commandService.registerListener(this);
    }

    public void startStreamClicked() {
        acquisitionService.startStream();
    }

    public void logout() {
        acquisitionService.stopStream();
        view.goBackToLoginPage();
    }

    public void toggleSyncAllowance() {
        commandService.setSynchronization(true);
    }

    public void forceSynchronization() {
        commandService.synchronize();
    }

    public void buttonConnectClicked() {
        if (state.equalsIgnoreCase("") || state.equalsIgnoreCase("disconnected")) {
            acquisitionService.connectToDevice();
        } else {
            acquisitionService.disconnectFromDevice();
        }
    }

    public void notifyBluetoothGranted() {
        acquisitionService.connectToDevice();
    }

    public void notifyBluetoothDenied() {
        view.showBluetoothRequirementMessage();
    }

    @Override
    public void onConnect() {

    }

    @Override
    public void onServiceDisconnected(String reason) {

    }

    @Override
    public void reportState(String state) {
        this.state = state;
        view.setState(state);
    }

    @Override
    public void reportPoorSignal(int value) {
        if (value == 200) {
            view.headsetProblem(true);
        } else if (value > 0) {
            view.headsetProblem(true);
            view.showHeadsetMessage(res.getString(R.string.headsetPlacementMessage), 1000);
        } else {
            view.headsetProblem(false);
        }
    }


    @Override
    public void reportBluetoothRequest() {
        view.requestBluetooth();
    }

    @Override
    public void webServiceAddressChanged(String newAddress) {
        commandService.setNewEndpoint(newAddress);
    }

    @Override
    public void isSynchronizing(boolean synchronizing) {
        view.setSynchronizing(synchronizing);
    }

    @Override
    public void onlyWiFi(boolean wifi) {

    }
}
