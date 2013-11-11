package pl.mbos.bachelor_thesis.service.data.connector.authentication;

import android.os.IBinder;
import android.os.Messenger;

import pl.mbos.bachelor_thesis.dao.User;

/**
 * Handles all communication with {@link pl.mbos.bachelor_thesis.service.data.MainService}
 */
public class AuthorizationServiceCommunicationHandler {

    private AuthorizationServiceClient connector;
    private AuthorizationServiceInboundHandler inbound;
    private AuthorizationServiceOutboundHandler outbound;
    private Messenger inboundMessenger;

    public AuthorizationServiceCommunicationHandler(AuthorizationServiceClient connector) {
        this.connector = connector;
        inbound = new AuthorizationServiceInboundHandler(this);
        inboundMessenger = new Messenger(inbound);
    }

    public Messenger getInboundMessenger() {
        return inboundMessenger;
    }

    public Messenger getOutboundMessenger() {
        if (outbound != null) {
            return outbound.getMessenger();
        } else {
            return null;
        }
    }

    public boolean isConnectedToService() {
        return getOutboundMessenger() != null;
    }

    public void createOutboundMessenger(IBinder service) {
        outbound = new AuthorizationServiceOutboundHandler(new Messenger(service));
    }

    public void authenticateUser(User user) {
        outbound.sendUserToAuthentication(user);
    }

    protected void userAuthenticated(User user) {
        connector.userAuthorized(user);
    }

    protected void userUnauthorized(User user, String reason) {
        connector.userUnauthorized(user, reason);
    }

    public void sayGoodbye() {
        outbound.sayGoodbye(inboundMessenger);
    }
}
