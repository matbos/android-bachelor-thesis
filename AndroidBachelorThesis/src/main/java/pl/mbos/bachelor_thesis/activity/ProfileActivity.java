package pl.mbos.bachelor_thesis.activity;

import android.os.Bundle;
import android.view.MenuItem;

import pl.mbos.bachelor_thesis.BaseApplication;
import pl.mbos.bachelor_thesis.R;
import pl.mbos.bachelor_thesis.controller.ProfileController;
import pl.mbos.bachelor_thesis.view.ProfileView;

public class ProfileActivity extends SlidingMenuActivity implements ProfileView {

    private ProfileController controller;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        BaseApplication.getBaseGraph().inject(this);
        controller = new ProfileController(this);
        configureBaseController(controller);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        if(item.getItemId() == R.id.logout){
            controller.logout();
        }
        return super.onContextItemSelected(item);
    }

}
