package pl.mbos.bachelor_thesis.service.data.connector.authentication;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import pl.mbos.bachelor_thesis.dao.User;
import pl.mbos.bachelor_thesis.service.data.IPCConnector;

/**
 * Handles communicates received from {@link pl.mbos.bachelor_thesis.service.data.DataService}
 */
public class AuthorizationInboundCommunicationHandler extends Handler {

    private final static String TAG = "Authentication" + AuthorizationInboundCommunicationHandler.class.getSimpleName();
    AuthorizationCommunicationHandler handler;

    public AuthorizationInboundCommunicationHandler(AuthorizationCommunicationHandler handler) {
        this.handler = handler;
    }

    @Override
    public void handleMessage(Message msg) {
        User user;
        String reason;
        Bundle bundle;

        if (msg.arg1 == IPCConnector.TYPE_AUTHENTICATION) {
            switch (msg.arg2) {
                case IPCConnector.AUTH_SUCCEED:
                    bundle = msg.getData();
                    bundle.setClassLoader(User.class.getClassLoader());
                    user = (User) bundle.getParcelable(User.USER_KEY);
                    handler.userAuthenticated(user);
                    break;
                case IPCConnector.AUTH_FAILED:
                    bundle = msg.getData();
                    bundle.setClassLoader(User.class.getClassLoader());
                    user = (User) bundle.getParcelable(User.USER_KEY);
                    reason = (String) msg.getData().getString(User.REASON_KEY);
                    handler.userUnauthorized(user, reason);
                    break;
                default:
                    Log.e(TAG, "Unknown authentication message received (of type: " + msg.arg2 + ")");
            }
        }
    }
}
