package pl.mbos.bachelor_thesis.service.data.services;

/**
 * Created by Mateusz on 01.12.13.
 */
public class DataSyncPackage {

    private SyncTimes requested;
    private SyncTimes given;
    private DataPackage data;

    public DataSyncPackage(SyncTimes requested){
        this.requested = requested;
    }

    public DataSyncPackage(SyncTimes given, DataPackage data){
        this.given = given;
        this.data = data;
    }

    public DataPackage getData() {
        return data;
    }

    public void setData(DataPackage data) {
        this.data = data;
    }

    public SyncTimes getGiven() {
        return given;
    }

    public void setGiven(SyncTimes given) {
        this.given = given;
    }

    public SyncTimes getRequested() {
        return requested;
    }

    public void setRequested(SyncTimes requested) {
        this.requested = requested;
    }




}
