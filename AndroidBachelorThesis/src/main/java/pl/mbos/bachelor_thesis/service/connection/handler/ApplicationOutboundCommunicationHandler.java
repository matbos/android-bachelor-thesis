package pl.mbos.bachelor_thesis.service.connection.handler;

import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.util.Log;

import pl.mbos.bachelor_thesis.service.connection.EEGAcquisitionService;
import pl.mbos.bachelor_thesis.service.connection.EEGAcquisitionServiceConnectionConnector;

/**
 * Created by Mateusz on 20.10.13.
 */

/**
 * Class handles all communication that origins in {@link pl.mbos.bachelor_thesis.service.connection.EEGAcquisitionService}
 * and goes to a bounded listener (most probably {@link pl.mbos.bachelor_thesis.service.connection.EEGAcquisitionServiceConnectionConnector}
 */
public class ApplicationOutboundCommunicationHandler {

    private static final String TAG = ApplicationOutboundCommunicationHandler.class.getSimpleName();
    protected Messenger messenger;

    /**
     * Constructor that takes Messenger object <u><b>to</b></u> bounded class
     * @param messenger Messenger object <u><b>to</b></u> bounded class
     */
    public ApplicationOutboundCommunicationHandler(Messenger messenger) {
        this.messenger = messenger;
    }

    public void sendReturnMessage(Message msg) {
        try {
            messenger.send(msg);
        } catch (RemoteException e) {
            Log.e(TAG, e.getMessage());
        }
    }

    public static Message buildValueMessage(int gauge, int value) {
        Message msg = Message.obtain();
        msg.arg1 = EEGAcquisitionServiceConnectionConnector.REPORT_VALUE + gauge;
        msg.arg2 = value;
        return msg;
    }

    public static Message buildStateMessage(int state) {
        Message msg = Message.obtain();
        msg.arg1 = EEGAcquisitionServiceConnectionConnector.REPORT_STATE;
        msg.arg2 = state;
        return msg;
    }

    public static Message buildRequestMessage() {
        Message msg = Message.obtain();
        msg.arg1 = EEGAcquisitionService.REQUEST_BLUETOOTH;
        return msg;
    }

    public void requestBluetooth() {
        sendReturnMessage(buildRequestMessage());
    }
}
