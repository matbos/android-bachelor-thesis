package pl.mbos.bachelor_thesis.service.data.services;

import android.util.Log;

import java.util.ArrayList;
import java.util.Date;

import pl.mbos.bachelor_thesis.dao.Attention;
import pl.mbos.bachelor_thesis.dao.Blink;
import pl.mbos.bachelor_thesis.dao.Meditation;
import pl.mbos.bachelor_thesis.dao.PoorSignal;
import pl.mbos.bachelor_thesis.dao.PowerEEG;

/**
 * Service object that handles data manipulation
 */
public class DataService implements IDataService {

    private static final String TAG = DataService.class.getSimpleName();
    private IDataServiceParent parent;

    public DataService(IDataServiceParent parent) {
        this.parent = parent;
    }


    @Override
    public ArrayList<Attention> getAllAttentionData() {
        //TODO: Implement it!
        Log.i(TAG, "All Attention requested");
        ArrayList<Attention> data = new ArrayList<Attention>(1);
        data.add(new Attention(1, 1, new Date()));
        return data;
    }

    @Override
    public ArrayList<Meditation> getAllMeditationData() {
        //TODO: Implement it!
        ArrayList<Meditation> data = new ArrayList<Meditation>(1);
        data.add(new Meditation(1, 1, new Date()));
        return data;
    }

    @Override
    public ArrayList<Blink> getAllBlinkData() {
        //TODO: Implement it!
        ArrayList<Blink> data = new ArrayList<Blink>(1);
        data.add(new Blink(1, 1, new Date()));
        return data;
    }

    @Override
    public ArrayList<PowerEEG> getAllPowerData() {
        //TODO: Implement it!
        ArrayList<PowerEEG> data = new ArrayList<PowerEEG>(1);
        data.add(new PowerEEG(1, 1, 1, 1, 1, 1, 1, 1, 1, new Date()));
        return data;
    }

    @Override
    public ArrayList<PoorSignal> getAllPoorSignalData() {
        //TODO: Implement it!
        ArrayList<PoorSignal> data = new ArrayList<PoorSignal>(1);
        data.add(new PoorSignal(1, 1, new Date()));
        return data;
    }

    @Override
    public void addAttentionData(Attention data) {
        //TODO: Implement it!
        Log.i(TAG, "Kindly add attention data " + data);
    }

    @Override
    public void addMeditationData(Meditation data) {
        //TODO: Implement it!
        Log.i(TAG, "Kindly add Meditation data " + data);
    }

    @Override
    public void addBlinkData(Blink data) {
        //TODO: Implement it!
        Log.i(TAG, "Kindly add Blink data " + data);
    }

    @Override
    public void addPowerData(PowerEEG data) {
        //TODO: Implement it!
        Log.i(TAG, "Kindly add PowerEEG data " + data);
    }

    @Override
    public void addPoorSignalData(PoorSignal data) {
        //TODO: Implement it!
        Log.i(TAG, "Kindly add PoorSignal data " + data);
    }
}
