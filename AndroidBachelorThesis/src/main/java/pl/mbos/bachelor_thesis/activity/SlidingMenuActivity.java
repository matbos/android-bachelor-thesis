package pl.mbos.bachelor_thesis.activity;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;

import javax.inject.Inject;

import pl.mbos.bachelor_thesis.BaseApplication;
import pl.mbos.bachelor_thesis.R;
import pl.mbos.bachelor_thesis.controller.SlidingMenuBaseController;
import pl.mbos.bachelor_thesis.menu.Adapter;
import pl.mbos.bachelor_thesis.menu.Item;

/**
 * Created by Mateusz on 08.12.13.
 */
public class SlidingMenuActivity extends Activity {

    private SlidingMenu slidingMenu;
    @Inject
    Adapter slidingMenuAdapter;
    protected ActionBar actionBar;
    private boolean backPressed = false;
    protected MenuItem headsetItem;
    protected MenuItem logoutItem;
    protected SlidingMenuBaseController controller;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        BaseApplication.getBaseGraph().inject(this);
        configureMenu();
        actionBar = getActionBar();
    }

    /**
     * This method HAS TO BE invoked to ensure proper functioning of action bar buttons.
     *
     * @param controller
     */
    protected void configureBaseController(SlidingMenuBaseController controller) {
        this.controller = controller;
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.action_bar_menu, menu);
        headsetItem = menu.findItem(R.id.headsetButton);
        headsetItem.setVisible(false);
        headsetItem.setEnabled(true);
        logoutItem = menu.findItem(R.id.logout);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.logout) {
            controller.logout();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (slidingMenu.isMenuShowing()) {
            slidingMenu.toggle();
        }
    }

    @Override
    public void onBackPressed() {
        if (slidingMenu.isMenuShowing()) {
            slidingMenu.toggle();
        } else {
            if (isTaskRoot()) {
                if (!backPressed) {
                    backPressed = true;
                    Toast.makeText(this, "Tap back once again to exit", Toast.LENGTH_SHORT).show();
                } else {
                    backPressed = false;
                    super.onBackPressed();
                }
            } else {
                super.onBackPressed();
            }
        }
    }

    protected void disableSlidingMenu() {
        slidingMenu.setSlidingEnabled(false);
    }

    protected void hideLogoutButton() {
        logoutItem.setVisible(false);
    }

    protected void hideHeadsetIcon() {
        headsetItem.setVisible(false);
    }

    private void configureMenu() {
        slidingMenuAdapter = new Adapter();
        slidingMenu = new SlidingMenu(this);
        slidingMenu.setMode(SlidingMenu.LEFT);
        slidingMenu.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
        slidingMenu.setShadowDrawable(R.drawable.menu_shadow);
        slidingMenu.setShadowWidth(getResources().getDimensionPixelSize(R.dimen.slidingMenuShadowWidth));
        slidingMenu.setBehindOffset(getResources().getDimensionPixelSize(R.dimen.slidingMenuBehindOffset));
        slidingMenu.setFadeDegree(0.8f);
        slidingMenu.attachToActivity(this, SlidingMenu.SLIDING_WINDOW);
        slidingMenu.setMenu(R.layout.sl_menu);
        ListView container = (ListView) slidingMenu.findViewById(R.id.sl_container);
        container.setAdapter(slidingMenuAdapter);
        container.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (slidingMenuAdapter.isActive(position)) {
                    Item item = (Item) slidingMenuAdapter.getItem(position);
                    slidingMenuAdapter.setActivePosition(position);
                    SlidingMenuActivity.this.startActivity(new Intent(SlidingMenuActivity.this, item.getActivityClass()));
                }
            }
        });
    }
}
