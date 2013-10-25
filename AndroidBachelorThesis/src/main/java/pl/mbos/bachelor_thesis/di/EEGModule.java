package pl.mbos.bachelor_thesis.di;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import pl.mbos.bachelor_thesis.controller.MainActivityController;
import pl.mbos.bachelor_thesis.service.connection.EEGAcquisitionServiceConnectionConnector;
import pl.mbos.bachelor_thesis.service.connection.contract.IEEGAcquisitionServiceConnection;

@Module(injects = MainActivityController.class,
        library = true)
public class EEGModule {

    @Provides
    @Singleton
    IEEGAcquisitionServiceConnection provideEEGAcquisitionServiceConnection() {
        return new EEGAcquisitionServiceConnectionConnector();
    }



}
