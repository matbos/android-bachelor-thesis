package pl.mbos.bachelor_thesis.controller;

import android.app.Application;
import android.widget.Toast;

import com.neurosky.thinkgear.TGDevice;
import com.neurosky.thinkgear.TGRawMulti;

import javax.inject.Inject;

import dagger.ObjectGraph;
import pl.mbos.bachelor_thesis.BaseApplication;
import pl.mbos.bachelor_thesis.di.IoCModule;
import pl.mbos.bachelor_thesis.service.connection.contract.IEEGAcquisitionServiceConnection;
import pl.mbos.bachelor_thesis.service.connection.contract.IEEGAcquisitionServiceConnectionListener;
import pl.mbos.bachelor_thesis.service.data.contract.ICommandServiceConnection;
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
    @Inject
    public ICommandServiceConnection commandService;

    private MainView view;

    public MainActivityController(MainView view) {
        BaseApplication.getBaseGraph().inject(this);
        acquisitionService.connectToService(view.getUserID());
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
    public void reportState(String state) {

    }


    @Override
    public void reportPoorSignal(int value) {
        view.setPoorSignal(value);
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
