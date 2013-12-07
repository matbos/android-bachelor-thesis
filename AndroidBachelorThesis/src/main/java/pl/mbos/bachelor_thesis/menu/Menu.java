package pl.mbos.bachelor_thesis.menu;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;

import javax.inject.Inject;
import javax.inject.Singleton;

import pl.mbos.bachelor_thesis.BaseApplication;
import pl.mbos.bachelor_thesis.R;

/**
 * Created by Mateusz on 06.12.13.
 */
public class Menu {

    private Adapter adapter = new Adapter();
    private Activity activity = null;
    @Inject
    Resources res;
    public Menu(){
        BaseApplication.getBaseGraph().inject(this);
    }

    public void attachMenu(Activity activity){
        this.activity = activity;
        SlidingMenu menu = new SlidingMenu(activity);
        menu.setMode(SlidingMenu.LEFT);
        menu.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
        menu.setShadowWidth(30);
        menu.setShadowDrawable(R.color.losingHope);
        menu.setBehindOffset(res.getDimensionPixelSize(R.dimen.slidingMenuBehindOffset));
        menu.setFadeDegree(0.8f);
        menu.attachToActivity(activity, SlidingMenu.SLIDING_WINDOW);
        menu.setMenu(R.layout.sl_menu);
        configureMenu(menu);
    }

    private void configureMenu(SlidingMenu menu) {
        ListView container = (ListView) menu.findViewById(R.id.sl_container);
        container.setAdapter(adapter);
        container.setOnItemClickListener( new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Item item = (Item) adapter.getItem(position);
                activity.startActivity(new Intent(activity,item.getActivityClass()));
            }
        });
    }



}
