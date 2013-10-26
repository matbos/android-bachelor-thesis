package pl.mbos.bachelor_thesis.service.data.connector.authentication;

import android.os.Handler;
import android.os.Message;
import android.util.Log;

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
        if (msg.arg1 == IPCConnector.TYPE_AUTHENTICATION) {
            switch (msg.arg2) {
                case IPCConnector.AUTH_SUCCEED:
                    handler.userAuthenticated();
                    break;
                case IPCConnector.AUTH_FAILED:
                    String reason = (String) msg.getData().get(IPCConnector.REASON);
                    handler.userUnauthorized(reason);
                    break;
                default:
                    Log.e(TAG, "Unknown authentication message received (of type: " + msg.arg2 + ")");
            }
        }
    }
}
