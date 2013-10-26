package pl.mbos.bachelor_thesis.service.data.connector.authentication;

import android.os.IBinder;
import android.os.Messenger;

import pl.mbos.bachelor_thesis.dao.User;

/**
 * Handles all communication with {@link pl.mbos.bachelor_thesis.service.data.DataService}
 */
public class AuthorizationCommunicationHandler {

    private AuthorizationInboundCommunicationHandler inbound;
    private AuthorizationOutboundCommunicationHandler outbound;
    private Messenger inboundMessenger;
    private AuthorizationServiceConnectionConnector connector;

    public AuthorizationCommunicationHandler(AuthorizationServiceConnectionConnector connector) {
        this.connector = connector;
        inbound = new AuthorizationInboundCommunicationHandler(this);
        inboundMessenger = new Messenger(inbound);
    }

    public Messenger getInboundMessenger() {
        return inboundMessenger;
    }

    public Messenger getOutboundMessenger() {
        if(outbound != null){
            return outbound.getMessenger();
        } else {
            return null;
        }
    }

    public void createOutboundMessenger(IBinder service) {
        outbound = new AuthorizationOutboundCommunicationHandler(new Messenger(service));
    }

    public void authenticateUser(User user) {
        outbound.sendUserToAuthentication(user);
    }

    protected void userAuthenticated() {
        connector.userAuthorized();
    }

    protected void userUnauthorized(String reason) {
        connector.userUnauthorized(reason);
    }

}
