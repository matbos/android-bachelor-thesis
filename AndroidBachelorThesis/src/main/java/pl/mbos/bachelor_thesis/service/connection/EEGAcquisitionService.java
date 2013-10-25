package pl.mbos.bachelor_thesis.service.connection;

import android.app.Service;
import android.bluetooth.BluetoothAdapter;
import android.content.Intent;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.util.Log;

import com.neurosky.thinkgear.TGDevice;
import com.neurosky.thinkgear.TGRawMulti;

import pl.mbos.bachelor_thesis.eeg.ITGDeviceHandlerListener;
import pl.mbos.bachelor_thesis.eeg.TGDeviceHandler;
import pl.mbos.bachelor_thesis.service.connection.handler.ApplicationIncomingCommunicationHandler;
import pl.mbos.bachelor_thesis.service.connection.handler.ApplicationOutboundCommunicationHandler;

import static pl.mbos.bachelor_thesis.service.connection.EEGAcquisitionServiceConnectionConnector.VALUE_ATTENTION;
import static pl.mbos.bachelor_thesis.service.connection.EEGAcquisitionServiceConnectionConnector.VALUE_MEDITATION;
import static pl.mbos.bachelor_thesis.service.connection.EEGAcquisitionServiceConnectionConnector.VALUE_MULTI;
import static pl.mbos.bachelor_thesis.service.connection.EEGAcquisitionServiceConnectionConnector.VALUE_POOR_SIGNAL;

/**
 * This service acquires data in the background until stopped from application.
 */
public class EEGAcquisitionService extends Service {

    public static final int REQUEST_BLUETOOTH = Integer.MAX_VALUE;
    private static final String TAG = EEGAcquisitionService.class.getSimpleName();
    public static int CONNECT_TO_DEVICE = 1;
    public static int DISCONNECT_FROM_DEVICE = 2;
    public static int REQUEST_STATE = 10;
    public static int START_STREAM = 11;
    public static int STOP_STREAM = 12;
    public static int STOP_SERVICE = 128;
    /**
     * Messenger to be returned on all onBind requests
     */
    protected Messenger messenger;
    protected BluetoothAdapter btAdapter;
    protected TGDevice tgDevice;
    protected TGDeviceHandler handler;
    protected ApplicationOutboundCommunicationHandler applicationHandler;

    @Override
    public IBinder onBind(Intent intent) {
        Messenger returnMessenger = (Messenger) intent.getParcelableExtra("Messenger");
        applicationHandler = new ApplicationOutboundCommunicationHandler(returnMessenger);
        return messenger.getBinder();
    }

    @Override
    public void onCreate() {
        super.onCreate();
        messenger = new Messenger(new ApplicationIncomingCommunicationHandler(this));
        initTGD();
    }

    private void initTGD() {
        btAdapter = BluetoothAdapter.getDefaultAdapter();
        handler = new TGDeviceHandler(new TGDeviceListener());
        tgDevice = new TGDevice(btAdapter, handler);

    }

    public void startDeviceStream() {
        tgDevice.start();
        reportDeviceState();
    }

    public void stopDeviceStream() {
        tgDevice.stop();
        reportDeviceState();
    }

    public void requestDeviceState() {
        reportDeviceState();
    }

    public void connectDevice() {
        if (btAdapter != null) {
            if (!btAdapter.isEnabled()) {
                applicationHandler.requestBluetooth();
                btAdapter = BluetoothAdapter.getDefaultAdapter();
            } else {
                if(tgDevice == null){
                    tgDevice = new TGDevice(btAdapter, handler);
                }
                if (!isConnectedOrConnecting()) {
                    tgDevice.connect(true);
                    tgDevice.stop();
                }
            }
        }
    }

    public void disconnectDevice() {
        tgDevice.close();
        reportDeviceState();
    }

    public void serviceStopSelf() {
        Log.i(TAG, "Shutdown");
        stopSelf();
    }

    private void reportDeviceState(){
        Message msg = ApplicationOutboundCommunicationHandler.buildStateMessage(tgDevice.getState());
        applicationHandler.sendReturnMessage(msg);
    }

    private boolean isConnectedOrConnecting() {
        int state = tgDevice.getState();
        if (state == TGDevice.STATE_CONNECTED || state == TGDevice.STATE_CONNECTING) {
            return true;
        }
        return false;
    }

    class TGDeviceListener implements ITGDeviceHandlerListener {

        @Override
        public void reportDeviceConnected() {
            startDeviceStream();
            Message msg = ApplicationOutboundCommunicationHandler.buildStateMessage(TGDevice.STATE_CONNECTED);
            applicationHandler.sendReturnMessage(msg);
        }

        @Override
        public void reportState(int state) {
            Message msg = ApplicationOutboundCommunicationHandler.buildStateMessage(state);
            applicationHandler.sendReturnMessage(msg);
        }

        @Override
        public void reportPoorSignal(int level) {
            Message msg = ApplicationOutboundCommunicationHandler.buildValueMessage(VALUE_POOR_SIGNAL, level);
            applicationHandler.sendReturnMessage(msg);
        }

        @Override
        public void reportAttention(int level) {
            Message msg = ApplicationOutboundCommunicationHandler.buildValueMessage(VALUE_ATTENTION, level);
            applicationHandler.sendReturnMessage(msg);
        }

        @Override
        public void reportMeditation(int level) {
            Message msg = ApplicationOutboundCommunicationHandler.buildValueMessage(VALUE_MEDITATION, level);
            applicationHandler.sendReturnMessage(msg);
        }

        @Override
        public void reportMulti(TGRawMulti tgRawMulti) {
            Message msg = ApplicationOutboundCommunicationHandler.buildValueMessage(VALUE_MULTI, 0);
            msg.obj = tgRawMulti;
            applicationHandler.sendReturnMessage(msg);
        }

    }
}