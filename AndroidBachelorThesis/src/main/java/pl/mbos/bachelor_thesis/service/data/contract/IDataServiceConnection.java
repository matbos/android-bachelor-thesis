package pl.mbos.bachelor_thesis.service.data.contract;

import java.util.List;

import pl.mbos.bachelor_thesis.dao.Attention;
import pl.mbos.bachelor_thesis.dao.Blink;
import pl.mbos.bachelor_thesis.dao.Meditation;
import pl.mbos.bachelor_thesis.dao.PoorSignal;
import pl.mbos.bachelor_thesis.dao.PowerEEG;

/**
 * Interface implemented by object
 */
public interface IDataServiceConnection {

    void requestAllAttentionData();
    void requestAllMeditationData();
    void requestAllBlinkData();
    void requestAllPowerData();
    void requestAllPoorSignalData();


}
