package pl.mbos.bachelor_thesis.controller;

import android.app.Activity;
import android.content.Intent;
import android.os.Handler;
import pl.mbos.bachelor_thesis.BaseApplication;
import pl.mbos.bachelor_thesis.R;
import pl.mbos.bachelor_thesis.activity.LoginActivity;

/**
 * Created with IntelliJ IDEA.
 * User: Mateusz Boś
 * Date: 15.09.13
 * Time: 02:51
 */
public class SplashScreenController {

    Activity activity;
    Handler runnableHandler;

    public SplashScreenController(Activity activity){
        this.activity = activity;
        runnableHandler = new Handler();
    }

    Runnable splashScreenActivator = new Runnable() {
        @Override
        public void run() {
            Intent i = LoginActivity.getIntentToStartThisActivity(activity);
            activity.startActivity(i);
            // close this activity
            activity.finish();
        }
    };

    /**
     * Starts next activity with delay defined in {@see /res/splash.xml}
     */
    public void startNextActivity(){
        runnableHandler.postDelayed(splashScreenActivator, activity.getResources().getInteger(R.integer.splash_timeout));
    }
}
