package pl.mbos.bachelor_thesis.service.data;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.Messenger;

import java.util.Timer;
import java.util.TimerTask;

import pl.mbos.bachelor_thesis.dao.User;
import pl.mbos.bachelor_thesis.service.data.handler.AuthorizationConnector;

/**
 * Service responsible for data persistence and synchronization between main server and device.
 */
public class DataService extends Service {

    protected Messenger messenger;
    protected IPCConnector mainConnector;
    protected AuthorizationConnector authConnector;

    /**
     * Return the communication channel to the service.  May return null if
     * clients can not bind to the service.  The returned
     * {@link android.os.IBinder} is usually for a complex interface
     * that has been <a href="{@docRoot}guide/components/aidl.html">described using
     * aidl</a>.
     * <p/>
     * <p><em>Note that unlike other application components, calls on to the
     * IBinder interface returned here may not happen on the main thread
     * of the process</em>.  More information about the main thread can be found in
     * <a href="{@docRoot}guide/topics/fundamentals/processes-and-threads.html">Processes and
     * Threads</a>.</p>
     *
     * @param intent The Intent that was used to bind to this service,
     *               as given to {@link android.content.Context#bindService
     *               Context.bindService}.  Note that any extras that were included with
     *               the Intent at that point will <em>not</em> be seen here.
     * @return Return an IBinder through which clients can call on to the
     *         service.
     */
    @Override
    public IBinder onBind(Intent intent) {
        Messenger returnMessenger = (Messenger) intent.getParcelableExtra(IPCConnector.MESSENGER);
        int type = intent.getIntExtra(IPCConnector.TYPE, 0);
        mainConnector.addListener(type, returnMessenger);
        return messenger.getBinder();
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mainConnector = new IPCConnector(this);
        messenger = new Messenger(mainConnector.inbound);
    }

    protected void authorizeUser(User user){
        if(user.getId() == 123 && user.getFirstName().equals("asd")){
            Timer timer = new Timer();
            timer.schedule(new AuthTask(user), 2000);
        } else {
            mainConnector.userUnauthorized(user,"bad login/password");
        }
    }

    class AuthTask extends TimerTask {
        private User user;

        public AuthTask(User user){
            this.user = user;
        }
        public void run() {
            mainConnector.userAuthorized(user);
        }
    }
}

