package pl.mbos.bachelor_thesis.service.connection;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.Parcelable;
import android.os.RemoteException;
import android.util.Log;

import java.util.Date;

import javax.inject.Inject;

import pl.mbos.bachelor_thesis.BaseApplication;
import pl.mbos.bachelor_thesis.dao.Attention;
import pl.mbos.bachelor_thesis.dao.Blink;
import pl.mbos.bachelor_thesis.dao.Meditation;
import pl.mbos.bachelor_thesis.dao.PoorSignal;
import pl.mbos.bachelor_thesis.dao.PowerEEG;
import pl.mbos.bachelor_thesis.service.data.IPCConnector;
import pl.mbos.bachelor_thesis.service.data.MainService;

/**
 * Created by Mateusz on 11.11.13.
 */
public class MainServiceClient implements ServiceConnection {
    @Inject
    Context context;
    private Messenger outboundMessenger;
    private static final String TAG = MainServiceClient.class.getSimpleName();

    public MainServiceClient(){
        BaseApplication.getBaseGraph().inject(this);
    }
    /**
     * Connects to service
     */
    protected void connectToService() {
        Intent intent = new Intent(context, MainService.class);
        context.bindService(intent, this, Context.BIND_ABOVE_CLIENT);
    }

    @Override
    public void onServiceConnected(ComponentName name, IBinder service) {
        outboundMessenger = new Messenger(service);
    }

    @Override
    public void onServiceDisconnected(ComponentName name) {
        outboundMessenger = null;
    }

    public void sendAttention(Attention data) {
        Message msg = getMessage(IPCConnector.DATA_ADD_ATTENTION, data);
        send(msg);
    }

    public void sendMeditation(Meditation data) {
        Message msg = getMessage(IPCConnector.DATA_ADD_MEDITATION, data);
        send(msg);
    }

    public void sendBlink(Blink data) {
        Message msg = getMessage(IPCConnector.DATA_ADD_BLINK, data);
        send(msg);
    }

    public void sendPoorSignal(PoorSignal data) {
        Message msg = getMessage(IPCConnector.DATA_ADD_POOR_SIGNAL, data);
        send(msg);
    }

    public void sendPower(PowerEEG data) {
        Message msg = getMessage(IPCConnector.DATA_ADD_POWER, data);
        send(msg);
    }

    private Message getMessage(int type, Parcelable data) {
        Message msg = Message.obtain();
        msg.arg1 = IPCConnector.TYPE_DATA;
        msg.arg2 = type;
        Bundle bundle = new Bundle();
        bundle.putParcelable(IPCConnector.DATA_DATA, data);
        msg.setData(bundle);
        return msg;
    }

    private void send(Message msg) {
        try {
            if(outboundMessenger != null){
                outboundMessenger.send(msg);
            }
        } catch (RemoteException e) {
            Log.e(TAG, "Error sending message to MainService.class "+e.getMessage());
        }
    }
}
