package pl.mbos.bachelor_thesis.font;

import android.content.Context;
import android.graphics.Typeface;

import javax.inject.Inject;

import dagger.ObjectGraph;
import pl.mbos.bachelor_thesis.di.StaticModule;

/**
 * Created by Mateusz on 06.12.13.
 */
public class Awesome {

    public static String USER = Character.toString((char)0xf001);
    public static String CHECK = Character.toString((char)0xf00c);
    public static String CROSS= Character.toString((char)0xf00d);
    public static String SETTINGS = Character.toString((char)0xf013);
    public static String HOME = Character.toString((char)0xf015);
    public static String SIGNAL = Character.toString((char)0xf012);
    public static String REPEAT = Character.toString((char)0xf01e);
    public static String DOWNLOAD = Character.toString((char)0xf019);
    public static String REFRESH = Character.toString((char)0xf021);
    public static String HEADPHONES = Character.toString((char)0xf025);
    public static String PAUSE = Character.toString((char)0xf04c);
    public static String CHEVRON_LEFT = Character.toString((char)0xf053);
    public static String CHEVRON_RIGHT = Character.toString((char)0xf054);
    public static String STOP = Character.toString((char)0xf04d);
    public static String PLAY = Character.toString((char)0xf04b);
    public static String MINUS_CIRCLE = Character.toString((char)0xf056);
    public static String CHECK_CIRCLE = Character.toString((char)0xf058);
    public static String BAN = Character.toString((char)0xf05e);
    public static String ARROW_RIGHT = Character.toString((char)0xf061);
    public static String ARROW_LEFT = Character.toString((char)0xf060);
    public static String ARROW_UP = Character.toString((char)0xf062);
    public static String ARROW_DOWN = Character.toString((char)0xf063);
    public static String SHARE = Character.toString((char)0xf064);
    public static String EXCLAMATION_CIRCLE = Character.toString((char)0xf06a);
    public static String MALE = Character.toString((char)0xf183);
    public static String FEMALE = Character.toString((char)0xf182);
    public static String BUG = Character.toString((char)0xf188);
    public static String SEARCH = Character.toString((char)0xf002);
    public static String MUSIC = Character.toString((char)0xf001);
    public static String HEART = Character.toString((char)0xf004);
    public static String STAR = Character.toString((char)0xf006);
    public static String SIGN_OUT = Character.toString((char)0xf08b);

    @Inject
    static Context ctx;

    private static Typeface font;

    public static Typeface getFont() {
        if (font == null) {
            initStatics();
            font = Typeface.createFromAsset(ctx.getAssets(), "fontawesome-webfont.ttf");
        }
        return font;
    }

    private static void initStatics(){
        ObjectGraph graph = ObjectGraph.create(new StaticModule());
        graph.injectStatics();
    }

}
