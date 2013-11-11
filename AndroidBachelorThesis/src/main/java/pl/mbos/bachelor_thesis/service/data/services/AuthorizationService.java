package pl.mbos.bachelor_thesis.service.data.services;

import java.util.Timer;
import java.util.TimerTask;

import pl.mbos.bachelor_thesis.dao.User;

/**
 * Service that actually performs user authentication
 */
public class AuthorizationService implements IAuthorizationService {

    private IAuthorizationServiceParent parent;

    public AuthorizationService(IAuthorizationServiceParent parent) {
        this.parent = parent;
    }

    @Override
    public void authorizeUser(User user) {
        if (user.getId() != 123 && !user.getFirstName().equals("asd")) {
            Timer timer = new Timer();
            timer.schedule(new AuthTask(user), 2000);
        } else {
            parent.authorizationOutcome(user, false, "bad login/password");
        }
    }

    class AuthTask extends TimerTask {
        private User user;

        public AuthTask(User user) {
            this.user = user;
        }
        @Override
        public void run() {
            parent.authorizationOutcome(user, true, null);
        }
    }
}
