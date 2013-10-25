package pl.mbos.bachelor_thesis.di;


import android.content.Context;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import pl.mbos.bachelor_thesis.BaseApplication;
import pl.mbos.bachelor_thesis.activity.MainActivity;
import pl.mbos.bachelor_thesis.controller.MainActivityController;
import pl.mbos.bachelor_thesis.service.connection.EEGAcquisitionServiceConnectionConnector;
import pl.mbos.bachelor_thesis.service.connection.contract.IEEGAcquisitionServiceConnection;

/**
 * Created with IntelliJ IDEA.
 * User: Mateusz Boś
 * Date: 29.09.13
 * Time: 17:19
 */

@Module(
        injects = {
                IEEGAcquisitionServiceConnection.class,
                MainActivityController.class,
                EEGAcquisitionServiceConnectionConnector.class
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
}
