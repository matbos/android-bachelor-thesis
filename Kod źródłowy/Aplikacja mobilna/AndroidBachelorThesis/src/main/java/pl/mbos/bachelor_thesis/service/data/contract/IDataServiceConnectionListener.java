package pl.mbos.bachelor_thesis.service.data.contract;

import java.util.List;

import pl.mbos.bachelor_thesis.dao.Attention;
import pl.mbos.bachelor_thesis.dao.Blink;
import pl.mbos.bachelor_thesis.dao.Meditation;
import pl.mbos.bachelor_thesis.dao.PoorSignal;
import pl.mbos.bachelor_thesis.dao.PowerEEG;

/**
 * Created by Mateusz on 25.10.13.
 */
public interface IDataServiceConnectionListener {

    void receivedAttentionData(List<Attention> data);

    void receivedMeditationData(List<Meditation> data);

    void receivedBlinkData(List<Blink> data);

    void receivedPowerData(List<PowerEEG> data);

    void receivedPoorSignalData(List<PoorSignal> data);

}
