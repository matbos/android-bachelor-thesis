package pl.mbos.bachelor_thesis.di;

import dagger.Module;
import pl.mbos.bachelor_thesis.font.Awesome;

/**
 * Created by Mateusz on 07.12.13.
 */
@Module(
        staticInjections = Awesome.class,
        includes = ApplicationModule.class
)
public class StaticModule {

}
