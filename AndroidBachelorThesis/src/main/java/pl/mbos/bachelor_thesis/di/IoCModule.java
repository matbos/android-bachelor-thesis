package pl.mbos.bachelor_thesis.di;


import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import pl.mbos.bachelor_thesis.controller.LoginController;
import pl.mbos.bachelor_thesis.controller.MainActivityController;
import pl.mbos.bachelor_thesis.service.connection.EEGAcquisitionServiceConnectionConnector;
import pl.mbos.bachelor_thesis.service.connection.contract.IEEGAcquisitionServiceConnection;
import pl.mbos.bachelor_thesis.service.data.connector.BaseServiceClient;
import pl.mbos.bachelor_thesis.service.data.connector.authentication.AuthorizationServiceClient;
import pl.mbos.bachelor_thesis.service.data.connector.command.CommandServiceClient;
import pl.mbos.bachelor_thesis.service.data.connector.data.DataServiceClient;
import pl.mbos.bachelor_thesis.service.data.contract.ICommandServiceConnection;
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
                BaseServiceClient.class,
                AuthorizationServiceClient.class,
                CommandServiceClient.class,
                DataServiceClient.class,
                ICommandServiceConnection.class
        },
        includes = ApplicationModule.class
)
public class IoCModule {

    @Provides
    @Singleton
    IEEGAcquisitionServiceConnection provideEEGAcquisitionServiceConnection() {
        return new EEGAcquisitionServiceConnectionConnector();
    }

    @Provides
    IUserAuthorizationConnection provideIUserAuthorizationConnection(){
        return new AuthorizationServiceClient();
    }

    @Provides
    ICommandServiceConnection provideICommandServiceConnection(){
        return new CommandServiceClient();
    }
}
