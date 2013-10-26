package pl.mbos.bachelor_thesis.service.data.connector.command;

import android.os.Handler;
import android.os.Message;

/**
 * Handles communicates received from {@link pl.mbos.bachelor_thesis.service.data.DataService}
 *
 */
public class ServiceInboundCommunicationHandler extends Handler {


    public ServiceInboundCommunicationHandler(){

    }


    @Override
    public void handleMessage(Message msg) {
        super.handleMessage(msg);
    }
}
