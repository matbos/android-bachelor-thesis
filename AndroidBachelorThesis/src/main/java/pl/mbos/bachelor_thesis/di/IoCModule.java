package pl.mbos.bachelor_thesis.di;


import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import pl.mbos.bachelor_thesis.activity.LoginActivity;
import pl.mbos.bachelor_thesis.activity.MainActivity;
import pl.mbos.bachelor_thesis.activity.ProfileActivity;
import pl.mbos.bachelor_thesis.activity.SettingsActivity;
import pl.mbos.bachelor_thesis.activity.SlidingMenuActivity;
import pl.mbos.bachelor_thesis.controller.LoginController;
import pl.mbos.bachelor_thesis.controller.MainActivityController;
import pl.mbos.bachelor_thesis.controller.SettingsController;
import pl.mbos.bachelor_thesis.controller.WebAddressTextWatcher;
import pl.mbos.bachelor_thesis.custom.AwesomeText;
import pl.mbos.bachelor_thesis.font.Awesome;
import pl.mbos.bachelor_thesis.menu.Adapter;
import pl.mbos.bachelor_thesis.menu.Menu;
import pl.mbos.bachelor_thesis.service.connection.EEGAcquisitionServiceConnectionConnector;
import pl.mbos.bachelor_thesis.service.connection.MainServiceClient;
import pl.mbos.bachelor_thesis.service.connection.contract.IEEGAcquisitionServiceConnection;
import pl.mbos.bachelor_thesis.service.data.connector.BaseServiceClient;
import pl.mbos.bachelor_thesis.service.data.connector.authentication.AuthorizationServiceClient;
import pl.mbos.bachelor_thesis.service.data.connector.command.CommandServiceClient;
import pl.mbos.bachelor_thesis.service.data.connector.data.DataServiceClient;
import pl.mbos.bachelor_thesis.service.data.contract.ICommandServiceConnection;
import pl.mbos.bachelor_thesis.service.data.contract.IUserAuthorizationConnection;
import pl.mbos.bachelor_thesis.service.data.remote.BaseService;
import pl.mbos.bachelor_thesis.service.data.services.AuthorizationService;
import pl.mbos.bachelor_thesis.service.data.services.CommandService;
import pl.mbos.bachelor_thesis.service.data.services.DataService;

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
                MainServiceClient.class,
                DataService.class,
                LoginController.class,
                BaseServiceClient.class,
                AuthorizationServiceClient.class,
                CommandServiceClient.class,
                DataServiceClient.class,
                ICommandServiceConnection.class,
                BaseService.class,
                AuthorizationService.class,
                CommandService.class,
                Menu.class,
                Adapter.class,
                MainActivity.class,
                AwesomeText.class,
                SlidingMenuActivity.class,
                SettingsActivity.class,
                LoginActivity.class,
                SettingsController.class,
                ProfileActivity.class,
                WebAddressTextWatcher.class
        },
        includes = {
                ApplicationModule.class,
                MenuModule.class
        }
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

    @Provides
    BaseService provideBaseService(){
        return new BaseService();
    }

    @Provides
    @Singleton
    Menu providesMenu(){
        return new Menu();
    }
}
