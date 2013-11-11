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
     * Asks whether or not synchronization is in progress,
     * Response is given through {@link pl.mbos.bachelor_thesis.service.data.contract.ICommandServiceConnectionListener}
     */
    void isSynchronizing();

    /**
     * Connects to service
     */
    void connectToService();

}
