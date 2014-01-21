package pl.mbos.bachelor_thesis.web;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.management.InvalidAttributeValueException;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import pl.mbos.bachelor_thesis.Container;
import pl.mbos.bachelor_thesis.objs.Attention;
import pl.mbos.bachelor_thesis.objs.Blink;
import pl.mbos.bachelor_thesis.objs.Meditation;
import pl.mbos.bachelor_thesis.objs.PoorSignal;
import pl.mbos.bachelor_thesis.objs.PowerEEG;

public class DataRequestParser {

	private static int tokenLength = 40;

	public static Container parseRequest(String request, ParseResult result) {
		Container container = null;	
		try {
		JSONObject root = parseRoot(request);
		String token = retrieveToken(root);
		if (token.length() != tokenLength) {
			throw new InvalidAttributeValueException("Token had invalid length ("+token.length()+") it was supposed to be "+tokenLength);
		}
		List<Attention> attentions = retrieveAttentions(root);
		List<Meditation> meditations = retrieveMeditations(root);
		List<Blink> blinks = retrieveBlinks(root);
		List<PowerEEG> powers = retrievePowers(root);
		List<PoorSignal> signals = retrieveSignals(root);
		container = new Container(token, attentions, meditations, blinks, powers, signals);
		} catch (InvalidAttributeValueException |JSONException e) {
			result.outcome = e.getMessage();
			result.success = false;
		}
		return container;
	}

	private static String retrieveToken(JSONObject root) throws JSONException {
		String token = root.getString("token");
		return token;
	}

	private static List<Attention> retrieveAttentions(JSONObject root) throws JSONException {
		List<Attention> attentions;
		attentions = new ArrayList<Attention>(100);
		JSONArray jArray = root.getJSONArray("attentions");
		for (int i = 0; i < jArray.length(); ++i) {
			attentions.add(Attention.parseJSON(jArray.getJSONObject(i)));
		}
		return attentions;
	}

	private static List<Meditation> retrieveMeditations(JSONObject root) throws JSONException {
		List<Meditation> objs;
		objs = new ArrayList<Meditation>(100);
		JSONArray jArray = root.getJSONArray("meditations");
		for (int i = 0; i < jArray.length(); ++i) {
			objs.add(Meditation.parseJSON(jArray.getJSONObject(i)));
		}
		return objs;
	}

	private static List<Blink> retrieveBlinks(JSONObject root) throws JSONException {
		List<Blink> objs;
		objs = new ArrayList<Blink>(100);
		JSONArray jArray = root.getJSONArray("blinks");
		for (int i = 0; i < jArray.length(); ++i) {
			objs.add(Blink.parseJSON(jArray.getJSONObject(i)));
		}
		return objs;
	}

	private static List<PowerEEG> retrievePowers(JSONObject root) throws JSONException {
		List<PowerEEG> objs;
		objs = new ArrayList<PowerEEG>(100);
		JSONArray jArray = root.getJSONArray("powers");
		for (int i = 0; i < jArray.length(); ++i) {
			objs.add(PowerEEG.parseJSON(jArray.getJSONObject(i)));
		}
		return objs;
	}

	private static List<PoorSignal> retrieveSignals(JSONObject root) throws JSONException {
		List<PoorSignal> objs;
		objs = new ArrayList<PoorSignal>(100);
		JSONArray jArray = root.getJSONArray("signals");
		for (int i = 0; i < jArray.length(); ++i) {
			objs.add(PoorSignal.parseJSON(jArray.getJSONObject(i)));
		}
		return objs;
	}

	private static JSONObject parseRoot(String request) throws JSONException {
		JSONObject root;
		root = new JSONObject(request);

		return root;
	}

}
