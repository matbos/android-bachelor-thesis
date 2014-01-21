package pl.mbos.bachelor_thesis;

import java.util.List;

import pl.mbos.bachelor_thesis.objs.Attention;
import pl.mbos.bachelor_thesis.objs.Blink;
import pl.mbos.bachelor_thesis.objs.Meditation;
import pl.mbos.bachelor_thesis.objs.PoorSignal;
import pl.mbos.bachelor_thesis.objs.PowerEEG;

public class Container {

	private String token;
	private List<Attention> attentions;
	private List<Meditation> meditations;
	private List<Blink> blinks;
	private List<PowerEEG> powers;
	private List<PoorSignal> signals;

	/*public Container() {
		token = "";
		attentions = Collections.emptyList();
		blinks = Collections.emptyList();
		meditations = Collections.emptyList();
		signals = Collections.emptyList();
		powers = Collections.emptyList();
	}
*/
	public Container(String token, List<Attention> attentions, List<Meditation> meditations, List<Blink> blinks, List<PowerEEG> powers, List<PoorSignal> signals) {
		this.token = token;
		this.attentions = attentions;
		this.meditations = meditations;
		this.blinks = blinks;
		this.powers = powers;
		this.signals = signals;
	}

	public String getToken() {
		return token;
	}

	public List<Attention> getAttentions() {
		return attentions;
	}

	public List<Meditation> getMeditations() {
		return meditations;
	}

	public List<Blink> getBlinks() {
		return blinks;
	}

	public List<PowerEEG> getPowers() {
		return powers;
	}

	public List<PoorSignal> getSignals() {
		return signals;
	}
	
	
}
