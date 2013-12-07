package pl.mbos.bachelor_thesis.activity;

import android.app.Activity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

import javax.inject.Inject;

import butterknife.InjectView;
import butterknife.OnClick;
import butterknife.Views;
import pl.mbos.bachelor_thesis.BaseApplication;
import pl.mbos.bachelor_thesis.R;
import pl.mbos.bachelor_thesis.controller.WebAddressTextWatcher;
import pl.mbos.bachelor_thesis.menu.Menu;

public class SettingsActivity extends Activity {

    @InjectView(R.id.tv_connectionState)
    TextView tv_state;

    @InjectView(R.id.tv_streamState)
    TextView tv_streamState;

    @InjectView(R.id.et_webAddress)
    EditText et_webAddress;

    @InjectView(R.id.tv_synchronize)
    TextView tv_synchronize;

    @Inject
    private Menu slidingMenu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        BaseApplication.getBaseGraph().inject(this);
        Views.inject(this);
        slidingMenu.attachMenu(this);
        et_webAddress.addTextChangedListener(new WebAddressTextWatcher(et_webAddress));
    }

    @OnClick(R.id.btn_connect)
    protected void buttonConnectClicked(){

    }

    @OnClick(R.id.btn_streamState)
    protected void buttonStreamStateClicked(){

    }

    @OnClick(R.id.btn_synchronize)
    protected void buttonSynchronizeClicked(){

    }

    @OnClick(R.id.btn_synchronizeNow)
    protected void buttonSynchronizeNowClicked(){

    }

}
