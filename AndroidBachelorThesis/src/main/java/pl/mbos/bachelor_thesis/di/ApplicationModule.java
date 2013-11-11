package pl.mbos.bachelor_thesis.di;

import android.content.Context;
import dagger.Module;
import dagger.Provides;
import pl.mbos.bachelor_thesis.BaseApplication;

/**
 * Created with IntelliJ IDEA.
 * User: Mateusz Boś
 * Date: 02.10.13
 * Time: 23:55
 */
@Module(library = true)
public class ApplicationModule {

    @Provides
    public Context provideContext(){
        return BaseApplication.getContext();
    }

}
