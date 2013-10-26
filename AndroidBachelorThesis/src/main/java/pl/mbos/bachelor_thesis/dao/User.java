package pl.mbos.bachelor_thesis.dao;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT. Enable "keep" sections if you want to edit. 

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Entity mapped to table USER.
 */
public class User implements Parcelable {

    // KEEP FIELDS - put your custom fields here
    public static final String FIELD = User.class.getCanonicalName();
    public static final String USER_KEY = User.class.getCanonicalName() + ".USER_KEY";
    public static Creator<User> CREATOR = new Creator<User>() {
        @Override
        public User createFromParcel(Parcel parcel) {
            return new User(parcel.readLong(), parcel.readString(), parcel.readString());
        }

        @Override
        public User[] newArray(int i) {
            return new User[i];
        }
    };
    // KEEP FIELDS END
    private long id;
    /**
     * Not-null value.
     */
    private String firstName;
    /**
     * Not-null value.
     */
    private String lastName;

    public User() {
    }

    public User(long id) {
        this.id = id;
    }

    public User(long id, String firstName, String lastName) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    /**
     * Not-null value.
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * Not-null value; ensure this value is available before it is saved to the database.
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    /**
     * Not-null value.
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * Not-null value; ensure this value is available before it is saved to the database.
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    // KEEP METHODS - put your custom methods here
    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeLong(id);
        parcel.writeString(firstName);
        parcel.writeString(lastName);
    }
    // KEEP METHODS END

}
