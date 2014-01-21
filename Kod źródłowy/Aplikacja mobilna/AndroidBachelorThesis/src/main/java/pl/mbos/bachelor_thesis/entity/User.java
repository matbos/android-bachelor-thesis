package pl.mbos.bachelor_thesis.entity;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created with IntelliJ IDEA.
 * User: Mateusz Boś
 * Date: 29.09.13
 * Time: 22:49
 */
@Deprecated
public class User implements Parcelable {
    public static String USER_KEY = User.class.getCanonicalName() + ".USER_KEY";
    public static Creator<User> CREATOR = new Creator<User>() {
        @Override
        public User createFromParcel(Parcel parcel) {
            return new User(parcel.readInt(), parcel.readString(), parcel.readString());
        }

        @Override
        public User[] newArray(int i) {
            return new User[i];
        }
    };
    private Integer id;
    private String name;
    private String lastName;

    public User(Integer id, String name, String lastName) {
        this.id = id;
        this.name = name;
        this.lastName = lastName;
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getLastName() {
        return lastName;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(id);
        parcel.writeString(name);
        parcel.writeString(lastName);
    }
}
