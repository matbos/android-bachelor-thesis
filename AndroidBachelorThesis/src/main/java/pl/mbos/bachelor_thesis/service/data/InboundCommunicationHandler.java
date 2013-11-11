package pl.mbos.bachelor_thesis.service.data;

import android.os.Handler;
import android.os.Message;
import android.util.Log;

/**
 * Handles communicates received from {@link pl.mbos.bachelor_thesis.service.data.connector.data.DataServiceClient}
 */
public class InboundCommunicationHandler extends Handler {

    private static final String TAG = "MainService" + InboundCommunicationHandler.class.getSimpleName();
    private IPCConnector connector;

    public InboundCommunicationHandler(IPCConnector connector) {
        this.connector = connector;
    }

    @Override
    public void handleMessage(Message msg) {
        switch (msg.arg1) {
            case IPCConnector.TYPE_AUTHENTICATION:
                connector.receivedAuthorizationMessage(msg);
                break;
            case IPCConnector.TYPE_DATA:
                connector.receivedDataMessage(msg);
                break;
            case IPCConnector.TYPE_COMMAND:
                connector.receivedCommandMessage(msg);
                break;
            default:
                //TODO Something more?
                Log.d(TAG, "Unknown message type recieved (" + msg.arg1 + ")");
                break;
        }
    }
}
