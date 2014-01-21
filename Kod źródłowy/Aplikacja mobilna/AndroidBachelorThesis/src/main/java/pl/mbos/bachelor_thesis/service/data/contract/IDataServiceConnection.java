package pl.mbos.bachelor_thesis.service.data.contract;

/**
 * Interface implemented by object
 */
public interface IDataServiceConnection {

    void requestAllAttentionData();
    void requestAllMeditationData();
    void requestAllBlinkData();
    void requestAllPowerData();
    void requestAllPoorSignalData();


    void registerListener(IDataServiceConnectionListener listener);

    boolean unregisterListener(IDataServiceConnectionListener listener);
}
