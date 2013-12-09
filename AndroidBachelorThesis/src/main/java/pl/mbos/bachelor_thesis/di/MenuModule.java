package pl.mbos.bachelor_thesis.di;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import pl.mbos.bachelor_thesis.menu.Adapter;

/**
 * Created by Mateusz on 08.12.13.
 */
@Module(
        injects = Adapter.class,
        includes = ApplicationModule.class,
        library = true
)
public class MenuModule {

    @Provides
    @Singleton
    Adapter providesAdapter(){
        return new Adapter();
    }

}

