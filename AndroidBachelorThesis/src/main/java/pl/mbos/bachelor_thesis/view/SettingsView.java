package pl.mbos.bachelor_thesis.view;

/**
 * Created with IntelliJ IDEA.
 * User: Mateusz Boś
 * Date: 02.10.13
 * Time: 23:34
 */
public interface SettingsView {

    void setState(String state);
    void setPoorSignal(int value);
    void headsetProblem(boolean is);
    void showHeadsetMessage(String msg, int time);
    void requestBluetooth();
    void showBluetoothRequirementMessage();
    long getUserID();
    void setSynchronizing(boolean synchronizing);
    void goBackToLoginPage();
    void setSyncRepresentation(boolean state);
    void setSyncAllowance(boolean allowance);
}
