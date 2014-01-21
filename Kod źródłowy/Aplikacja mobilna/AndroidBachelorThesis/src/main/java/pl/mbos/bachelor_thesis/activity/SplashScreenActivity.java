package pl.mbos.bachelor_thesis.activity;

import android.app.Activity;
import android.os.Bundle;

import pl.mbos.bachelor_thesis.R;
import pl.mbos.bachelor_thesis.controller.SplashScreenController;

/**
 * Created with IntelliJ IDEA.
 * User: Mateusz Boś
 * Date: 15.09.13
 * Time: 02:14
 */

public class SplashScreenActivity extends Activity {

    private SplashScreenController controller;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        controller = new SplashScreenController(this);
        controller.startNextActivity();
    }

}

