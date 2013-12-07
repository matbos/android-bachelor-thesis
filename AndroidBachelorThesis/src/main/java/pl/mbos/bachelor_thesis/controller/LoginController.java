package pl.mbos.bachelor_thesis.controller;

import javax.inject.Inject;

import dagger.ObjectGraph;
import pl.mbos.bachelor_thesis.dao.User;
import pl.mbos.bachelor_thesis.di.IoCModule;
import pl.mbos.bachelor_thesis.service.data.contract.IUserAuthorizationConnection;
import pl.mbos.bachelor_thesis.service.data.contract.IUserAuthorizationConnectionListener;
import pl.mbos.bachelor_thesis.view.LoginView;

/**
 * Created with IntelliJ IDEA.
 * User: Mateusz Boś
 * Date: 29.09.13
 * Time: 20:44
 */
public class LoginController implements IUserAuthorizationConnectionListener {

    private LoginView view;
    private User user;
    @Inject
    protected IUserAuthorizationConnection userAuthenticator;

    public LoginController(LoginView view){
        ObjectGraph graph = ObjectGraph.create(IoCModule.class);
        userAuthenticator = graph.get(IUserAuthorizationConnection.class);
        userAuthenticator.registerListener(this);
        this.view = view;
        user = new User();
    }

    /**
     * Sends asynchronous request to authenticate user with given login and password
     * @param id users login
     * @param pass users password
     */
    public void performLogin(Long id, String pass){
        view.showSpinner();
        user.setId(id);
        user.setFirstName(pass);
        completeLogin(user);
//        userAuthenticator.authorizeUser(user);
    }

    private void completeLogin(User user){
        view.startNextActivity(user);
    }

    /**
     * Called by object that takes care of authentication, the one that implements {@link pl.mbos.bachelor_thesis.service.data.contract.IUserAuthorizationConnection}
     * to notify that user was successfully authenticated
     *
     * @param user user authorized successfully
     */
    @Override
    public void userAuthorized(User user) {
        view.hideSpinner();
        completeLogin(user);
    }
    /**
     * Called by object that takes care of authentication, the one that implements {@link pl.mbos.bachelor_thesis.service.data.contract.IUserAuthorizationConnection}
     * to notify that user was not authorized
     *
     * @param user user unauthorized
     * @param reason reason that user was not authenticated
     */
    @Override
    public void userUnauthorized(String reason) {
        view.hideSpinner();
        view.showError(reason);
    }
}
