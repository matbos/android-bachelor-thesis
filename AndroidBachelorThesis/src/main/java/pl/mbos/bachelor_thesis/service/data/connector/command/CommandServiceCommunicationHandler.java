package pl.mbos.bachelor_thesis.service.data.connector.command;

import android.os.IBinder;
import android.os.Messenger;

/**
 * Handles all communication with {@link pl.mbos.bachelor_thesis.service.data.MainService}
 */
public class CommandServiceCommunicationHandler {

    CommandServiceClient connector;
    public CommandServiceInboundHandler inbound;
    public CommandServiceOutboundHandler outbound;
    private Messenger inboundMessenger;

    public CommandServiceCommunicationHandler(CommandServiceClient connector) {
        this.connector = connector;
        inbound = new CommandServiceInboundHandler(this);
        inboundMessenger = new Messenger(inbound);
    }

    public void createOutboundMessenger(IBinder service) {
        outbound = new CommandServiceOutboundHandler(new Messenger(service));
    }

    public Messenger getInboundMessenger() {
        return inboundMessenger;
    }

    public Messenger getOutboundMessenger() {
        if (outbound != null) {
            return outbound.getMessenger();
        } else {
            return null;
        }
    }

    public boolean isConnectedToService() {
        return getOutboundMessenger() != null;
    }

    public void synchronize() {
        if (outbound != null) {
            outbound.synchronize();
        }
    }

    public void setSynchronization(boolean synchronize) {
        if (outbound != null) {
            outbound.setSyncState(synchronize);
        }
    }

    public void requestSyncState() {
        if (outbound != null) {
            outbound.requestSyncState();
        }
    }

    public void requestSyncAllowance() {
        if (outbound != null) {
            outbound.requestSyncAllowance();
        }
    }

    public void requestSyncProgress() {
        if (outbound != null) {
            outbound.requestSyncProgress();
        }
    }

    public void sayGoodbye() {
        if (outbound != null) {
            outbound.sayGoodbye(inboundMessenger);
        }
    }

    public void setNewEndpointAddress(String address) {
        if (outbound != null) {
            outbound.sendNewEndpointAddress(address);
        }
    }

    public void setSyncMedium(boolean wifiOnly) {
        if (outbound != null) {
            outbound.setSyncMedium(wifiOnly);
        }
    }


    protected void reportState(boolean value) {
        connector.reportState(value);
    }

    protected void reportAllowance(boolean value) {
        connector.reportAllowance(value);
    }

    protected void reportRunning(boolean value) {
        connector.reportRunning(value);
    }
}
