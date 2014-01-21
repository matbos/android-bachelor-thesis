package pl.mbos.bachelor_thesis.service.data.connector.data;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import pl.mbos.bachelor_thesis.dao.Attention;
import pl.mbos.bachelor_thesis.dao.Blink;
import pl.mbos.bachelor_thesis.dao.Meditation;
import pl.mbos.bachelor_thesis.dao.PoorSignal;
import pl.mbos.bachelor_thesis.dao.PowerEEG;
import pl.mbos.bachelor_thesis.service.data.IPCConnector;

/**
 * Handles communicates received from {@link pl.mbos.bachelor_thesis.service.data.MainService}
 *
 */
public class DataServiceInboundHandler extends Handler {


    private final static String TAG = "Authentication" + DataServiceInboundHandler.class.getSimpleName();
    DataServiceCommunicationHandler handler;

    public DataServiceInboundHandler(DataServiceCommunicationHandler handler) {
        this.handler = handler;
    }

    @Override
    public void handleMessage(Message msg) {
        if(msg.arg1 == IPCConnector.TYPE_DATA){
            switch(msg.arg2){
                case IPCConnector.DATA_REQ_ATTENTION:
                    List<Attention> data = Arrays.asList(retrieveAttentionArray(msg));
                    handler.notifyAttentionDataReceived(data);
                    break;
                case IPCConnector.DATA_REQ_MEDITATION:
                    List<Meditation> mData = Arrays.asList(retrieveMeditationArray(msg));
                    handler.notifyMeditationDataReceived(mData);
                    break;
                case IPCConnector.DATA_REQ_BLINK:
                    List<Blink> bData = Arrays.asList(retrieveBlinkArray(msg));
                    handler.notifyBlinkDataReceived(bData);
                    break;
                case IPCConnector.DATA_REQ_POWER:
                    List<PowerEEG> pData = Arrays.asList(retrievePowerEEGArray(msg));
                    handler.notifyPowerDataReceived(pData);
                    break;
                case IPCConnector.DATA_REQ_POOR_SIGNAL:
                    List<PoorSignal> psData = Arrays.asList(retrievePoorSignalArray(msg));
                    handler.notifyPoorSignalDataReceived(psData);
                    break;
                default:
                    Log.d(TAG,"Incorrect data communicate ("+msg.arg2+")");
                    break;
            }
        }
    }

    private Attention[] retrieveAttentionArray(Message msg){
        Bundle bundle = msg.getData();
        bundle.setClassLoader(Attention.class.getClassLoader());
        Attention[] array = Attention.convertParcelableToAttention(bundle.getParcelableArray(IPCConnector.DATA_DATA));
        return  array;
    }

    private Meditation[] retrieveMeditationArray(Message msg){
        Bundle bundle = msg.getData();
        bundle.setClassLoader(Meditation.class.getClassLoader());
        Meditation[] array = Meditation.convertParcelableToMeditation(bundle.getParcelableArray(IPCConnector.DATA_DATA));
        return  array;
    }

    private Blink[] retrieveBlinkArray(Message msg){
        Bundle bundle = msg.getData();
        bundle.setClassLoader(Blink.class.getClassLoader());
        Blink[] array = Blink.convertParcelableToBlink(bundle.getParcelableArray(IPCConnector.DATA_DATA));
        return  array;
    }

    private PowerEEG[] retrievePowerEEGArray(Message msg){
        Bundle bundle = msg.getData();
        bundle.setClassLoader(PowerEEG.class.getClassLoader());
        PowerEEG[] array = PowerEEG.convertParcelableToPowerEEG(bundle.getParcelableArray(IPCConnector.DATA_DATA));
        return  array;
    }

    private PoorSignal[] retrievePoorSignalArray(Message msg){
        Bundle bundle = msg.getData();
        bundle.setClassLoader(PoorSignal.class.getClassLoader());
        PoorSignal[] array = PoorSignal.convertParcelableToPoorSignal(bundle.getParcelableArray(IPCConnector.DATA_DATA));
        return  array;
    }

}
