package pl.mbos.bachelor_thesis.dao.helper;

import android.content.SharedPreferences;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import pl.mbos.bachelor_thesis.BaseApplication;
import pl.mbos.bachelor_thesis.dao.User;

/**
 * Created by Mateusz on 10.12.13.
 */
public class UserHelper {

    public static void persistUserToSO(User user){
        SharedPreferences.Editor editor = BaseApplication.getUserPreferences().edit();
        editor.putString("USER_IN_JSON",user.toJSON());
        editor.commit();
    }

    public static void removeUserFromSO(){
        SharedPreferences.Editor editor = BaseApplication.getUserPreferences().edit();
        editor.remove("USER_IN_JSON");
        editor.commit();
    }

    public static User retrieveUserFromSO(){
        SharedPreferences prefs = BaseApplication.getUserPreferences();
        String userInJson = prefs.getString("USER_IN_JSON", null);
        if(userInJson == null){
            return null;
        } else {
            try {
                User user = User.parseJSON(new JSONObject(userInJson));
                return user;
            } catch (JSONException e) {
                Log.e("Retrieve User from SO", e.getMessage());
                return null;
            }
        }
    }
}
