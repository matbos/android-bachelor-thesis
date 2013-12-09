package pl.mbos.bachelor_thesis.service.data;

import android.os.Bundle;
import android.os.Message;
import android.os.Messenger;
import android.os.Parcelable;
import android.util.Log;

import java.util.ArrayList;

import pl.mbos.bachelor_thesis.dao.Attention;
import pl.mbos.bachelor_thesis.dao.Blink;
import pl.mbos.bachelor_thesis.dao.Meditation;
import pl.mbos.bachelor_thesis.dao.PoorSignal;
import pl.mbos.bachelor_thesis.dao.PowerEEG;
import pl.mbos.bachelor_thesis.dao.User;
import pl.mbos.bachelor_thesis.service.data.handler.AuthorizationConnector;
import pl.mbos.bachelor_thesis.service.data.handler.CommandConnector;
import pl.mbos.bachelor_thesis.service.data.handler.DataConnector;

/**
 * Handles all communication with {@link pl.mbos.bachelor_thesis.service.data.connector.data.DataServiceClient}
 */
public class IPCConnector {

    public static final String MESSENGER    = "MESSENGER";
    public static final String TYPE         = "TYPE";
    public static final String REASON       = "REASON";

    public static final int TYPE_AUTHENTICATION = 1;
    public static final int TYPE_DATA           = 2;
    public static final int TYPE_COMMAND        = 4;
    public static final int TYPE_UNIVERSAL      = 4;

    public static final int MESSAGE_GOODBYE = Integer.MAX_VALUE;

    public static final int AUTH_AUTHENTICATE   = 1;

    public static final int UNIV_ADDRESS_CHANGED        = 32768;
    public static final String UNIV_ENDPOINT_ADDRESS    = "endpointAddress";

    public static final int CMD_REPORT_STATE            = 1;
    public static final int CMD_REPORT_RUNNING          = 2;
    public static final int CMD_SYNCHRONIZE             = 4;
    public static final int CMD_ALLOW_SYNC              = 8;
    public static final int CMD_DENY_SYNC               = 16;
    public static final int CMD_SYNCHRONIZATION_MEDIUM  = 32;

    public static final int DATA_REQ_ATTENTION      = 1;
    public static final int DATA_REQ_MEDITATION     = 2;
    public static final int DATA_REQ_BLINK          = 4;
    public static final int DATA_REQ_POWER          = 8;
    public static final int DATA_REQ_POOR_SIGNAL    = 16;
    public static final int DATA_ADD_ATTENTION      = 1024;
    public static final int DATA_ADD_MEDITATION     = 2048;
    public static final int DATA_ADD_BLINK          = 4096;
    public static final int DATA_ADD_POWER          = 8192;
    public static final int DATA_ADD_POOR_SIGNAL    = 16384;
    public static final String DATA_DATA            = "DATA";

    public static final int AUTH_FAILED     = 1;
    public static final int AUTH_SUCCEEDED  = 2;

    private static final String TAG = IPCConnector.class.getSimpleName();

    public final InboundCommunicationHandler inbound;
    private AuthorizationConnector authConnector;
    private CommandConnector cmdConnector;
    private DataConnector dataConnector;

    MainService service;

    public IPCConnector(MainService service) {
        this.service = service;
        inbound = new InboundCommunicationHandler(this);
        authConnector = new AuthorizationConnector();
        cmdConnector = new CommandConnector();
        dataConnector = new DataConnector();
    }

    protected void addListener(int type, Messenger messenger) {
        Log.d(TAG,"Received listener for "+ type);
        switch (type) {
            case TYPE_AUTHENTICATION:
                authConnector.addListener(messenger);
                break;
            case TYPE_COMMAND:
                cmdConnector.addListener(messenger);
                break;
            case TYPE_DATA:
                dataConnector.addListener(messenger);
                break;
            default:
                Log.e(TAG, "Unknown type of listener (" + type + ")");
        }
    }

    protected void removeListener(int type, Messenger messenger) {
        switch (type) {
            case TYPE_AUTHENTICATION:
                authConnector.removeListener(messenger);
                break;
            case TYPE_COMMAND:
                cmdConnector.removeListener(messenger);
                break;
            case TYPE_DATA:
                dataConnector.removeListener(messenger);
                break;
            default:
                Log.e(TAG, "Unknown listener type (" + type + ")");
        }
    }

    protected void receivedAuthorizationMessage(Message msg) {
        if (!isGoodbyeMessage(msg)) {
            service.authMessage(msg);
        } else {
            unregisterListener(msg);
        }
    }

    protected void receivedDataMessage(Message msg) {
        if (!isGoodbyeMessage(msg)) {
            service.dataMessage(msg);
        } else {
            unregisterListener(msg);
        }
    }

    protected void receivedCommandMessage(Message msg) {
        if (!isGoodbyeMessage(msg)) {
            service.cmdMessage(msg);
        } else {
            unregisterListener(msg);
        }
    }

    protected void userAuthorized(User user) {
        authConnector.userAuthorized(user);
    }

    protected void userUnauthorized(User user, String reason) {
        authConnector.userUnauthorized(user, reason);
    }

    protected void reportRunning(boolean running) {
        cmdConnector.reportSyncRunning(running);
    }

    protected void reportState(boolean state) {
        cmdConnector.reportSyncState(state);
    }

    private boolean isGoodbyeMessage(Message msg) {
        if (msg.arg2 == IPCConnector.MESSAGE_GOODBYE) {
            return true;
        } else {
            return false;
        }
    }

    private void unregisterListener(Message msg){
        Messenger messenger = (Messenger) msg.obj;
        if(messenger != null){
            removeListener(msg.arg1,messenger);
        } else {
            Log.d(TAG, "Messenger passed to remove it from listener list is null! (type to unregister "+msg.arg1+")");
        }
    }

    protected void sendAttentionData(ArrayList<Attention> data){
        dataConnector.sendAttentionData(Attention.convertToArray(data));
    }

    protected void sendMeditationData(ArrayList<Meditation> data) {
        dataConnector.sendMeditationData(Meditation.convertToArray(data));
    }

    protected void sendBlinkData(ArrayList<Blink> data) {
        dataConnector.sendBlinkData(Blink.convertToArray(data));
    }

    protected void sendPowerData(ArrayList<PowerEEG> data) {
        dataConnector.sendPowerData(PowerEEG.convertToArray(data));
    }

    protected void sendPoorSignalData(ArrayList<PoorSignal> data) {
        dataConnector.sendPoorSignalData(PoorSignal.convertToArray(data));
    }
}
