package pl.mbos.bachelor_thesis;

import android.app.Application;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;

import dagger.ObjectGraph;
import pl.mbos.bachelor_thesis.di.IoCModule;
import pl.mbos.bachelor_thesis.service.data.MainService;

/**
 * Created with IntelliJ IDEA.
 * User: Mateusz Boś
 * Date: 15.09.13
 * Time: 00:04
 */

/**
 * Base application class, allows to obtain context wherever in the application.
 */
public class BaseApplication extends Application {
    private static Application instance;
    private static ObjectGraph objectGraph;

    /**
     * Accessor method to applications context
     *
     * @return applications Context object
     */
    public static Context getContext() {
        if (instance != null) {
            return instance.getApplicationContext();
        } else {
            return null;
        }
    }
    public static ObjectGraph getBaseGraph(){
        return objectGraph;
    }

    /**
     * Accessor to applications resources
     *
     * @return Resources object
     */
    public static Resources getAppResources() {
        if (instance != null) {
            return instance.getResources();
        } else {
            return null;
        }
    }

    public static SharedPreferences getUserPreferences(){
       return instance.getSharedPreferences("USER_PREFS", MODE_PRIVATE);
    } 
    @Override
    public void onCreate() {
        super.onCreate();
        objectGraph = ObjectGraph.create(new IoCModule());
        instance = this;
        Context ctx = instance.getApplicationContext();
        ComponentName serviceName = ctx.startService(new Intent(ctx, MainService.class));
    }

}
