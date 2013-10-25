package pl.mbos.bachelor_thesis.controller;

import pl.mbos.bachelor_thesis.entity.User;
import pl.mbos.bachelor_thesis.view.LoginView;

/**
 * Created with IntelliJ IDEA.
 * User: Mateusz Boś
 * Date: 29.09.13
 * Time: 20:44
 */
public class LoginController {

    private LoginView view;
    private User user;

    public LoginController(LoginView view){
        this.view = view;
    }

    public boolean performLogin(String login, String pass){
        view.showSpinner();
        //TODO invoke service ...  :]
        view.hideSpinner();
        // for now create dummy user and pass it
        user = new User(1,"Dummy", "User");
        completeLogin();
        //TODO remember about unsuccessful login case
        return true;
    }

    public String getLoginMessage(){
        //TODO This should be obtained from login service!!!!!!!!!!!!
        return "It' just a test message!";
    }

    private void completeLogin(){
        view.startNextActivity(user);
    }

}
