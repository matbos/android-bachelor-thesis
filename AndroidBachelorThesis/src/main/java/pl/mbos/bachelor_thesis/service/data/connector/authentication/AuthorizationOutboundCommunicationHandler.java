package pl.mbos.bachelor_thesis.service.data.connector.authentication;

import android.os.Bundle;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.util.Log;

import pl.mbos.bachelor_thesis.BaseApplication;
import pl.mbos.bachelor_thesis.dao.User;
import pl.mbos.bachelor_thesis.service.data.IPCConnector;

/**
 * Handles sending communicates to {@link pl.mbos.bachelor_thesis.service.data.DataService}
 */
public class AuthorizationOutboundCommunicationHandler {

    private final static String TAG = "Authentication" + AuthorizationOutboundCommunicationHandler.class.getSimpleName();
    private Messenger messenger;

    public AuthorizationOutboundCommunicationHandler(Messenger messenger) {
        this.messenger = messenger;
    }

    public Messenger getMessenger() {
        return messenger;
    }

    public void sendUserToAuthentication(User user) {
        try {
            Message message = Message.obtain();
            message.arg1 = IPCConnector.TYPE_AUTHENTICATION;
            message.arg2 = IPCConnector.CMD_AUTHENTICATE;
            message.obj = user;
            Bundle bundle = new Bundle();
            ClassLoader classLoader = BaseApplication.getContext().getClassLoader();
            bundle.setClassLoader(classLoader);
            bundle.putParcelable(User.FIELD, user);
            message.setData(bundle);
            messenger.send(message);
        } catch (RemoteException e) {
            Log.e(TAG, e.getMessage());
        }
    }

}
