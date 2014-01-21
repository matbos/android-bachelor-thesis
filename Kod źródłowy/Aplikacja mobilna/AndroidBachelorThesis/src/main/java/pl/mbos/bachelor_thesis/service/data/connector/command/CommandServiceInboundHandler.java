package pl.mbos.bachelor_thesis.service.data.connector.command;

import android.os.Handler;
import android.os.Message;
import android.util.Log;

import pl.mbos.bachelor_thesis.service.data.IPCConnector;

/**
 * Handles communicates received from {@link pl.mbos.bachelor_thesis.service.data.MainService}
 */
public class CommandServiceInboundHandler extends Handler {
    private static final String TAG = CommandServiceInboundHandler.class.getSimpleName();

    private CommandServiceCommunicationHandler communicationHandler;

    public CommandServiceInboundHandler(CommandServiceCommunicationHandler communicationHandler) {
        this.communicationHandler = communicationHandler;
    }

    @Override
    public void handleMessage(Message msg) {
        try {
            if (msg.arg1 == IPCConnector.TYPE_COMMAND) {
                boolean val;
                switch (msg.arg2) {
                    case IPCConnector.CMD_REPORT_STATE:
                        val = msg.getData().getBoolean(IPCConnector.CMD_REPORT_STATE_MSG);
                        communicationHandler.reportState(val);
                        break;
                    case IPCConnector.CMD_REPORT_ALLOWANCE:
                        val = msg.getData().getBoolean(IPCConnector.CMD_REPORT_ALLOWANCE_MSG);
                        communicationHandler.reportAllowance(val);
                        break;
                    case IPCConnector.CMD_REPORT_RUNNING:
                        val = msg.getData().getBoolean(IPCConnector.CMD_REPORT_RUNNING_MSG);
                        communicationHandler.reportRunning(val);
                        break;
                    default:
                        super.handleMessage(msg);
                }
            }
        } catch (NullPointerException e) {
            Log.e(TAG, e.getMessage());
        }
    }
}
