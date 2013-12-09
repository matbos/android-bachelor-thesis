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
        outbound.synchronize();
    }

    public void setSynchronization(boolean synchronize) {
        outbound.setSyncState(synchronize);
    }

    public void requestSyncState() {
        if (outbound != null) {
            outbound.requestSyncState();
        }
    }

    public void requestSyncProgress() {
        outbound.requestSyncProgress();
    }

    public void sayGoodbye() {
        outbound.sayGoodbye(inboundMessenger);
    }

    public void setNewEndpointAddress(String address) {
        outbound.sendNewEndpointAddress(address);
    }
}
