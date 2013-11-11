package pl.mbos.bachelor_thesis.service.data.connector.command;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import pl.mbos.bachelor_thesis.BaseApplication;
import pl.mbos.bachelor_thesis.service.data.IPCConnector;
import pl.mbos.bachelor_thesis.service.data.MainService;
import pl.mbos.bachelor_thesis.service.data.connector.BaseServiceClient;
import pl.mbos.bachelor_thesis.service.data.contract.ICommandServiceConnection;

/**
 * Created by Mateusz on 26.10.13.
 */
public class CommandServiceClient extends BaseServiceClient implements ICommandServiceConnection {

    CommandServiceCommunicationHandler communicationHandler;
    List<ICommandAuthorizationConnectionListener> listenerList;

    public CommandServiceClient() {
        super();
        listenerList = new ArrayList<ICommandAuthorizationConnectionListener>(2);
        communicationHandler = new CommandServiceCommunicationHandler(this);
        connectToService();
    }

    @Override
    public void synchronize() {
        communicationHandler.synchronize();
    }

    @Override
    public void setSynchronization(boolean synchronize) {
        communicationHandler.setSynchronization(synchronize);
    }

    @Override
    public void isSynchronizationPermitted() {
        communicationHandler.requestSyncState();
    }

    @Override
    public void isSynchronizing() {
        communicationHandler.requestSyncProgress();
    }


    @Override
    public void onServiceConnected(ComponentName name, IBinder binder) {
        communicationHandler.createOutboundMessenger(binder);
    }

    /**
     * Connects to service
     */
    @Override
    public void connectToService() {
        Log.d("ASDA", "CMD :: Connecting to service " + communicationHandler.isConnectedToService());
        if (!communicationHandler.isConnectedToService()) {
            connectToService(IPCConnector.TYPE_COMMAND, communicationHandler.getInboundMessenger());
        }
    }

    public void disconnectFromService() {
        communicationHandler.sayGoodbye();
    }

    public void registerListener(ICommandAuthorizationConnectionListener listener) {
        listenerList.add(listener);
    }

    public boolean unregisterListener(ICommandAuthorizationConnectionListener listener) {
        return listenerList.remove(listener);
    }


}