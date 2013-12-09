package pl.mbos.bachelor_thesis.service.data.services;

import android.content.res.Resources;
import android.os.AsyncTask;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;

import javax.inject.Inject;

import pl.mbos.bachelor_thesis.BaseApplication;
import pl.mbos.bachelor_thesis.R;
import pl.mbos.bachelor_thesis.dao.Attention;
import pl.mbos.bachelor_thesis.dao.Blink;
import pl.mbos.bachelor_thesis.dao.Meditation;
import pl.mbos.bachelor_thesis.dao.PoorSignal;
import pl.mbos.bachelor_thesis.dao.PowerEEG;
import pl.mbos.bachelor_thesis.service.data.remote.BaseService;
import pl.mbos.bachelor_thesis.service.data.remote.Response;

/**
 * Service object that handles commands regarding functioning of DataService
 * Also, what can be unintuitive takes care of synchronization of data
 */
public class CommandService implements ICommandService {

    @Inject
    BaseService baseService;
    @Inject
    Resources resources;
    private boolean allowedSynchronization;
    private boolean synchronizing;
    private DataService dataService;
    private Timer timer;
    private String dataAddress;
    private String lastDataAddress;
    private boolean wifiOnly;


    public CommandService(DataService dataService) {
        BaseApplication.getBaseGraph().inject(this);
        recreateAddresses(resources.getString(R.string.webservice_base));
        this.dataService = dataService;
    }

    @Override
    public void setSynchronizationPermission(boolean state) {
        if (state == true) {
            // 4800000 == 30 minut
            if (timer == null) {
                timer = new Timer();
                timer.schedule(new SyncTimerTask(), 5000);
            }
        } else {
            if (timer != null) {
                timer.cancel();
                timer = null;
            }
        }
        allowedSynchronization = state;
    }

    @Override
    public boolean reportState() {
        return allowedSynchronization;
    }

    @Override
    public boolean reportRunning() {
        return synchronizing;
    }

    @Override
    public void synchronizationMedium(boolean wifiOnly) {
        this.wifiOnly = wifiOnly;
    }

    @Override
    public void synchronizeNow() {
        if (allowedSynchronization) {
            beginSynchronization();
        }
    }

    @Override
    public void recreateAddresses(String baseAddress) {
        dataAddress = baseAddress + resources.getString(R.string.webservice_data);
        lastDataAddress = baseAddress + resources.getString(R.string.webservice_data) + resources.getString(R.string.webservice_last);
    }

    private void beginSynchronization() {
        HashMap<String, Object> params = new HashMap<String, Object>(1);
        params.put("userID", "1");
        DataGetTask dgt = new DataGetTask(lastDataAddress, params);
        dgt.execute();
    }

    private void isolateRequiredData(SyncTimes from) {
        DataSyncPackage pack = new DataSyncPackage(from);
        dataService.retrieveData(pack);
        DataPutTask dpt = new DataPutTask(dataAddress, pack);
        dpt.execute();
    }

    private boolean checkResponse(Response response) {
        return response.getCode() == java.net.HttpURLConnection.HTTP_OK;
    }
    private class SyncTimerTask extends TimerTask {
        @Override
        public void run() {
            synchronizeNow();
        }
    }

    private class DataGetTask extends AsyncTask<Void, Void, Response> {

        String address;
        private HashMap<String, Object> params;

        private DataGetTask(String address, HashMap<String, Object> params) {
            this.address = address;
            this.params = params;
        }

        @Override
        protected Response doInBackground(Void... p) {
            Response response = baseService.getJSONRequest(params, lastDataAddress);
            return response;
        }

        @Override
        protected void onPostExecute(Response response) {
            try {
                if (checkResponse(response)) {
                    SyncTimes lastTimes = new SyncTimes(response.getBody());
                    isolateRequiredData(lastTimes);
                } else {
                    // powiadomienie o błedzie
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    private class DataPutTask extends AsyncTask<Void, Void, Response> {

        private final String address;
        private final DataSyncPackage syncPackage;
        private JSONObject json;

        public DataPutTask(String address, DataSyncPackage syncPackage) {
            this.address = address;
            this.syncPackage = syncPackage;
        }

        @Override
        protected void onPreExecute() {
            json = new JSONObject();
            DataPackage data = syncPackage.getData();
            //TODO: fix the token thing
            String token = "a23e567f9012e4ef7890acde5678901234567890";
            try {
                String at = Attention.toJSONArray(data.attentions);
                JSONArray attentions = new JSONArray(at);
                String mt = Meditation.toJSONArray(data.meditations);
                JSONArray meditations = new JSONArray(mt);
                String bt = Blink.toJSONArray(data.blinks);
                JSONArray blinks = new JSONArray(bt);
                String pt = PowerEEG.toJSONArray(data.powers);
                JSONArray powers = new JSONArray(pt);
                String st = PoorSignal.toJSONArray(data.signals);
                JSONArray signals = new JSONArray(st);

                json.put("token", token);
                json.put("attentions", attentions);
                json.put("meditations", meditations);
                json.put("blinks", blinks);
                json.put("powers", powers);
                json.put("signals", signals);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        @Override
        protected Response doInBackground(Void... params) {
            Response response = baseService.putJSONRequest(json, dataAddress);
            return response;
        }

        @Override
        protected void onPostExecute(Response o) {
            super.onPostExecute(o);
        }
    }


}
