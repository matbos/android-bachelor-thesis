package pl.mbos.bachelor_thesis.service.data.contract;

import pl.mbos.bachelor_thesis.dao.User;

/**
 * Interface implemented by classes that want to know about authorization outcomes.
 */
public interface IUserAuthorizationConnectionListener {
    /**
     * Indicates that user has been authorized successfully
     * @param user user authorized successfully
     */
    void userAuthorized(User user);

    /**
     * Indicates that user has <b>not</b> been authorized successfully
     * @param user user that failed authorization
     * @param reason reason of authentication failure
     */
    void userUnauthorized(User user, String reason);
}
