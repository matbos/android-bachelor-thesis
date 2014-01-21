package pl.mbos.bachelor_thesis.di;

import android.content.Context;
import android.content.res.Resources;

import dagger.Module;
import dagger.Provides;
import pl.mbos.bachelor_thesis.BaseApplication;

/**
 * Created with IntelliJ IDEA.
 * User: Mateusz Boś
 * Date: 02.10.13
 * Time: 23:55
 */
@Module(injects = {
        Context.class,
        Resources.class
        },
        library = true)
public class ApplicationModule {

    @Provides
    public Context provideContext() {
        return BaseApplication.getContext();
    }

    @Provides
    public Resources provideResources(Context ctx) {
        return ctx.getResources();
    }

}
