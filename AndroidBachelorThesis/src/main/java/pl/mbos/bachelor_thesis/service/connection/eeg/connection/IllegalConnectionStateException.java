package pl.mbos.bachelor_thesis.service.connection.eeg.connection;

/**
 * Created with IntelliJ IDEA.
 * User: Mateusz Boś
 * Date: 22.09.13
 * Time: 14:47
 */

/**
 * Exception indicating illegal state of connection with EEG Device
 */
public class IllegalConnectionStateException extends RuntimeException {

    public IllegalConnectionStateException(){
        super();
    }

    public IllegalConnectionStateException(String message){
        super(message);
    }

    public IllegalConnectionStateException(String message, Throwable cause){
        super(message,cause);
    }

    public IllegalConnectionStateException(Throwable cause){
        super(cause);
    }
}
