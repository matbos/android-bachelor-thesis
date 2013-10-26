package pl.mbos.bachelor_thesis.service.data.contract;

import android.os.Message;
import android.os.Messenger;

public interface IOutboundCommunication {

    void addListener(Messenger messenger);
    void removeListener(Messenger messenger);
    void send(Message message);
}
