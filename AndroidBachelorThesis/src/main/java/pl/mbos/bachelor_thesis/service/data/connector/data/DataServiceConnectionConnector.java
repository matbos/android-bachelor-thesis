package pl.mbos.bachelor_thesis.service.data.connector.data;

import android.content.ComponentName;
import android.content.Context;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.Messenger;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import pl.mbos.bachelor_thesis.dao.Attention;
import pl.mbos.bachelor_thesis.dao.Blink;
import pl.mbos.bachelor_thesis.dao.Meditation;
import pl.mbos.bachelor_thesis.dao.PoorSignal;
import pl.mbos.bachelor_thesis.dao.PowerEEG;
import pl.mbos.bachelor_thesis.service.data.contract.IDataServiceConnection;
import pl.mbos.bachelor_thesis.service.data.contract.IDataServiceConnectionListener;

/**
 * Provides means to communicate with {@link pl.mbos.bachelor_thesis.service.data.DataService} to obtain collected data
 */
public class DataServiceConnectionConnector implements ServiceConnection, IDataServiceConnection {

    private static String TAG = DataServiceConnectionConnector.class.getSimpleName();
    @Inject
    public Context context;
    List<IDataServiceConnectionListener> clients;

    private Messenger serviceMessenger = null;
    private Messenger serviceReturnMessenger = null;

    public DataServiceConnectionConnector(){
        serviceReturnMessenger = new Messenger(new ServiceInboundCommunicationHandler());
        clients = new ArrayList<IDataServiceConnectionListener>(10);
    }

    /**
     * Registers clients that want to be notified about incoming messages
     * @param client client to be notified
     */
    public void registerListener(IDataServiceConnectionListener client){
        clients.add(client);
    }
    /**
     * Unregister clients
     * @param client client to be unregistered
     */
    public void unregisterListener(IDataServiceConnectionListener client){
        clients.remove(client);
    }

    @Override
    public void onServiceConnected(ComponentName componentName, IBinder iBinder) {

    }

    @Override
    public void onServiceDisconnected(ComponentName componentName) {

    }

    @Override
    public List<Attention> getAllAttentionData() {
        return null;
    }

    @Override
    public List<Meditation> getAllMeditationData() {
        return null;
    }

    @Override
    public List<Blink> getAllBlinkData() {
        return null;
    }

    @Override
    public List<PowerEEG> getAllPowerData() {
        return null;
    }

    @Override
    public List<PoorSignal> getAllPoorSignalData() {
        return null;
    }
}
