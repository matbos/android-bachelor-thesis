package pl.mbos.bachelor_thesis.service.data.handler;

import android.os.Bundle;
import android.os.Message;

import pl.mbos.bachelor_thesis.service.data.IPCConnector;
import pl.mbos.bachelor_thesis.service.data.OutboundCommunicationHandler;


public class AuthorizationConnector extends OutboundCommunicationHandler {

    public static final String TAG = AuthorizationConnector.class.getSimpleName();


    public AuthorizationConnector() {
        super();
    }

    public void userAuthorized(){
        send(buildMessage());
    }

    public void userUnauthorized(String reason){
        send(buildMessage(reason));
    }

    /**
     * Builds message with success case
     * @return message with success content
     */
    private Message buildMessage(){
        Message msg = Message.obtain();
        msg.arg1 = IPCConnector.TYPE_AUTHENTICATION;
        msg.arg2 = IPCConnector.AUTH_SUCCEED;
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
