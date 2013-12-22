package pl.mbos.bachelor_thesis.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;

import butterknife.InjectView;
import butterknife.OnClick;
import butterknife.Views;
import pl.mbos.bachelor_thesis.R;
import pl.mbos.bachelor_thesis.chart.DataSupplier;
import pl.mbos.bachelor_thesis.controller.LoginController;
import pl.mbos.bachelor_thesis.controller.WebAddressTextWatcher;
import pl.mbos.bachelor_thesis.custom.AwesomeText;
import pl.mbos.bachelor_thesis.dao.User;
import pl.mbos.bachelor_thesis.font.Awesome;
import pl.mbos.bachelor_thesis.view.LoginView;
import zeitdata.charts.view.LineChart;

/**
 * Created with IntelliJ IDEA.
 * User: Mateusz Boś
 * Date: 15.09.13
 * Time: 01:14
 */
public class ChartsActivity extends SlidingMenuActivity {

    @InjectView(R.id.lineChart)
    LineChart chart;

    DataSupplier dataSupplier = new DataSupplier();

    /**
     * Method used to build an intent that can start this activity
     *
     * @param parent calling activity
     * @return An {@link android.content.Intent}
     */
    public static Intent getIntentToStartThisActivity(Activity parent) {
        Intent intent = new Intent(parent, ChartsActivity.class);
        intent.putExtra("clearData", false);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_charts);
        super.onCreate(savedInstanceState);
        Views.inject(this);
        chart.setData(dataSupplier.getAttentionData());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        boolean outcome = super.onCreateOptionsMenu(menu);
        if(outcome){
            hideLogoutButton();
        }
        return outcome;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

}