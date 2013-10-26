package pl.mbos.bachelor_thesis.service.data;

import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.util.Log;

import java.util.ArrayList;

import pl.mbos.bachelor_thesis.service.data.contract.IOutboundCommunication;

/**
 * Handles sending communicates to {@link pl.mbos.bachelor_thesis.service.data.connector.data.DataServiceConnectionConnector}
 */
public abstract class OutboundCommunicationHandler implements IOutboundCommunication {

    private static final String TAG = OutboundCommunicationHandler.class.getSimpleName();
    protected ArrayList<Messenger> messengers;

    public OutboundCommunicationHandler(){
        messengers = new ArrayList<Messenger>(3);
    }

    @Override
    public void addListener(Messenger messenger) {
        messengers.add(messenger);
    }

    @Override
    public void removeListener(Messenger messenger) {
        messengers.remove(messenger);
    }

    @Override
    public void send(Message message) {
        try {
            for(Messenger m : messengers){
                m.send(message);
            }
        } catch (RemoteException e) {
            Log.e(TAG, e.getMessage());
        }
    }
}
