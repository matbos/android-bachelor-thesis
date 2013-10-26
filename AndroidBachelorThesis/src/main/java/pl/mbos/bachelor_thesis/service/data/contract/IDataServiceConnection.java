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

    List<Attention> getAllAttentionData();
    List<Meditation> getAllMeditationData();
    List<Blink> getAllBlinkData();
    List<PowerEEG> getAllPowerData();
    List<PoorSignal> getAllPoorSignalData();


}
