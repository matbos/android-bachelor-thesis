package pl.mbos.bachelor_thesis.service.data.contract;

/**
 * Created by Mateusz on 25.10.13.
 */
public interface ICommandServiceConnection {

    /**
     * Tells {@link pl.mbos.bachelor_thesis.service.data.MainService} to synchronize now
     */
    void synchronize();

    /**
     * Permits or denies synchronization depending on value of the parameter
     * @param synchronize if true synchronization is permitted else forbidden
     */
    void setSynchronization(boolean synchronize);

    /**
     * Asks whether or not synchronization is permitted,
     * Response is given through {@link pl.mbos.bachelor_thesis.service.data.contract.ICommandServiceConnectionListener}
     */
    void isSynchronizationPermitted();

    /**
     * Asks whether synchronization is only allowed when in wifis range
     * Response is given through {@link pl.mbos.bachelor_thesis.service.data.contract.ICommandServiceConnectionListener}
     */
    void isSynchronizationOnlyOnWifi();

    /**
     * Asks whether or not synchronization is in progress,
     * Response is given through {@link pl.mbos.bachelor_thesis.service.data.contract.ICommandServiceConnectionListener}
     */
    void isSynchronizing();

    /**
     * Connects to service
     */
    void connectToService();

    /**
     * Tell service to synchronize only when on given transmission medium
     *
     * @param wifiOnly if set to true, synchronization will occur only over WiFi, otherwise on both Wifi and cellular network
     */
    void setSynchronizationMedium(boolean wifiOnly);

    /**
     * Tell service to switch to new address
     *
     * @param newAddress address of new endpoint
     */
    void setNewEndpoint(String newAddress);

    void registerListener(ICommandServiceConnectionListener listener);

    boolean unregisterListener(ICommandServiceConnectionListener listener);
}
