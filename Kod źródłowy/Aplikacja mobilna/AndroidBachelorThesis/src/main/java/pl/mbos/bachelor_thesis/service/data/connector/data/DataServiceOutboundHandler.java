package pl.mbos.bachelor_thesis.service.data.connector.data;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.util.Log;

import pl.mbos.bachelor_thesis.dao.User;
import pl.mbos.bachelor_thesis.service.data.IPCConnector;

/**
 * Handles sending communicates to {@link pl.mbos.bachelor_thesis.service.data.MainService}
 */
public class DataServiceOutboundHandler {

    private final static String TAG = "Data" + DataServiceOutboundHandler.class.getSimpleName();
    private Messenger messenger;

    public DataServiceOutboundHandler(Messenger messenger) {
        this.messenger = messenger;
    }

    protected Messenger getMessenger() {
        return messenger;
    }

    protected void sayGoodbye(Messenger messenger) {
        try {
            Message message = Message.obtain();
            message.arg1 = IPCConnector.TYPE_DATA;
            message.arg2 = IPCConnector.MESSAGE_GOODBYE;
            message.obj = messenger;
            messenger.send(message);
        } catch (RemoteException e) {
            Log.e(TAG, e.getMessage());
        }
    }

    protected void requestAttentionData(){
        send(getMessage(IPCConnector.DATA_REQ_ATTENTION));
    }

    protected void requestMeditationData(){
        send(getMessage(IPCConnector.DATA_REQ_MEDITATION));
    }

    protected void requestBlinkData(){
        send(getMessage(IPCConnector.DATA_REQ_BLINK));
    }

    protected void requestPowerData(){
        send(getMessage(IPCConnector.DATA_REQ_POWER));
    }

    protected void requestPoorSignalData(){
        send(getMessage(IPCConnector.DATA_REQ_POOR_SIGNAL));
    }

    private Message getMessage(int requestType){
        Message message = Message.obtain();
        message.arg1 = IPCConnector.TYPE_DATA;
        message.arg2 = requestType;
        return message;
    }

    private void send(Message msg){
        try {
            messenger.send(msg);
        } catch (RemoteException e) {
            Log.e(TAG, e.getMessage());
        }
    }
}
