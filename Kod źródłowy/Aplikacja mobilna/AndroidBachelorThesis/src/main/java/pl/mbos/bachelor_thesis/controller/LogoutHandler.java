package pl.mbos.bachelor_thesis.controller;

import javax.inject.Inject;

import pl.mbos.bachelor_thesis.BaseApplication;
import pl.mbos.bachelor_thesis.service.connection.contract.IEEGAcquisitionServiceConnection;

/**
 * Created by Mateusz on 09.12.13.
 */
public class LogoutHandler {

    @Inject
    IEEGAcquisitionServiceConnection connection;

    public LogoutHandler(){
        BaseApplication.getBaseGraph().inject(this);
    }

    public void logout(){
        connection.logout();
    }

}
