package pl.mbos.bachelor_thesis.service.data.handler;

import android.os.Bundle;
import android.os.Message;

import pl.mbos.bachelor_thesis.service.data.IPCConnector;
import pl.mbos.bachelor_thesis.service.data.OutboundCommunicationHandler;

/**
 * Created by Mateusz on 04.11.13.
 */
public class CommandConnector extends OutboundCommunicationHandler {


    public void reportSyncState(boolean synchronization){
        send(buildSyncStateMessage(synchronization));
    }

    public void reportSyncRunning(boolean running){
        send(buildSyncRunningMessage(running));
    }

    private Message buildSyncStateMessage(boolean state){
        Message msg = Message.obtain();
        msg.arg1 = IPCConnector.TYPE_COMMAND;
        msg.arg2 = IPCConnector.CMD_REPORT_STATE;
        Bundle bundle = new Bundle();
        //TODO fix magic string below
        bundle.putBoolean("SYNC_RUNNING",state);
        msg.setData(bundle);
        return msg;
    }

    private Message buildSyncRunningMessage(boolean running){
        Message msg = Message.obtain();
        msg.arg1 = IPCConnector.TYPE_COMMAND;
        msg.arg2 = IPCConnector.CMD_REPORT_RUNNING;
        Bundle bundle = new Bundle();
        //TODO fix magic string below
        bundle.putBoolean("SYNC_RUNNING",running);
        msg.setData(bundle);
        return msg;
    }

}
