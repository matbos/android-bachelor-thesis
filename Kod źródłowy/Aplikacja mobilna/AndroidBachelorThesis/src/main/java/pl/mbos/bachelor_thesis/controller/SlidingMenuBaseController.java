package pl.mbos.bachelor_thesis.controller;

import javax.inject.Inject;

import pl.mbos.bachelor_thesis.BaseApplication;

/**
 * Created by Mateusz on 09.12.13.
 */
public class SlidingMenuBaseController {

    @Inject
    protected LogoutHandler logoutHandler;

    public SlidingMenuBaseController(){
        BaseApplication.getBaseGraph().inject(this);
    }

    public void logout(){
        logoutHandler.logout();
    }
}
