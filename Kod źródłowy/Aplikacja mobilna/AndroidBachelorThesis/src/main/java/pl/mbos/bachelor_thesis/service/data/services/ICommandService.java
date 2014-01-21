package pl.mbos.bachelor_thesis.service.data.services;

/**
 * Created by Mateusz on 08.11.13.
 */
public interface ICommandService {
    /**
     * Sets wheter or not service can synchronize with remote server
     *
     * @param state state to set
     */
    void setSynchronizationPermission(boolean state);

    /**
     * Asks service to provide information if it is allowed to synchronize with remote server
     */
    boolean reportState();

    /**
     * Asks service to provide information if it is currently in process of synchronizing data with remote server.
     */
    boolean reportRunning();

    /**
     * Asks service to provide information if it is allowed to synchronize only on wifi
     *
     * @return true if synchronization on wifi only is permitted, flase if service can synchronize always
     */
    boolean reportAllowance();

    /**
     * Tell service to synchronize only when on given transmission medium
     *
     * @param wifiOnly if set to true, synchronization will occur only over WiFi, otherwise on both Wifi and cellular network
     */
    void synchronizationMedium(boolean wifiOnly);

    /**
     * Tells service to start synchronization now (if it is allowed)
     */
    void synchronizeNow();

    /**
     * Tells object to rewrite addresses used to communicate with webservice
     *
     * @param newAddress base address (e.g. http://182.923.28.22:8800/My_Webservice)
     */
    void recreateAddresses(String newAddress);
}
