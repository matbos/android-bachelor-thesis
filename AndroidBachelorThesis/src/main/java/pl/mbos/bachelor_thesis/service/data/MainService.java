package pl.mbos.bachelor_thesis.service.data;

import android.app.Service;
import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.util.Log;

import javax.inject.Singleton;

import pl.mbos.bachelor_thesis.BaseApplication;
import pl.mbos.bachelor_thesis.R;
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
    private String endpointAddress;

    @Override
    public IBinder onBind(Intent intent) {
        return messenger.getBinder();
    }

    @Override
    public void onCreate() {
        super.onCreate();
        endpointAddress = BaseApplication.getContext().getResources().getString(R.string.webservice_base);
        initCommunication();
        initServices();
    }

    private void initCommunication() {
        mainConnector = new IPCConnector(this);
        messenger = new Messenger(mainConnector.inbound);
    }

    private void initServices() {
        dataService = new DataService(this);
        authorizationService = new AuthorizationService(this, endpointAddress);
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
            case IPCConnector.CMD_SYNC_WIFI_ONLY:
                commandService.synchronizationMedium(true);
                break;
            case IPCConnector.CMD_SYNC_ALWAYS:
                commandService.synchronizationMedium(false);
                break;
            case IPCConnector.CMD_REPORT_RUNNING:
                mainConnector.reportRunning(commandService.reportRunning());
                break;
            case IPCConnector.CMD_REPORT_STATE:
                mainConnector.reportState(commandService.reportState());
                break;
            case IPCConnector.CMD_REPORT_ALLOWANCE:
                mainConnector.reportAllowance(commandService.reportAllowance());
                break;
            case IPCConnector.CMD_SYNCHRONIZE:
                commandService.synchronizeNow();
                break;
            case IPCConnector.UNIV_ADDRESS_CHANGED:
                endpointAddress = message.getData().getString(IPCConnector.UNIV_ENDPOINT_ADDRESS);
                authorizationService.changeAddress(endpointAddress);
                commandService.recreateAddresses(endpointAddress);
            default:
                Log.d(TAG, "Bad argument for cmdService " + message.arg2);
                break;
        }
    }

    protected void dataMessage(Message message) {
        switch (message.arg2) {
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
            case IPCConnector.DATA_ADD_ATTENTION:
                dataService.addAttentionData(extractAttention(message));
                break;
            case IPCConnector.DATA_ADD_MEDITATION:
                dataService.addMeditationData(extractMeditation(message));
                break;
            case IPCConnector.DATA_ADD_BLINK:
                dataService.addBlinkData(extractBlink(message));
                break;
            case IPCConnector.DATA_ADD_POWER:
                dataService.addPowerData(extractPower(message));
                break;
            case IPCConnector.DATA_ADD_POOR_SIGNAL:
                dataService.addPoorSignalData(extractPoorSignal(message));
                break;
            default:
                Log.d(TAG, "Bad argument for dataService " + message.arg2);
                break;
        }
    }

    @Override
    public void authorizationOutcome(User user, boolean success, String reason) {
        if (success) {
            mainConnector.userAuthorized(user);
        } else {
            if(reason.equalsIgnoreCase("null")){
                reason = "Authorization server could not be reached.";
            }
            mainConnector.userUnauthorized(user, reason);
        }
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        int result = super.onStartCommand(intent, flags, startId);
        Messenger returnMessenger = intent.getParcelableExtra(IPCConnector.MESSENGER);
        int type = intent.getIntExtra(IPCConnector.TYPE, 0);
        mainConnector.addListener(type, returnMessenger);
        return result;
    }

    private Attention extractAttention(Message msg) {
        Bundle bundle = msg.getData();
        if(bundle != null){
        bundle.setClassLoader(Attention.class.getClassLoader());
        Attention data = bundle.getParcelable(IPCConnector.DATA_DATA);
        return data;
        } else {
            Log.i(TAG,"Attention has not been passed with bundle! ");
            return null;
        }
    }

    private PoorSignal extractPoorSignal(Message message) {
        Bundle bundle = message.getData();
        if(bundle != null){
            bundle.setClassLoader(PoorSignal.class.getClassLoader());
            PoorSignal data = bundle.getParcelable(IPCConnector.DATA_DATA);
            return data;
        } else {
            Log.i(TAG,"PoorSignal has not been passed with bundle! ");
            return null;
        }
    }

    private PowerEEG extractPower(Message message) {
        Bundle bundle = message.getData();
        if(bundle != null){
            bundle.setClassLoader(PowerEEG.class.getClassLoader());
            PowerEEG data = bundle.getParcelable(IPCConnector.DATA_DATA);
            return data;
        } else {
            Log.i(TAG,"PowerEEG has not been passed with bundle! ");
            return null;
        }
    }

    private Blink extractBlink(Message message) {
        Bundle bundle = message.getData();
        if(bundle != null){
            bundle.setClassLoader(Blink.class.getClassLoader());
            Blink data = bundle.getParcelable(IPCConnector.DATA_DATA);
            return data;
        } else {
            Log.i(TAG,"Blink has not been passed with bundle! ");
            return null;
        }
    }

    private Meditation extractMeditation(Message message) {
        Bundle bundle = message.getData();
        if(bundle != null){
            bundle.setClassLoader(Meditation.class.getClassLoader());
            Meditation data = bundle.getParcelable(IPCConnector.DATA_DATA);
            return data;
        } else {
            Log.i(TAG,"Meditation has not been passed with bundle! ");
            return null;
        }
    }

}



