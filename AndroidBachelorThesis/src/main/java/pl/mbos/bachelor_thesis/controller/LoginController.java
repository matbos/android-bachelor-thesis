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
        this.view = view;
        user = new User();
    }

    public boolean performLogin(String login, String pass){
        view.showSpinner();
        user.setFirstName("Diego");
        user.setLastName("Picarra");
        user.setId(666999);
        userAuthenticator.authorizeUser(user);
        return true;
    }

    public String getLoginMessage(){
        //TODO This should be obtained from login service!!!!!!!!!!!!
        return "It' just a test message!";
    }

    private void completeLogin(User user){
        view.startNextActivity(user);
    }

    @Override
    public void userAuthorized(User user) {
        view.hideSpinner();
        completeLogin(user);
    }

    @Override
    public void userUnauthorized(User user, String reason) {
        view.hideSpinner();
    }
}
