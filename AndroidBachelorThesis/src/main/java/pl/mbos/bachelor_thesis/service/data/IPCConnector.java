package pl.mbos.bachelor_thesis.service.data;

import android.os.Bundle;
import android.os.Message;
import android.os.Messenger;
import android.util.Log;

import pl.mbos.bachelor_thesis.dao.User;
import pl.mbos.bachelor_thesis.service.data.handler.AuthorizationConnector;

/**
 * Handles all communication with {@link pl.mbos.bachelor_thesis.service.data.connector.data.DataServiceConnectionConnector}
 */
public class IPCConnector {

    public static final String MESSENGER = "MESSENGER";
    public static final String TYPE = "TYPE";
    public static final String REASON = "REASON";

    public static final int TYPE_AUTHENTICATION = 1;
    public static final int TYPE_DATA = 2;
    public static final int TYPE_COMMAND = 4;
    public static final int CMD_AUTHENTICATE = 1;
    public static final int AUTH_FAILED = 0;
    public static final int AUTH_SUCCEED = 1;

    private static final String TAG = IPCConnector.class.getSimpleName();


    public final InboundCommunicationHandler inbound;
    private AuthorizationConnector authConnector;

    DataService service;

    public IPCConnector(DataService service){
        this.service = service;
        inbound = new InboundCommunicationHandler(this);
        authConnector = new AuthorizationConnector();
    }

    protected void addListener(int type, Messenger messenger){
        switch (type){
            case TYPE_AUTHENTICATION:
                authConnector.addListener(messenger);
                break;
            case TYPE_COMMAND:

                break;
            case TYPE_DATA:

                break;
            default:
                Log.e(TAG,"Unknown type of listener ("+type+")");
        }
    }

    protected void removeListener(int type, Messenger messenger){
        switch (type){
            case TYPE_AUTHENTICATION:
                authConnector.removeListener(messenger);
                break;
            case TYPE_COMMAND:

                break;
            case TYPE_DATA:

                break;
            default:
                Log.e(TAG,"Unknown type of listener ("+type+")");
        }
    }

    protected void receivedAuthorizationMessage(Message msg){
        try {
            Bundle bundle = msg.getData();
            bundle.setClassLoader(User.class.getClassLoader());
            User user = (User) bundle.getParcelable(User.USER_KEY);
            service.authorizeUser(user);
        } catch (NullPointerException e) {
            authConnector.userUnauthorized(new User(),"User passed to authorization was null");
        }
    }

    protected void receivedDataMessage(Message msg){

    }

    protected void receivedCommandMessage(Message msg){

    }

    protected void userAuthorized(User user){
        authConnector.userAuthorized(user);
    }

    protected void userUnauthorized(User user, String reason){
        authConnector.userUnauthorized(user, reason);
    }
}
