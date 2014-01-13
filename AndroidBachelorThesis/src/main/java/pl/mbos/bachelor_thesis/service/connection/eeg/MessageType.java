package pl.mbos.bachelor_thesis.service.connection.eeg;

import com.neurosky.thinkgear.TGDevice;

/**
 * Created with IntelliJ IDEA.
 * User: Mateusz Boś
 * Date: 18.09.13
 * Time: 22:57
 */
public enum MessageType {
    STATE_IDLE(TGDevice.STATE_IDLE),
    STATE_CONNECTING(TGDevice.STATE_CONNECTING),
    STATE_CONNECTED(TGDevice.STATE_CONNECTED),
    STATE_DISCONNECTED(TGDevice.STATE_DISCONNECTED),
    STATE_NOT_FOUND(TGDevice.STATE_NOT_FOUND),
    STATE_NOT_PAIRED(TGDevice.STATE_NOT_PAIRED),
    MSG_STATE_CHANGE(TGDevice.MSG_STATE_CHANGE),
    MSG_POOR_SIGNAL(TGDevice.MSG_POOR_SIGNAL),
    MSG_HEART_RATE(TGDevice.MSG_HEART_RATE),
    MSG_ATTENTION(TGDevice.MSG_ATTENTION),
    MSG_MEDITATION(TGDevice.MSG_MEDITATION),
    MSG_EGO_TRIM(TGDevice.MSG_EGO_TRIM),
    MSG_BLINK(TGDevice.MSG_BLINK),
    MSG_RAW_DATA(TGDevice.MSG_RAW_DATA),
    MSG_EEG_POWER(TGDevice.MSG_EEG_POWER),
    MSG_RAW_MULTI(TGDevice.MSG_RAW_MULTI),
    MSG_THINKCAP_RAW(TGDevice.MSG_THINKCAP_RAW),
    MSG_RAW_COUNT(TGDevice.MSG_RAW_COUNT),
    MSG_LOW_BATTERY(TGDevice.MSG_LOW_BATTERY),
    MSG_EKG_IDENTIFIED(TGDevice.MSG_EKG_IDENTIFIED),
    MSG_EKG_TRAINED(TGDevice.MSG_EKG_TRAINED),
    MSG_EKG_TRAIN_STEP(TGDevice.MSG_EKG_TRAIN_STEP),
    MSG_SLEEP_STAGE(TGDevice.MSG_SLEEP_STAGE);

    private int value;

    private MessageType(int value){
        this.value = value;
    }


}
