package pl.mbos.bachelor_thesis.eeg;

import com.neurosky.thinkgear.TGEegPower;
import com.neurosky.thinkgear.TGRawMulti;

/**
 * Created by Mateusz on 12.10.13.
 */
public interface ITGDeviceHandlerListener {

    void reportDeviceConnected();

    void reportState(int state);

    void reportPoorSignal(int level);

    void reportAttention(int level);

    void reportMeditation(int level);

    void reportBlink(int level);

    void reportMulti(TGRawMulti tgRawMulti);

    void reportPower(TGEegPower tgEegPower);
}
