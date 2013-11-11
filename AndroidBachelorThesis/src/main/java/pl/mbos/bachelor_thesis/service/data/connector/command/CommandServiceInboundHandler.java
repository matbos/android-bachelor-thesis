package pl.mbos.bachelor_thesis.service.data.connector.command;

import android.os.Handler;
import android.os.Message;

/**
 * Handles communicates received from {@link pl.mbos.bachelor_thesis.service.data.MainService}
 *
 */
public class CommandServiceInboundHandler extends Handler {


    private CommandServiceCommunicationHandler communicationHandler;

    public CommandServiceInboundHandler(CommandServiceCommunicationHandler communicationHandler){
        this.communicationHandler = communicationHandler;
    }


    @Override
    public void handleMessage(Message msg) {
        super.handleMessage(msg);
    }
}
