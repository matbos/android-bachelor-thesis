package pl.mbos.bachelor_thesis.service.data.connector.data;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.Messenger;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import pl.mbos.bachelor_thesis.dao.Attention;
import pl.mbos.bachelor_thesis.dao.Blink;
import pl.mbos.bachelor_thesis.dao.Meditation;
import pl.mbos.bachelor_thesis.dao.PoorSignal;
import pl.mbos.bachelor_thesis.dao.PowerEEG;
import pl.mbos.bachelor_thesis.service.data.IPCConnector;
import pl.mbos.bachelor_thesis.service.data.MainService;
import pl.mbos.bachelor_thesis.service.data.connector.BaseServiceClient;
import pl.mbos.bachelor_thesis.service.data.contract.IDataServiceConnection;
import pl.mbos.bachelor_thesis.service.data.contract.IDataServiceConnectionListener;

/**
 * Provides means to communicate with {@link pl.mbos.bachelor_thesis.service.data.MainService} to obtain collected data
 */
public class DataServiceClient extends BaseServiceClient implements IDataServiceConnection {

    private static String TAG = DataServiceClient.class.getSimpleName();

    List<IDataServiceConnectionListener> clients;

    private DataServiceCommunicationHandler communicationHandler;

    public DataServiceClient() {
        super();
        communicationHandler = new DataServiceCommunicationHandler(this);
        clients = new ArrayList<IDataServiceConnectionListener>(10);
        connectToService();
    }

    /**
     * Registers clients that want to be notified about incoming messages
     *
     * @param client client to be notified
     */
    public void registerListener(IDataServiceConnectionListener client) {
        clients.add(client);
    }

    /**
     * Unregister clients
     *
     * @param client client to be unregistered
     */
    public boolean unregisterListener(IDataServiceConnectionListener client) {
        return clients.remove(client);
    }

    @Override
    public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
        communicationHandler.createOutboundMessenger(iBinder);
    }

    @Override
    public void requestAllAttentionData() {
        communicationHandler.requestAttentionData();
    }

    @Override
    public void requestAllMeditationData() {
        communicationHandler.requestMeditationData();
    }

    @Override
    public void requestAllBlinkData() {
        communicationHandler.requestBlinkData();
    }

    @Override
    public void requestAllPowerData() {
        communicationHandler.requestPowerData();
    }

    @Override
    public void requestAllPoorSignalData() {
        communicationHandler.requestPoorSignalData();
    }

    public void connectToService() {
        Log.d(TAG, "Data :: Connecting to service "+communicationHandler.isConnectedToService());
        if (! communicationHandler.isConnectedToService()) {
            connectToService(IPCConnector.TYPE_DATA, communicationHandler.getInboundMessenger());
        }
    }

    @Override
    protected void disconnectFromService(){
        communicationHandler.sayGoodbye();
        super.disconnectFromService();
    }

    protected void receivedAttentionData(List<Attention> data){
        for(IDataServiceConnectionListener client : clients){
            client.receivedAttentionData(data);
        }
    }

    protected void receivedMeditationData(List<Meditation> data){
        for(IDataServiceConnectionListener client : clients){
            client.receivedMeditationData(data);
        }
    }

    protected void receivedBlinkData(List<Blink> data){
        for(IDataServiceConnectionListener client : clients){
            client.receivedBlinkData(data);
        }
    }

    protected void receivedPowerData(List<PowerEEG> data){
        for(IDataServiceConnectionListener client : clients){
            client.receivedPowerData(data);
        }
    }

    protected void receivedPoorSignalData(List<PoorSignal> data){
        for(IDataServiceConnectionListener client : clients){
            client.receivedPoorSignalData(data);
        }
    }
}
