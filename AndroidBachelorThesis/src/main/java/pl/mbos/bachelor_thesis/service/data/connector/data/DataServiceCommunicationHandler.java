package pl.mbos.bachelor_thesis.service.data.connector.data;

import android.os.IBinder;
import android.os.Messenger;

import java.util.List;

import pl.mbos.bachelor_thesis.dao.Attention;
import pl.mbos.bachelor_thesis.dao.Blink;
import pl.mbos.bachelor_thesis.dao.Meditation;
import pl.mbos.bachelor_thesis.dao.PoorSignal;
import pl.mbos.bachelor_thesis.dao.PowerEEG;

/**
 * Handles all communication with {@link pl.mbos.bachelor_thesis.service.data.MainService}
 */
public class DataServiceCommunicationHandler {

    private DataServiceInboundHandler inbound;
    private DataServiceOutboundHandler outbound;
    private DataServiceClient connector;
    private Messenger inboundMessenger;

    public DataServiceCommunicationHandler(DataServiceClient connector) {
        this.connector = connector;
        inbound = new DataServiceInboundHandler(this);
        inboundMessenger = new Messenger(inbound);
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

    public void createOutboundMessenger(IBinder service) {
        outbound = new DataServiceOutboundHandler(new Messenger(service));
    }

    /**
     * Notifies service that you are about to stop listening
     */
    protected void sayGoodbye() {
        outbound.sayGoodbye(inboundMessenger);
    }

    protected void requestAttentionData() {
        outbound.requestAttentionData();
    }

    protected void requestMeditationData() {
        outbound.requestMeditationData();
    }

    protected void requestBlinkData() {
        outbound.requestBlinkData();
    }

    protected void requestPowerData() {
        outbound.requestPowerData();
    }

    protected void requestPoorSignalData() {
        outbound.requestPoorSignalData();
    }

    protected void notifyAttentionDataReceived(List<Attention> data) {
        connector.receivedAttentionData(data);
    }

    protected void notifyMeditationDataReceived(List<Meditation> data) {
        connector.receivedMeditationData(data);
    }

    protected void notifyBlinkDataReceived(List<Blink> data) {
        connector.receivedBlinkData(data);
    }

    protected void notifyPowerDataReceived(List<PowerEEG> data) {
        connector.receivedPowerData(data);
    }

    protected void notifyPoorSignalDataReceived(List<PoorSignal> data) {
        connector.receivedPoorSignalData(data);
    }

}
