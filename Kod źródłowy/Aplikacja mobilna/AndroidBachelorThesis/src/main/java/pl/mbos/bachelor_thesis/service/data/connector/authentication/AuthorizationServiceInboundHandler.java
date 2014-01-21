package pl.mbos.bachelor_thesis.service.data.connector.authentication;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import pl.mbos.bachelor_thesis.dao.User;
import pl.mbos.bachelor_thesis.service.data.IPCConnector;

/**
 * Handles communicates received from {@link pl.mbos.bachelor_thesis.service.data.MainService}
 */
public class AuthorizationServiceInboundHandler extends Handler {

    private final static String TAG = "Authentication" + AuthorizationServiceInboundHandler.class.getSimpleName();
    AuthorizationServiceCommunicationHandler handler;

    public AuthorizationServiceInboundHandler(AuthorizationServiceCommunicationHandler handler) {
        this.handler = handler;
    }

    @Override
    public void handleMessage(Message msg) {
        User user;
        String reason;
        Bundle bundle;

        if (msg.arg1 == IPCConnector.TYPE_AUTHENTICATION) {
            switch (msg.arg2) {
                case IPCConnector.AUTH_SUCCEEDED:
                    bundle = msg.getData();
                    user = getUser(bundle);
                    handler.userAuthenticated(user);
                    break;
                case IPCConnector.AUTH_FAILED:
                    bundle = msg.getData();
                    user = getUser(bundle);
                    reason = getReason(bundle);
                    handler.userUnauthorized(user, reason);
                    break;
                default:
                    Log.e(TAG, "Unknown authentication message received (of type: " + msg.arg2 + ")");
            }
        }
    }

    private String getReason(Bundle bundle){
        return (String) bundle.getString(User.REASON_KEY);
    }

    private User getUser(Bundle bundle){
        bundle.setClassLoader(User.class.getClassLoader());
        return (User) bundle.getParcelable(User.USER_KEY);
    }
}
