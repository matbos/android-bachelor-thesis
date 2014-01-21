package pl.mbos.bachelor_thesis.entity;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: Mateusz Boś
 * Date: 16.09.13
 * Time: 22:02
 */
public class DataEntity implements Parcelable {

    public static Creator<DataEntity> CREATOR = new Creator<DataEntity>() {
        @Override
        public DataEntity createFromParcel(Parcel source) {
            return new DataEntity(source);
        }

        @Override
        public DataEntity[] newArray(int size) {
            return new DataEntity[size];
        }
    };

    private int meditation;
    private int attention;
    private int blink;
    private Date timestamp;

    /**
     * Creates object from parcel
     * @param parcel parcel to create an object from
     */
    public DataEntity(Parcel parcel){
        readFromParcel(parcel);
    }
    /**
     * Creates an object and populates it with given data
     * @param meditation meditation level
     * @param attention attention level
     * @param blink blink level
     */
    public DataEntity(int meditation, int attention, int blink){
        this.meditation = meditation;
        this.attention = attention;
        this.blink = blink;
    }
    /**
     * Creates an object and populates it with given data
     * @param meditation meditation level
     * @param attention attention level
     * @param blink blink level
     * @param timestamp acquisition timestamp
     */
    public DataEntity(int meditation, int attention,int blink, Date timestamp){
        this(meditation, attention, blink);
        this.timestamp = timestamp;
    }

    /**
     * Describe the kinds of special objects contained in this Parcelable's
     * marshalled representation.
     *
     * @return a bitmask indicating the set of special object types marshalled
     *         by the Parcelable.
     */
    @Override
    public int describeContents() {
        return 0;
    }

    /**
     * Flatten this object in to a Parcel.
     *
     * @param dest  The Parcel in which the object should be written.
     * @param flags Additional flags about how the object should be written.
     *              May be 0 or {@link #PARCELABLE_WRITE_RETURN_VALUE}.
     */
    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(meditation);
        dest.writeInt(attention);
        dest.writeInt(blink);
        dest.writeLong(timestamp.getTime());
    }

    /**
     * Fills object with data read from parcel
     * @param parcel parcel to read data from
     */
    public void readFromParcel(Parcel parcel){
        meditation = parcel.readInt();
        attention = parcel.readInt();
        blink = parcel.readInt();
        timestamp = new Date(parcel.readLong());
    }
}
