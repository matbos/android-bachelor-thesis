package pl.mbos.bachelor_thesis.service.data.connector;

import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.Messenger;
import android.util.Log;

import javax.inject.Inject;

import pl.mbos.bachelor_thesis.BaseApplication;
import pl.mbos.bachelor_thesis.service.data.IPCConnector;
import pl.mbos.bachelor_thesis.service.data.MainService;

/**
 * Base class for clients connecting to {@link pl.mbos.bachelor_thesis.service.data.MainService}
 */
public class BaseServiceClient implements ServiceConnection {

    @Inject
    protected Context context;
    protected String SERVICE_NAME = "pl.mbos.bachelor_thesis.service.data.MainService";
    private boolean mServiceUp = false;

    public BaseServiceClient() {
        BaseApplication.getBaseGraph().inject(this);
    }

    /**
     * Starts {@link pl.mbos.bachelor_thesis.service.data.MainService} if it is not already running
     *
     * @param type      type of communicates that will be send using this client
     * @param messenger return messenger that will be used for back communication
     */
    protected boolean startService(int type, Messenger messenger) {
        boolean didStart = false;
        if (!mServiceUp) {
            Intent intent = new Intent(context, MainService.class);
            intent.putExtra(IPCConnector.MESSENGER, messenger);
            intent.putExtra(IPCConnector.TYPE, type);
            Log.d(BaseServiceClient.class.getSimpleName(), "Starting service with type: " + type + " and messenger: " + messenger);
            ComponentName serviceName = context.startService(intent);
            mServiceUp = true;
            didStart = (serviceName != null) ? true : false;
        }
        return didStart;
    }

    /**
     * Connects to service
     *
     * @param type      type of communicates that will be send using this client
     * @param messenger return messenger that will be used for back communication
     */
    protected void connectToService(int type, Messenger messenger) {
        startService(type, messenger);
        bindToService();
    }

    private void bindToService() {
        Intent intent = new Intent(context, MainService.class);
        context.bindService(intent, this, Context.BIND_ABOVE_CLIENT);
    }

    /**
     * Disconnects from service
     */
    protected void disconnectFromService() {
        context.unbindService(this);
    }

    /**
     * Checks if {@link pl.mbos.bachelor_thesis.service.data.MainService} is running
     *
     * @return Returns true is {@link pl.mbos.bachelor_thesis.service.data.MainService} is running, false otherwise
     */
    protected boolean isMyServiceRunning() {
        ActivityManager manager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
            if (SERVICE_NAME.equals(service.service.getClassName())) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void onServiceConnected(ComponentName name, IBinder service) {
        throw new UnsupportedOperationException("This method should not have been called!");
    }

    @Override
    public void onServiceDisconnected(ComponentName name) {

    }
}
