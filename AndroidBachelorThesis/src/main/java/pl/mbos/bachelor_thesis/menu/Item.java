package pl.mbos.bachelor_thesis.menu;

import android.content.Intent;

/**
 * Created by Mateusz on 06.12.13.
 */
public class Item {
    private final String icon;
    private final String text;
    private final Class activityClass;

    public Item(String icon, String text, Class activityClass){
        this.icon = icon;
        this.text = text;
        this.activityClass = activityClass;
    }

    public String getText() {
        return text;
    }

    public String getIcon() {
        return icon;
    }

    public Class getActivityClass(){
        return activityClass;
    }

}
