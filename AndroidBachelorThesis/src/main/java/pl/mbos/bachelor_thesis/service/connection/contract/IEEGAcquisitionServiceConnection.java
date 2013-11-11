package pl.mbos.bachelor_thesis.service.connection.contract;

/**
 * Interface separating actual connector to service, allowing it to be replaced.
 * Implemented by {@link pl.mbos.bachelor_thesis.service.connection.EEGAcquisitionServiceConnectionConnector}.
 */

public interface IEEGAcquisitionServiceConnection {

    void connectToService(int userID);

    void disconnectFromService();

    void stopService();

    void registerListener(IEEGAcquisitionServiceConnectionListener client);

    boolean unregisterListener(IEEGAcquisitionServiceConnectionListener client);

    void connectToDevice();

    void disconnectFromDevice();

    void startStream();

    void stopStream();

    void requestState();
}
