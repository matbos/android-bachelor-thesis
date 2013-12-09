package pl.mbos.bachelor_thesis.service.data.connector.command;

import android.os.Bundle;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.util.Log;

import pl.mbos.bachelor_thesis.service.data.IPCConnector;

/**
 * Handles sending communicates to {@link pl.mbos.bachelor_thesis.service.data.MainService}
 */
public class CommandServiceOutboundHandler {

    private final static String TAG = "Command" + CommandServiceOutboundHandler.class.getSimpleName();
    private Messenger messenger;

    public CommandServiceOutboundHandler(Messenger messenger) {
        this.messenger = messenger;
    }

    public Messenger getMessenger() {
        return messenger;
    }

    public void synchronize() {
        sendMessage(createSyncMessage());
    }

    public void requestSyncState() {
        sendMessage(createGetStateMessage());
    }

    public void requestSyncProgress() {
        sendMessage(createIsSyncInProgressMessage());
    }

    public void setSyncState(boolean synchronize) {
        sendMessage(createSetSyncMessage(synchronize));
    }

    public void sendNewEndpointAddress(String address) {
        if (address != null) {
            Message msg = Message.obtain();
            msg.arg1 = IPCConnector.TYPE_UNIVERSAL;
            msg.arg2 = IPCConnector.UNIV_ADDRESS_CHANGED;
            Bundle bundle = new Bundle();
            bundle.putString(IPCConnector.UNIV_ENDPOINT_ADDRESS, address);
            msg.setData(bundle);

            sendMessage(msg);
        }
    }

    private Message createSyncMessage() {
        Message msg = Message.obtain();
        msg.arg1 = IPCConnector.TYPE_COMMAND;
        msg.arg2 = IPCConnector.CMD_SYNCHRONIZE;
        return msg;
    }

    private Message createGetStateMessage() {
        Message msg = Message.obtain();
        msg.arg1 = IPCConnector.TYPE_COMMAND;
        msg.arg2 = IPCConnector.CMD_REPORT_STATE;
        return msg;
    }

    private Message createIsSyncInProgressMessage() {
        Message msg = Message.obtain();
        msg.arg1 = IPCConnector.TYPE_COMMAND;
        msg.arg2 = IPCConnector.CMD_REPORT_RUNNING;
        return msg;
    }

    private Message createSetSyncMessage(boolean synchronize) {
        Message msg = Message.obtain();
        msg.arg1 = IPCConnector.TYPE_COMMAND;
        msg.arg2 = (synchronize) ? IPCConnector.CMD_ALLOW_SYNC : IPCConnector.CMD_DENY_SYNC;
        return msg;
    }

    private void sendMessage(Message message) {
        try {
            messenger.send(message);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    public void sayGoodbye(Messenger messenger) {
        try {
            Message message = Message.obtain();
            message.arg1 = IPCConnector.TYPE_COMMAND;
            message.arg2 = IPCConnector.MESSAGE_GOODBYE;
            message.obj = messenger;
            messenger.send(message);
        } catch (RemoteException e) {
            Log.e(TAG, e.getMessage());
        }
    }
}
