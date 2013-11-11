package pl.mbos.bachelor_thesis.service.data.contract;

/**
 * Created by Mateusz on 25.10.13.
 */
public interface ICommandServiceConnectionListener {
    /**
     *  Informs whether or not data synchronization is in progress
     * @param synchronizing true if synchronization is in progress false otherwise
     */
    void isSynchronizing(boolean synchronizing);

    /**
     * Informs whether synchronization can be performed only when in range of WiFi network
     * @param wifi true if only when WiFi in range false otherwise
     */
    void onlyWiFi(boolean wifi);
}
