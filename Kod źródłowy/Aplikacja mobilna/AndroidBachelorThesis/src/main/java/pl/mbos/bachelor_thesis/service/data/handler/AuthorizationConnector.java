package pl.mbos.bachelor_thesis.service.data.handler;

import android.os.Bundle;
import android.os.Message;

import pl.mbos.bachelor_thesis.dao.User;
import pl.mbos.bachelor_thesis.service.data.IPCConnector;
import pl.mbos.bachelor_thesis.service.data.OutboundCommunicationHandler;


public class AuthorizationConnector extends OutboundCommunicationHandler {

    public static final String TAG = AuthorizationConnector.class.getSimpleName();


    public AuthorizationConnector() {
        super();
    }

    public void userAuthorized(User user){
        send(buildSuccessMessage(user));
    }

    public void userUnauthorized(User user, String reason){
        send(buildFailureMessage(user, reason));
    }

    /**
     * Builds message in failure case
     * @return message with failure content
     */
    private Message buildFailureMessage(User user, String reason){
        Message msg = Message.obtain();
        msg.arg1 = IPCConnector.TYPE_AUTHENTICATION;
        msg.arg2 = IPCConnector.AUTH_FAILED;
        Bundle bundle = new Bundle();
        bundle.putParcelable(User.USER_KEY, user);
        bundle.putString(User.REASON_KEY, reason);
        msg.setData(bundle);
        return msg;
    }

    /**
     * Builds message with success case
     * @return message with success content
     */
    private Message buildSuccessMessage(User user){
        Message msg = Message.obtain();
        msg.arg1 = IPCConnector.TYPE_AUTHENTICATION;
        msg.arg2 = IPCConnector.AUTH_SUCCEEDED;
        Bundle bundle = new Bundle();
        bundle.putParcelable(User.USER_KEY, user);
        msg.setData(bundle);
        return msg;
    }

    /**
     * Builds message with failure case, attaches reason in form of string as a Bundle data
     * @param reason Reason why user has not been authenticated
     * @return message with failure content
     */
    private Message buildMessage(String reason){
        Message msg = Message.obtain();
        msg.arg1 = IPCConnector.TYPE_AUTHENTICATION;
        msg.arg2 = IPCConnector.AUTH_FAILED;
        Bundle bundle = new Bundle();
        bundle.putString(IPCConnector.REASON, reason);
        msg.setData(bundle);
        return msg;
    }

}
