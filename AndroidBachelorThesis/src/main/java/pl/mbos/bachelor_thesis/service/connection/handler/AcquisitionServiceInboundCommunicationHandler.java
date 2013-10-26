package pl.mbos.bachelor_thesis.service.connection.handler;

import android.os.Handler;
import android.os.Message;
import android.util.Log;

import pl.mbos.bachelor_thesis.service.connection.EEGAcquisitionService;

import static pl.mbos.bachelor_thesis.service.connection.EEGAcquisitionService.*;



//TODO: Refactor it
// ----------------------------------------------------------------------------------
// Rethink that approach!
// ----------------------------------------------------------------------------------
// Refactor it so that it aggregates two classes, one to handle incoming messages and
// other to handle sending messages to the application
//

/**
 *
 */
public class AcquisitionServiceInboundCommunicationHandler extends Handler {

    private static final String TAG = AcquisitionServiceInboundCommunicationHandler.class.getSimpleName();
    private EEGAcquisitionService parent;

    public AcquisitionServiceInboundCommunicationHandler(EEGAcquisitionService parent){
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
