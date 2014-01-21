package pl.mbos.bachelor_thesis.service.data.connector.authentication;

import android.os.Bundle;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.util.Log;

import pl.mbos.bachelor_thesis.dao.User;
import pl.mbos.bachelor_thesis.service.data.IPCConnector;

/**
 * Handles sending communicates to {@link pl.mbos.bachelor_thesis.service.data.MainService}
 */
public class AuthorizationServiceOutboundHandler {

    private final static String TAG = "Authentication" + AuthorizationServiceOutboundHandler.class.getSimpleName();
    private Messenger messenger;

    public AuthorizationServiceOutboundHandler(Messenger messenger) {
        this.messenger = messenger;
    }

    public Messenger getMessenger() {
        return messenger;
    }

    public void sendUserToAuthentication(User user) {
        try {
            Message message = Message.obtain();
            message.arg1 = IPCConnector.TYPE_AUTHENTICATION;
            message.arg2 = IPCConnector.AUTH_AUTHENTICATE;
            Bundle bundle = new Bundle();
            bundle.putParcelable(User.USER_KEY, user);
            message.setData(bundle);
            messenger.send(message);
        } catch (RemoteException e) {
            Log.e(TAG, e.getMessage());
        }
    }

    public void sayGoodbye(Messenger messenger) {
        try {
            Message message = Message.obtain();
            message.arg1 = IPCConnector.TYPE_AUTHENTICATION;
            message.arg2 = IPCConnector.MESSAGE_GOODBYE;
            message.obj = messenger;
            messenger.send(message);
        } catch (RemoteException e) {
            Log.e(TAG, e.getMessage());
        }
    }
}
