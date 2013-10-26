package pl.mbos.bachelor_thesis.service.data.connector.authentication;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;

import pl.mbos.bachelor_thesis.BaseApplication;
import pl.mbos.bachelor_thesis.dao.User;
import pl.mbos.bachelor_thesis.service.data.DataService;
import pl.mbos.bachelor_thesis.service.data.IPCConnector;
import pl.mbos.bachelor_thesis.service.data.contract.IUserAuthorizationConnection;

/**
 * Created by Mateusz on 26.10.13.
 */
public class AuthorizationServiceConnectionConnector implements ServiceConnection, IUserAuthorizationConnection {

    //@Inject
    public android.content.Context context = BaseApplication.getContext();
    private AuthorizationCommunicationHandler communicationHandler;
    private boolean serviceUp = false;

    public AuthorizationServiceConnectionConnector() {
        startService();
        communicationHandler = new AuthorizationCommunicationHandler(this);
        bindToService();
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
    public void onServiceDisconnected(ComponentName componentName) {

    }

    /**
     * Starts {@link pl.mbos.bachelor_thesis.service.data.DataService} if it is not already running
     *
     * @return true if service was started, false otherwise (service has already been running or did not start)
     */
    private boolean startService() {
        boolean didStart = false;
        if (!serviceUp) {
            ComponentName serviceName = context.startService(new Intent(context, DataService.class));
            serviceUp = (serviceName != null) ? true : false;
            didStart = serviceUp;
        }
        return didStart;
    }

    /**
     * Connects to service
     */
    private void bindToService() {
        if (communicationHandler.getOutboundMessenger() == null) {
            Intent intent = new Intent(context, DataService.class);
            intent.putExtra(IPCConnector.MESSENGER, communicationHandler.getInboundMessenger());
            intent.putExtra(IPCConnector.TYPE, IPCConnector.TYPE_AUTHENTICATION);
            context.bindService(intent, this, Context.BIND_AUTO_CREATE);
        }
    }

    protected void userAuthorized() {
        //here notify that user has been authorized
    }

    protected void userUnauthorized(String reason) {
        //here notify that user has not been authorized
    }
}
