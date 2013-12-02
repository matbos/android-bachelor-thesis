package pl.mbos.bachelor_thesis.view;

/**
 * Created with IntelliJ IDEA.
 * User: Mateusz Boś
 * Date: 02.10.13
 * Time: 23:34
 */
public interface MainView {

    void setState(String state);
    void setMeditation(int value);
    void setAttention(int  value);
    void setPoorSignal(int value);
    void requestBluetooth();
    void showBluetoothRequirementMessage();
    long getUserID();
}
