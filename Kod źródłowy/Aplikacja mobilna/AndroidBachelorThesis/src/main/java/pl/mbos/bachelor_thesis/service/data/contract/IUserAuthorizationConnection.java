package pl.mbos.bachelor_thesis.service.data.contract;

import pl.mbos.bachelor_thesis.dao.User;

/**
 * This interface is to be invoked by objects that request authorization
 *
 * It is to be implemented by classes that communicate with authorizing service
 * (most probably {@link pl.mbos.bachelor_thesis.service.data.MainService})
 */
public interface IUserAuthorizationConnection {
    /**
     * Asks to authorize user, result is given through {@link pl.mbos.bachelor_thesis.service.data.contract.IUserAuthorizationConnectionListener}
     *
     * @param user  User to be authorized
     */
    void authorizeUser(User user);

    void registerListener(IUserAuthorizationConnectionListener listener);

    boolean unregisterListener(IUserAuthorizationConnectionListener listener);


}
