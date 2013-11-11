package pl.mbos.bachelor_thesis.service.data.services;

import pl.mbos.bachelor_thesis.service.data.MainService;

/**
 * Service object that handles commands regarding functioning of DataService
 */
public class CommandService implements ICommandService {

    private boolean allowedSynchronization;
    private boolean synchronizing;
    private DataService dataService;

    public CommandService(DataService dataService){
        this.dataService = dataService;
    }

    @Override
    public void setSynchronizationPermission(boolean state) {

    }

    @Override
    public boolean reportState() {
        return allowedSynchronization;
    }

    @Override
    public boolean reportRunning() {
        return synchronizing;
    }

    @Override
    public void synchronizeNow() {

    }
}
