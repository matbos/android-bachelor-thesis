package pl.mbos.bachelor_thesis.service.data.services;

import java.util.List;

import pl.mbos.bachelor_thesis.dao.Attention;
import pl.mbos.bachelor_thesis.dao.Blink;
import pl.mbos.bachelor_thesis.dao.Meditation;
import pl.mbos.bachelor_thesis.dao.PoorSignal;
import pl.mbos.bachelor_thesis.dao.PowerEEG;

/**
 * Created by Mateusz on 01.12.13.
 */
public class DataPackage {
    public List<Attention> attentions;
    public List<Meditation> meditations;
    public List<PowerEEG> powers;
    public List<PoorSignal> signals;
    public List<Blink> blinks;

    public DataPackage(){

    }

    public DataPackage(List<Attention> attentions, List<Meditation> meditations, List<PowerEEG> powers, List<PoorSignal> signals, List<Blink> blinks){
        this.attentions = attentions;
        this.meditations = meditations;
        this.powers = powers;
        this.signals = signals;
        this.blinks = blinks;
    }
}
