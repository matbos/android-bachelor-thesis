package pl.mbos.bachelor_thesis.eeg;

import android.os.Handler;
import android.os.Message;

import com.neurosky.thinkgear.TGDevice;
import com.neurosky.thinkgear.TGEegPower;
import com.neurosky.thinkgear.TGRawMulti;

public class TGDeviceHandler extends Handler {

    private ITGDeviceHandlerListener listener;

    public TGDeviceHandler( ITGDeviceHandlerListener tgDevice) {
        this.listener = tgDevice;
    }

    @Override
    public void handleMessage(Message msg) {
        switch (msg.what) {
            case TGDevice.MSG_STATE_CHANGE:
                switch (msg.arg1) {
                    case TGDevice.STATE_IDLE:
                        listener.reportState(TGDevice.STATE_IDLE);
                        break;
                    case TGDevice.STATE_CONNECTING:
                        listener.reportState(TGDevice.STATE_CONNECTING);
                        break;
                    case TGDevice.STATE_CONNECTED:
                        listener.reportDeviceConnected();
                        break;
                    case TGDevice.STATE_DISCONNECTED:
                        listener.reportState(TGDevice.STATE_DISCONNECTED);
                        break;
                    case TGDevice.STATE_NOT_FOUND:
                        listener.reportState(TGDevice.STATE_NOT_FOUND);
                        break;
                    case TGDevice.STATE_NOT_PAIRED:
                        listener.reportState(TGDevice.STATE_NOT_PAIRED);
                        break;
                    default:
                        break;
                }
                break;
            case TGDevice.MSG_POOR_SIGNAL:
                listener.reportPoorSignal(msg.arg1);
            case TGDevice.MSG_ATTENTION:
                listener.reportAttention(msg.arg1);
                break;
            case TGDevice.MSG_MEDITATION:
                listener.reportMeditation(msg.arg1);
                break;
            case TGDevice.MSG_RAW_DATA:
                int rawValue = msg.arg1;
                break;
            case TGDevice.MSG_RAW_MULTI:
                listener.reportMulti((TGRawMulti) msg.obj);
            case TGDevice.MSG_EEG_POWER:
                listener.reportPower((TGEegPower) msg.obj);
            default:
                break;
        }
    }
}