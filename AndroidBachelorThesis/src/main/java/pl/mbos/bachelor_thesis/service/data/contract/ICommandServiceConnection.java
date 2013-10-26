package pl.mbos.bachelor_thesis.service.data.contract;

/**
 * Created by Mateusz on 25.10.13.
 */
public interface ICommandServiceConnection {

    /**
     * Tells {@link pl.mbos.bachelor_thesis.service.data.DataService} to synchronize
     */
    void synchronize();

    /**
     * Forbids synchronization
     */
    void forbidSynchronization();

    /**
     * Asks whether or not synchronization is in progress,
     * Response is given through {@link pl.mbos.bachelor_thesis.service.data.contract.ICommandServiceConnectionListener}
     */
    void isSynchronizing();

}
