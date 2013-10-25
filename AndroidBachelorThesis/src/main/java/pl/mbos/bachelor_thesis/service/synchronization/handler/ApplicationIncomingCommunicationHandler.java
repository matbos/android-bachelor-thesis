package pl.mbos.bachelor_thesis.service.synchronization.handler;

import android.os.Handler;
import android.os.Message;
import android.util.Log;

import pl.mbos.bachelor_thesis.service.connection.EEGAcquisitionService;

import static pl.mbos.bachelor_thesis.service.connection.EEGAcquisitionService.CONNECT_TO_DEVICE;
import static pl.mbos.bachelor_thesis.service.connection.EEGAcquisitionService.DISCONNECT_FROM_DEVICE;
import static pl.mbos.bachelor_thesis.service.connection.EEGAcquisitionService.REQUEST_STATE;
import static pl.mbos.bachelor_thesis.service.connection.EEGAcquisitionService.START_STREAM;
import static pl.mbos.bachelor_thesis.service.connection.EEGAcquisitionService.STOP_SERVICE;
import static pl.mbos.bachelor_thesis.service.connection.EEGAcquisitionService.STOP_STREAM;

/**
 *
 */
public class ApplicationIncomingCommunicationHandler extends Handler {

    private static final String TAG = ApplicationIncomingCommunicationHandler.class.getSimpleName();
    private EEGAcquisitionService parent;

    public ApplicationIncomingCommunicationHandler(EEGAcquisitionService parent){
        this.parent = parent;
    }

    @Override
    public void handleMessage(Message msg) {
        int action = msg.arg1;
        if (action == STOP_SERVICE) {
            parent.serviceStopSelf();
        } else if (action == DISCONNECT_FROM_DEVICE) {
            parent.disconnectDevice();
        } else if (action == CONNECT_TO_DEVICE) {
            parent.connectDevice();
        } else if (action == START_STREAM) {
            parent.startDeviceStream();
        } else if (action == STOP_STREAM) {
            parent.stopDeviceStream();
        } else if (action == REQUEST_STATE) {
            parent.requestDeviceState();
        } else {
            Log.i(TAG, "Recieved unknown message " + msg.arg1);
            throw new RuntimeException(TAG + " Recieved unknown message "+ msg.arg1);
        }
    }
}
