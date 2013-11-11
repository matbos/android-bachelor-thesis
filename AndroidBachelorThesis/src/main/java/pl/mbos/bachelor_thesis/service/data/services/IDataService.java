package pl.mbos.bachelor_thesis.service.data.services;

import java.util.ArrayList;

import pl.mbos.bachelor_thesis.dao.Attention;
import pl.mbos.bachelor_thesis.dao.Blink;
import pl.mbos.bachelor_thesis.dao.Meditation;
import pl.mbos.bachelor_thesis.dao.PoorSignal;
import pl.mbos.bachelor_thesis.dao.PowerEEG;

/**
 * Created by Mateusz on 08.11.13.
 */
public interface IDataService {

    ArrayList<Attention> getAllAttentionData();

    ArrayList<Meditation> getAllMeditationData();

    ArrayList<Blink> getAllBlinkData();

    ArrayList<PowerEEG> getAllPowerData();

    ArrayList<PoorSignal> getAllPoorSignalData();

    void addAttentionData(Attention data);

    void addMeditationData(Meditation data);

    void addBlinkData(Blink data);

    void addPowerData(PowerEEG data);

    void addPoorSignalData(PoorSignal data);
}
