package pl.mbos.bachelor_thesis.service.data.services;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Date;

/**
 * Created by Mateusz on 01.12.13.
 */
public class SyncTimes {

    public Date attention;
    public Date meditation;
    public Date power;
    public Date blink;
    public Date poorSignal;

    public SyncTimes(Date attention, Date meditation, Date power, Date blink, Date poorSignal){
        this.attention = attention;
        this.meditation = meditation;
        this.power = power;
        this.blink = blink;
        this.poorSignal = poorSignal;
    }

    public SyncTimes(String jsonString) throws JSONException {
        this(new JSONObject(jsonString));
    }

    public SyncTimes(JSONObject json) throws JSONException {
        attention = new Date(json.getLong("attention"));
        meditation = new Date(json.getLong("meditation"));
        power= new Date(json.getLong("power"));
        blink = new Date(json.getLong("blink"));
        poorSignal = new Date(json.getLong("poorSignal"));
    }

}
