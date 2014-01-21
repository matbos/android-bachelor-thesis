package pl.mbos.bachelor_thesis.db;

import java.util.List;

import pl.mbos.bachelor_thesis.objs.*;

public class DataContainer {
	private List<Attention> attentions;
	private List<Meditation> meditations;
	private List<Blink> blinks;
	private List<PowerEEG> powers;
	private List<PoorSignal> signals;
	private List<User> users;

	public DataContainer() {

	}

	public DataContainer(List<Attention> attentions, List<Meditation> meditations, List<Blink> blinks, List<PowerEEG> powers, List<PoorSignal> signals, List<User> users) {
		this.attentions = attentions;
		this.meditations = meditations;
		this.blinks = blinks;
		this.powers = powers;
		this.signals = signals;
		this.users = users;
	}

	public List<Attention> getAttentions() {
		return attentions;
	}

	public void setAttentions(List<Attention> attentions) {
		this.attentions = attentions;
	}

	public List<Meditation> getMeditations() {
		return meditations;
	}

	public void setMeditations(List<Meditation> meditations) {
		this.meditations = meditations;
	}

	public List<Blink> getBlinks() {
		return blinks;
	}

	public void setBlinks(List<Blink> blinks) {
		this.blinks = blinks;
	}

	public List<PowerEEG> getPowers() {
		return powers;
	}

	public void setPowers(List<PowerEEG> powers) {
		this.powers = powers;
	}

	public List<PoorSignal> getSignals() {
		return signals;
	}

	public void setSignals(List<PoorSignal> signals) {
		this.signals = signals;
	}

	public List<User> getUsers() {
		return users;
	}

	public void setUsers(List<User> users) {
		this.users = users;
	}
}
