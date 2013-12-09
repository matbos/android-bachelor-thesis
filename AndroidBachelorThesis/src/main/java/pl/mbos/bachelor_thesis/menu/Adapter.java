package pl.mbos.bachelor_thesis.menu;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import pl.mbos.bachelor_thesis.BaseApplication;
import pl.mbos.bachelor_thesis.R;
import pl.mbos.bachelor_thesis.activity.LoginActivity;
import pl.mbos.bachelor_thesis.activity.ProfileActivity;
import pl.mbos.bachelor_thesis.activity.SettingsActivity;
import pl.mbos.bachelor_thesis.custom.AwesomeText;
import pl.mbos.bachelor_thesis.font.Awesome;

/**
 * Created by Mateusz on 06.12.13.
 */
public class Adapter extends BaseAdapter {

    private static final boolean DO_NOT_ATTACH = false;
    List<Item> list = new ArrayList<Item>(6);
    static LayoutInflater inflater;
    static int activePosition = 1;

    @Inject
    Context ctx;

    public Adapter() {
        BaseApplication.getBaseGraph().inject(this);
        inflater = (LayoutInflater) ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        configureAdapter();
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    public boolean isActive(int position){
        return position != activePosition;
    }

    @Override
    public long getItemId(int position) {
        return Long.MIN_VALUE;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.sl_item, parent, DO_NOT_ATTACH);

            holder = new ViewHolder(convertView);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        Item item = list.get(position);
        holder.icon.setText(item.getIcon());
        holder.text.setText(item.getText());
        if(activePosition == position){
            holder.text.setTextColor(ctx.getResources().getColor(R.color.hopeLost));
        }
        return convertView;
    }

    public void configureAdapter() {
        list.add(new Item(Awesome.USER, "user", ProfileActivity.class));
        list.add(new Item(Awesome.SETTINGS, "settings", SettingsActivity.class));
    }

    static class ViewHolder {
        AwesomeText icon;
        TextView text;

        public ViewHolder(View v) {
            icon = (AwesomeText) v.findViewById(R.id.at_icon);
            text = (TextView) v.findViewById(R.id.tv_text);
            v.setTag(this);
        }
    }

    public void setActivePosition(int position){
        activePosition = position;
    }

}
