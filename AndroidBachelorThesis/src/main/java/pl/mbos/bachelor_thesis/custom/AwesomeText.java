package pl.mbos.bachelor_thesis.custom;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;

import javax.inject.Inject;

import pl.mbos.bachelor_thesis.BaseApplication;
import pl.mbos.bachelor_thesis.R;
import pl.mbos.bachelor_thesis.font.Awesome;

/**
 * Created by Mateusz on 05.12.13.
 */
public class AwesomeText extends TextView {
    public static Typeface fontAwesome;
    @Inject
    Resources res;

    public AwesomeText(Context context) {
        super(context);
        BaseApplication.getBaseGraph().inject(this);
        init(context);
    }

    public AwesomeText(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public AwesomeText(Context context, AttributeSet attrs, int defStyle) {
        this(context, attrs);
    }

    private void init(Context context) {
        res = context.getApplicationContext().getResources();
        if (!isInEditMode()) {
            setTypeface(Awesome.getFont());
        }
        setTextColor(res.getColor(R.color.waterEnds));
    }
}
