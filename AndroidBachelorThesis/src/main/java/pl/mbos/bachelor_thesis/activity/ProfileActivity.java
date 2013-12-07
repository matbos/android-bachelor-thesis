package pl.mbos.bachelor_thesis.activity;

import android.app.Activity;
import android.os.Bundle;

import javax.inject.Inject;

import pl.mbos.bachelor_thesis.BaseApplication;
import pl.mbos.bachelor_thesis.R;
import pl.mbos.bachelor_thesis.menu.Menu;

public class ProfileActivity extends Activity {

    @Inject
    private Menu slidingMenu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        BaseApplication.getBaseGraph().inject(this);
    }
}
