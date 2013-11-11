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
     * Tells service to start synchronization now (if it is allowed)
     */
    void synchronizeNow();
}
