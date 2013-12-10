package pl.mbos.bachelor_thesis.activity;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

import butterknife.InjectView;
import pl.mbos.bachelor_thesis.BaseApplication;
import pl.mbos.bachelor_thesis.R;
import pl.mbos.bachelor_thesis.controller.ProfileController;
import pl.mbos.bachelor_thesis.dao.User;
import pl.mbos.bachelor_thesis.dao.helper.UserHelper;
import pl.mbos.bachelor_thesis.view.ProfileView;

public class ProfileActivity extends SlidingMenuActivity implements ProfileView {

    @InjectView(R.id.tv_userName)
    TextView tv_name;

    @InjectView(R.id.tv_userSurname)
    TextView tv_surname;

    @InjectView(R.id.tv_userPhoneNumber)
    TextView tv_phone;

    private ProfileController controller;
    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        BaseApplication.getBaseGraph().inject(this);
        user = UserHelper.retrieveUserFromSO();
        controller = new ProfileController(this);
        configureBaseController(controller);
        displayUserData(user);
    }

    private void displayUserData(User user) {
        if(user == null){
            return;
        }

        tv_name.setText(user.getFirstName());
        tv_surname.setText(user.getLastName());
        tv_phone.setText("+48 696 064 150");
    }
}
