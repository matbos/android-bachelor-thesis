package pl.mbos.bachelor_thesis.service.data.connector.authentication;

import android.content.ComponentName;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;


import pl.mbos.bachelor_thesis.dao.User;
import pl.mbos.bachelor_thesis.service.data.IPCConnector;
import pl.mbos.bachelor_thesis.service.data.connector.BaseServiceClient;
import pl.mbos.bachelor_thesis.service.data.contract.IUserAuthorizationConnection;
import pl.mbos.bachelor_thesis.service.data.contract.IUserAuthorizationConnectionListener;

/**
 * Created by Mateusz on 26.10.13.
 */
public class AuthorizationServiceClient extends BaseServiceClient implements ServiceConnection, IUserAuthorizationConnection {

    private AuthorizationServiceCommunicationHandler communicationHandler;
    private List<IUserAuthorizationConnectionListener> listenerList;

    public AuthorizationServiceClient() {
        super();
        listenerList = new ArrayList<IUserAuthorizationConnectionListener>(2);
        communicationHandler = new AuthorizationServiceCommunicationHandler(this);
        connectToService();
    }

    @Override
    public void authorizeUser(User user) {
        communicationHandler.authenticateUser(user);
    }

    @Override
    public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
        communicationHandler.createOutboundMessenger(iBinder);
    }

    @Override
    public void registerListener(IUserAuthorizationConnectionListener listener){
        listenerList.add(listener);
    }

    @Override
    public boolean unregisterListener(IUserAuthorizationConnectionListener listener){
        return listenerList.remove(listener);
    }

    /**
     * Connects to service
     */
    protected void connectToService() {
        Log.d("ASDA", "AUTH :: Connecting to service " + communicationHandler.isConnectedToService());
        if (communicationHandler.getOutboundMessenger() == null) {
            connectToService(IPCConnector.TYPE_AUTHENTICATION, communicationHandler.getInboundMessenger());
        }
    }
    @Override
    protected void disconnectFromService(){
        communicationHandler.sayGoodbye();
        super.disconnectFromService();
    }

    protected void userAuthorized(User user) {
        for(IUserAuthorizationConnectionListener listener : listenerList){
            listener.userAuthorized(user);
        }
    }

    protected void userUnauthorized(User user, String reason) {
        for(IUserAuthorizationConnectionListener listener : listenerList){
            listener.userUnauthorized(reason);
        }
    }

}
