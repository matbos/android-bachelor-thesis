package pl.mbos.bachelor_thesis.di;


import android.content.Context;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import pl.mbos.bachelor_thesis.BaseApplication;
import pl.mbos.bachelor_thesis.controller.LoginController;
import pl.mbos.bachelor_thesis.controller.MainActivityController;
import pl.mbos.bachelor_thesis.service.connection.EEGAcquisitionServiceConnectionConnector;
import pl.mbos.bachelor_thesis.service.connection.contract.IEEGAcquisitionServiceConnection;
import pl.mbos.bachelor_thesis.service.data.connector.authentication.AuthorizationServiceConnectionConnector;
import pl.mbos.bachelor_thesis.service.data.contract.IUserAuthorizationConnection;

/**
 * Created with IntelliJ IDEA.
 * User: Mateusz Boś
 * Date: 29.09.13
 * Time: 17:19
 */

@Module(
        injects = {
                IEEGAcquisitionServiceConnection.class,
                EEGAcquisitionServiceConnectionConnector.class,
                IUserAuthorizationConnection.class,
                MainActivityController.class,
                LoginController.class,
                AuthorizationServiceConnectionConnector.class
        }
)
public class IoCModule {

    @Provides
    @Singleton
    IEEGAcquisitionServiceConnection provideEEGAcquisitionServiceConnection() {
        return new EEGAcquisitionServiceConnectionConnector();
    }

    @Provides
    public Context provideContext(){
        return BaseApplication.getContext();
    }

    @Provides
    IUserAuthorizationConnection provideIUserAuthorizationConnection(){
        return new AuthorizationServiceConnectionConnector();
    }
}
