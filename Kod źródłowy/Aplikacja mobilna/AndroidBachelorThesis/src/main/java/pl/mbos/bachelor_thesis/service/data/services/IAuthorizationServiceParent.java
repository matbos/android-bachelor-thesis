package pl.mbos.bachelor_thesis.service.data.services;

import pl.mbos.bachelor_thesis.dao.User;

/**
 * Created by Mateusz on 08.11.13.
 */
public interface IAuthorizationServiceParent {

    void authorizationOutcome(User user, boolean success, String reason);
}
