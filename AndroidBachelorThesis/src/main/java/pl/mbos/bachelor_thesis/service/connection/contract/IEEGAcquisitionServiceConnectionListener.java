package pl.mbos.bachelor_thesis.service.connection.contract;

/**
 * Created with IntelliJ IDEA.
 * User: Mateusz Boś
 * Date: 02.10.13
 * Time: 23:47
 */

/**
 * Interface implemented by objects that are using {@link pl.mbos.bachelor_thesis.service.connection.EEGAcquisitionServiceConnectionConnector}
 * to be notified about changes and occurring events.
 */
public interface IEEGAcquisitionServiceConnectionListener {
    /**
     * Method invoked when connection to service is established
     */
    public void onConnect();
    /**
     * Method invoked when connection to service has been unexpectedly closed
     * @param reason Reason of the closure in the form of text
     */
    public void onServiceDisconnected(String reason);
    /**
     * Invoked when state report is available
     * @param state state of the device
     */
    public void reportState(String state);
    /**
     * Invoked when new reading of poor signal gauge is available
     * @param value value of given gauge
     */
    public void reportPoorSignal(int value);

    /**
     * Invoked when bluetooth communication is not enabled
     */
    public void reportBluetoothRequest();

}
