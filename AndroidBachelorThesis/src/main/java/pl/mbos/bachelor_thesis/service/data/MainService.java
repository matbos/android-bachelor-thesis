package pl.mbos.bachelor_thesis.service.data;

import android.app.Service;
import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.util.Log;

import java.util.ArrayList;

import javax.inject.Singleton;

import pl.mbos.bachelor_thesis.BaseApplication;
import pl.mbos.bachelor_thesis.dao.Attention;
import pl.mbos.bachelor_thesis.dao.Blink;
import pl.mbos.bachelor_thesis.dao.Meditation;
import pl.mbos.bachelor_thesis.dao.PoorSignal;
import pl.mbos.bachelor_thesis.dao.PowerEEG;
import pl.mbos.bachelor_thesis.dao.User;
import pl.mbos.bachelor_thesis.service.data.services.AuthorizationService;
import pl.mbos.bachelor_thesis.service.data.services.CommandService;
import pl.mbos.bachelor_thesis.service.data.services.DataService;
import pl.mbos.bachelor_thesis.service.data.services.IAuthorizationService;
import pl.mbos.bachelor_thesis.service.data.services.IAuthorizationServiceParent;
import pl.mbos.bachelor_thesis.service.data.services.ICommandService;
import pl.mbos.bachelor_thesis.service.data.services.IDataService;
import pl.mbos.bachelor_thesis.service.data.services.IDataServiceParent;

/**
 * Service responsible for data persistence and synchronization between main server and device.
 */
@Singleton
public class MainService extends Service implements IAuthorizationServiceParent, IDataServiceParent {

    private static final String TAG = MainService.class.getSimpleName();
    private Messenger messenger;
    private IPCConnector mainConnector;
    private ICommandService commandService;
    private IAuthorizationService authorizationService;
    private IDataService dataService;
    private static int dupa = 0;

    @Override
    public IBinder onBind(Intent intent) {
        return messenger.getBinder();
    }

    @Override
    public void onCreate() {
        super.onCreate();
        initCommunication();
        initServices();
    }

    private void initCommunication(){
        mainConnector = new IPCConnector(this);
        messenger = new Messenger(mainConnector.inbound);
    }

    private void initServices(){
        dataService = new DataService(this);
        authorizationService = new AuthorizationService(this);
        //TODO Wyprostować ten cast!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
        commandService = new CommandService((DataService) dataService);
    }

    protected void authMessage(Message message) {
        if (message.arg2 != IPCConnector.AUTH_AUTHENTICATE) {
            //TODO jakis exception?
            Log.d(TAG, "Bad argument for authService " + message.arg2);
        } else {
            try {
                Bundle bundle = message.getData();
                bundle.setClassLoader(User.class.getClassLoader());
                authorizationService.authorizeUser((User) bundle.getParcelable(User.USER_KEY));
            } catch (NullPointerException e) {
                authorizationOutcome(new User(), false, "User passed to authorization was null");
            }
        }
    }

    protected void cmdMessage(Message message) {
        switch (message.arg2) {
            case IPCConnector.CMD_ALLOW_SYNC:
                commandService.setSynchronizationPermission(true);
                break;
            case IPCConnector.CMD_DENY_SYNC:
                commandService.setSynchronizationPermission(false);
                break;
            case IPCConnector.CMD_REPORT_RUNNING:
                mainConnector.reportRunning(commandService.reportRunning());
                break;
            case IPCConnector.CMD_REPORT_STATE:
                mainConnector.reportState(commandService.reportState());
                break;
            case IPCConnector.CMD_SYNCHRONIZE:
                commandService.synchronizeNow();
                break;
            default:
                Log.d(TAG,"Bad argument for cmdService " + message.arg2);
                break;
        }
    }

    protected void dataMessage(Message message) {
        switch(message.arg2){
            case IPCConnector.DATA_REQ_ATTENTION:
                mainConnector.sendAttentionData(dataService.getAllAttentionData());
                break;
            case IPCConnector.DATA_REQ_MEDITATION:
                mainConnector.sendMeditationData(dataService.getAllMeditationData());
                break;
            case IPCConnector.DATA_REQ_BLINK:
                mainConnector.sendBlinkData(dataService.getAllBlinkData());
                break;
            case IPCConnector.DATA_REQ_POWER:
                mainConnector.sendPowerData(dataService.getAllPowerData());
                break;
            case IPCConnector.DATA_REQ_POOR_SIGNAL:
                mainConnector.sendPoorSignalData(dataService.getAllPoorSignalData());
                break;
            default:
                Log.d(TAG,"Bad argument for dataService " + message.arg2);
                break;
        }
    }

    @Override
    public void authorizationOutcome(User user, boolean success, String reason) {
        if(success){
            mainConnector.userAuthorized(user);
        } else {
            mainConnector.userUnauthorized(user, reason);
        }
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        int result = super.onStartCommand(intent, flags, startId);
        Messenger returnMessenger = intent.getParcelableExtra(IPCConnector.MESSENGER);
        int type = intent.getIntExtra(IPCConnector.TYPE, 0);
        mainConnector.addListener(type,returnMessenger);
        return result;
    }
}

