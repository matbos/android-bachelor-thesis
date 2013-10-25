package pl.mbos.bachelor_thesis.controller;

import android.widget.Toast;

import com.neurosky.thinkgear.TGDevice;
import com.neurosky.thinkgear.TGRawMulti;

import javax.inject.Inject;

import dagger.ObjectGraph;
import pl.mbos.bachelor_thesis.BaseApplication;
import pl.mbos.bachelor_thesis.di.IoCModule;
import pl.mbos.bachelor_thesis.service.connection.contract.IEEGAcquisitionServiceConnection;
import pl.mbos.bachelor_thesis.service.connection.contract.IEEGAcquisitionServiceConnectionListener;
import pl.mbos.bachelor_thesis.view.MainView;

/**
 * Created with IntelliJ IDEA.
 * User: Mateusz Boś
 * Date: 02.10.13
 * Time: 23:32
 */

/**
 * Please call {@link #close() method on object before nulling it's reference!}
 */
public class MainActivityController implements IEEGAcquisitionServiceConnectionListener {

    @Inject
    public IEEGAcquisitionServiceConnection acquisitionService;
    private MainView view;

    public MainActivityController(MainView view) {
        ObjectGraph graph = ObjectGraph.create(IoCModule.class);
        acquisitionService = graph.get(IEEGAcquisitionServiceConnection.class);
        acquisitionService.connectToService();
        this.view = view;
    }

    /**
     * Method that does cleaning concerning {@link #acquisitionService} object
     */
    public void close() {
        acquisitionService.unregisterListener(this);
    }

    public void resume() {
        acquisitionService.registerListener(this);
    }

    public void buttonConnectClicked() {
        acquisitionService.connectToDevice();
    }

    /**
     * Method invoked when connection to service is established
     */
    @Override
    public void onConnect() {
        Toast.makeText(BaseApplication.getContext(), "Service Connected !!", Toast.LENGTH_LONG).show();
    }

    public void buttonLogoutClicked() {
        acquisitionService.requestState();
    }

    public void startStreamClicked() {
        acquisitionService.startStream();
    }

    public void buttonDisconnectClicked() {
        acquisitionService.stopStream();
    }

    /**
     * Method invoked when connection to service has been unexpectedly closed
     *
     * @param reason Reason of the closure in the form of text
     */
    @Override
    public void onServiceDisconnected(String reason) {
        Toast.makeText(BaseApplication.getContext(), reason, Toast.LENGTH_LONG).show();
    }

    @Override
    public void reportState(int state) {
        switch (state) {
            case TGDevice.STATE_CONNECTED:
                view.setState("Connected");
                break;
            case TGDevice.STATE_CONNECTING:
                view.setState("Connecting");
                break;
            case TGDevice.STATE_IDLE:
                view.setState("Idle");
                break;
            case TGDevice.STATE_DISCONNECTED:
                view.setState("Disconnected");
                break;
            case TGDevice.STATE_NOT_FOUND:
                view.setState("Not found");
                break;
            default:
                view.setState("UNKNOWN");
                break;
        }
    }

    @Override
    public void reportMeditation(int value) {
        view.setMeditation(value);
    }

    @Override
    public void reportAttention(int value) {
        view.setAttention(value);
    }

    @Override
    public void reportPoorSignal(int value) {
        view.setPoorSignal(value);
    }

    @Override
    public void reportMulti(TGRawMulti multi) {

    }

    @Override
    public void reportBluetoothRequest() {
        view.requestBluetooth();
    }

    public void notifyBluetoothGranted(){
        acquisitionService.connectToDevice();
    }

    public void notifyBluetoothDenied(){
        view.showBluetoothRequirementMessage();
    }

}
