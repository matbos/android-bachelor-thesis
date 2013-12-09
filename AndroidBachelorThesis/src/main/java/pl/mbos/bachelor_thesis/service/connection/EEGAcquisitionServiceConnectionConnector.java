package pl.mbos.bachelor_thesis.service.connection;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.util.Log;
import android.widget.Toast;

import com.neurosky.thinkgear.TGDevice;
import com.neurosky.thinkgear.TGEegPower;
import com.neurosky.thinkgear.TGRawMulti;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import pl.mbos.bachelor_thesis.BaseApplication;
import pl.mbos.bachelor_thesis.service.connection.contract.IEEGAcquisitionServiceConnection;
import pl.mbos.bachelor_thesis.service.connection.contract.IEEGAcquisitionServiceConnectionListener;

/**
 * Class used to communicate with (@link EEGAcquisitionService}
 */
public class EEGAcquisitionServiceConnectionConnector implements ServiceConnection, IEEGAcquisitionServiceConnection {

    public static final int REPORT_STATE = 64;
    public static final int REPORT_VALUE = 32;
    public static final int VALUE_ATTENTION = 16;
    public static final int VALUE_MEDITATION = 24;
    public static final int VALUE_MULTI = 28;
    public static final int VALUE_POOR_SIGNAL = 30;
    public static final int VALUE_POWER = 31;
    private static boolean mServiceUp = false;

    private static String TAG = EEGAcquisitionServiceConnectionConnector.class.getSimpleName();
    @Inject
    public Context context;
    List<IEEGAcquisitionServiceConnectionListener> clients;
    private Messenger serviceMessenger = null;
    private Messenger serviceReturnMessenger = null;

    @Inject
    public EEGAcquisitionServiceConnectionConnector() {
        clients = new ArrayList<IEEGAcquisitionServiceConnectionListener>();
        context = BaseApplication.getContext();
        serviceReturnMessenger = new Messenger(new InboundCommunicationHandler());
    }

    /**
     * Register object passed via an argument as a client of EEGAcquisitionServiceConnectionConnector.
     * Allowing it to be notified about significant changes and events.
     *
     * @param client instance of class implementing (@link IEEGAcquisitionServiceConnectionListener}.
     */
    @Override
    public void registerListener(IEEGAcquisitionServiceConnectionListener client) {
        try {
            clients.add(client);
        } catch (UnsupportedOperationException ex) {
            Log.e(TAG, "While registering client: " + ex.getMessage());
        } catch (IllegalArgumentException ex) {
            Log.e(TAG, "While registering client: " + ex.getMessage());
        } catch (ClassCastException ex) {
            Log.e(TAG, "While registering client: " + ex.getMessage());
        }
    }

    /**
     * Unregister object passed via an argument from list of clients that are notified
     * about significant changes and events.
     *
     * @param client object that is already registered as a client
     */
    @Override
    public boolean unregisterListener(IEEGAcquisitionServiceConnectionListener client) {
        boolean outcome;
        try {
            outcome = clients.remove(client);
        } catch (UnsupportedOperationException ex) {
            Log.e(TAG, ex.getMessage());
            outcome = false;
        }
        return outcome;
    }

    @Override
    public void stopService() {
        context.stopService(new Intent(context, EEGAcquisitionService.class));
        mServiceUp = false;
    }

    @Override
    public void connectToService(long userID) {
        if (isServiceRunning()) {
            startService();
            Log.i("ACQ-SERV", "Started service");
        } else {
            bindToService(userID);
            Log.i("ACQ-SERV", "Connected to service");
        }
    }

    private boolean isServiceRunning() {
        return mServiceUp;
    }

    /**
     * Starts service if it is not already running
     *
     * @return true if service was started, false if it is already running
     */
    private boolean startService() {
        boolean didStart = false;
        if (!mServiceUp) {
            Intent intent = new Intent(context, EEGAcquisitionService.class);
            ComponentName serviceName = context.startService(intent);
            mServiceUp = true;
            didStart = (serviceName != null) ? true : false;
        }
        return didStart;
    }

    /**
     * This is called when the connection with the service has been
     * established, giving us the object we can use to
     * interact with the service.  We are communicating with the
     * service using a Messenger, so here we get a client-side
     * representation of that from the raw IBinder object.
     *
     * @param className
     * @param service
     */
    public void onServiceConnected(ComponentName className, IBinder service) {
        serviceMessenger = new Messenger(service);
        clients.get(0).onConnect();
        // forEach(clients).onConnect();
    }

    /**
     * This is called when the connection with the service has been
     * unexpectedly disconnected -- that is, its process crashed.
     *
     * @param className
     */
    public void onServiceDisconnected(ComponentName className) {
        serviceMessenger = null;
        mServiceUp = false;
        // forEach(clients).onServiceDisconnected("Random reason, should be filled later");
    }

    public void sendMessage(int what) {
        if (serviceMessenger != null) {
            try {
                Message msg = Message.obtain();
                msg.arg1 = what;
                serviceMessenger.send(msg);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Connects to service
     */
    private void bindToService(long userID) {
        if (serviceMessenger == null) {
            Intent intent = new Intent(context, EEGAcquisitionService.class);
            intent.putExtra("Messenger", serviceReturnMessenger);
            intent.putExtra("UserID", userID);
            context.bindService(intent, this, Context.BIND_AUTO_CREATE);
        }
    }

    /**
     * Disconnects from service
     */
    @Override
    public void disconnectFromService() {
        if (serviceMessenger != null) {
            Toast.makeText(context, "Disconnecting", Toast.LENGTH_LONG).show();
            serviceMessenger = null;
            context.unbindService(this);
        }
    }

    @Override
    public void connectToDevice() {
        sendMessage(EEGAcquisitionService.CONNECT_TO_DEVICE);
    }

    @Override
    public void disconnectFromDevice() {
        sendMessage(EEGAcquisitionService.DISCONNECT_FROM_DEVICE);
    }

    @Override
    public void requestState() {
        sendMessage(EEGAcquisitionService.REQUEST_STATE);
    }

    @Override
    public void startStream() {
        sendMessage(EEGAcquisitionService.START_STREAM);
    }

    @Override
    public void stopStream() {
        sendMessage(EEGAcquisitionService.STOP_STREAM);
    }

    private void reportState(int state) {
        for (IEEGAcquisitionServiceConnectionListener client : clients) {
            client.reportState(decodeDeviceState(state));
        }
    }

    private void reportBluetoothRequest() {
        for (IEEGAcquisitionServiceConnectionListener client : clients) {
            client.reportBluetoothRequest();
        }
    }

    private void reportPoorSignal(int value) {
        for (IEEGAcquisitionServiceConnectionListener client : clients) {
            client.reportPoorSignal(value);
        }
    }

    private void reportMulti(TGRawMulti multi) {
        // Unfortunately my device does not emit raw data :|
    }

    private void reportMeditation(int value) {
        //TODO remove this method
    }

    private void reportAttention(int value) {
        //TODO remove this method
    }

    private void reportPower(TGEegPower eegPower) {
        for (IEEGAcquisitionServiceConnectionListener client : clients) {
            throw new RuntimeException("POWER PASSING IS NOT YET IMPLEMENTED");
        }
    }
    private String decodeDeviceState(int state){
        switch (state) {
            case TGDevice.STATE_CONNECTED:
                 return "connected";
            case TGDevice.STATE_CONNECTING:
                return "connecting";
            case TGDevice.STATE_IDLE:
                return "idle";
            case TGDevice.STATE_DISCONNECTED:
                return "disconnected";
            case TGDevice.STATE_NOT_FOUND:
                return "not found";
            default:
                return "unknown";
        }
    }
    class InboundCommunicationHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            if (msg.arg1 == EEGAcquisitionService.REQUEST_BLUETOOTH) {
                reportBluetoothRequest();
            }
            if ((msg.arg1 & (REPORT_STATE)) == REPORT_STATE) {
                reportState(msg.arg2);
            } else {
                switch (msg.arg1 - REPORT_VALUE) {
                    case VALUE_ATTENTION:
                        reportAttention(msg.arg2);
                        break;
                    case VALUE_MEDITATION:
                        reportMeditation(msg.arg2);
                        break;
                    case VALUE_MULTI:
                        reportMulti((TGRawMulti) msg.obj);
                        break;
                    case VALUE_POOR_SIGNAL:
                        reportPoorSignal(msg.arg2);
                        break;
                    case VALUE_POWER:
                        reportPower((TGEegPower) msg.obj);
                        break;
                    default:

                        break;
                }
            }
        }
    }
}
