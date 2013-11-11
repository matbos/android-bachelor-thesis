package pl.mbos.bachelor_thesis.service.data.handler;

import android.os.Bundle;
import android.os.Message;
import android.util.Log;

import pl.mbos.bachelor_thesis.dao.Attention;
import pl.mbos.bachelor_thesis.dao.Blink;
import pl.mbos.bachelor_thesis.dao.Meditation;
import pl.mbos.bachelor_thesis.dao.PoorSignal;
import pl.mbos.bachelor_thesis.dao.PowerEEG;
import pl.mbos.bachelor_thesis.service.data.IPCConnector;
import pl.mbos.bachelor_thesis.service.data.OutboundCommunicationHandler;

/**
 * Created by Mateusz on 08.11.13.
 */
public class DataConnector extends OutboundCommunicationHandler {

    private static final String TAG = "ADSH";

    public void sendAttentionData(Attention[] data) {
        Message msg = getBaseMessage(IPCConnector.DATA_REQ_ATTENTION);
        Bundle bundle = new Bundle();
        bundle.putParcelableArray(IPCConnector.DATA_DATA, data);
        msg.setData(bundle);
        Log.d(TAG, "Send please");
        send(msg);
    }

    public void sendMeditationData(Meditation[] data) {
        Message msg = getBaseMessage(IPCConnector.DATA_REQ_MEDITATION);
        Bundle bundle = new Bundle();
        bundle.putParcelableArray(IPCConnector.DATA_DATA, data);
        msg.setData(bundle);
        Log.d(TAG, "Send please");
        send(msg);
    }

    public void sendBlinkData(Blink[] data) {
        Message msg = getBaseMessage(IPCConnector.DATA_REQ_BLINK);
        Bundle bundle = new Bundle();
        bundle.putParcelableArray(IPCConnector.DATA_DATA, data);
        msg.setData(bundle);
        Log.d(TAG, "Send please");
        send(msg);
    }

    public void sendPowerData(PowerEEG[] data) {
        Message msg = getBaseMessage(IPCConnector.DATA_REQ_POWER);
        Bundle bundle = new Bundle();
        bundle.putParcelableArray(IPCConnector.DATA_DATA, data);
        msg.setData(bundle);
        Log.d(TAG, "Send please");
        send(msg);
    }

    public void sendPoorSignalData(PoorSignal[] data) {
        Message msg = getBaseMessage(IPCConnector.DATA_REQ_POOR_SIGNAL);
        Bundle bundle = new Bundle();
        bundle.putParcelableArray(IPCConnector.DATA_DATA, data);
        msg.setData(bundle);
        Log.d(TAG, "Send please");
        send(msg);
    }

    private Message getBaseMessage(int type) {
        Message msg = Message.obtain();
        msg.arg1 = IPCConnector.TYPE_DATA;
        msg.arg2 = type;
        return msg;
    }

}
