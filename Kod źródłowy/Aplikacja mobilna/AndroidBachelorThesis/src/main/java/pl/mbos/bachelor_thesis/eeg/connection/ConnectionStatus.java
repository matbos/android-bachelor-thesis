package pl.mbos.bachelor_thesis.eeg.connection;

/**
 * Created with IntelliJ IDEA.
 * User: Mateusz Boś
 * Date: 18.09.13
 * Time: 22:40
 */

import android.os.Parcel;
import android.os.Parcelable;
import com.neurosky.thinkgear.TGDevice;

/**
 * Enumeration used to indicate state of the connection
 */
public enum ConnectionStatus implements Parcelable {
    STATE_IDLE(TGDevice.STATE_IDLE),
    STATE_CONNECTING(TGDevice.STATE_CONNECTING),
    STATE_CONNECTED(TGDevice.STATE_CONNECTED),
    STATE_DISCONNECTED(TGDevice.STATE_DISCONNECTED),
    STATE_NOT_FOUND(TGDevice.STATE_NOT_FOUND),
    STATE_NOT_PAIRED(TGDevice.STATE_NOT_PAIRED);
    /**
     * CREATOR property  used in (de)parcelation processs
     */
    public static Parcelable.Creator<ConnectionStatus> CREATOR = new Parcelable.Creator<ConnectionStatus>() {
        @Override
        public ConnectionStatus createFromParcel(Parcel source) {
            return getFromParcel(source);
        }

        @Override
        public ConnectionStatus[] newArray(int size) {
            return new ConnectionStatus[size];
        }
    };
    private int value;

    private ConnectionStatus(int value) {
        this.value = value;
    }

    /**
     * Returns enum based on content of Parcel containing just one int value.
     *
     * @param source Parcel containing only one int
     * @return apropriate enum
     * @throws IllegalConnectionStateException
     *          when connection state from parcel is incorrect
     */
    private static ConnectionStatus getFromParcel(Parcel source) throws IllegalConnectionStateException {
        switch (source.readInt()) {
            case TGDevice.STATE_IDLE:
                return STATE_IDLE;
            case TGDevice.STATE_CONNECTING:
                return STATE_CONNECTING;
            case TGDevice.STATE_CONNECTED:
                return STATE_CONNECTED;
            case TGDevice.STATE_DISCONNECTED:
                return STATE_DISCONNECTED;
            case TGDevice.STATE_NOT_FOUND:
                return STATE_NOT_FOUND;
            case TGDevice.STATE_NOT_PAIRED:
                return STATE_NOT_PAIRED;
            default:
                throw new IllegalConnectionStateException("State read from parcel is " + source.readInt());
        }
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
        dest.writeInt(value);
    }

}
