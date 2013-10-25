package pl.mbos.bachelor_thesis.view;

import pl.mbos.bachelor_thesis.entity.User;

/**
 * Created with IntelliJ IDEA.
 * User: Mateusz Boś
 * Date: 29.09.13
 * Time: 22:18
 */
public interface LoginView {
    void showSpinner();

    void hideSpinner();

    void startNextActivity(User user);
}
